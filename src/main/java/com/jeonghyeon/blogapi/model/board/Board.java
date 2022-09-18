package com.jeonghyeon.blogapi.model.board;

import com.jeonghyeon.blogapi.model.BaseTimeEntity;
import com.jeonghyeon.blogapi.model.account.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "board_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_pk")
    private Account account;

    private Long view;

    public Board(Account account, String title, String content){
        this.account = account;
        account.getBoardList().add(this);

        this.title = title;
        this.content = content;
        this.view = 1L;
    }

    public Board update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public Board deleteMapping() {
        this.account.getBoardList().remove(this);
        this.account = null;
        return this;
    }
}
