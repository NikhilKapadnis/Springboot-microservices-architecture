package edu.tus.winemanager.dao;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.tus.winemanager.dto.Wine;



//@Repository
public interface WineRepository extends JpaRepository<Wine, Long>{

	Page<Wine> findAll(Pageable pageable);

	Optional<Wine> findByNameAndYear(String name, int year);

	List<Wine> findByCountry(String country);

}
