package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author : wuxiao
 * @date : 19:16 2023-12-06
 */
@Data
public class DeleteMedicalDeptByIdsForm {

    @NotEmpty(message = "ids不能为空")
    private Integer[] ids;

}
