package com.william.createwebservice.ui.controller;

import com.william.createwebservice.exception.UserServiceException;
import com.william.createwebservice.service.UserService;
import com.william.createwebservice.shared.dto.UserDTO;
import com.william.createwebservice.ui.model.request.UserDetailsRequestModel;
import com.william.createwebservice.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDTO> users = userService.getUsers(page, limit);

        for (UserDTO userDTO : users) {
            ModelMapper modelMapper = new ModelMapper();
            UserRest userModel = modelMapper.map(userDTO, UserRest.class);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserRest> getUser(@PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = userService.getUserByUserId(id);
        UserRest returnValue = modelMapper.map(userDTO, UserRest.class);

        return ResponseEntity.ok(returnValue);
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

        UserDTO createdUser = userService.createUser(userDTO);
        UserRest returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserRest> updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable String id) {
        ModelMapper modelMapper = new ModelMapper();

        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

        UserDTO updateUser = userService.updateUser(id, userDTO);
        UserRest returnValue = modelMapper.map(updateUser, UserRest.class);

        return ResponseEntity.ok(returnValue);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<OperationStatusModel> deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return ResponseEntity.ok(returnValue);
    }
}
