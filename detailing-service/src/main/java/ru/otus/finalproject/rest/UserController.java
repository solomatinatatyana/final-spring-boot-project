package ru.otus.finalproject.rest;

import io.micrometer.core.annotation.Timed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.rest.dto.UserDto;
import ru.otus.finalproject.rest.mappers.UserMapper;
import ru.otus.finalproject.service.users.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.otus.finalproject.metrics.Metrics.Users.*;

@Controller
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Timed(GET_REGISTRATION_REQ_TIME)
    @GetMapping(value = "/registration")
    public String registrationPage(){
        return "sign-up";
    }

    @Timed(CREATE_USER_REQ_TIME)
    @PostMapping(value = "/registration")
    public String registrationUser(User user, Model model){
        userService.createUser(user);
        model.addAttribute("username",user.getUsername());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("email",user.getEmail());
        return "redirect:/login";
    }

    @Timed(GET_USERS_REQ_TIME)
    @GetMapping(value = "/admin/user")
    public String getUsers(
            @RequestParam(required = false, name = "fio") String firstName,
            @RequestParam(required = false, name = "phone") String phone,
            Model model){
        List<User> users;
        if(firstName!=null && !firstName.isEmpty()){
            users = userService.getAllUsers();
        }else if(phone!=null && !phone.isEmpty()) {
            users = userService.getAllUsers();
        }else{
            users = userService.getAllUsers();
        }
        model.addAttribute("users", users);
        return "user-list";
    }

    @Timed(GET_USER_EDIT_REQ_TIME)
    @GetMapping(value = "/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model){
        UserDto user = userMapper.toUserDto(userService.getUserById(id));
        model.addAttribute("user", user);
        return "user-edit";
    }

    @Timed(GET_USER_INFO_REQ_TIME)
    @GetMapping(value = "/user/my-info")
    public String infoUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByName(username);
        model.addAttribute("user", user);
        return "user-info";
    }

    @Timed(PATCH_USER_REQ_TIME)
    @PatchMapping(value = "/user/{id}")
    public String saveUser(@PathVariable("id") long id,
                              @ModelAttribute("user") @Valid UserDto user, BindingResult result){
        if(result.hasErrors()){
            return "user-edit";
        }
        userService.updateUserById(id,userMapper.toUser(user));
        return "user-info";
    }
}
