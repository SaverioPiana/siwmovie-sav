package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.controller.util.FileUploadUtil;
import it.uniroma3.siw.siwmoviesav.model.User;
import it.uniroma3.siw.siwmoviesav.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/registered/profile")
    public String showProfilePage(Model model){
        return "/registered/profile";
    }
    @PostMapping("/registered/saveProfileImage")
    public String saveProfileImage(User user, @RequestParam("image")MultipartFile multipartFile, Model model) throws IOException{
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        user.setPicFilename(fileName);
        userService.saveUser(user);
        String uploadDir = "src/main/upload/images/user_pics/" + user.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    return showProfilePage(model);
    }
}
