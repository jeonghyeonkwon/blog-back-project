package com.jeonghyeon.blogapi.security.auth;

import com.jeonghyeon.blogapi.model.account.Account;
import com.jeonghyeon.blogapi.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> opAccount = accountRepository.findByUserId(username);
        if(!opAccount.isPresent()){
            throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
        }
        return new PrincipalDetails(opAccount.get());
    }
}