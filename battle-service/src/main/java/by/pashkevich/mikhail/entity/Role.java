package by.pashkevich.mikhail.entity;

import by.pashkevich.mikhail.entity.enums.Rolename;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Rolename name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
