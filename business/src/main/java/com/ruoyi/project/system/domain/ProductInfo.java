package com.ruoyi.project.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 菜品管理对象 product_info
 *
 * @author ws
 * @date 2023-03-13
 */
public class ProductInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜品ID */
    private Integer productId;

    /** 菜名 */
    @Excel(name = "菜名")
    private String productName;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal productPrice;

    /** 详情 */
    @Excel(name = "详情")
    private String description;

    /** 口味 */
    @Excel(name = "口味")
    private Integer productFlavour;

    /** 商家ID */
    @Excel(name = "商家ID")
    private Long businessId;

    /** 商家 */
    @Excel(name = "商家")
    private String businessName;

    /** 类型 */
    private Integer type;

    /** 状态 */
    private String status;

    /** 图片 */
    @Excel(name = "图片")
    private String productPicture;

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
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setProductFlavour(Integer productFlavour)
    {
        this.productFlavour = productFlavour;
    }

    public Integer getProductFlavour()
    {
        return productFlavour;
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
    public void setProductPicture(String productPicture)
    {
        this.productPicture = productPicture;
    }

    public String getProductPicture()
    {
        return productPicture;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("productPrice", getProductPrice())
            .append("description", getDescription())
            .append("productFlavour", getProductFlavour())
            .append("businessId", getBusinessId())
            .append("businessName", getBusinessName())
            .append("type", getType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("productPicture", getProductPicture())
            .toString();
    }
}
