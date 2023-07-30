package com.llye.springboot.springbootthree.service;

import com.llye.springboot.springbootthree.dto.CustomerDto;
import com.llye.springboot.springbootthree.dto.CustomerRequestDto;
import com.llye.springboot.springbootthree.entity.Customer;
import com.llye.springboot.springbootthree.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<CustomerDto> create(CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.save(Customer.builder()
                                                            .firstName(customerRequestDto.getFirstName())
                                                            .lastName(customerRequestDto.getLastName())
                                                            .build());
        return Optional.of(CustomerDto.toDto(customer));
    }

    public List<CustomerDto> findAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Page<Customer> customerPage = customerRepository.findAll(pageRequest);
        List<Customer> customers = customerPage.getContent();
        if (customers.isEmpty()) {
            return Collections.emptyList();
        }
        return customers.stream()
                        .map(CustomerDto::toDto)
                        .toList();
    }

    public Optional<CustomerDto> findById(long id) {
        Optional<Customer> maybeCustomer = customerRepository.findById(id);
        if (maybeCustomer.isPresent()) {
            Customer customer = maybeCustomer.get();
            return Optional.of(CustomerDto.toDto(customer));
        }
        return Optional.empty();
    }

    public Optional<CustomerDto> update(long id, CustomerRequestDto customerRequestDto) {
        Optional<Customer> maybeCustomer = customerRepository.findById(id);
        if (maybeCustomer.isPresent()) {
            Customer customer = maybeCustomer.get();
            if (Objects.nonNull(customerRequestDto.getFirstName())) {
                customer.setFirstName(customerRequestDto.getFirstName());
            }
            if (Objects.nonNull(customerRequestDto.getLastName())) {
                customer.setLastName(customerRequestDto.getLastName());
            }
            return Optional.of(CustomerDto.toDto(customerRepository.save(customer)));
        }
        return Optional.empty();
    }

    public void delete(long id) {
        customerRepository.deleteById(id);
    }
}
