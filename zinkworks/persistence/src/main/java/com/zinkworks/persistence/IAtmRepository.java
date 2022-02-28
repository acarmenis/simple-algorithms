package com.zinkworks.persistence;

import com.zinkworks.domain.Atm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA IAtmRepository for the atm entity
 */
@Repository
public interface IAtmRepository extends JpaRepository<Atm, Long> {}
