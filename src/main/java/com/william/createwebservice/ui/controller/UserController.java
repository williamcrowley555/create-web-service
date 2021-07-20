package com.william.createwebservice.ui.controller;

import com.william.createwebservice.service.UserService;
import com.william.createwebservice.shared.dto.UserDTO;
import com.william.createwebservice.ui.model.request.UserDetailsRequest;
import com.william.createwebservice.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public List<UserResponse> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserResponse> returnValue = new ArrayList<>();

        List<UserDTO> users = userService.getUsers(page, limit);

        for (UserDTO userDTO : users) {
            ModelMapper modelMapper = new ModelMapper();
            UserResponse userModel = modelMapper.map(userDTO, UserResponse.class);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = userService.getUserByUserId(id);
        UserResponse returnValue = modelMapper.map(userDTO, UserResponse.class);

        return ResponseEntity.ok(returnValue);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserDetailsRequest userDetails) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

        UserDTO createdUser = userService.createUser(userDTO);
        UserResponse returnValue = modelMapper.map(createdUser, UserResponse.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserDetailsRequest userDetails, @PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();

        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

        UserDTO updateUser = userService.updateUser(id, userDTO);
        UserResponse returnValue = modelMapper.map(updateUser, UserResponse.class);

        return ResponseEntity.ok(returnValue);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OperationStatusModel> deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return ResponseEntity.ok(returnValue);
    }
}
