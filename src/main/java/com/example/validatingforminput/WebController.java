package com.example.validatingforminput;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Controller
public class WebController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
        registry.addViewController("/thanks").setViewName("thanks");
    }

    @GetMapping("/")
    public String showForm(TriviaForm triviaForm) {
        return "form";
    }

    @PostMapping("/")
    public ModelAndView checkPersonInfo(@ModelAttribute TriviaForm triviaForm) {
        ModelAndView resultModelAndView = new ModelAndView("results");
        resultModelAndView.addObject(triviaForm);

        ModelAndView formModelAndView = new ModelAndView("form");
        formModelAndView.addObject(triviaForm);

        System.out.println("triviaForm:::: " + triviaForm);

        if (triviaForm.allCorrect()){
            return resultModelAndView;
        }
        else
            return formModelAndView;
    }

    public boolean checkAnswers(TriviaForm form){
        return true;
//        if (!form.getStore().equalsIgnoreCase("apu")){
//            re
//        }
    }

    @GetMapping("/error")
    public String showError() {
        return "error";
    }

    @GetMapping("/results")
    public String results(@ModelAttribute @Valid TriviaForm triviaForm, Model model) {
        model.addAttribute("triviaform", triviaForm);
        System.out.println("hello from results() method *******"  + triviaForm.getName());
        return "results";
    }

    @PostMapping("/thanks")
    public String showThanks() {
        return "redirect:/thanks";
    }
}