package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Organizations;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Organizations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationsRepository extends JpaRepository<Organizations, Long> {

}
