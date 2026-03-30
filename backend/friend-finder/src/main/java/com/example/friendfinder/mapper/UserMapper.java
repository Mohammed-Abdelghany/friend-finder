package com.example.friendfinder.mapper;

import com.example.friendfinder.dto.UserDto;
import com.example.friendfinder.model.Role;
import com.example.friendfinder.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "rolesIds", expression = "java(mapRolesToIds(user.getRoles()))")
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
    default List<Long> mapRolesToIds(List<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());  
    }
//
//    default List<Long> mapPostsToIds(List<?> posts) {
//        if (posts == null) return List.of();
//        // يجب تعديل هذا حسب Post entity عندك
//        return List.of();
//    }
}
