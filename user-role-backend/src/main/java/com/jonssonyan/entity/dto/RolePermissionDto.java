package com.jonssonyan.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolePermissionDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = -4528470984796447897L;
    private Long id;
    private Long roleId;
    private Long permissionId;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}
