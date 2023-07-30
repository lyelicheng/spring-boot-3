package com.llye.springboot.springbootthree.dto;

import com.llye.springboot.springbootthree.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private long id;
    private String type;
    private BigDecimal amount;
    private long customerId;
    private ZonedDateTime updatedAt;

    public static AccountDto toDto(Account account) {
        return AccountDto.builder()
                         .id(account.getId())
                         .type(account.getType())
                         .amount(account.getAmount())
                         .customerId(account.getCustomer()
                                            .getId())
                         .updatedAt(account.getUpdatedAt())
                         .build();
    }
}
