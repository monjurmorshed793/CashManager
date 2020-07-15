package org.cash.manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import org.cash.manager.domain.enumeration.MonthType;

/**
 * A Expanse.
 */
@Entity
@Table(name = "expanse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Expanse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "voucher_no")
    private Integer voucherNo;

    @Column(name = "voucher_date")
    private LocalDate voucherDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private MonthType month;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private Instant modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties(value = "expanses", allowSetters = true)
    private PayTo payTo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public Expanse loginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public Expanse voucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
        return this;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public Expanse voucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
        return this;
    }

    public void setVoucherDate(LocalDate voucherDate) {
        this.voucherDate = voucherDate;
    }

    public MonthType getMonth() {
        return month;
    }

    public Expanse month(MonthType month) {
        this.month = month;
        return this;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public String getNotes() {
        return notes;
    }

    public Expanse notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Expanse createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Expanse createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Expanse modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedOn() {
        return modifiedOn;
    }

    public Expanse modifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(Instant modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public PayTo getPayTo() {
        return payTo;
    }

    public Expanse payTo(PayTo payTo) {
        this.payTo = payTo;
        return this;
    }

    public void setPayTo(PayTo payTo) {
        this.payTo = payTo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expanse)) {
            return false;
        }
        return id != null && id.equals(((Expanse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Expanse{" +
            "id=" + getId() +
            ", loginId='" + getLoginId() + "'" +
            ", voucherNo=" + getVoucherNo() +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", month='" + getMonth() + "'" +
            ", notes='" + getNotes() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
