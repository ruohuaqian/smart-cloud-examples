package org.smartframework.cloud.examples.basic.auth.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.starter.mybatis.plus.common.mapper.entity.BaseEntity;

/**
 * 角色表
 *
 * @author liyulin
 * @date 2021-07-02
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@TableName("t_role_info")
public class RoleInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /** 角色编码 */
    @TableField(value = "f_code")
	private String code;
	
    /** 角色描述 */
    @TableField(value = "f_description")
	private String description;
	
}