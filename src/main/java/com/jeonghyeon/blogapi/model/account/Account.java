package com.jeonghyeon.blogapi.model.account;

import com.jeonghyeon.blogapi.model.BaseTimeEntity;
import com.jeonghyeon.blogapi.model.board.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "account_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String userPassword;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToMany(mappedBy = "account")
    private List<Board> boardList;

    public Account(String userId, String userPassword, String userName) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userRole = UserRole.BASIC;
    }


}
