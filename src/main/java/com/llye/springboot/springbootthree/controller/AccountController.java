package com.llye.springboot.springbootthree.controller;

import com.llye.springboot.springbootthree.dto.AccountDto;
import com.llye.springboot.springbootthree.dto.AccountRequestDto;
import com.llye.springboot.springbootthree.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{customerId}/accounts")
    public ResponseEntity<AccountDto> createAccountForCustomer(@PathVariable Long customerId, @RequestBody AccountRequestDto accountRequestDto) {
        Optional<AccountDto> maybeAccount = accountService.createAccountForCustomer(customerId, accountRequestDto);
        return maybeAccount.map(account -> new ResponseEntity<>(account, HttpStatus.CREATED))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/{customerId}/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccountsForCustomer(@PathVariable Long customerId,
                                                                      @RequestParam(defaultValue = "0") int pageNumber,
                                                                      @RequestParam(defaultValue = "10") int pageSize) {
        List<AccountDto> accountDtos = accountService.getAllAccountsByCustomerId(customerId, pageNumber, pageSize);
        if (CollectionUtils.isEmpty(accountDtos)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);
    }

    @GetMapping("/{customerId}/accounts/{accountId}")
    public ResponseEntity<AccountDto> getAccountForCustomer(@PathVariable Long customerId, @PathVariable Long accountId) {
        Optional<AccountDto> maybeAccount = accountService.getAccountByCustomerIdAndAccountId(customerId, accountId);
        return maybeAccount.map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{customerId}/accounts/{accountId}")
    public ResponseEntity<AccountDto> updateAccountForCustomer(@PathVariable Long customerId,
                                                               @PathVariable Long accountId,
                                                               @RequestBody AccountRequestDto accountRequestDto) {
        Optional<AccountDto> maybeAccount = accountService.updateAccountForCustomer(customerId, accountId, accountRequestDto);
        return maybeAccount.map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{customerId}/accounts/{accountId}")
    public ResponseEntity<HttpStatus> deleteAccountForCustomer(@PathVariable Long customerId, @PathVariable Long accountId) {
        try {
            accountService.deleteAccountForCustomer(customerId, accountId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
