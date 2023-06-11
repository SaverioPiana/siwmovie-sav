package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.controller.util.FileUploadUtil;
import it.uniroma3.siw.siwmoviesav.controller.validator.ArtistValidator;
import it.uniroma3.siw.siwmoviesav.model.Artist;
import it.uniroma3.siw.siwmoviesav.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
public class ArtistController {
    @Autowired
    private ArtistValidator artistValidator;
    @Autowired
    private ArtistService artistService;
    @GetMapping("/artist")
    public String showArtists(Model model){
        model.addAttribute("artists", artistService.findAll());
        return "artists";
    }
    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable("id") Long id, Model model){
        model.addAttribute("artist", artistService.findById(id));
        return "artist";
    }
    //###########  ADMIN  ############
    @GetMapping("/admin/formNewArtist")
    public String formNewArtist(Model model){
        model.addAttribute("artist", new Artist());
        return "/admin/formNewArtist";
    }
    @PostMapping("/admin/artist")
    public String newArtist(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model){
        artistValidator.validate(artist, bindingResult);
        if(!bindingResult.hasErrors()){
            this.artistService.save(artist);
            model.addAttribute("movie", artist);
            return "/admin/artistAdmin";
        }else{
            return "/admin/formNewArtist";
        }
    }
    @GetMapping("/admin/artist")
    public String showArtistsAdmin(Model model){
        model.addAttribute("artists", artistService.findAll());
        return "admin/artistsAdmin";
    }
    @GetMapping("/admin/artist/{id}")
    public String getArtistAdmin(@PathVariable("id") Long id, Model model){
        Artist artist = artistService.findById(id);
        if(artist == null) return "errors/artistNotFoundError";

        model.addAttribute("artist", artist);
        return "admin/artistAdmin";
    }
    @PostMapping("/admin/saveArtistImage/{id}")
    public String saveArtistImage(@PathVariable("id") Long id,
                                  @RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        Artist artist = artistService.findById(id);
        if(artist == null) return "errors/artistNotFoundError";

        artist.setPicFilename(fileName);
        artistService.save(artist);
        String uploadDir = "src/main/upload/images/artist_pics/" + artist.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return "redirect:/admin/artist/"+ id;
    }

}
