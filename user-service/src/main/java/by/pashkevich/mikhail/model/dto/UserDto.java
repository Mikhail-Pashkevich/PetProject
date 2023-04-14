package by.pashkevich.mikhail.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String login;
    private Set<String> roles;
}
