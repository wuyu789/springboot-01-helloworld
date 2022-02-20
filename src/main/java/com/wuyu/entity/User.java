package com.wuyu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:      wy
 * Create Date: 2022/2/20
 * Create Time: 20:03
 * Description:
 */
@Data
@ApiModel("用户实体类")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
