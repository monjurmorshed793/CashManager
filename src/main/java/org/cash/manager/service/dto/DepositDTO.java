package org.cash.manager.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import org.cash.manager.domain.enumeration.DepositMedium;

/**
 * A DTO for the {@link org.cash.manager.domain.Deposit} entity.
 */
public class DepositDTO implements Serializable {
    
    private Long id;

    private String loginId;

    private Integer depositNo;

    private String depositBy;

    private LocalDate depositDate;

    private DepositMedium medium;

    private BigDecimal amount;

    private String createdBy;

    private Instant createdOn;

    private String modifiedBy;

    private Instant modifiedOn;

    
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

    public Integer getDepositNo() {
        return depositNo;
    }

    public void setDepositNo(Integer depositNo) {
        this.depositNo = depositNo;
    }

    public String getDepositBy() {
        return depositBy;
    }

    public void setDepositBy(String depositBy) {
        this.depositBy = depositBy;
    }

    public LocalDate getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(LocalDate depositDate) {
        this.depositDate = depositDate;
    }

    public DepositMedium getMedium() {
        return medium;
    }

    public void setMedium(DepositMedium medium) {
        this.medium = medium;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepositDTO)) {
            return false;
        }

        return id != null && id.equals(((DepositDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepositDTO{" +
            "id=" + getId() +
            ", loginId='" + getLoginId() + "'" +
            ", depositNo=" + getDepositNo() +
            ", depositBy='" + getDepositBy() + "'" +
            ", depositDate='" + getDepositDate() + "'" +
            ", medium='" + getMedium() + "'" +
            ", amount=" + getAmount() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
