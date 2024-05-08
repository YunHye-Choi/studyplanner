package com.smallcherry.studyplanner.controller;

import com.smallcherry.studyplanner.service.PlannerSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PlannerController {
    private final PlannerSerivce plannerSerivce;

    @GetMapping("")
    public String init(Model model, @ModelAttribute("input") String userInput) {
        return "plan";
    }

    @PostMapping("/result")
    public String submitPlan(Model model, @ModelAttribute("input") String userInput) {
        plannerSerivce.getPlan(userInput);
        model.addAttribute("plan", "");
        return "result";
    }
}
