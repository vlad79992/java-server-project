package com.example.restservice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

	@GetMapping("/mathExpression")
    public String showForm(Model model) {
        return "mathExpression/mathExpression";
    }

}