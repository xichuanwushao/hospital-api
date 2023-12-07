package com.example.hospital.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.bean.BeanUtil;
import com.example.hospital.api.common.PageUtils;
import com.example.hospital.api.common.R;
import com.example.hospital.api.controller.form.InsertMedicalDeptSubForm;
import com.example.hospital.api.controller.form.SearchMedicalDeptSubByPageForm;
import com.example.hospital.api.db.pojo.MedicalDeptSubEntity;
import com.example.hospital.api.service.MedicalDeptSubService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 16:05 2023-12-07
 */
@RestController
@RequestMapping("/medical/dept/sub")
public class MedicalDeptSubController {
    @Resource
    private MedicalDeptSubService medicalDeptSubService;
    @PostMapping("/searchByPage")
    @SaCheckLogin
    @SaCheckPermission(value = {"ROOT", "MEDICAL_DEPT_SUB:SELECT"}, mode = SaMode.OR)
    public R searchByPage(@RequestBody @Valid SearchMedicalDeptSubByPageForm form) {
        Map param = BeanUtil.beanToMap(form);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = medicalDeptSubService.searchByPage(param);
        return R.ok().put("result", pageUtils);
    }

    @PostMapping("/insert")
    @SaCheckLogin
    @SaCheckPermission(value = {"ROOT", "MEDICAL_DEPT_SUB:INSERT"}, mode = SaMode.OR)
    public R insert(@RequestBody @Valid InsertMedicalDeptSubForm form) {
        MedicalDeptSubEntity entity = BeanUtil.toBean(form, MedicalDeptSubEntity.class);
        medicalDeptSubService.insert(entity);
        return R.ok();
    }
}
