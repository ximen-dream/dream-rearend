package com.ximen.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MrBird
 */
@TableName("t_role_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -7573904024872252113L;

    @TableId(value = "ROLE_ID")
    private Long roleId;
    @TableId(value = "MENU_ID")
    private Long menuId;
}