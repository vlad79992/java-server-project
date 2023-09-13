package com.example.restservice;

import org.springframework.stereotype.Controller;

//import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyTestController {

  //gets html from a default 'resources/public' or 'resources/static' folder
  @RequestMapping(path="/test")
  public String getWelcomePage(){
      return "test.html";
  }

  //gets html from a default 'resources/public' or 'resources/static' folder
  @RequestMapping("/test1")
  public ModelAndView getWelcomePageAsModel() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("test.html");
      return modelAndView;
  }
}