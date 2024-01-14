package ch.noseryoung.gg.controller;

import ch.noseryoung.gg.dto.UserDto;
import ch.noseryoung.gg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/user")
    public void create(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @GetMapping("/user/{id}")
    public UserDto readUserById(@PathVariable int  id) {
        return userService.readUserById(id);
    }

    @GetMapping("/user/all")
    public List<UserDto> readAllUser() {
        return userService.readAllUser();
    }

    @PutMapping("/user/{id}")
    public void updateUserById(@PathVariable int id, @RequestBody UserDto userDto) {
        userService.updateUserById(id, userDto);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
