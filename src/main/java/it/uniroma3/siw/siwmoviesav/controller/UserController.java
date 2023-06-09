package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.controller.util.FileUploadUtil;
import it.uniroma3.siw.siwmoviesav.model.Movie;
import it.uniroma3.siw.siwmoviesav.model.User;
import it.uniroma3.siw.siwmoviesav.service.MovieService;
import it.uniroma3.siw.siwmoviesav.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;

    @GetMapping("/registered/profile")
    public String showProfilePage(Model model){
        return "registered/profile";
    }
    @PostMapping("/registered/saveProfileImage")
    public String saveProfileImage(@RequestParam("image")MultipartFile multipartFile, Model model) throws IOException{
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        User user = userService.getCurrentUser();
        user.setPicFilename(fileName);
        userService.save(user);
        String uploadDir = "src/main/upload/images/user_pics/" + user.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    return showProfilePage(model);
    }
    @GetMapping("/registered/addToMyList/{id}")
    public String addToWatchlist(@PathVariable("id") Long id,Model model  ){
        Movie movie = movieService.findById(id);
        if (movie==null) return "errors/movieNotFoundError";

        User user = userService.getCurrentUser();
        user.getWatchList().add(movie);
        userService.save(user);
        return "redirect:/movie";
    }
    @GetMapping("/registered/removeToMyList/{id}")
    public String removeToWatchlist(@PathVariable("id") Long id,Model model  ){
        Movie movie = movieService.findById(id);
        if (movie==null) return "errors/movieNotFoundError";

        User user = userService.getCurrentUser();
        user.getWatchList().remove(movie);
        userService.save(user);
        return "redirect:/movie";
    }
    //######ADMIN########

    @GetMapping("/admin/addToMyList/{id}")
    public String addToWatchlistAdmin(@PathVariable("id") Long id,Model model  ){
        Movie movie = movieService.findById(id);
        if (movie==null) return "errors/movieNotFoundError";

        User user = userService.getCurrentUser();
        user.getWatchList().add(movie);
        userService.save(user);
        return "redirect:/admin/movie";
    }
    @GetMapping("/admin/removeToMyList/{id}")
    public String removeToWatchlistAdmin(@PathVariable("id") Long id,Model model  ){
        Movie movie = movieService.findById(id);
        if (movie==null) return "errors/movieNotFoundError";

        User user = userService.getCurrentUser();
        user.getWatchList().remove(movie);
        userService.save(user);
        return "redirect:/admin/movie";
    }
}
