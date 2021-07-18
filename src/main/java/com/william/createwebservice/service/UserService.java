package com.william.createwebservice.service;

import com.william.createwebservice.shared.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDTO> getUsers(int page, int limit);
    UserDTO createUser(UserDTO user);
}
