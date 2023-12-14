package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author : wuxiao
 * @date : 16:18 2023-12-14
 */
@Data
public class SearchWorkPlanInRangeForm {
    @NotBlank(message = "startDate不能为空")
//    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3
//            message = "startDate内容不正确")
    private String startDate;
    @NotBlank(message = "endDate不能为空")
//    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3
//            message = "endDate内容不正确")
    private String endDate;
    @Min(value = 1, message = "deptId不能为空")
    private Integer deptId;
//    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}$", message = "doctorName内容不正
    private String doctorName;
}

