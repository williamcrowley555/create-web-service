package com.william.createwebservice.io.entity;

import com.william.createwebservice.ui.model.response.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -693729771004156402L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role name;

    public RoleEntity() {

    }

    public RoleEntity(Role name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", roleId='" + roleId + '\'' +
                ", name=" + name +
                '}';
    }
}
