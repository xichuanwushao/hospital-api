package com.example.hospital.api.service;

import cn.hutool.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 15:37 2023-12-14
 */
public interface MedicalDeptSubWorkPlanService {
    public JSONArray searchWorkPlanInRange(Map param, ArrayList dateList);
}
