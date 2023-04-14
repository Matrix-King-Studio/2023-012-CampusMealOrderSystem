package com.ruoyi.project.system.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.system.domain.ProductInfo;
import com.ruoyi.project.system.service.IProductInfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 菜品管理Controller
 * 
 * @author ws
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/system/ProductInfo")
public class ProductInfoController extends BaseController
{
    @Autowired
    private IProductInfoService productInfoService;

    /**
     * 查询菜品管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:ProductInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductInfo productInfo)
    {
        startPage();
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        return getDataTable(list);
    }

    /**
     * 导出菜品管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:ProductInfo:export')")
    @Log(title = "菜品管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ProductInfo productInfo)
    {
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
        ExcelUtil<ProductInfo> util = new ExcelUtil<ProductInfo>(ProductInfo.class);
        return util.exportExcel(list, "菜品管理数据");
    }

    /**
     * 获取菜品管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:ProductInfo:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Integer productId)
    {
        return AjaxResult.success(productInfoService.selectProductInfoByProductId(productId));
    }

    /**
     * 新增菜品管理
     */
    @PreAuthorize("@ss.hasPermi('system:ProductInfo:add')")
    @Log(title = "菜品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductInfo productInfo)
    {
        return toAjax(productInfoService.insertProductInfo(productInfo));
    }

    /**
     * 修改菜品管理
     */
    @PreAuthorize("@ss.hasPermi('system:ProductInfo:edit')")
    @Log(title = "菜品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductInfo productInfo)
    {
        return toAjax(productInfoService.updateProductInfo(productInfo));
    }

    /**
     * 删除菜品管理
     */
    @PreAuthorize("@ss.hasPermi('system:ProductInfo:remove')")
    @Log(title = "菜品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Integer[] productIds)
    {
        return toAjax(productInfoService.deleteProductInfoByProductIds(productIds));
    }
}
