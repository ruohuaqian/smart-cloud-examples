package org.smartframework.cloud.examples.mall.service.order.mapper.base;

import org.smartframework.cloud.examples.mall.service.order.entity.base.OrderBillEntity;
import org.smartframework.cloud.examples.mall.service.rpc.order.response.base.OrderBillBaseRespBody;
import org.smartframework.cloud.starter.mybatis.common.mapper.ext.ExtMapper;

public interface OrderBillBaseMapper extends ExtMapper<OrderBillEntity, OrderBillBaseRespBody, Long> {

}