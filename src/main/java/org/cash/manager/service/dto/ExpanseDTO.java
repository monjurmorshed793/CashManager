package org.cash.manager.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import org.cash.manager.domain.enumeration.MonthType;

/**
 * A DTO for the {@link org.cash.manager.domain.Expanse} entity.
 */
public class ExpanseDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String loginId;

    
    private Integer voucherNo;

    @NotNull
    private LocalDate voucherDate;

    @NotNull
    private MonthType month;

    
    @Lob
    private String notes;

    private Boolean isPosted;

    private Instant postDate;

    private String createdBy;

    private Instant createdOn;

    private String modifiedBy;

    private Instant modifiedOn;


    private Long payToId;

    private String payToName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public MonthType getMonth() {
        return month;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean isIsPosted() {
        return isPosted;
    }

    public void setIsPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public void setPostDate(Instant postDate) {
        this.postDate = postDate;
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

    public Long getPayToId() {
        return payToId;
    }

    public void setPayToId(Long payToId) {
        this.payToId = payToId;
    }

    public String getPayToName() {
        return payToName;
    }

    public void setPayToName(String payToName) {
        this.payToName = payToName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpanseDTO)) {
            return false;
        }

        return id != null && id.equals(((ExpanseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpanseDTO{" +
            "id=" + getId() +
            ", loginId='" + getLoginId() + "'" +
            ", voucherNo=" + getVoucherNo() +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", month='" + getMonth() + "'" +
            ", notes='" + getNotes() + "'" +
            ", isPosted='" + isIsPosted() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", payToId=" + getPayToId() +
            ", payToName='" + getPayToName() + "'" +
            "}";
    }
}
