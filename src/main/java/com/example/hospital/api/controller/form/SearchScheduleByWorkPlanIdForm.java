package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author : wuxiao
 * @date : 16:04 2023-12-19
 */
@Data
public class SearchScheduleByWorkPlanIdForm {
    @NotNull(message = "workPlanId不能为空")
    @Min(value = 1, message = "workPlanId不能小于1")
    private Integer workPlanId;
}
