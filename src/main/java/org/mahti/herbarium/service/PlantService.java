package org.mahti.herbarium.service;

import java.util.Date;
import java.util.List;
import org.mahti.herbarium.domain.Comment;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.repository.CommentRepository;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PlantService {
    
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    
    public List<Plant> getLatestPlants(int pageIndex, int pageElementsCount){
        Pageable pageable = new PageRequest(pageIndex, pageElementsCount, Sort.Direction.DESC, "date");
        Page<Plant> plantPage = plantRepository.findAll(pageable);
        List<Plant> plants = plantPage.getContent();
        return plants;
    }
    
    public int getLatestPlantsTotalPages(int pageElementsCount){
        Pageable pageable = new PageRequest(0, pageElementsCount, Sort.Direction.DESC, "date");
        Page<Plant> plantPage = plantRepository.findAll(pageable);
        return plantPage.getTotalPages();
    }
    
    public void addComment(long plantId, String commentString, String commenterUsername){
    
        Plant plant = plantRepository.findOne(plantId);
        
        Comment comment = new Comment();
        comment.setPlantId(plantId);
        comment.setComment(commentString);
        comment.setDateAdded(new Date());
        comment.setUserId(userRepository.findByUsername(commenterUsername).getId());
        commentRepository.save(comment);
        
        plant.getComments().add(comment);
        plantRepository.save(plant);
        
    }
}
