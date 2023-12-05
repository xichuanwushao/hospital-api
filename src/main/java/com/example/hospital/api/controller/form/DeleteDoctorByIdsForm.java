package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author : wuxiao
 * @date : 18:01 2023-12-05
 */
@Data
public class DeleteDoctorByIdsForm {

    @NotEmpty(message = "ids不能为空")
    private Integer[] ids;
}
