package org.cash.manager.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link org.cash.manager.domain.ExpanseDtl} entity.
 */
public class ExpanseDtlDTO implements Serializable {
    
    private Long id;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal amount;

    private String createdBy;

    private Instant createdOn;

    private String modifiedBy;

    private Instant modifiedOn;


    private Long itemId;

    private String itemName;

    private Long expanseId;

    private String expanseVoucherNo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getExpanseId() {
        return expanseId;
    }

    public void setExpanseId(Long expanseId) {
        this.expanseId = expanseId;
    }

    public String getExpanseVoucherNo() {
        return expanseVoucherNo;
    }

    public void setExpanseVoucherNo(String expanseVoucherNo) {
        this.expanseVoucherNo = expanseVoucherNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpanseDtlDTO)) {
            return false;
        }

        return id != null && id.equals(((ExpanseDtlDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpanseDtlDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitPrice=" + getUnitPrice() +
            ", amount=" + getAmount() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", itemId=" + getItemId() +
            ", itemName='" + getItemName() + "'" +
            ", expanseId=" + getExpanseId() +
            ", expanseVoucherNo='" + getExpanseVoucherNo() + "'" +
            "}";
    }
}
