package com.ruoyi.project.system.mapper;

import java.util.List;
import com.ruoyi.project.system.domain.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品管理Mapper接口
 *
 * @author ws
 * @date 2023-03-13
 */
@Mapper
public interface ProductInfoMapper
{
    /**
     * 查询菜品管理
     *
     * @param productId 菜品管理主键
     * @return 菜品管理
     */
    public ProductInfo selectProductInfoByProductId(Integer productId);

    /**
     * 查询菜品管理列表
     *
     * @param productInfo 菜品管理
     * @return 菜品管理集合
     */
    public List<ProductInfo> selectProductInfoList(ProductInfo productInfo);

    /**
     * 新增菜品管理
     *
     * @param productInfo 菜品管理
     * @return 结果
     */
    public int insertProductInfo(ProductInfo productInfo);

    /**
     * 修改菜品管理
     *
     * @param productInfo 菜品管理
     * @return 结果
     */
    public int updateProductInfo(ProductInfo productInfo);

    /**
     * 删除菜品管理
     *
     * @param productId 菜品管理主键
     * @return 结果
     */
    public int deleteProductInfoByProductId(Integer productId);

    /**
     * 批量删除菜品管理
     *
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProductInfoByProductIds(Integer[] productIds);
}
