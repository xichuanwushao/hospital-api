package com.example.hospital.api.service.impl;

import com.example.hospital.api.db.dao.DoctorWorkPlanDao;
import com.example.hospital.api.db.dao.DoctorWorkPlanScheduleDao;
import com.example.hospital.api.db.pojo.DoctorWorkPlanScheduleEntity;
import com.example.hospital.api.service.DoctorWorkPlanScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

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
            //TODO 设置Redis缓存，用于防止挂号超售
        }
        @Transactional
        void insertScheduleHandle(ArrayList<DoctorWorkPlanScheduleEntity> list) {
            for (DoctorWorkPlanScheduleEntity entity : list) {
                doctorWorkPlanScheduleDao.insert(entity);
            }
        }

}

