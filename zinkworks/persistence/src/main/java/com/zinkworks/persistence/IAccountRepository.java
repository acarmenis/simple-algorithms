package com.zinkworks.persistence;

import com.zinkworks.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA IAccountRepository for the Account entity
 */
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {}
