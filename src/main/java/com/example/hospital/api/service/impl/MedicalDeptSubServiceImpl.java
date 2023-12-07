package com.example.hospital.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.example.hospital.api.common.PageUtils;
import com.example.hospital.api.db.dao.MedicalDeptSubDao;
import com.example.hospital.api.db.pojo.MedicalDeptSubEntity;
import com.example.hospital.api.service.MedicalDeptSubService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 16:01 2023-12-07
 */
@Service
public class MedicalDeptSubServiceImpl implements MedicalDeptSubService {


    @Resource
    private MedicalDeptSubDao medicalDeptSubDao;
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

}
