package it.uniroma3.siw.siwmoviesav.controller;

import it.uniroma3.siw.siwmoviesav.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NewsController {
    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/news")
    public String showNews(Model model){
        model.addAttribute("news", newsRepository.findAll());
        return "news.html";
    }
    @GetMapping("/news/{id}")
    public String getNews(@PathVariable("id") Long id, Model model){
        model.addAttribute("snews", newsRepository.findById(id).get());
        return "singleNews.html";
    }
}
