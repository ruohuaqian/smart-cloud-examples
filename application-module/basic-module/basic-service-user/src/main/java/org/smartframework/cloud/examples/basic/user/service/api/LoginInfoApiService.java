package org.smartframework.cloud.examples.basic.user.service.api;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.smartframework.cloud.common.pojo.Base;
import org.smartframework.cloud.common.pojo.vo.RespVO;
import org.smartframework.cloud.examples.basic.rpc.enums.user.UserStateEnum;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.ExitReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.request.api.login.LoginReqVO;
import org.smartframework.cloud.examples.basic.rpc.user.response.api.login.LoginRespVO;
import org.smartframework.cloud.examples.basic.user.biz.api.LoginInfoApiBiz;
import org.smartframework.cloud.examples.basic.user.biz.api.UserInfoApiBiz;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertBizBO;
import org.smartframework.cloud.examples.basic.user.bo.login.LoginInfoInsertServiceBO;
import org.smartframework.cloud.examples.basic.user.config.UserParamValidateMessage;
import org.smartframework.cloud.examples.basic.user.entity.base.LoginInfoEntity;
import org.smartframework.cloud.examples.basic.user.entity.base.UserInfoEntity;
import org.smartframework.cloud.examples.basic.user.enums.UserReturnCodeEnum;
import org.smartframework.cloud.examples.basic.user.mapper.base.LoginInfoBaseMapper;
import org.smartframework.cloud.examples.support.rpc.gateway.UserRpc;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.CacheUserInfoReqVO;
import org.smartframework.cloud.examples.support.rpc.gateway.request.rpc.ExitLoginReqVO;
import org.smartframework.cloud.starter.core.business.exception.BusinessException;
import org.smartframework.cloud.starter.core.business.exception.ParamValidateException;
import org.smartframework.cloud.starter.core.business.exception.ServerException;
import org.smartframework.cloud.starter.core.business.util.PasswordUtil;
import org.smartframework.cloud.starter.core.business.util.RespUtil;
import org.smartframework.cloud.starter.mybatis.common.mapper.enums.DelStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@Slf4j
public class LoginInfoApiService extends ServiceImpl<LoginInfoBaseMapper, LoginInfoEntity> {

    @Autowired
    private LoginInfoApiBiz loginInfoApiBiz;
    @Autowired
    private UserInfoApiBiz userInfoApiBiz;
    @Autowired
    private UserRpc userRpc;

    /**
     * 登陆校验
     *
     * @param req
     * @return
     */
    public LoginRespVO login(LoginReqVO req) {
        LoginInfoEntity loginInfoEntity = loginInfoApiBiz.queryByUsername(req.getUsername());
        if (Objects.isNull(loginInfoEntity)) {
            throw new BusinessException(UserReturnCodeEnum.ACCOUNT_NOT_EXIST);
        }
        // 校验密码
        String salt = loginInfoEntity.getSalt();
        String securePassword = PasswordUtil.secure(req.getPassword(), salt);
        if (!Objects.equals(securePassword, loginInfoEntity.getPassword())) {
            throw new BusinessException(UserReturnCodeEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if (Objects.equals(loginInfoEntity.getUserState(), UserStateEnum.UNENABLE.getValue())) {
            throw new BusinessException(UserReturnCodeEnum.USER_UNENABLE);
        }
        if (Objects.equals(loginInfoEntity.getDelState(), DelStateEnum.DELETED.getDelState())) {
            throw new BusinessException(UserReturnCodeEnum.USER_DELETED);
        }

        UserInfoEntity userInfoEntity = userInfoApiBiz.getUserInfoBaseMapper().selectById(loginInfoEntity.getUserId());

        LoginRespVO loginRespVO = LoginRespVO.builder()
                .userId(userInfoEntity.getId())
                .username(loginInfoEntity.getUsername())
                .realName(userInfoEntity.getRealName())
                .mobile(userInfoEntity.getMobile())
                .build();

        // 缓存登录信息到网关
        cacheUserInfo(req.getToken(), loginRespVO);

        return loginRespVO;
    }


    /**
     * 退出登录
     *
     * @param req
     * @return
     */
    public void exit(ExitReqVO req) {
        RespVO<Base> exitLoginResp = userRpc.exit(ExitLoginReqVO.builder().token(req.getToken()).build());
        if (!RespUtil.isSuccess(exitLoginResp)) {
            throw new ServerException(RespUtil.getFailMsg(exitLoginResp));
        }
    }

    /**
     * 缓存登录信息到网关
     *
     * @param token
     * @param loginRespVO
     */
    public void cacheUserInfo(String token, LoginRespVO loginRespVO) {
        RespVO<Base> cacheUserInfoResp = userRpc.cacheUserInfo(CacheUserInfoReqVO.builder()
                .token(token)
                .userId(loginRespVO.getUserId())
                .username(loginRespVO.getUsername())
                .realName(loginRespVO.getRealName())
                .mobile(loginRespVO.getMobile())
                .build());
        if (!RespUtil.isSuccess(cacheUserInfoResp)) {
            throw new ServerException(RespUtil.getFailMsg(cacheUserInfoResp));
        }
    }

    /**
     * 插入登陆信息
     *
     * @param bo
     * @return
     */
    public LoginInfoEntity insert(LoginInfoInsertServiceBO bo) {
        // 判断该用户名是否已存在
        boolean existUsername = loginInfoApiBiz.existByUsername(bo.getUsername());
        if (existUsername) {
            throw new ParamValidateException(UserParamValidateMessage.REGISTER_USERNAME_EXSITED);
        }

        String salt = generateRandomSalt();
        String securePassword = PasswordUtil.secure(bo.getPassword(), salt);

        LoginInfoInsertBizBO loginInfoInsertDto = LoginInfoInsertBizBO.builder()
                .userId(bo.getUserId())
                .username(bo.getUsername())
                .password(securePassword)
                .pwdState(bo.getPwdState())
                .salt(salt)
                .build();
        return loginInfoApiBiz.insert(loginInfoInsertDto);
    }


    /**
     * 生成随机盐值
     *
     * @return
     */
    private String generateRandomSalt() {
        String salt = null;
        try {
            salt = PasswordUtil.generateRandomSalt();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(UserReturnCodeEnum.GENERATE_SALT_FAIL);
        }
        return salt;
    }

}