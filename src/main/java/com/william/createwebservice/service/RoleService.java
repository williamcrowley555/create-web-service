package com.william.createwebservice.service;

import com.william.createwebservice.shared.dto.RoleDTO;
import com.william.createwebservice.ui.model.response.Role;

public interface RoleService {
    RoleDTO getRoleByName(Role roleName);
}
