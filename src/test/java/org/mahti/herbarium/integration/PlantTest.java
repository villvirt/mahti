package org.mahti.herbarium.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mahti.herbarium.Application;
import org.mahti.herbarium.domain.Plant;
import org.mahti.herbarium.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PlantTest {
    
    @Autowired
    private PlantRepository plantRepository;


    @Test
    public void sizeOfRepository() throws Exception {
        assertEquals("Repository size in the beginning.", 0, plantRepository.count());

        addPlantDaisy();
        assertEquals("Repository size after first plant is saved to the repository.", 1, plantRepository.count());

        addPlantWindflower();
        assertEquals("Repository size after second plant is saved to the repository.", 2, plantRepository.count());
    }

    @Test
    public void firstPlantHasRightFieldsAndContent() throws Exception {
        Long daisyId = addPlantDaisy();
        Plant plant = plantRepository.findOne(daisyId);
        // Uncomment when you know how to compare contents
        // MockMultipartFile multipartFile = new MockMultipartFile("file", "../img/LeucanthemumVulgare.jpg", "image/jpg", "LeucanthemumVulgare".getBytes());
        // assertTrue("Contents not equal.", multipartFile.getBytes().equals(plant.getContent()));

        assertEquals("Check name", "Daisy", plant.getName());
        assertEquals("Check family.", "Asteraceae", plant.getFamily());
        assertEquals("Check genus.", "Leucanthemum", plant.getGenus());
        assertEquals("Check species.", "L. vulgare", plant.getSpecies());
        // Just test if plant has some content
        assertTrue("Does content exist?", plant.getContent().length > 0);
    }

    @Test
    public void secondPlantHasRightFieldsAndContent() throws Exception {
        addPlantDaisy();
        Long WindflowerId = addPlantWindflower();
        Plant plant = plantRepository.findOne(WindflowerId);

        assertEquals("Check name", "Windflower", plant.getName());
        assertEquals("Check family.", "Ranunculales", plant.getFamily());
        assertEquals("Check genus.", "Anemone", plant.getGenus());
        assertEquals("Check species.", "A. nemorosa", plant.getSpecies());
        assertTrue("Does content exist?", plant.getContent().length > 0);
    }

    @After
    public void clear() {
        plantRepository.deleteAll();
    }

    private Long addPlantDaisy() throws Exception {
        return addPlant("Daisy", "Asteraceae", "Leucanthemum", "L. vulgare", "../img/LeucanthemumVulgare.jpg", "LeucanthemumVulgare");
    }

    private Long addPlantWindflower() throws Exception {
        return addPlant("Windflower", "Ranunculales", "Anemone", "A. nemorosa", "../img/AnemoneNemorosa.jpg", "AnemoneNemorosa");
    }

    private Long addPlant(String name, String family, String genus, String species, String fileUri, String fileName) throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileUri, "image/jpg", fileName.getBytes());

        Plant plant = new Plant();
        plant.setName(name);
        plant.setFamily(family);
        plant.setGenus(genus);
        plant.setSpecies(species);
        plant.setContent(multipartFile.getBytes());
        plantRepository.save(plant);

        return plant.getId();
    }


}
