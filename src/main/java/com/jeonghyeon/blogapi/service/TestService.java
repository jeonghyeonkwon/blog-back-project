package com.jeonghyeon.blogapi.service;

import com.jeonghyeon.blogapi.dto.request.AccountRequest;
import com.jeonghyeon.blogapi.model.account.Account;
import com.jeonghyeon.blogapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public Long register(AccountRequest dto){
        Optional<Account> byUserId = accountRepository.findByUserId(dto.getUserId());
        if(byUserId.isPresent()){
            return null;
        }
        Account account = new Account(dto.getUserId(),passwordEncoder.encode(dto.getUserPassword()),dto.getUserName());
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();
    }
    @Transactional
    public void createAdmin() {
        accountRepository.save(new Account("givejeong", passwordEncoder.encode("1234"), "권정현"));
    }
}
