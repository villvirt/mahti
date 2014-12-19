package org.mahti.herbarium.integration;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.domain.Comment;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.repository.UserRepository;
import org.mahti.herbarium.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PlantServiceTest {
    
    @Autowired
    private PlantRepository plantRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PlantService plantService;
    
    private final static String COMMENT = "kommentti";
    private final static String USERNAME = "test";
    
    private final static String[] PLANTNAMES = {
            "Voikukka",
            "Leskenlehti",
            "Valkovuokko",
            "Auringonkukka",
            "Päivänkakkara",
            "Sinikello",
            "Rairuoho"};
    
    @Test
    public void testAddComment(){
        
        Plant plant = new Plant();
        plant.setName(PLANTNAMES[0]);
        plantRepository.saveAndFlush(plant);
        
        plantService.addComment(plant.getId(), COMMENT, USERNAME);
        
        Plant commentedPlant = plantRepository.findOne(plant.getId());
        Comment comment = commentedPlant.getComments().iterator().next();
        assertEquals(COMMENT, comment.getComment());
        assertEquals(userRepository.findByUsername(USERNAME).getId().longValue(), comment.getUserId());
        assertEquals(plant.getId().longValue(), comment.getPlantId());
        
        plantRepository.delete(plant);
    
    }
    
    @Test
    public void testGetLatestPlantsTotalPages(){
    
        for (String plantName : Arrays.asList(PLANTNAMES)){
            Plant plant = new Plant();
            plant.setName(plantName);
            plantRepository.save(plant);
        }
        
        assertEquals(3, plantService.getLatestPlantsTotalPages(3));
        
        plantRepository.deleteAll();
    }
    
    @Test
    public void testGetLatestPlants(){
    
        for (String plantName : Arrays.asList(PLANTNAMES)){
            Plant plant = new Plant();
            plant.setName(plantName);
            plantRepository.save(plant);
        }
        
        List<Plant> secondPageOfPlants = plantService.getLatestPlants(1,3);
        
        assertEquals(3, secondPageOfPlants.size());
        assertEquals(PLANTNAMES[3], secondPageOfPlants.get(0).getName());
        assertEquals(PLANTNAMES[4], secondPageOfPlants.get(1).getName());
        assertEquals(PLANTNAMES[5], secondPageOfPlants.get(2).getName());
        
        plantRepository.deleteAll();
    }
    
}
