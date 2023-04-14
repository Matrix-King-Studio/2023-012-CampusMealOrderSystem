package com.ruoyi.project.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.system.domain.OrderInfo;
import com.ruoyi.project.system.domain.SysUserRole;
import com.ruoyi.project.system.mapper.OrderInfoMapper;
import com.ruoyi.project.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.ShopCartMapper;
import com.ruoyi.project.system.domain.ShopCart;
import com.ruoyi.project.system.service.IShopCartService;

/**
 * 购物车Service业务层处理
 *
 * @author ws
 * @date 2023-03-13
 */
@Service
public class ShopCartServiceImpl implements IShopCartService
{
    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    /**
     * 查询购物车
     *
     * @param shopCartId 购物车主键
     * @return 购物车
     */
    @Override
    public ShopCart selectShopCartByShopCartId(Integer shopCartId)
    {
        return shopCartMapper.selectShopCartByShopCartId(shopCartId);
    }

    /**
     * 查询购物车列表
     *
     * @param shopCart 购物车
     * @return 购物车
     */
    @Override
    public List<ShopCart> selectShopCartList(ShopCart shopCart)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        long userID = loginUser.getUserId();
        SysUserRole sysUserRole = sysUserRoleMapper.selectRoleIByUserId(userID);
        if(sysUserRole.getRoleId() == 102)
        {
            shopCart.setBuyerId(userID);
        }
        if (sysUserRole.getRoleId() == 103)
        {
            shopCart.setBusinessId(userID);
        }
        return shopCartMapper.selectShopCartList(shopCart);
    }

    /**
     * 新增购物车
     *
     * @param shopCart 购物车
     * @return 结果
     */
    @Override
    public int insertShopCart(ShopCart shopCart)
    {
        shopCart.setCreateTime(DateUtils.getNowDate());
        return shopCartMapper.insertShopCart(shopCart);
    }

    /**
     * 修改购物车
     *
     * @param shopCart 购物车
     * @return 结果
     */
    @Override
    public int updateShopCart(ShopCart shopCart)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        String nickName = loginUser.getUser().getNickName();
        if (shopCart.getType() == 1)
        {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setProductId(shopCart.getProductId());
            orderInfo.setProductName(shopCart.getProductName());
            orderInfo.setProductPrice(shopCart.getProductPrice());
            orderInfo.setProductPicture(shopCart.getProductPicture());
            orderInfo.setDescription(shopCart.getDescription());
            orderInfo.setProductFlavour(shopCart.getProductFlavour());
            orderInfo.setBusinessId(shopCart.getBusinessId());
            orderInfo.setBusinessName(shopCart.getBusinessName());
            orderInfo.setBuyerId(userId);
            orderInfo.setBuyerName(nickName);
            orderInfo.setOrderStatus(1);
            orderInfo.setShopNum(shopCart.getShopNum());
            orderInfo.setCreateTime(DateUtils.getNowDate());
            orderInfoMapper.insertOrderInfo(orderInfo);
            return shopCartMapper.deleteShopCartByShopCartId(shopCart.getShopCartId());
        }
        shopCart.setUpdateTime(DateUtils.getNowDate());
        return shopCartMapper.updateShopCart(shopCart);
    }

    /**
     * 批量删除购物车
     *
     * @param shopCartIds 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteShopCartByShopCartIds(Integer[] shopCartIds)
    {
        return shopCartMapper.deleteShopCartByShopCartIds(shopCartIds);
    }

    /**
     * 删除购物车信息
     *
     * @param shopCartId 购物车主键
     * @return 结果
     */
    @Override
    public int deleteShopCartByShopCartId(Integer shopCartId)
    {
        return shopCartMapper.deleteShopCartByShopCartId(shopCartId);
    }
}
