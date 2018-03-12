package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Users;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Users entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

}
