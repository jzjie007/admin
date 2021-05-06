package com.jzj.admin.service.twopc.impl;

import com.jzj.admin.mybatis.dao.IAdminDAO;
import com.jzj.goods.client.IGoodRpcService;
import com.jzj.order.client.IOrderRpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service(value = "twoPCBuyServiceImpl")
@Slf4j
public class BuyServiceTwoPCImpl implements IBuyServiceTwoPC {

    @Resource
    private IAdminDAO adminDAO;

    @DubboReference
    private IOrderRpcService orderRpcService;

    @DubboReference
    private IGoodRpcService goodsRpcService;

    @Override
    public void adminBusGoods(String logMsg, Integer payNumber) {

    }
}
