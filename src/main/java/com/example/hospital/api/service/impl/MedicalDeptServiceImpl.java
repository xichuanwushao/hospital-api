package com.example.hospital.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.example.hospital.api.common.PageUtils;
import com.example.hospital.api.db.dao.MedicalDeptDao;
import com.example.hospital.api.service.MedicalDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 14:20 2023-12-02
 */
@Service
public class MedicalDeptServiceImpl implements MedicalDeptService {
    @Resource
    private MedicalDeptDao medicalDeptDao;
    @Override
    public ArrayList<HashMap> searchAll() {
        ArrayList<HashMap> list = medicalDeptDao.searchAll();
        return list;
    }

    @Override
    public HashMap searchDeptAndSub() {
        ArrayList<HashMap> list = medicalDeptDao.searchDeptAndSub();
        LinkedHashMap map = new LinkedHashMap();
        for (HashMap one : list) {
            Integer deptId = MapUtil.getInt(one, "deptId");
            Integer subId = MapUtil.getInt(one, "subId");
            String deptName = MapUtil.getStr(one, "deptName");
            String subName = MapUtil.getStr(one, "subName");
            if (map.containsKey(deptName)) {
                ArrayList<HashMap> subList = (ArrayList<HashMap>) map.get(deptName);
                subList.add(new HashMap() {{
                    put("subId", subId);
                    put("subName", subName);
                }});
            } else {
                map.put(deptName, new ArrayList() {{
                    add ( new HashMap() {{

                        put("subId", subId);
                        put("subName", subName);
                    }});
                }});
            }
        }
        return map;
    }



    @Override
    public PageUtils searchByPage(Map param) {
        ArrayList<HashMap> list = null;
        long count = medicalDeptDao.searchCount(param);
        if (count > 0) {
            list = medicalDeptDao.searchByPage(param);
        } else {
            list = new ArrayList<>();
        }
        int page = MapUtil.getInt(param, "page");
        int length = MapUtil.getInt(param, "length");
        PageUtils pageUtils = new PageUtils(list, count, page, length);
        return pageUtils;
    }
}
