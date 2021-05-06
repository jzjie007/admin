package com.jzj.admin.service.twopc.impl;

import com.jzj.admin.bean.entity.AdminPO;
import com.jzj.admin.mybatis.dao.IAdminDAO;
import com.jzj.goods.client.IGoodRpcService;
import com.jzj.order.client.IOrderRpcService;
import com.jzj.order.client.bean.OrderModel;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
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

    /**
     * 管理员通过 订单购买货物 <P></P>
     * 1.管理员记录操作日志
     * 2.增加订单记录
     * 3.根据订单的数量减少货物库存
     * 这三部是一个整体，要么全部执行，要么全部回滚
     */
    @Override
    @GlobalTransactional(timeoutMills = 10000 )
    public void adminBusGoods(String logMsg, Integer payNumber) {
        //!!! 注意方法上面加了GlobalTransactional 表明全局事务的开启是在这里开始的，管理员开始购买货物，发起全局事务
        log.info("Admin 开始全局事务，XID = " + RootContext.getXID());
        //第一步 记录管理员日志
        AdminPO adminPO = AdminPO.builder().userName("root").password("root").log(logMsg).build();
        adminDAO.insertUseGeneratedKeys(adminPO);

        //第二步 调用订单服务, 下订单，买 payNumber个货物
        OrderModel orderModel = OrderModel.builder().orderName("first").payNumber(payNumber).build();
        orderRpcService.addOrder(orderModel);

        //第三步 调用货物服务，减少库存，货物减少10个,把goodsId=1的货物减少10个
        goodsRpcService.deleteGoods(1, payNumber);
    }

}
