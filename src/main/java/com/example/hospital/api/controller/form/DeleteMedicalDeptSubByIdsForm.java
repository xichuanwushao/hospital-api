package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author : wuxiao
 * @date : 17:43 2023-12-07
 */
@Data
public class DeleteMedicalDeptSubByIdsForm {
    @NotEmpty(message = "ids不能为空")
    private Integer[] ids;
}
