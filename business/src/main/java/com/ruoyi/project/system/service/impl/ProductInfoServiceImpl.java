package com.ruoyi.project.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.system.domain.ShopCart;
import com.ruoyi.project.system.domain.SysUserRole;
import com.ruoyi.project.system.mapper.ShopCartMapper;
import com.ruoyi.project.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.system.mapper.ProductInfoMapper;
import com.ruoyi.project.system.domain.ProductInfo;
import com.ruoyi.project.system.service.IProductInfoService;

/**
 * 菜品管理Service业务层处理
 *
 * @author ws
 * @date 2023-03-13
 */
@Service
public class ProductInfoServiceImpl implements IProductInfoService
{
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private ShopCartMapper shopCartMapper;

    /**
     * 查询菜品管理
     *
     * @param productId 菜品管理主键
     * @return 菜品管理
     */
    @Override
    public ProductInfo selectProductInfoByProductId(Integer productId)
    {
        return productInfoMapper.selectProductInfoByProductId(productId);
    }

    /**
     * 查询菜品管理列表
     *
     * @param productInfo 菜品管理
     * @return 菜品管理
     */
    @Override
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        long userID = loginUser.getUserId();
        SysUserRole sysUserRole = sysUserRoleMapper.selectRoleIByUserId(userID);
        if (sysUserRole.getRoleId() == 103)
        {
            productInfo.setBusinessId(userID);
        }
        return productInfoMapper.selectProductInfoList(productInfo);
    }

    /**
     * 新增菜品管理
     *
     * @param productInfo 菜品管理
     * @return 结果
     */
    @Override
    public int insertProductInfo(ProductInfo productInfo)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        productInfo.setBusinessId(loginUser.getUserId());
        productInfo.setBusinessName(loginUser.getUser().getNickName());
        productInfo.setCreateTime(DateUtils.getNowDate());
        return productInfoMapper.insertProductInfo(productInfo);
    }

    /**
     * 修改菜品管理
     *
     * @param productInfo 菜品管理
     * @return 结果
     */
    @Override
    public int updateProductInfo(ProductInfo productInfo)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        String nickName = loginUser.getUser().getNickName();

        // 购物车
        if (productInfo.getType() == 1)
        {
            ShopCart shopCart = new ShopCart();
            ShopCart shop = new ShopCart();
            shop.setBuyerId(userId);
            shop.setProductId(productInfo.getProductId());
            List<ShopCart> shopCarts = shopCartMapper.selectShopCartList(shop);
            if (shopCarts.size() == 0)
            {
                shopCart.setShopNum(0);
            }
            else
            {
                ShopCart shopCart1 = shopCarts.get(0);
                shopCart1.setShopNum(shopCart1.getShopNum() + 1);
                return shopCartMapper.updateShopCart(shopCart1);
            }
            shopCart.setProductId(productInfo.getProductId());
            shopCart.setProductName(productInfo.getProductName());
            shopCart.setProductPrice(productInfo.getProductPrice());
            shopCart.setProductPicture(productInfo.getProductPicture());
            shopCart.setDescription(productInfo.getDescription());
            shopCart.setProductFlavour(productInfo.getProductFlavour());
            shopCart.setBusinessId(productInfo.getBusinessId());
            shopCart.setBusinessName(productInfo.getBusinessName());
            shopCart.setBuyerId(userId);
            shopCart.setBuyerName(nickName);
            return shopCartMapper.insertShopCart(shopCart);
        }
        productInfo.setUpdateTime(DateUtils.getNowDate());
        return productInfoMapper.updateProductInfo(productInfo);
    }

    /**
     * 批量删除菜品管理
     *
     * @param productIds 需要删除的菜品管理主键
     * @return 结果
     */
    @Override
    public int deleteProductInfoByProductIds(Integer[] productIds)
    {
        return productInfoMapper.deleteProductInfoByProductIds(productIds);
    }

    /**
     * 删除菜品管理信息
     *
     * @param productId 菜品管理主键
     * @return 结果
     */
    @Override
    public int deleteProductInfoByProductId(Integer productId)
    {
        return productInfoMapper.deleteProductInfoByProductId(productId);
    }
}
