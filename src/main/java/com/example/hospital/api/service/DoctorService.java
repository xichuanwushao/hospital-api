package com.example.hospital.api.service;

import com.example.hospital.api.common.PageUtils;

import java.util.Map;

/**
 * @author : wuxiao
 * @date : 14:36 2023-11-29
 */
public interface DoctorService {
    public PageUtils searchByPage(Map param);
}
