package com.example.hospital.api.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.BiMap;
import cn.hutool.core.map.MapUtil;
import com.example.hospital.api.db.dao.DoctorWorkPlanDao;
import com.example.hospital.api.db.dao.DoctorWorkPlanScheduleDao;
import com.example.hospital.api.db.pojo.DoctorWorkPlanScheduleEntity;
import com.example.hospital.api.service.DoctorWorkPlanScheduleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 15:25 2023-12-15
 */
@Service
public class DoctorWorkPlanScheduleServiceImpl implements DoctorWorkPlanScheduleService {
        @Resource
        private DoctorWorkPlanScheduleDao doctorWorkPlanScheduleDao;
        @Resource
        private DoctorWorkPlanDao doctorWorkPlanDao;
        @Override
        public void insert(ArrayList<DoctorWorkPlanScheduleEntity> list) {
            insertScheduleHandle(list);

            //创建Redis缓存，避免挂号超售
            this.addScheduleCache(list);
        }
        @Transactional
        void insertScheduleHandle(ArrayList<DoctorWorkPlanScheduleEntity> list) {
            for (DoctorWorkPlanScheduleEntity entity : list) {
                doctorWorkPlanScheduleDao.insert(entity);
            }
        }

    @Resource
    private RedisTemplate redisTemplate;
    //hutool库提供的可以双向查找的Map
    private BiMap<String, Integer> range = new BiMap<>(new HashMap() {{
        put("08:00", 1);
        put("08:30", 2);
        put("09:00", 3);
        put("09:30", 4);
        put("10:00", 5);
        put("10:30", 6);
        put("11:00", 7);
        put("11:30", 8);
        put("13:00", 9);
        put( "13:30",10);
        put( "14:00",11);
        put("14:30", 12);
        put("15:00", 13);
        put("15:30", 14);
        put("16:00", 15);
    }});






    //封装创建缓存的过程，为当前类其他业务方法提供复用
    private void addScheduleCache(ArrayList<DoctorWorkPlanScheduleEntity> list) {
        //如果list中没有元素，就不需要创建缓存
        if (list == null || list.size() == 0) {
            return;
        }
        //一个出诊计划中插入到schedule数据表的时间段记录的外键值相同，取出列表第一个元素获得外键
        int workPlanId = list.get(0).getWorkPlanId();
        //查询数据库记录
        ArrayList<HashMap> newList = doctorWorkPlanScheduleDao.searchNewSchedule ( workPlanId);
        for (HashMap one : newList) {
            int id = MapUtil.getInt(one, "id");
            int slot = MapUtil.getInt(one, "slot");
            //定义缓存记录的Key
            String key = "doctor_schedule_" + id;

            //把出诊时间段缓存到Redis
            redisTemplate.opsForHash().putAll(key, one);
            //出诊日期
            String date = MapUtil.getStr(one, "date");
            //该时间段起始时间
            String time = range.getKey(slot);

            //设置缓存过期时间
            redisTemplate.expireAt(key, DateUtil.parse(date + " " + time));
        }
    }

    @Override
    public ArrayList searchDeptSubSchedule(Map param) {
        ArrayList<HashMap> list = doctorWorkPlanScheduleDao.searchDeptSubSchedule(param);
        ArrayList<HashMap> result = new ArrayList();
        int tempDoctorId = 0;
        HashMap doctor = new HashMap();

        for (HashMap map : list) {
            int doctorId = MapUtil.getInt(map, "doctorId");
            int slot = MapUtil.getInt(map, "slot");
            //如果当前记录跟上一条记录的医生不是同一个人
            if (tempDoctorId != doctorId) {
                tempDoctorId = doctorId;
                doctor = map;
                doctor.replace("slot", new ArrayList<Integer>() {{
                    add(slot);
                }});
                result.add(doctor);
            }
            //如果当前记录与上一条记录是同一个医生
            else if (tempDoctorId == doctorId) {
                ArrayList<Integer> slotList = (ArrayList) doctor.get("slot");
                slotList.add(slot);
            }
        }
        //筛选哪些时段出诊，哪些时段不出诊
        for (HashMap map : result) {
            ArrayList<Integer> slot = (ArrayList) map.get("slot");
            ArrayList tempSlot = new ArrayList();
            for (int i = 1; i <= 15; i++) {
                tempSlot.add(slot.contains(i));
            }
            map.replace("slot", tempSlot);
        }
        return result;
    }
}

