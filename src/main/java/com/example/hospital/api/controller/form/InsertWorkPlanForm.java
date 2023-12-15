package com.example.hospital.api.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @author : wuxiao
 * @date : 16:37 2023-12-15
 */
@Data
public class InsertWorkPlanForm {
    @NotNull(message = "deptSubId不能为空")
    @Min(value = 1, message = "deptSubId不能小于1")
    private Integer deptSubId;
    @NotNull(message = "doctorId不能为空")
    @Min(value = 1, message = "doctorId不能小于1")
    private Integer doctorId;
    @NotBlank(message = "date不能为空")
//    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3
//            message = "date内容不正确")
    private String date;
    @NotNull(message = "totalMaximum不能为空")
    @Range(min = 1, max = 150, message = "totalMaximum内容不正确")
    private Integer totalMaximum;

    @NotNull(message = "slotMaximum不能为空")
    @Range(min = 1, max = 10, message = "slotMaximum内容不正确")
    private Integer slotMaximum;
    @NotEmpty(message = "slots不能为空")
    private Integer[] slots;
}
