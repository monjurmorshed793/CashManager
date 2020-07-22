package org.cash.manager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.cash.manager.domain.enumeration.MonthType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A Expanse.
 */
@Entity
@Table(name = "expanse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
public class Expanse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "login_id", nullable = false)
    private String loginId;


    @Column(name = "voucher_no", unique = true)
    private Integer voucherNo;

    @NotNull
    @Column(name = "voucher_date", nullable = false)
    private LocalDate voucherDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    private MonthType month;


    @Lob
    @Column(name = "notes", nullable = false)
    private String notes;

    @Column(name = "total_amount", precision = 21, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "is_posted")
    private Boolean isPosted;

    @Column(name = "post_date")
    private Instant postDate;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_on")
    @CreatedDate
    private Instant createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_on")
    @LastModifiedDate
    private Instant modifiedOn;

    @OneToMany(mappedBy = "expanse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ExpanseDtl> expanseDtls = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Expanse totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean isIsPosted() {
        return isPosted;
    }

    public Expanse isPosted(Boolean isPosted) {
        this.isPosted = isPosted;
        return this;
    }

    public void setIsPosted(Boolean isPosted) {
        this.isPosted = isPosted;
    }

    public Instant getPostDate() {
        return postDate;
    }

    public Expanse postDate(Instant postDate) {
        this.postDate = postDate;
        return this;
    }

    public void setPostDate(Instant postDate) {
        this.postDate = postDate;
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

    public Set<ExpanseDtl> getExpanseDtls() {
        return expanseDtls;
    }

    public Expanse expanseDtls(Set<ExpanseDtl> expanseDtls) {
        this.expanseDtls = expanseDtls;
        return this;
    }

    public Expanse addExpanseDtl(ExpanseDtl expanseDtl) {
        this.expanseDtls.add(expanseDtl);
        expanseDtl.setExpanse(this);
        return this;
    }

    public Expanse removeExpanseDtl(ExpanseDtl expanseDtl) {
        this.expanseDtls.remove(expanseDtl);
        expanseDtl.setExpanse(null);
        return this;
    }

    public void setExpanseDtls(Set<ExpanseDtl> expanseDtls) {
        this.expanseDtls = expanseDtls;
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
            ", totalAmount=" + getTotalAmount() +
            ", isPosted='" + isIsPosted() + "'" +
            ", postDate='" + getPostDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
