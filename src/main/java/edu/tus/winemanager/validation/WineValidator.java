package edu.tus.winemanager.validation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.tus.winemanager.dao.WineRepository;
import edu.tus.winemanager.dto.*;
import edu.tus.winemanager.exception.WineValidationException;

@Component
public class WineValidator {
	Wine wine;
	
	@Autowired
	WineRepository wineRepo;
	
	public void validateWine(Wine wine) throws WineValidationException {
		this.wine = wine;
		checkEmptyFields(wine);
		checkForVintage(wine);
		checkMoreThanThreeWinesFromCountry(wine);
		checkForGrapes(wine);
	}
	

	private void checkEmptyFields(Wine wine) throws WineValidationException {
		if ((wine.getName().length() == 0) || (wine.getCountry().length() == 0) || (wine.getYear() == 0)
				|| (wine.getGrapes().length() == 0)) {
			throw new WineValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
		}
	}
		//only allow three wines from a country
	private void checkMoreThanThreeWinesFromCountry(Wine wine) throws WineValidationException {
		List<Wine> winesFromCountry = wineRepo.findByCountry(wine.getCountry());
		if (winesFromCountry.size()>=3) {
			throw new WineValidationException(ErrorMessages.INVALID_COUNTRY.getMsg());
		}
	}
	 //if wine with name and year already exists
	private void checkForVintage(Wine wine) throws WineValidationException {
		this.wine = wine;
		Optional<Wine> savedWine = wineRepo.findByNameAndYear(wine.getName(), wine.getYear());
		if (savedWine.isPresent()){
			throw new WineValidationException(ErrorMessages.ALREADY_EXISTS.getMsg());
		}
	}
	
	private void checkForGrapes(Wine wine) throws WineValidationException {
		if ((wine.getGrapes().toUpperCase()).equals("MERLOT"))		
			throw new WineValidationException(ErrorMessages.BAD_GRAPES.getMsg());
		}
	}
