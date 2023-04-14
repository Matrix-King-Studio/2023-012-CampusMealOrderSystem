package com.ruoyi.project.system.service;

import java.util.List;
import com.ruoyi.project.system.domain.OrderInfo;

/**
 * 订单管理 Service接口
 * 
 * @author ws
 * @date 2023-03-13
 */
public interface IOrderInfoService 
{
    /**
     * 查询订单管理 
     * 
     * @param orderId 订单管理 主键
     * @return 订单管理 
     */
    public OrderInfo selectOrderInfoByOrderId(Integer orderId);

    /**
     * 查询订单管理 列表
     * 
     * @param orderInfo 订单管理 
     * @return 订单管理 集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 新增订单管理 
     * 
     * @param orderInfo 订单管理 
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单管理 
     * 
     * @param orderInfo 订单管理 
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 批量删除订单管理 
     * 
     * @param orderIds 需要删除的订单管理 主键集合
     * @return 结果
     */
    public int deleteOrderInfoByOrderIds(Integer[] orderIds);

    /**
     * 删除订单管理 信息
     * 
     * @param orderId 订单管理 主键
     * @return 结果
     */
    public int deleteOrderInfoByOrderId(Integer orderId);
}
