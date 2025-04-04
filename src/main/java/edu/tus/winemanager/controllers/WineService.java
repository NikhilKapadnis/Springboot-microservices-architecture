package edu.tus.winemanager.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import edu.tus.winemanager.dao.WineRepository;
import edu.tus.winemanager.dto.Wine;
import edu.tus.winemanager.exception.WineException;
import edu.tus.winemanager.exception.WineNotFoundException;
import edu.tus.winemanager.validation.ErrorMessage;
import edu.tus.winemanager.validation.WineValidator;


@RestController
@Service
public class WineService {
	
	@Autowired
	WineRepository wineRepo;
	
	@Autowired
	WineValidator wineValidator;

	
	
	@GetMapping("/wines")
	public List<Wine> findAllWines(){
		List<Wine> wines = wineRepo.findAll();
		return wines;	
	}
	
	
	@PostMapping("/wines")
	ResponseEntity createWine(@Valid @RequestBody Wine wine) {
		try {	
		wineValidator.validateWine(wine);
		Wine savedWine=wineRepo.save(wine);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedWine);
		}catch(WineException e) {
			ErrorMessage errorMessage=new ErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}
}
