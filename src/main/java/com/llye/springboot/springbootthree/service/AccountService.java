package com.llye.springboot.springbootthree.service;

import com.llye.springboot.springbootthree.dto.AccountDto;
import com.llye.springboot.springbootthree.dto.AccountRequestDto;
import com.llye.springboot.springbootthree.entity.Account;
import com.llye.springboot.springbootthree.entity.Customer;
import com.llye.springboot.springbootthree.exception.BusinessException;
import com.llye.springboot.springbootthree.repository.AccountRepository;
import com.llye.springboot.springbootthree.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository,
                          CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<AccountDto> createAccountForCustomer(Long customerId, AccountRequestDto accountRequestDto) throws BusinessException {
        Optional<Customer> maybeCustomer = customerRepository.findById(customerId);
        if (maybeCustomer.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid customer id.");
        }
        Account account = accountRepository.save(Account.builder()
                                                        .type(accountRequestDto.getType())
                                                        .amount(accountRequestDto.getAmount().setScale(2, RoundingMode.DOWN))
                                                        .customer(maybeCustomer.get())
                                                        .build());
        return Optional.of(AccountDto.toDto(account));
    }

    public List<AccountDto> getAllAccountsByCustomerId(Long customerId,
                                                       int pageNumber,
                                                       int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));
        Page<Account> accountPage = accountRepository.findByCustomerId(customerId, pageRequest);
        List<Account> accounts = accountPage.getContent();
        if (accounts.isEmpty()) {
            return Collections.emptyList();
        }
        return accounts.stream()
                       .map(AccountDto::toDto)
                       .toList();
    }

    public Optional<AccountDto> getAccountByCustomerIdAndAccountId(Long customerId, Long accountId) {
        Optional<Account> maybeAccount = accountRepository.findByIdAndCustomerId(accountId, customerId);
        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            return Optional.of(AccountDto.toDto(account));
        }
        return Optional.empty();
    }

    public Optional<AccountDto> updateAccountForCustomer(Long customerId,
                                                         Long accountId,
                                                         AccountRequestDto accountRequestDto) {
        Optional<Account> maybeAccount = accountRepository.findByIdAndCustomerId(accountId, customerId);
        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            if (Objects.nonNull(accountRequestDto.getType())) {
                account.setType(accountRequestDto.getType());
            }
            if (Objects.nonNull(accountRequestDto.getAmount())) {
                account.setAmount(accountRequestDto.getAmount()
                                                   .setScale(2, RoundingMode.DOWN));
            }
            return Optional.of(AccountDto.toDto(accountRepository.save(account)));
        }
        return Optional.empty();
    }

    public void deleteAccountForCustomer(Long customerId, Long accountId) throws BusinessException {
        Optional<Account> maybeAccount = accountRepository.findByIdAndCustomerId(accountId, customerId);
        if (maybeAccount.isEmpty()) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Invalid account id for customer id - " + customerId + ".");
        }
        accountRepository.deleteById(accountId);
    }
}
