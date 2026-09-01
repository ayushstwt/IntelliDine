package com.hunger.saviour.portal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="roles")
@Data
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "role_name", unique = true)
    private String roleName;
    public RoleEntity(String roleName){
        this.roleName = roleName;
    }
}
