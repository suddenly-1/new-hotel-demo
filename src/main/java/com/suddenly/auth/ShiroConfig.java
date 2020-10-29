package com.suddenly.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {

    private Logger logger = LogManager.getLogger(getClass());

    @Bean("securityManager")
    public org.apache.shiro.mgt.SecurityManager createSecurityManager(@Qualifier("platformAuthRealm") Realm authRealm, @Qualifier("authSessionManager") SessionManager sessionManager ) {
        logger.info("------------创建SecurityManager------------");
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(authRealm);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean("platformAuthRealm")
    public ShiroRealm getShiroRealm() {
        return new ShiroRealm();
    }


    @Bean("authSessionManager")
    public SessionManager createSessionManager(@Qualifier("authSessionDao") SessionDAO sessionDao, @Qualifier("simpleCookie") SimpleCookie simpleCookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(-1000000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }

    @Bean("simpleCookie")
    public SimpleCookie createSimpleCookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("shiro.session.id");
        return cookie;
    }

    @Bean("authSessionDao")
    public SessionDAO createSessionDao() {
        EnterpriseCacheSessionDAO dao = new EnterpriseCacheSessionDAO();
        dao.setActiveSessionsCacheName("suddenly-center");
        return dao;
    }

}
