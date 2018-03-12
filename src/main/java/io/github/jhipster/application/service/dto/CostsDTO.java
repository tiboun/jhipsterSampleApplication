package io.github.jhipster.application.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Costs entity.
 */
public class CostsDTO implements Serializable {

    private Long id;

    private Integer dayOfMonth;

    private Integer spentDays;

    private Long activitiesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getSpentDays() {
        return spentDays;
    }

    public void setSpentDays(Integer spentDays) {
        this.spentDays = spentDays;
    }

    public Long getActivitiesId() {
        return activitiesId;
    }

    public void setActivitiesId(Long activitiesId) {
        this.activitiesId = activitiesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CostsDTO costsDTO = (CostsDTO) o;
        if(costsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostsDTO{" +
            "id=" + getId() +
            ", dayOfMonth=" + getDayOfMonth() +
            ", spentDays=" + getSpentDays() +
            "}";
    }
}
