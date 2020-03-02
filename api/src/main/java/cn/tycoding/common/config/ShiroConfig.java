package cn.tycoding.common.config;

import cn.tycoding.common.properties.ShiroProperties;
import cn.tycoding.common.properties.TumoProperties;
import cn.tycoding.common.realm.AuthRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author TyCoding
 * @date 2018/10/21
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private TumoProperties properties;

    /**
     * shiro的总的权限管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置session管理器
        securityManager.setSessionManager(sessionManager());
        // 设置用户认证的realm/以及密码匹配规则
        securityManager.setRealm(userRealm(hashedCredentialsMatcher()));
        return securityManager;
    }

    /**
     * 用户认证
     * @param hashedCredentialsMatcher
     * @return
     */
    @Bean
    public Realm userRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        AuthRealm authRealm = new AuthRealm();
        // 设置用户认证时的密码匹配规则
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return authRealm;
    }


    @Bean
    public SimpleCookie rememberMeCookie() {
        ShiroProperties shiro = properties.getShiro();
        SimpleCookie simpleCookie = new SimpleCookie("remember");
        simpleCookie.setMaxAge(shiro.getCookieTimeout());
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 设置密码配置规则
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // md5(md5("123456"))
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheManager();
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new EnterpriseCacheSessionDAO();
    }

    /**
     * 当请求过来，shiro创建session后，会把sessionId写回到客户端的cookie中。
     * 每二个请求过来时，shiro会拿到这个cookie里的sessionId去查找，如果查找不到，
     * 就有可能抛There is no session with id的异常
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        ShiroProperties shiro = properties.getShiro();
        //使用自定义SessionManager
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setCacheManager(cacheManager());
        sessionManager.setGlobalSessionTimeout(shiro.getSessionTimeout() * 1000L);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdUrlRewritingEnabled(true);
        return sessionManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroProperties shiro = properties.getShiro();
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChain = new LinkedHashMap<>();
        String[] urls = shiro.getAnonUrl().split(",");
        for (String url : urls) {
            filterChain.put(url, "anon");
        }
        filterChain.put("/**", "user");
        filterFactoryBean.setFilterChainDefinitionMap(filterChain);
        return filterFactoryBean;
    }


}
