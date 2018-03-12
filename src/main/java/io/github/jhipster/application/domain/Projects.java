package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Projects.
 */
@Entity
@Table(name = "projects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @ManyToOne
    private Organizations organizations;

    @OneToOne
    @JoinColumn(unique = true)
    private Organizations organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Projects name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Projects description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Projects creationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Organizations getOrganizations() {
        return organizations;
    }

    public Projects organizations(Organizations organizations) {
        this.organizations = organizations;
        return this;
    }

    public void setOrganizations(Organizations organizations) {
        this.organizations = organizations;
    }

    public Organizations getOrganization() {
        return organization;
    }

    public Projects organization(Organizations organizations) {
        this.organization = organizations;
        return this;
    }

    public void setOrganization(Organizations organizations) {
        this.organization = organizations;
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
        Projects projects = (Projects) o;
        if (projects.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projects.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projects{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
