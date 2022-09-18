package com.jeonghyeon.blogapi.service;

import com.jeonghyeon.blogapi.dto.PageFrame;
import com.jeonghyeon.blogapi.dto.request.AccountRequest;
import com.jeonghyeon.blogapi.dto.request.BoardRequest;
import com.jeonghyeon.blogapi.dto.response.BoardDetailResponse;
import com.jeonghyeon.blogapi.dto.response.BoardListResponse;
import com.jeonghyeon.blogapi.model.account.Account;
import com.jeonghyeon.blogapi.model.board.Board;
import com.jeonghyeon.blogapi.repository.AccountRepository;
import com.jeonghyeon.blogapi.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return savedBoard.getId();
    }

    public PageFrame boardList(Pageable pageable) {
        Page<BoardListResponse> boardPage = boardRepository.boardList(pageable);
        return new PageFrame(boardPage.getNumber(),boardPage.isFirst(),boardPage.isLast(),boardPage.getTotalPages(),boardPage.getTotalElements(),boardPage.getContent());
    }

    public BoardDetailResponse boardDetail(Long id){
        Optional<BoardDetailResponse> opBoardDetailResponse = boardRepository.boardDetail(id);
        if(opBoardDetailResponse.isEmpty()) throw new IllegalStateException("해당 게시글이 없습니다.");

        return opBoardDetailResponse.get();
    }

    @Transactional
    public Long updateBoard(Long id, String accountId, BoardRequest dto) {
        Optional<Account> opAccount = accountRepository.findByUserId(accountId);
        if(opAccount.isEmpty()) throw new IllegalStateException("해당 유저는 존재하지 않습니다.");

        Optional<Board> opBoard = boardRepository.findById(id);
        if(opBoard.isEmpty()) throw new IllegalStateException("해당 게시글은 존재하지 않습니다.");
        Board board = opBoard.get();
        if(!accountId.equals(board.getAccount().getUserId())) throw new IllegalStateException("해당 게시글을 작성한 유저가 아닙니다.");
        Board updateBoard = board.update(dto.getTitle(), dto.getContent());
        boardRepository.save(updateBoard);

        return id;
    }

    @Transactional
    public Long deleteBoard(Long id, String accountId) {
        Optional<Account> opAccount = accountRepository.findByUserId(accountId);
        if(opAccount.isEmpty()) throw new IllegalStateException("해당 유저는 존재하지 않습니다.");
        Optional<Board> opBoard = boardRepository.findById(id);
        if(opBoard.isEmpty()) throw new IllegalStateException("해당 게시글은 존재하지 않습니다.");
        Board board = opBoard.get();
        if(!accountId.equals(board.getAccount().getUserId())) throw new IllegalStateException("해당 게시글을 작성한 유저가 아닙니다.");
        board.deleteMapping();
        boardRepository.delete(board);
        return id;

    }
}
