package com.example.seckilldemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.seckilldemo.imp.TGoodsServiceImpl;
import com.example.seckilldemo.imp.TUserServiceImpl;
import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.service.ITUserService;
import com.example.seckilldemo.utils.MD5util;
import com.example.seckilldemo.vo.DetailVo;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.goodsVo;
import com.example.seckilldemo.vo.logintemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ITUserService itUserService;
    @Autowired
    TUserServiceImpl loginService;
    @Autowired
    TGoodsServiceImpl tGoodsService;
    /*
    跳转到商品页面

    REDIS缓存对象QPS:2100

    * */

    @RequestMapping("/toList")
    public String toList( Model model,TUser user){


//        if (user==null) {
//            log.warn("2");
//            return "login";
//        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String goodsListcontext = (String) valueOperations.get("goodsList");
        model.addAttribute("user", user);
        if (goodsListcontext!=null){
            List goosList = JSON.parseObject(goodsListcontext, List.class);
            model.addAttribute("goodsList",goosList);
            return "goodsList";
        }
        else {
            List<goodsVo> goods = tGoodsService.getGoods();
            model.addAttribute("goodsList", goods);
            String s = JSON.toJSONString(goods);
            valueOperations.set("goodsList",s,90, TimeUnit.SECONDS);
            return "goodsList";
        }

    }

    //商品详情页面Rdis优化QPS：6500
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public RespBean getGoodsDetail(TUser user, @PathVariable Long goodsId ){
//        model.addAttribute("user",user);
        goodsVo goodsById = tGoodsService.getGoodsById(goodsId);
        Date startDate = goodsById.getStartDate();
        Date endDate = goodsById.getEndDate();
        Date now =new Date();
        int remainSeconds=0;
        int secKillStatus=0;
        if (now.before(startDate)){
            remainSeconds=((int)(startDate.getTime()-now.getTime())/1000);
        }else if (now.after(endDate)){
            secKillStatus=2;
            remainSeconds=-1;
        }else {
            secKillStatus=1;
            remainSeconds=0;
        }
        DetailVo detailVo=new DetailVo();
        detailVo.setGoods(goodsById);
        detailVo.setRemainSeconds(remainSeconds);
        detailVo.setUser(user);
        detailVo.setSecKillStatus(secKillStatus);
        return RespBean.success(detailVo);
    }
//    @RequestMapping("/toDetail/{goodsId}")
//    public String getGoodsDetail(Model model,TUser user, @PathVariable Long goodsId ){
//        model.addAttribute("user",user);
//        goodsVo goodsById = tGoodsService.getGoodsById(goodsId);
//        Date startDate = goodsById.getStartDate();
//        Date endDate = goodsById.getEndDate();
//        Date now =new Date();
//        int remainSeconds=0;
//        int secKillStatus=0;
//        if (now.before(startDate)){
//            remainSeconds=((int)(startDate.getTime()-now.getTime())/1000);
//        }else if (now.after(endDate)){
//            secKillStatus=2;
//        }else {
//            secKillStatus=1;
//            remainSeconds=-1;
//        }
//        model.addAttribute("secKillStatus",secKillStatus);
//        model.addAttribute("remainSeconds",remainSeconds);
//
//
//        model.addAttribute("goods",goodsById);
//        return "goodsDetail";
//    }

}
