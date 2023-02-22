package com.example.algorytmy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
@Controller
public class ControllerAlgorytmy {
    List<Person> sortedPeople;
    private ServiceAlgorytmy service;

    public ControllerAlgorytmy(ServiceAlgorytmy service) {
        this.service = service;
    }

    @RequestMapping("/")
    public ModelAndView index() {
        if (sortedPeople != null && !sortedPeople.isEmpty()) sortedPeople = null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }


    @PostMapping("/upload")
    public RedirectView uploadFile(@RequestParam("file") MultipartFile file, Model model,
                                   @RequestParam("sortBy") String sortBy,
                                   @RequestParam("order") String order) throws IOException {
        if (!file.isEmpty()) {
            sortedPeople = service.getAllPersonsFromFile(file, model, sortBy, order);
        }
        return new RedirectView( "sorted-results");
    }




    @GetMapping("/sorted-results")
    public String listPersons(Model model) {
        model.addAttribute("persons", sortedPeople);
        return "sorted-results";

}}
