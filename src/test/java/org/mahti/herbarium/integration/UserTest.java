package org.mahti.herbarium.integration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.domain.User;
import org.mahti.herbarium.repository.PlantRepository;
import org.mahti.herbarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class UserTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PlantRepository plantRepository;
    
    private static final String USER_NAME = "Hemuli Kasvitieteilijä";
    private static final String USER_USERNAME = "hemuli";
    private static final String USER_DESCRIPTION = "Viimeinkin se kasvi on minun!";
    private static final String USER_EMAIL = "hemuli@muumilaakso.com";
    private static final String USER_PASSWORD = "herbaario";
    
    private static final String[] PLANTNAMES = {"Voikukka","Leskenlehti"};
    
    @Test
    public void testSaveUser() {
        
        User retrieved = userRepository.findByUsername(USER_USERNAME);
        assertNull(retrieved);
        
        User user = new User();
        user.setName(USER_NAME);
        user.setUsername(USER_USERNAME);
        user.setDescription(USER_DESCRIPTION);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        
        userRepository.save(user);

        retrieved = userRepository.findByUsername(USER_USERNAME);
        assertNotNull(retrieved);
        assertEquals(USER_NAME, retrieved.getName());
        assertEquals(USER_USERNAME, retrieved.getUsername());
        assertEquals(USER_DESCRIPTION, retrieved.getDescription());
        assertEquals(USER_EMAIL, retrieved.getEmail());
        
        userRepository.deleteAll();
    }
    
    @Test
    @Transactional
    public void testUserPlants(){
        
        User retrieved = userRepository.findByUsername(USER_USERNAME);
        assertNull(retrieved);    
        
        User user = new User();
        user.setName(USER_NAME);
        user.setUsername(USER_USERNAME);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        
        List<Plant> userPlants = new LinkedList();
        for (String plantName : Arrays.asList(PLANTNAMES)){
            Plant plant = new Plant();
            plant.setName(plantName);
            userPlants.add(plant);
        }
        
        user.setPlants(userPlants);
        plantRepository.save(userPlants);
        userRepository.save(user);

        retrieved = userRepository.findByUsername(USER_USERNAME);
        List<Plant> retrievedPlants = retrieved.getPlants();
        
        assertEquals(PLANTNAMES.length, retrievedPlants.size());
        
        for (int i = 0; i < PLANTNAMES.length; i++){
            assertEquals(PLANTNAMES[i], retrievedPlants.get(i).getName());
        }
        
        userRepository.deleteAll();
        plantRepository.deleteAll();
        
    }
    
}
