package ch.noseryoung.gg.controller;

import ch.noseryoung.gg.dto.UserDto;
import ch.noseryoung.gg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @PutMapping("/user")
    public void create(UserDto userDto) {
        userService.createUser(userDto);
    }

    @GetMapping("/user/{id}")
    public UserDto readUserById(int id) {
        return userService.readUserById(id);
    }

    @GetMapping("/user/all")
    public List<UserDto> readAllUser() {
        return userService.readAllUser();
    }
}
