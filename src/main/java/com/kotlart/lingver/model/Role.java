package com.kotlart.lingver.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Role {
    private Long id;
    private String name;

    @Id
    @Column(name = "role_pk")
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long rolePk) {
        this.id = rolePk;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!Objects.equals(id, role.id)) return false;
        if (!Objects.equals(name, role.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
