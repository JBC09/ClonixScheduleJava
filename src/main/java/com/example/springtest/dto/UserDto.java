package com.example.springtest.dto;

import com.example.springtest.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userRank;
    private boolean isAdmin;

    public UserDto(UserEntity user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userRank = user.getUserRank();
        this.isAdmin = user.isAdmin();
    }

    public UserEntity toEntity() {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserRank(userRank);
        user.setAdmin(isAdmin);
        return user;
    }

    public String getUserPw() {
        return "********";
    }

}
