package com.jzj.admin.service.twopc.impl;

public interface IBuyServiceTwoPC {

    /**
     * 管理员通过 订单购买货物 <P></P>
     * 1.管理员记录操作日志
     * 2.增加订单记录
     * 3.根据订单的数量减少货物库存
     * 这三部是一个整体，要么全部执行，要么全部回滚
     */
    void adminBusGoods(String logMsg, Integer payNumber);
}
