package by.pashkevich.mikhail.client;

import by.pashkevich.mikhail.model.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userClient", url = "${app.vars.user.url}")
public interface UserClient {
    @GetMapping(value = "/auth")
    UserDto getUser(@RequestHeader("Authorization") String token);
}
