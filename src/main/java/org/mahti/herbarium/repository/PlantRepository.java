package org.mahti.herbarium.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.mahti.herbarium.domain.Plant;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository  extends JpaRepository<Plant, Long> {
	
    public List<Plant> findByOwner(String owner);
    
}
