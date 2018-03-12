package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activities.
 */
@Entity
@Table(name = "activities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Timesheets timesheets;

    @OneToOne
    @JoinColumn(unique = true)
    private Projects project;

    @OneToMany(mappedBy = "activities")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Costs> costs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timesheets getTimesheets() {
        return timesheets;
    }

    public Activities timesheets(Timesheets timesheets) {
        this.timesheets = timesheets;
        return this;
    }

    public void setTimesheets(Timesheets timesheets) {
        this.timesheets = timesheets;
    }

    public Projects getProject() {
        return project;
    }

    public Activities project(Projects projects) {
        this.project = projects;
        return this;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }

    public Set<Costs> getCosts() {
        return costs;
    }

    public Activities costs(Set<Costs> costs) {
        this.costs = costs;
        return this;
    }

    public Activities addCosts(Costs costs) {
        this.costs.add(costs);
        costs.setActivities(this);
        return this;
    }

    public Activities removeCosts(Costs costs) {
        this.costs.remove(costs);
        costs.setActivities(null);
        return this;
    }

    public void setCosts(Set<Costs> costs) {
        this.costs = costs;
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
        Activities activities = (Activities) o;
        if (activities.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activities.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activities{" +
            "id=" + getId() +
            "}";
    }
}
