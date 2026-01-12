package com.example.friendfinder.service.imp;

import com.example.friendfinder.dto.UserDto;
import com.example.friendfinder.helper.UserAuthenticated;
import com.example.friendfinder.service.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImp implements ProfileService {

    @Override
    public UserDto getProfile() {
        return UserAuthenticated.getUserDtoAuthenticated();
    }
}
