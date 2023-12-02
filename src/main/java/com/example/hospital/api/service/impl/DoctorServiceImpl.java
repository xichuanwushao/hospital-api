package com.example.hospital.api.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.hospital.api.common.PageUtils;
import com.example.hospital.api.db.dao.DoctorDao;
import com.example.hospital.api.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 14:36 2023-11-29
 */
@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private DoctorDao doctorDao;
    @Override
    public PageUtils searchByPage(Map param) {
        ArrayList<HashMap> list = null;
        long count = doctorDao.searchCount(param);
        if (count > 0) {
            list = doctorDao.searchByPage(param);
        } else {
            list = new ArrayList<>();
        }
        int page = MapUtil.getInt(param, "page");
        int length = MapUtil.getInt(param, "length");
        PageUtils pageUtils = new PageUtils(list, count, page, length);
        return pageUtils;
    }

    @Override
    public HashMap searchContent(int id) {
        HashMap map = doctorDao.searchContent(id);
        JSONArray tag = JSONUtil.parseArray(MapUtil.getStr(map, "tag"));
        map.replace("tag", tag);
        return map;
    }

}
