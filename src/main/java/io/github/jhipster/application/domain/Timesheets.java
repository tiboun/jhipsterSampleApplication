package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Timesheets.
 */
@Entity
@Table(name = "timesheets")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Timesheets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month")
    private Integer month;

    @Column(name = "jhi_year")
    private Integer year;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @ManyToOne
    private Users users;

    @OneToOne
    @JoinColumn(unique = true)
    private Users user;

    @OneToMany(mappedBy = "timesheets")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Activities> activities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public Timesheets month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public Timesheets year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Timesheets creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Users getUsers() {
        return users;
    }

    public Timesheets users(Users users) {
        this.users = users;
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getUser() {
        return user;
    }

    public Timesheets user(Users users) {
        this.user = users;
        return this;
    }

    public void setUser(Users users) {
        this.user = users;
    }

    public Set<Activities> getActivities() {
        return activities;
    }

    public Timesheets activities(Set<Activities> activities) {
        this.activities = activities;
        return this;
    }

    public Timesheets addActivities(Activities activities) {
        this.activities.add(activities);
        activities.setTimesheets(this);
        return this;
    }

    public Timesheets removeActivities(Activities activities) {
        this.activities.remove(activities);
        activities.setTimesheets(null);
        return this;
    }

    public void setActivities(Set<Activities> activities) {
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
        Timesheets timesheets = (Timesheets) o;
        if (timesheets.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timesheets.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Timesheets{" +
            "id=" + getId() +
            ", month=" + getMonth() +
            ", year=" + getYear() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
