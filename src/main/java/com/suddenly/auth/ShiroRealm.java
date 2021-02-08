package com.suddenly.auth;

import com.suddenly.entity.UserInfo;
import com.suddenly.mapper.UserInfoMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void setName(String name) {
        super.setName("ShiroRealm");
    }

    /**
     * 方法描述： 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 方法描述： 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        try {
            UsernamePasswordToken userToken = (UsernamePasswordToken) token;
            UserInfo account = userInfoMapper.queryByAccount(userToken.getUsername());
            if (Objects.nonNull(account)) {
                AuthenticationInfo authInfo = new SimpleAuthenticationInfo(account, account.getPassword(), this.getName());
                return authInfo;
            }
        } catch (Exception e) {
            logger.error("账号登录,查询登录账号信息异常:{}", e);
            throw new AuthenticationException("账号登录,查询登录账号信息异常", e);
        }
        return null;
    }

}
