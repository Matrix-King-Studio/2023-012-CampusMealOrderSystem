package com.ruoyi.project.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 购物车对象 shop_cart
 *
 * @author ws
 * @date 2023-03-13
 */
public class ShopCart extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Integer shopCartId;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Integer productId;

    /** 产品名 */
    @Excel(name = "产品名")
    private String productName;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal productPrice;

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

    public Integer getShopNum() {
        return shopNum;
    }

    public void setShopNum(Integer shopNum) {
        this.shopNum = shopNum;
    }

    private Integer shopNum;

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

    public void setShopCartId(Integer shopCartId)
    {
        this.shopCartId = shopCartId;
    }

    public Integer getShopCartId()
    {
        return shopCartId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("shopCartId", getShopCartId())
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
            .toString();
    }
}
