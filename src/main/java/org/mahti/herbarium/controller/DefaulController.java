package org.mahti.herbarium.controller;

import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.service.DefaultService;
import org.mahti.herbarium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/*")
public class DefaulController {
    
    @Autowired
    private DefaultService defaultService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private PlantRepository plantRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("appname", defaultService.getName());
        model.addAttribute("appdescription", defaultService.getDescription());
        return "index";
    }
    
    @RequestMapping(value ="login",method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value ="signup",method = RequestMethod.GET)
    public String register(Model model) {
        return "signup";
    }
    
    @RequestMapping(value ="user",method = RequestMethod.GET)
    public String user(Model model) {
        return "redirect:/users/" + userService.getAuthenticatedUser().getUsername();
    }

    @RequestMapping(value ="upload",method = RequestMethod.GET)
    public String upload(Model model) {
		model.addAttribute("userId", userService.getAuthenticatedUser().getId());
        return "upload";
    }

}
