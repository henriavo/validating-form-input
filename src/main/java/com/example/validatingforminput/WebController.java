package com.example.validatingforminput;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String showForm(TriviaForm triviaForm, HttpServletRequest request, HttpServletResponse response) {
        int count =  request.getCookies().length;
        System.out.println("request cookie count: " + count);
        for ( Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("firstCookie")){
                String ogvalue = cookie.getValue();
                System.out.println("found cookie with value: " + ogvalue);
                ogvalue = ogvalue + "+hey";
                cookie.setValue(ogvalue);
                response.addCookie(cookie);
            }
        }
//        Cookie aCookie = new Cookie("firstCookie", "hey");
//        response.addHeader("dummy-header","dummy-value");
//        response.addCookie(aCookie);

        return "form";
    }

    @PostMapping("/")
    public ModelAndView checkPersonInfo(@ModelAttribute TriviaForm triviaForm, HttpServletRequest request, HttpServletResponse response) {
        // THIS WORKED 👍🏽
        Cookie aCookie = new Cookie("secondCookie", "hey");
//        response.addHeader("dummy-header","dummy-value");
        response.addCookie(aCookie);


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