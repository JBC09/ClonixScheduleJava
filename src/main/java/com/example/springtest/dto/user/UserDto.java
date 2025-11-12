package com.example.springtest.dto.user;

import com.example.springtest.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userId;
    private String userPw;
    private String userName;

    public UserDto(UserEntity user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
    }

    public UserEntity toEntity() {
        UserEntity user = new UserEntity();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserPw(userPw);
        return user;
    }

    public String getUserPw() {
        return "********";
    }

}
