package com.ximen.system.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuInsertParamsDTO {

    private  Long roleId;

    private String roleName;

    private String remark;

    private List<Long> menuIds;
}
