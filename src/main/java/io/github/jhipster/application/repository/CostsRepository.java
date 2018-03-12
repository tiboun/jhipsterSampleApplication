package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Costs;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Costs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostsRepository extends JpaRepository<Costs, Long> {

}
