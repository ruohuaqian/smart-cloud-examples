/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.examples.api.ac.core.vo;

import io.github.smart.cloud.common.pojo.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 接口鉴权meta
 *
 * @author collin
 * @date 2021-05-01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthMetaRespVO extends Base {

    /**
     * 是否需要登陆校验（false则不需要校验）
     */
    private boolean requireUser;

    /**
     * 访问接口需要的角色（为空则不需要校验）
     */
    private String[] requireRoles;

    /**
     * 访问接口需要的权限（为空则不需要校验）
     */
    private String[] requirePermissions;

}