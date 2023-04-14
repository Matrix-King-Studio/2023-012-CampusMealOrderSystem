package com.ruoyi.project.system.service;

import java.util.List;
import com.ruoyi.project.system.domain.ShopCart;

/**
 * 购物车Service接口
 * 
 * @author ws
 * @date 2023-03-13
 */
public interface IShopCartService 
{
    /**
     * 查询购物车
     * 
     * @param shopCartId 购物车主键
     * @return 购物车
     */
    public ShopCart selectShopCartByShopCartId(Integer shopCartId);

    /**
     * 查询购物车列表
     * 
     * @param shopCart 购物车
     * @return 购物车集合
     */
    public List<ShopCart> selectShopCartList(ShopCart shopCart);

    /**
     * 新增购物车
     * 
     * @param shopCart 购物车
     * @return 结果
     */
    public int insertShopCart(ShopCart shopCart);

    /**
     * 修改购物车
     * 
     * @param shopCart 购物车
     * @return 结果
     */
    public int updateShopCart(ShopCart shopCart);

    /**
     * 批量删除购物车
     * 
     * @param shopCartIds 需要删除的购物车主键集合
     * @return 结果
     */
    public int deleteShopCartByShopCartIds(Integer[] shopCartIds);

    /**
     * 删除购物车信息
     * 
     * @param shopCartId 购物车主键
     * @return 结果
     */
    public int deleteShopCartByShopCartId(Integer shopCartId);
}
