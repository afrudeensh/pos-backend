package com.chennai.pos_backend.auth.dto.response;

import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.auth.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNo;
    private Role role;

    public static UserResponse from(User user) {
        UserResponse resp = new UserResponse();
        resp.setId(user.getId());
        resp.setName(user.getUsername());
        resp.setEmail(user.getEmail());
        resp.setPhoneNo(user.getPhone());
        resp.setRole(user.getRole());
        return resp;
    }
}