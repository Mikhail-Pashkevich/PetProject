package by.pashkevich.mikhail.client;

import by.pashkevich.mikhail.model.dto.UserDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface UserClient {
    @RequestLine("GET /auth")
    @Headers("Authorization: {token}")
    UserDto getUser(@Param("token") String token);
}
