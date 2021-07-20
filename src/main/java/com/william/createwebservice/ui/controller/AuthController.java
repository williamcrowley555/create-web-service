package com.william.createwebservice.ui.controller;

import com.william.createwebservice.security.UserDetailsImpl;
import com.william.createwebservice.security.jwt.JwtUtils;
import com.william.createwebservice.service.RoleService;
import com.william.createwebservice.service.UserService;
import com.william.createwebservice.shared.dto.RoleDTO;
import com.william.createwebservice.shared.dto.UserDTO;
import com.william.createwebservice.ui.model.request.UserDetailsRequest;
import com.william.createwebservice.ui.model.request.UserLoginRequest;
import com.william.createwebservice.ui.model.response.ErrorMessage;
import com.william.createwebservice.ui.model.response.JwtResponse;
import com.william.createwebservice.ui.model.response.Role;
import com.william.createwebservice.ui.model.response.SuccessMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUserId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDetailsRequest signUpRequest) {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(new Date(), "Email is already in use!"));
        }

        // Create new user's account
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(signUpRequest, UserDTO.class);

        Set<String> strRoles = signUpRequest.getRoles();
        Set<RoleDTO> roles = new HashSet<>();

        if (strRoles == null) {
            RoleDTO userRole = roleService.getRoleByName(Role.ROLE_USER);
            roles.add(userRole);
        } else {
            strRoles.stream().forEach((role) -> {
                switch (role) {
                    case "admin":
                        RoleDTO adminRole = roleService.getRoleByName(Role.ROLE_ADMIN);
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleDTO modRole = roleService.getRoleByName(Role.ROLE_MODERATOR);
                        roles.add(modRole);

                        break;
                    default:
                        RoleDTO userRole = roleService.getRoleByName(Role.ROLE_USER);
                        roles.add(userRole);
                }
            });
        }

        userDTO.setRoles(roles);
        userService.createUser(userDTO);

        return ResponseEntity.ok(new SuccessMessage(new Date(), "User registered successfully!"));
    }
}
