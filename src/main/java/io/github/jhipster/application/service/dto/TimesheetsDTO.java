package io.github.jhipster.application.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Timesheets entity.
 */
public class TimesheetsDTO implements Serializable {

    private Long id;

    private Integer month;

    private Integer year;

    private ZonedDateTime creationDate;

    private Long usersId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long usersId) {
        this.userId = usersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimesheetsDTO timesheetsDTO = (TimesheetsDTO) o;
        if(timesheetsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timesheetsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimesheetsDTO{" +
            "id=" + getId() +
            ", month=" + getMonth() +
            ", year=" + getYear() +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
