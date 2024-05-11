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
    public String init() {
        return "plan";
    }

    @PostMapping("/result")
    public String submitPlan(Model model, @ModelAttribute("url") String url) {
        String plan = plannerSerivce.getPlan(url);
        model.addAttribute("plan", plan);
        return "result";
    }
}
