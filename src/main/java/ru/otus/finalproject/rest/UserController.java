package ru.otus.finalproject.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.finalproject.domain.User;
import ru.otus.finalproject.service.users.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/registration")
    public String registrationPage(){
        return "sign-up";
    }

    @PostMapping(value = "/registration")
    public String registrationUser(User user, Model model){
        userService.createUser(user);
        model.addAttribute("username",user.getUsername());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("email",user.getEmail());
        return "redirect:/login";
    }

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

    //@Timed(GET_AUTHOR_EDIT_REQ_TIME)
    @GetMapping(value = "/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @GetMapping(value = "/user/my-info")
    public String infoUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByName(username);
        model.addAttribute("user", user);
        return "user-info";
    }

    //@Timed(PATCH_AUTHOR_REQ_TIME)
    @PatchMapping(value = "/user/{id}")
    public String saveRequest(@PathVariable("id") long id,
                              @ModelAttribute("user") @Valid User user, BindingResult result){
        //authorService.updateAuthorById(id, authorMapper.toAuthor(authorDto));
        if(result.hasErrors()){
            return "user-edit";
        }
        userService.updateUserById(id,user);
        return "user-info";
    }

    //@Timed(DELETE_AUTHOR_REQ_TIME)
    /*@DeleteMapping(value = "/user/{id}")
    public String deleteRequset(@PathVariable("id") long id){
        requestService.deleteRequestById(id);
        return "redirect:/request";
    }*/
}
