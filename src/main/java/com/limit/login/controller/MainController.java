package com.limit.login.controller;

import com.limit.login.payload.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

  @GetMapping("/login")
  public String login(@ModelAttribute("userForm") LoginRequest loginRequest,
                      BindingResult bindingResult) {
    return "login";
  }

  @GetMapping("/")
  public String loginSuccess() {
    return "success";
  }
}
