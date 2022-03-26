package com.example.seckilldemo.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckilldemo.exception.GlobalException;
import com.example.seckilldemo.mapper.TUserMapper;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.service.ITUserService;
import com.example.seckilldemo.utils.CookieUtil;
import com.example.seckilldemo.utils.MD5util;
import com.example.seckilldemo.utils.UUIDUtil;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.RespBeanEnum;
import com.example.seckilldemo.vo.logintemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-03-12
 */
@Service
@Slf4j
@CrossOrigin
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {
    @Autowired
    TUserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    //生成大量用户使用
    public RespBean doLogintest(logintemp temp, HttpServletRequest request, HttpServletResponse response) {

        String mobile = temp.getMobile();
        String password = temp.getPassword();

        TUser user = userMapper.selectById(mobile);
        if (null == user) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if (!MD5util.fromPassToDBPass(password, user.getSlat()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket = UUIDUtil.uuid();
        //将用户信息存入redis中
        redisTemplate.opsForValue().set("user:" + ticket, user);
        // request.getSession().setAttribute(ticket,user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success(ticket);

    }
    public String doLogin(logintemp temp, HttpServletRequest request, HttpServletResponse response) {
        String fromPassword = temp.getPassword();
        TUser tUser = (TUser)this.userMapper.selectById(temp.getMobile());
        if (tUser == null) {
            return "mobile or password can't be null";
        } else {
            log.warn(tUser.getPassword());
            log.warn(tUser.getSlat());
            String dbPass = MD5util.fromPassToDBPass(fromPassword, tUser.getSlat());
            if (!tUser.getPassword().equals(dbPass)) {
                return "wrong mobile  or password";
            } else {
                log.warn("success");
                log.warn(dbPass);
                String ticket = UUIDUtil.uuid();
                this.redisTemplate.opsForValue().set("user:" + ticket, tUser);
                CookieUtil.setCookie(request, response, "UserTicket", ticket);
                log.warn("ticket:" + ticket);

                try {
                    response.sendRedirect("/goods/toList");
                } catch (IOException var9) {
                    var9.printStackTrace();
                }

                return null;
            }
        }
    }
    @Override
    public TUser getUser(String ticket) {
        TUser user = (TUser) redisTemplate.opsForValue().get("user:" + ticket);
        return user;
    }
}
