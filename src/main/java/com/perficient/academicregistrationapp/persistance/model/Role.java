package com.perficient.academicregistrationapp.persistance.model;

import com.perficient.academicregistrationapp.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "`role`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(max = 255)
    @Enumerated(EnumType.STRING)
    private Roles role;
}
