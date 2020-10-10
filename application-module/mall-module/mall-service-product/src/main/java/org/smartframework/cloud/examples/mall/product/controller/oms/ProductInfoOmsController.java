package org.smartframework.cloud.examples.mall.product.controller.oms;

import org.smartframework.cloud.api.core.annotation.SmartApiAC;
import org.smartframework.cloud.api.core.enums.SignType;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.product.service.oms.ProductInfoOmsService;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductDeleteReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductInsertReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.oms.ProductUpdateReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.base.ProductInfoBaseRespVO;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品信息管理
 *
 * @author liyulin
 * @date 2020-09-10
 * @status done
 */
@RestController
@RequestMapping("product/oms/productInfo")
@Validated
public class ProductInfoOmsController {

    @Autowired
    private ProductInfoOmsService productOmsService;

    /**
     * 新增商品信息
     *
     * @param req
     * @return
     */
    @PostMapping("create")
    @SmartApiAC(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true)
    public RespVO<Base> create(@RequestBody @Valid ProductInsertReqVO req) {
        return productOmsService.create(req);
    }

    /**
     * 修改商品信息
     *
     * @param req
     * @return
     */
    @PostMapping("update")
    @SmartApiAC(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true)
    public RespVO<Base> update(@RequestBody @Valid ProductUpdateReqVO req) {
        return productOmsService.update(req);
    }

    /**
     * 逻辑删除商品
     *
     * @param req
     * @return
     */
    @PostMapping("logicDelete")
    @SmartApiAC(tokenCheck = true, sign = SignType.ALL, encrypt = true, decrypt = true)
    public RespVO<Base> logicDelete(@RequestBody @Valid ProductDeleteReqVO req) {
        return productOmsService.logicDelete(req);
    }

    /**
     * 分页查询商品信息
     *
     * @param req
     * @return
     */
    @GetMapping("pageProduct")
    public RespVO<BasePageRespVO<ProductInfoBaseRespVO>> pageProduct(@Valid PageProductReqVO req) {
        return RespUtil.success(productOmsService.pageProduct(req));
    }

}