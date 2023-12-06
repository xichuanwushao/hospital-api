package com.example.hospital.api.service;

import com.example.hospital.api.common.PageUtils;
import com.example.hospital.api.db.pojo.MedicalDeptEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 14:20 2023-12-02
 */
public interface MedicalDeptService {
    public ArrayList<HashMap> searchAll();

    public HashMap searchDeptAndSub();

    public PageUtils searchByPage(Map param);

    public void insert(MedicalDeptEntity entity);


}
