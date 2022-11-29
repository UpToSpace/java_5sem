package com.example.springfilm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.springfilm.forms.FilmForm;
import com.example.springfilm.model.Film;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class FilmController {
    private static List<Film> films = new ArrayList<>();

    static {
        films.add(new Film("The Pianist", "Roman Polanski"));
        films.add(new Film("The Matrix", "The Wachowskis"));
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index GET");
        return modelAndView;
    }

    @RequestMapping(value = {"/allfilms"}, method = RequestMethod.GET)
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("filmlist");
        model.addAttribute("films", films);
        log.info("all films GET");
        return modelAndView;
    }

    @RequestMapping(value = {"/addfilm"}, method = RequestMethod.GET)
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addfilm");
        FilmForm filmForm = new FilmForm();
        model.addAttribute("filmform", filmForm);
        return modelAndView;
    }

    @RequestMapping(value = {"/addfilm"}, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, //
                                   @ModelAttribute("filmform") FilmForm filmForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("filmlist");
        String title = filmForm.getTitle();
        String director = filmForm.getDirector();
        if (title != null && title.length() > 0 //
                && director != null && director.length() > 0) {
            Film newFilm = new Film(title, director);
            films.add(newFilm);
            model.addAttribute("films", films);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addfilm");
        return modelAndView;
    }

    @RequestMapping(value = {"/deletefilm"}, method = RequestMethod.GET)
    public ModelAndView showDeletePersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("deletefilm");
        FilmForm filmForm = new FilmForm();
        model.addAttribute("filmform", filmForm);
        return modelAndView;
    }

    @RequestMapping(value = {"/deletefilm"}, method = RequestMethod.POST)
    public ModelAndView deleteFilm(Model model, //
                                   @ModelAttribute("filmform") FilmForm filmForm) {
        ModelAndView modelAndView = new ModelAndView();
        int id = filmForm.getId();
        for (Film film:
             films) {
            if (film.getId() == id) {
                films.remove(film);
                modelAndView.setViewName("filmlist");
                model.addAttribute("films", films);
                return modelAndView;
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("deletefilm");
        return modelAndView;
    }

    @GetMapping(value = {"/changefilm"})
    public ModelAndView showChangePersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("changefilm");
        FilmForm filmForm = new FilmForm();
        model.addAttribute("filmform", filmForm);
        return modelAndView;
    }

    @PostMapping(value = {"/changefilm"})
    public ModelAndView changeFilm(Model model, //
                                   @ModelAttribute("filmform") FilmForm filmForm) {
        ModelAndView modelAndView = new ModelAndView();
        String title = filmForm.getTitle();
        String director = filmForm.getDirector();
        if (title != null && title.length() > 0 //
                && director != null && director.length() > 0) {
            int id = filmForm.getId();
            for (Film film :
                    films) {
                if (film.getId() == id) {
                    films.remove(film);
                    films.add(new Film(id, title, director));
                    modelAndView.setViewName("filmlist");
                    model.addAttribute("films", films);
                    return modelAndView;
                }
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("changefilm");
        return modelAndView;
    }
}
