package com.ruoyi.project.system.mapper;

import java.util.List;
import com.ruoyi.project.system.domain.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 订单管理 Mapper接口
 *
 * @author ws
 * @date 2023-03-13
 */
@Mapper
@Repository
public interface OrderInfoMapper
{
    /**
     * 查询订单管理
     *
     * @param orderId 订单管理 主键
     * @return 订单管理
     */
    public OrderInfo selectOrderInfoByOrderId(Integer orderId);

    public int selectOrderCount(OrderInfo orderInfo);

    public int selectAllOrderCount(OrderInfo orderInfo);

    /**
     * 查询订单管理 列表
     *
     * @param orderInfo 订单管理
     * @return 订单管理 集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    public List<OrderInfo> selectOrderInfoListBystatus(OrderInfo orderInfo);

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
     * 删除订单管理
     *
     * @param orderId 订单管理 主键
     * @return 结果
     */
    public int deleteOrderInfoByOrderId(Integer orderId);

    /**
     * 批量删除订单管理
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderInfoByOrderIds(Integer[] orderIds);
}
