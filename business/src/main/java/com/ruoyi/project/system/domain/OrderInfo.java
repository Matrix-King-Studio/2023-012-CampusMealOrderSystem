package com.ruoyi.project.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 订单管理 对象 order_info
 *
 * @author ws
 * @date 2023-03-13
 */
public class OrderInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Integer orderId;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Integer productId;

    /** 产品名 */
    @Excel(name = "产品名")
    private String productName;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal productPrice;

    public Integer getShopNum() {
        return shopNum;
    }

    public void setShopNum(Integer shopNum) {
        this.shopNum = shopNum;
    }

    private Integer shopNum;

    /** 图片 */
    @Excel(name = "图片")
    private String productPicture;

    /** 详情 */
    @Excel(name = "详情")
    private String description;

    /** 购买人ID */
    @Excel(name = "购买人ID")
    private Long buyerId;

    /** 购买人 */
    @Excel(name = "购买人")
    private String buyerName;

    /** 类型 */
    private Integer type;

    /** 状态 */
    private String status;

    /** 商家ID */
    @Excel(name = "商家ID")
    private Long businessId;

    /** 商家 */
    @Excel(name = "商家")
    private String businessName;

    /** 口味 */
    @Excel(name = "口味")
    private Integer productFlavour;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private Integer orderStatus;

    private Integer todayOrder;

    public Integer getTodayOrder() {
        return todayOrder;
    }

    public void setTodayOrder(Integer todayOrder) {
        this.todayOrder = todayOrder;
    }

    public Integer getAllOrder() {
        return allOrder;
    }

    public void setAllOrder(Integer allOrder) {
        this.allOrder = allOrder;
    }

    private Integer allOrder;

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    private Integer orderCount;

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Integer getOrderId()
    {
        return orderId;
    }
    public void setProductId(Integer productId)
    {
        this.productId = productId;
    }

    public Integer getProductId()
    {
        return productId;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setProductPrice(BigDecimal productPrice)
    {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductPrice()
    {
        return productPrice;
    }
    public void setProductPicture(String productPicture)
    {
        this.productPicture = productPicture;
    }

    public String getProductPicture()
    {
        return productPicture;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setBuyerId(Long buyerId)
    {
        this.buyerId = buyerId;
    }

    public Long getBuyerId()
    {
        return buyerId;
    }
    public void setBuyerName(String buyerName)
    {
        this.buyerName = buyerName;
    }

    public String getBuyerName()
    {
        return buyerName;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setBusinessId(Long businessId)
    {
        this.businessId = businessId;
    }

    public Long getBusinessId()
    {
        return businessId;
    }
    public void setBusinessName(String businessName)
    {
        this.businessName = businessName;
    }

    public String getBusinessName()
    {
        return businessName;
    }
    public void setProductFlavour(Integer productFlavour)
    {
        this.productFlavour = productFlavour;
    }

    public Integer getProductFlavour()
    {
        return productFlavour;
    }
    public void setOrderStatus(Integer orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus()
    {
        return orderStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productPrice", getProductPrice())
            .append("productPicture", getProductPicture())
            .append("description", getDescription())
            .append("buyerId", getBuyerId())
            .append("buyerName", getBuyerName())
            .append("type", getType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("businessId", getBusinessId())
            .append("businessName", getBusinessName())
            .append("productFlavour", getProductFlavour())
            .append("orderStatus", getOrderStatus())
            .append("orderCount",getOrderCount())
            .toString();
    }
}
