package com.formal.math.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
  @GetMapping("/")
  public String Home(){
    return "home";
  }
}
