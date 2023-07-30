package com.llye.springboot.springbootthree.repository;

import com.llye.springboot.springbootthree.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Page<Account> findByCustomerId(Long customerId, Pageable pageable);
    Optional<Account> findByIdAndCustomerId(Long id, Long customerId);
}
