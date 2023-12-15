package com.example.hospital.api.service;

import com.example.hospital.api.db.pojo.DoctorWorkPlanScheduleEntity;

import java.util.ArrayList;

/**
 * @author : wuxiao
 * @date : 15:24 2023-12-15
 */
public interface DoctorWorkPlanScheduleService {
    public void insert(ArrayList<DoctorWorkPlanScheduleEntity> list);
}
