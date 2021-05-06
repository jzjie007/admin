package com.jzj.admin.controller;

import com.jzj.admin.bean.entity.AdminPO;
import com.jzj.admin.mybatis.dao.IAdminDAO;
import com.jzj.admin.service.twopc.impl.IBuyServiceTwoPC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@ResponseBody
@RestController
@RequestMapping("test")
@Slf4j
public class SiteController {


    @Resource
    private IAdminDAO adminDAO;


    @Autowired
    private IBuyServiceTwoPC buyService;


    @RequestMapping("ping")
    public Object ping(){
        return "admin";
    }

    @RequestMapping("add")
    public Object add(){

        AdminPO adminPO = AdminPO.builder().userName("root").password("root").log("jzj buy someThings").build();
        int count = adminDAO.insertUseGeneratedKeys(adminPO);
        log.info("add Admin:{} , count={}", adminPO, count);
        return "ok";
    }


    @RequestMapping("buy")
    public Object buy(Integer number){
        String msg = "root buy "+ number+" someThings";
        log.info("管理员准备买东西===============================");
       buyService.adminBusGoods(msg, number);
        return "ok";
    }
}
