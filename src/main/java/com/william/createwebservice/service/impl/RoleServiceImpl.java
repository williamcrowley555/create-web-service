package com.william.createwebservice.service.impl;

import com.william.createwebservice.exception.RoleServiceException;
import com.william.createwebservice.io.entity.RoleEntity;
import com.william.createwebservice.io.repository.RoleRepository;
import com.william.createwebservice.service.RoleService;
import com.william.createwebservice.shared.dto.RoleDTO;
import com.william.createwebservice.ui.model.response.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO getRoleByName(Role roleName) {
        Optional<RoleEntity> optional = roleRepository.findByName(roleName);
        if (optional.isEmpty()) {
            throw new RoleServiceException("Role is not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        RoleEntity roleEntity = optional.get();
        RoleDTO returnValue = modelMapper.map(roleEntity, RoleDTO.class);

        return returnValue;
    }
}
