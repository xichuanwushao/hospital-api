package com.example.hospital.api.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import com.example.hospital.api.common.PageUtils;
import com.example.hospital.api.db.dao.DoctorWorkPlanDao;
import com.example.hospital.api.db.dao.MedicalDeptSubDao;
import com.example.hospital.api.db.pojo.MedicalDeptSubEntity;
import com.example.hospital.api.exception.HospitalException;
import com.example.hospital.api.service.MedicalDeptSubService;
import com.example.hospital.api.service.MedicalDeptSubWorkPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 16:01 2023-12-07
 */
@Service
public class MedicalDeptSubServiceImpl implements MedicalDeptSubService {


    @Resource
    private MedicalDeptSubDao medicalDeptSubDao;

    private DoctorWorkPlanDao doctorWorkPlanDao;

    @Override
    public PageUtils searchByPage(Map param) {
        ArrayList<HashMap> list = null;
        long count = medicalDeptSubDao.searchCount(param);
        if (count > 0) {
            list = medicalDeptSubDao.searchByPage(param);
        } else {
            list = new ArrayList<>();
        }
        int page = MapUtil.getInt(param, "page");
        int length = MapUtil.getInt(param, "length");
        PageUtils pageUtils = new PageUtils(list, count, page, length);
        return pageUtils;
    }


    @Override
    @Transactional
    public void insert(MedicalDeptSubEntity entity) {
        medicalDeptSubDao.insert(entity);
    }

    @Override
    public HashMap searchById(int id) {
        HashMap map = medicalDeptSubDao.searchById(id);
        return map;
    }
    @Override
    @Transactional
    public void update(MedicalDeptSubEntity entity) {
        medicalDeptSubDao.update(entity);
    }
    @Override
    @Transactional
    public void deleteByIds(Integer[] ids) {
        long count = medicalDeptSubDao.searchDoctorCount(ids);
        if (count == 0) {
            medicalDeptSubDao.deleteByIds(ids);
        } else {
            throw new HospitalException("诊室存在关联医生，无法删除记录");
        }
    }



    public JSONArray searchWorkPlanInRange(Map param, ArrayList dateList) {
        ArrayList<HashMap> list = doctorWorkPlanDao.searchWorkPlanInRange(param);
        Integer tempSubId = null;
        String tempDate = null;
        HashMap tempResult = new HashMap();
        for (HashMap one : list) {
            String deptName = MapUtil.getStr(one, "deptName");
            int deptSubId = MapUtil.getInt(one, "deptSubId");
            String deptSubName = MapUtil.getStr(one, "deptSubName");
            String doctorName = MapUtil.getStr(one, "doctorName");
            int workPlanId = MapUtil.getInt(one, "workPlanId");
            String date = MapUtil.getStr(one, "date");

            //判断是不是第一条记录
            if (tempSubId == null) {
                tempSubId = deptSubId;
                tempDate = date;
                //把第一条记录当做一个诊室
                HashMap temp = new HashMap() {{
                    put("deptName", deptName);
                    put("deptSubId", deptSubId);
                    put("deptSubName", deptSubName);
                    /*
                     * 该诊室出诊计划
                     * 为了保证添加的顺序不被打乱，必须用LinkedHashMap，不可以是HashMap
                     */
                    put("plan", new LinkedHashMap<>() {{
                        put(date, new ArrayList<>() {{
                            add(doctorName);
                        }});
                    }});
                }};
                tempResult.put(deptSubId, temp);
            }
            //非第一条记录，但是该记录与前一条记录是相同诊室，而且是同一天出诊
            else if (tempSubId == deptSubId && tempDate.equals(date)) {
                //取出该诊室
                HashMap map = (HashMap) tempResult.get(deptSubId);
                //从诊室中取出出诊计划
                LinkedHashMap plan = (LinkedHashMap) map.get("plan");
                //找到该天出诊医生名单列表
                ArrayList doctors = (ArrayList) plan.get(date);
                //把医生名字添加到列表中
                doctors.add(doctorName);
            }
        //非第一条记录，但是该记录与前一条记录是相同诊室，但不是同一天出诊
            else if (tempSubId == deptSubId && !tempDate.equals(date)){
                tempDate = date; //更新日期
                //取出该诊室
                HashMap map = (HashMap) tempResult.get(deptSubId);
                //从诊室中取出出诊计划
                LinkedHashMap plan = (LinkedHashMap) map.get("plan");
                //创建新的出诊日期列表，添加该医生的名字
                plan.put(date, new ArrayList<>() {{
                    add(doctorName);
                }});
            }

        }

        return null;
    }
}
