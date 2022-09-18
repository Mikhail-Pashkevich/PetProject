package by.pashkevich.mikhail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String login;
    private String password;
    private Set<Role> roles;
    private String nickname;
}
