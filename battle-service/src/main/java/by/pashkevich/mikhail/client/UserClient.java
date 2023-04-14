package by.pashkevich.mikhail.client;

import by.pashkevich.mikhail.model.dto.UserDto;
import feign.Param;
import feign.RequestLine;

public interface UserClient {
    @RequestLine("GET /username/{jwt}")
    UserDto getUser(@Param("jwt") String jwt);
}
