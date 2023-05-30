package com.slavynskyi.voiptestapp.web;

import com.slavynskyi.voiptestapp.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorService operatorService;

    @GetMapping("/operator")
    public ModelAndView get(@ModelAttribute("model") ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView("operators");
        modelMap.addAttribute("operatorsList", operatorService.getAllOperators());
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }

    @GetMapping("/operator/{id}")
    public ModelAndView getById(@PathVariable("id") int id, @ModelAttribute("model") ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView("operator");
        modelMap.addAttribute("operator", operatorService.getById(id));
        modelAndView.addAllObjects(modelMap);
        return modelAndView;
    }

    @GetMapping("/operator/{id}/conversation/{convId}/approve")
    public ModelAndView approveById(@PathVariable("id") int id, @PathVariable int convId, @ModelAttribute("model") ModelMap modelMap) {
        operatorService.approveById(id, convId);
        return new ModelAndView("redirect:/operator/" + id, modelMap);
    }

    @GetMapping("/operator/{id}/conversation/{convId}/decline")
    public ModelAndView declineById(@PathVariable("id") int id, @PathVariable int convId, @ModelAttribute("model") ModelMap modelMap) {
        operatorService.declineById(id, convId);
        return new ModelAndView("redirect:/operator/" + id, modelMap);
    }
}
