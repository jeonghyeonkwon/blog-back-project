package com.jeonghyeon.blogapi.service;

import com.jeonghyeon.blogapi.dto.request.AccountRequest;
import com.jeonghyeon.blogapi.dto.request.BoardRequest;
import com.jeonghyeon.blogapi.model.account.Account;
import com.jeonghyeon.blogapi.model.board.Board;
import com.jeonghyeon.blogapi.repository.AccountRepository;
import com.jeonghyeon.blogapi.repository.BoardRepository;
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
    private final BoardRepository boardRepository;
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
        Optional<Account> admin = accountRepository.findByUserId("givejeong");
        if(admin.isEmpty()){
            accountRepository.save(new Account("givejeong", passwordEncoder.encode("1234"), "권정현"));
        }

    }

    @Transactional
    public Long createBoard(String accountId, BoardRequest dto) {
        Optional<Account> opAccount = accountRepository.findByUserId(accountId);
        if(opAccount.isEmpty()) throw new IllegalStateException("존재하지 않는 유저 입니다.");
        Account account = opAccount.get();
        Board board = new Board(account,dto.getTitle(),dto.getContent());
        Board savedBoard = boardRepository.save(board);
        return board.getId();
    }
}
