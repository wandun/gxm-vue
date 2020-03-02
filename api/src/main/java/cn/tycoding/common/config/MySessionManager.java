package cn.tycoding.common.config;

import cn.tycoding.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-09-26
 */
@Slf4j
public class MySessionManager extends DefaultWebSessionManager {

    /**
     * 之前默认shiro 会从cookie中找JSESSIONID,但是这里
     * 他把那个值没有改，返回去了，但是cookie的名称不叫JSESSIONID了，
     * 叫做（vue-admin_template_token）,所以我们这里就需要，从新指定
     * 值的获取方式，我们会叫前端，放在resuest的header中发给我们，我们
     * 只需要重写shrio的获取session值的获取方式，即可，至于值的匹配
     * 我们不需要重写，因为我们没有改变
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(CommonConstant.AUTHORIZATION);
        log.info("Authorization ==> " + id);
        if (!StringUtils.isEmpty(id)) {
            // 说明以及登陆过了
//            referenced_session_id_source
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, CommonConstant.REFERENCED_SESSION_ID_SOURCE);
//            referenced_session_id
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
//            referenced_session_id_is_valid
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            // 第一次登陆
            return super.getSessionId(request, response);
        }
    }
}
