package org.smartframework.cloud.examples.system.test.module.mall.product.api;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import org.smartframework.cloud.common.pojo.vo.BasePageRespVO;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.mall.rpc.product.request.api.PageProductReqVO;
import org.smartframework.cloud.examples.mall.rpc.product.response.api.PageProductRespVO;
import org.smartframework.cloud.examples.system.test.config.SystemTestConfig;
import org.smartframework.cloud.examples.system.test.util.HttpHeaderUtil;
import org.smartframework.cloud.utility.HttpUtil;

import java.io.IOException;

@UtilityClass
public class ProductInfoApi {

    public RespVO<BasePageRespVO<PageProductRespVO>> pageProduct(PageProductReqVO reqVO) throws IOException {
        return HttpUtil.get(
                SystemTestConfig.getProductBaseUrl() + "product/api/productInfo/pageProduct", HttpHeaderUtil.build(),
                reqVO, new TypeReference<RespVO<BasePageRespVO<PageProductRespVO>>>() {
                });
    }

}