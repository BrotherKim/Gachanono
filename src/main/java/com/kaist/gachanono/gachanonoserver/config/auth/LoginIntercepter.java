package com.kaist.gachanono.gachanonoserver.config.auth;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginIntercepter implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public List loginEssential = Arrays.asList("/**");
    public List loginInessential = Arrays.asList("/login", "/css/**", "/js/**", "/img/**", "/favicon.ico", "/error**");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute("user");

        if(sessionUser != null) {
            return true;
        } else {
            String destUri = request.getRequestURI();
            String destQuery = request.getQueryString();
            String dest = (destQuery == null) ? destUri : destUri+"?"+destQuery;
            request.getSession().setAttribute("dest", dest);
        
            response.sendRedirect("/login");
            return false;
        }
    }
}