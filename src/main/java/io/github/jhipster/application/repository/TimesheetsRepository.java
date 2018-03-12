package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Timesheets;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Timesheets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetsRepository extends JpaRepository<Timesheets, Long> {

}
