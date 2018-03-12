package io.github.jhipster.application.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Activities entity.
 */
public class ActivitiesDTO implements Serializable {

    private Long id;

    private Long timesheetsId;

    private Long projectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimesheetsId() {
        return timesheetsId;
    }

    public void setTimesheetsId(Long timesheetsId) {
        this.timesheetsId = timesheetsId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectsId) {
        this.projectId = projectsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivitiesDTO activitiesDTO = (ActivitiesDTO) o;
        if(activitiesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activitiesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivitiesDTO{" +
            "id=" + getId() +
            "}";
    }
}
