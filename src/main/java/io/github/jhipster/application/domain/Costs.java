package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Costs.
 */
@Entity
@Table(name = "costs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Costs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    @Column(name = "spent_days")
    private Integer spentDays;

    @ManyToOne
    private Activities activities;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public Costs dayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        return this;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getSpentDays() {
        return spentDays;
    }

    public Costs spentDays(Integer spentDays) {
        this.spentDays = spentDays;
        return this;
    }

    public void setSpentDays(Integer spentDays) {
        this.spentDays = spentDays;
    }

    public Activities getActivities() {
        return activities;
    }

    public Costs activities(Activities activities) {
        this.activities = activities;
        return this;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Costs costs = (Costs) o;
        if (costs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Costs{" +
            "id=" + getId() +
            ", dayOfMonth=" + getDayOfMonth() +
            ", spentDays=" + getSpentDays() +
            "}";
    }
}
