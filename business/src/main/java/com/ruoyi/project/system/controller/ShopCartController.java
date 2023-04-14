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
import com.ruoyi.project.system.domain.ShopCart;
import com.ruoyi.project.system.service.IShopCartService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 购物车Controller
 * 
 * @author ws
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/system/ShopCart")
public class ShopCartController extends BaseController
{
    @Autowired
    private IShopCartService shopCartService;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('system:ShopCart:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopCart shopCart)
    {
        startPage();
        List<ShopCart> list = shopCartService.selectShopCartList(shopCart);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('system:ShopCart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ShopCart shopCart)
    {
        List<ShopCart> list = shopCartService.selectShopCartList(shopCart);
        ExcelUtil<ShopCart> util = new ExcelUtil<ShopCart>(ShopCart.class);
        return util.exportExcel(list, "购物车数据");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:ShopCart:query')")
    @GetMapping(value = "/{shopCartId}")
    public AjaxResult getInfo(@PathVariable("shopCartId") Integer shopCartId)
    {
        return AjaxResult.success(shopCartService.selectShopCartByShopCartId(shopCartId));
    }

    /**
     * 新增购物车
     */
    @PreAuthorize("@ss.hasPermi('system:ShopCart:add')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopCart shopCart)
    {
        return toAjax(shopCartService.insertShopCart(shopCart));
    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('system:ShopCart:edit')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopCart shopCart)
    {
        return toAjax(shopCartService.updateShopCart(shopCart));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('system:ShopCart:remove')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shopCartIds}")
    public AjaxResult remove(@PathVariable Integer[] shopCartIds)
    {
        return toAjax(shopCartService.deleteShopCartByShopCartIds(shopCartIds));
    }
}
