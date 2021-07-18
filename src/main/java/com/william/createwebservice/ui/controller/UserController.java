package com.william.createwebservice.ui.controller;

import com.william.createwebservice.service.UserService;
import com.william.createwebservice.shared.dto.UserDTO;
import com.william.createwebservice.ui.model.request.UserDetailsRequestModel;
import com.william.createwebservice.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDTO> users = userService.getUsers(page, limit);

        for (UserDTO userDTO : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDTO, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

        UserDTO createdUser = userService.createUser(userDTO);
        UserRest returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

}
