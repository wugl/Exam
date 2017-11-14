package com.wgl.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class WebSecurityConfig  extends WebMvcConfigurerAdapter {

    /**
     * 登录session key
     */
    public final static String SESSION_KEY_USER_ID = "user_id";
    public final static String SESSION_KEY_USER_NAME = "user_name";
    public final static String SESSION_KEY_USER_TYPE = "user_type";

    Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/login**");


        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            HttpSession session = request.getSession();


            if (session.getAttribute(SESSION_KEY_USER_ID) != null) {
               // logger.error("session:"+session.getAttribute(SESSION_KEY_USER_ID).toString());
               // System.out.println("session:"+session.getAttribute(SESSION_KEY_USER_ID).toString());
                return true;
            }

            // 跳转登录
            String url = "/login";
            response.sendRedirect(url);
            return false;
        }
    }
}