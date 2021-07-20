package com.william.createwebservice.io.repository;

import com.william.createwebservice.io.entity.RoleEntity;
import com.william.createwebservice.ui.model.response.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class RoleRepositoryTest {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateNewRole() {
        createNewRole(Role.ROLE_USER);
        createNewRole(Role.ROLE_ADMIN);
        createNewRole(Role.ROLE_MODERATOR);

        List<RoleEntity> roles = roleRepository.findAll();
        roles.forEach(System.out::println);
    }

    public void createNewRole(Role roleName) {
        RoleEntity roleEntity = new RoleEntity();

        String publicRoleId = generateRandomString(30);
        roleEntity.setRoleId(publicRoleId);
        roleEntity.setName(roleName);

        roleRepository.save(roleEntity);
    }

    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // Generate random integers in range 0 to length
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

}