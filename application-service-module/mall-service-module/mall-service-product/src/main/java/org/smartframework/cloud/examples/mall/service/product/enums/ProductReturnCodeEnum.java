package org.smartframework.cloud.examples.mall.service.product.enums;

import org.smartframework.cloud.common.pojo.enums.IBaseReturnCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品服务状态码
 *
 * @author liyulin
 * @date 2019年4月7日下午11:52:53
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProductReturnCodeEnum implements IBaseReturnCode {

	STOCK_NOT_ENOUGH("202101", "库存不足，操作失败");

	/** 状态码 */
	private String code;
	/** 提示信息 */
	private String message;

}