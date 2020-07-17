package org.cash.manager.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link org.cash.manager.domain.DepositSeq} entity.
 */
public class DepositSeqDTO implements Serializable {
    
    private Long id;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepositSeqDTO)) {
            return false;
        }

        return id != null && id.equals(((DepositSeqDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepositSeqDTO{" +
            "id=" + getId() +
            "}";
    }
}
