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
import com.ruoyi.project.system.domain.OrderInfo;
import com.ruoyi.project.system.service.IOrderInfoService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 订单管理 Controller
 * 
 * @author ws
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/system/OrderInfo")
public class OrderInfoController extends BaseController
{
    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 查询订单管理 列表
     */
    @PreAuthorize("@ss.hasPermi('system:OrderInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderInfo orderInfo)
    {
        startPage();
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        return getDataTable(list);
    }

    /**
     * 导出订单管理 列表
     */
    @PreAuthorize("@ss.hasPermi('system:OrderInfo:export')")
    @Log(title = "订单管理 ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OrderInfo orderInfo)
    {
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        ExcelUtil<OrderInfo> util = new ExcelUtil<OrderInfo>(OrderInfo.class);
        return util.exportExcel(list, "订单管理 数据");
    }

    /**
     * 获取订单管理 详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:OrderInfo:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Integer orderId)
    {
        return AjaxResult.success(orderInfoService.selectOrderInfoByOrderId(orderId));
    }

    /**
     * 新增订单管理 
     */
    @PreAuthorize("@ss.hasPermi('system:OrderInfo:add')")
    @Log(title = "订单管理 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderInfo orderInfo)
    {
        return toAjax(orderInfoService.insertOrderInfo(orderInfo));
    }

    /**
     * 修改订单管理 
     */
    @PreAuthorize("@ss.hasPermi('system:OrderInfo:edit')")
    @Log(title = "订单管理 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderInfo orderInfo)
    {
        return toAjax(orderInfoService.updateOrderInfo(orderInfo));
    }

    /**
     * 删除订单管理 
     */
    @PreAuthorize("@ss.hasPermi('system:OrderInfo:remove')")
    @Log(title = "订单管理 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Integer[] orderIds)
    {
        return toAjax(orderInfoService.deleteOrderInfoByOrderIds(orderIds));
    }
}
