package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author : wuxiao
 * @date : 19:00 2023-12-06
 */
@Data
public class SearchMedicalDeptByIdForm {
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Integer id;
}
