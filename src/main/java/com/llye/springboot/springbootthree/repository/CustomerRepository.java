package com.llye.springboot.springbootthree.repository;

import com.llye.springboot.springbootthree.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
