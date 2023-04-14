package com.ruoyi.project.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.system.domain.SysUserRole;
import com.ruoyi.project.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.OrderInfoMapper;
import com.ruoyi.project.system.domain.OrderInfo;
import com.ruoyi.project.system.service.IOrderInfoService;

/**
 * 订单管理 Service业务层处理
 *
 * @author ws
 * @date 2023-03-13
 */
@Service
public class OrderInfoServiceImpl implements IOrderInfoService
{
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 查询订单管理
     *
     * @param orderId 订单管理 主键
     * @return 订单管理
     */
    @Override
    public OrderInfo selectOrderInfoByOrderId(Integer orderId)
    {
        return orderInfoMapper.selectOrderInfoByOrderId(orderId);
    }

    /**
     * 查询订单管理 列表
     *
     * @param orderInfo 订单管理
     * @return 订单管理
     */
    @Override
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo)
    {
        if (orderInfo.getType() == 1)
        {
            List<OrderInfo> orderInfos = orderInfoMapper.selectOrderInfoListBystatus(orderInfo);
            return orderInfos;
        }

        LoginUser loginUser = SecurityUtils.getLoginUser();
        long userID = loginUser.getUserId();
        SysUserRole sysUserRole = sysUserRoleMapper.selectRoleIByUserId(userID);
        if(sysUserRole.getRoleId() == 102)
        {
            orderInfo.setBuyerId(userID);
        }
        if (sysUserRole.getRoleId() == 103)
        {
            orderInfo.setBusinessId(userID);
        }

        if (orderInfo.getType() == 2)
        {
            List<OrderInfo> orderInfos = new ArrayList<>();
            OrderInfo orderCount= new OrderInfo();
            int todayOrder = orderInfoMapper.selectOrderCount(orderInfo);
            int allOrder = orderInfoMapper.selectAllOrderCount(orderInfo);
            orderCount.setTodayOrder(todayOrder);
            orderCount.setAllOrder(allOrder);
            orderInfos.add(orderCount);
            return orderInfos;
        }

        return orderInfoMapper.selectOrderInfoList(orderInfo);
    }

    /**
     * 新增订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    @Override
    public int insertOrderInfo(OrderInfo orderInfo)
    {
        orderInfo.setCreateTime(DateUtils.getNowDate());
        return orderInfoMapper.insertOrderInfo(orderInfo);
    }

    /**
     * 修改订单管理
     *
     * @param orderInfo 订单管理
     * @return 结果
     */
    @Override
    public int updateOrderInfo(OrderInfo orderInfo)
    {
        orderInfo.setUpdateTime(DateUtils.getNowDate());
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

    /**
     * 批量删除订单管理
     *
     * @param orderIds 需要删除的订单管理 主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoByOrderIds(Integer[] orderIds)
    {
        return orderInfoMapper.deleteOrderInfoByOrderIds(orderIds);
    }

    /**
     * 删除订单管理 信息
     *
     * @param orderId 订单管理 主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoByOrderId(Integer orderId)
    {
        return orderInfoMapper.deleteOrderInfoByOrderId(orderId);
    }
}
