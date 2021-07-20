package com.william.createwebservice.service.impl;

import com.william.createwebservice.exception.UserServiceException;
import com.william.createwebservice.io.entity.UserEntity;
import com.william.createwebservice.io.repository.UserRepository;
import com.william.createwebservice.security.UserDetailsImpl;
import com.william.createwebservice.service.UserService;
import com.william.createwebservice.shared.Utils;
import com.william.createwebservice.shared.dto.UserDTO;
import com.william.createwebservice.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getUsers(int page, int limit) {
        List<UserDTO> returnValue = new ArrayList<>();

        if (page > 0) {
            page = page - 1;
        }

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = userPage.getContent();

        for (UserEntity userEntity : users) {
            ModelMapper modelMapper = new ModelMapper();
            UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
            returnValue.add(userDTO);
        }

        return returnValue;
    }

    @Override
    public UserDTO getUser(String email) {
        Optional<UserEntity> optional = userRepository.findByEmail(email);

        if (optional.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = optional.get();
        UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);

        return returnValue;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        Optional<UserEntity> optional = userRepository.findByUserId(userId);

        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User with ID: " + userId + " not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = optional.get();
        UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);

        return returnValue;
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDTO returnValue = modelMapper.map(storedUserDetails, UserDTO.class);

        return returnValue;
    }
    @Override
    public UserDTO updateUser(String userId, UserDTO user) {
        Optional<UserEntity> optional = userRepository.findByUserId(userId);

        if (optional.isEmpty()) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        UserEntity userEntity = optional.get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        ModelMapper modelMapper = new ModelMapper();
        UserEntity updatedUserDetails = userRepository.save(userEntity);
        UserDTO returnValue = modelMapper.map(updatedUserDetails, UserDTO.class);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> optional = userRepository.findByUserId(userId);

        if (optional.isEmpty()) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        userRepository.delete(optional.get());
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByEmail(email);

        if (optional.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        UserEntity userEntity = optional.get();

        return new UserDetailsImpl(userEntity);
    }
}
