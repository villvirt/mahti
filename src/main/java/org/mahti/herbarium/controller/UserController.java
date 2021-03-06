package org.mahti.herbarium.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.domain.User;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setName("test");
        user.setEmail("test@test.com");
        userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        userRepository.save(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public String view(@PathVariable String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("name", user.getName());
        model.addAttribute("userId", user.getId());
        model.addAttribute("userPlants", userRepository.findOne(user.getId()).getPlants());
        return "user";
    }

    @Transactional
    @RequestMapping(value = "/{userId}/upload", method = RequestMethod.POST)
    public String postImage(Model model
            , @PathVariable Long userId
            , @RequestParam("file") MultipartFile file
            , @RequestParam(required = false, defaultValue = "") String family
            , @RequestParam(required = false, defaultValue = "") String genus
            , @RequestParam(required = false, defaultValue = "") String species
            , @Valid @RequestParam("name") String name
            ) throws IOException {

        if (file.getContentType().equals("image/gif")
                || file.getContentType().equals("image/png")
                || file.getContentType().equals("image/jpg")
                || file.getContentType().equals("image/jpeg")) {

            Plant plant = new Plant();

            if (family.trim().length() == 0
                    || genus.trim().length() == 0
                    || species.trim().length() == 0) {
                plant.setIdentified(false);
                    } else {
                        plant.setIdentified(true);
                    }

            plant.setContent(file.getBytes());
            plant.setFamily(family);
            plant.setGenus(genus);
            plant.setSpecies(species);
            plant.setName(name);
            plant = plantRepository.save(plant);

            userRepository.findOne(userId).getPlants().add(plant);
        }

        return "redirect:/upload";
    }

    @Transactional
    @RequestMapping(value = "/{userId}/plants/{plantId}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable Long userId
            , @PathVariable Long plantId) {
        Plant plant = plantRepository.findOne(plantId);
        plantRepository.delete(plant);
        userRepository.findOne(userId).getPlants().remove(plant);
        return "redirect:/upload";
    }
}
