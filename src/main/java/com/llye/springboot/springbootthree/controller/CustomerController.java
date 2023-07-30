package com.llye.springboot.springbootthree.controller;

import com.llye.springboot.springbootthree.dto.CustomerDto;
import com.llye.springboot.springbootthree.dto.CustomerRequestDto;
import com.llye.springboot.springbootthree.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        Optional<CustomerDto> maybeCustomer = customerService.create(customerRequestDto);
        return maybeCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.CREATED))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping
    private ResponseEntity<List<CustomerDto>> getAllCustomers(@RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam(defaultValue = "10") int pageSize) {
        List<CustomerDto> customerDtos = customerService.findAll(pageNumber, pageSize);
        if (CollectionUtils.isEmpty(customerDtos)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    private ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") long id) {
        Optional<CustomerDto> maybeCustomer = customerService.findById(id);
        return maybeCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") long id, @RequestBody CustomerRequestDto customerRequestDto) {
        Optional<CustomerDto> maybeCustomer = customerService.update(id, customerRequestDto);
        return maybeCustomer.map(customerDto -> new ResponseEntity<>(customerDto, HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        try {
            customerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
