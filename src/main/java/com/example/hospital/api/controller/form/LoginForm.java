package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author : wuxiao
 * @date : 12:34 2023-11-28
 */
@Data
public class LoginForm {
    @NotBlank(message = "username不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,50}$", message = "username内容不正确")
    private String username;
    @NotBlank(message = "password不能为空")
    private String password;
}
