package com.william.createwebservice.shared.dto;

import com.william.createwebservice.ui.model.response.Role;

public class RoleDTO {

    private Long id;

    private String roleId;

    private Role name;

    public RoleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Role getName() {
        return name;
    }

    public void setName(Role name) {
        this.name = name;
    }
}
