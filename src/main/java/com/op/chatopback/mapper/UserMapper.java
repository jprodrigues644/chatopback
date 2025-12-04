package com.op.chatopback.mapper;

import com.op.chatopback.dto.UserDto;
import com.op.chatopback.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) return null;

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
