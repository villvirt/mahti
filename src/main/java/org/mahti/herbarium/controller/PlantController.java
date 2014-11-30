package org.mahti.herbarium.controller;

import java.io.IOException;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String redirect() {
        return "redirect:/";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String redirectToId(Model model, @PathVariable Long id) {
        if (plantRepository.exists(id)) {
            model.addAttribute("current", id);
        }
        if (plantRepository.exists(id + 1)) {
            model.addAttribute("next", id + 1);
        }
        if (plantRepository.exists(id - 1)) {
            model.addAttribute("previous", id - 1);
        }
        model.addAttribute("count", plantRepository.findAll().size());
        return "upload";
    }
    
    @ResponseBody
    @RequestMapping(value = "/{id}/content", produces = "image/*")
    public byte[] showImage(@PathVariable Long id) {
        return plantRepository.findOne(id).getContent();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postImage(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {
        if (file.getContentType().equals("image/gif")
                || file.getContentType().equals("image/png")
                || file.getContentType().equals("image/jpg")
                || file.getContentType().equals("image/jpeg")) {
            Plant plant = new Plant();
            plant.setContent(file.getBytes());
            plant.setUser(username);
            plantRepository.save(plant);
        }
        return "redirect:/user";
    }
}
