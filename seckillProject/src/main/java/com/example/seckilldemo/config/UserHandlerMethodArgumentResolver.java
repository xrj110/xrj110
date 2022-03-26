package com.example.seckilldemo.config;

import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.service.ITUserService;
import com.example.seckilldemo.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    ITUserService itUserService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz== TUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String cookieValue = CookieUtil.getCookieValue(nativeRequest,"UserTicket");
        if (cookieValue==null)return null;

        return itUserService.getUser(cookieValue);
    }
}
