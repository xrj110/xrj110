package com.example.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.logintemp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-03-12
 */
public interface ITUserService extends IService<TUser> {

 //生成大量用户使用
 RespBean doLogintest(logintemp temp, HttpServletRequest request, HttpServletResponse response);
String doLogin(logintemp temp, HttpServletRequest request, HttpServletResponse response);
    TUser getUser(String ticket);
}
