package alex.matiakis.data;



import java.net.InetAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/politis")
public class PolitisController {
	private final PolitisRepository repo;
	
	PolitisController(PolitisRepository repo){
		this.repo = repo;
	}
	
	
	@GetMapping(produces = {"application/json"})
	List<Politis> getPolitis() {
		return repo.findAll();
		
	}
	
	@GetMapping(value = "{id}", produces = {"application/json"})
	Politis getPolitis(@PathVariable String id) {
		return repo.findById(id).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Politis with given id does not exist!"));
	}
	
	
	@PostMapping(consumes = {"application/json"})
	ResponseEntity<?> insertPolitis(@Valid @RequestBody Politis politis) {
		if (repo.findById(politis.getLegalId()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Politis with given id already exists!");
		else {
			repo.save(politis);
			try {
				String url = "http://" + InetAddress.getLocalHost().getHostName() + ":8080/api/politis/" + politis.getLegalId();
				return ResponseEntity.created(new URI(url)).build();
			}
			catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while generating the response!");
			}
		}
	}
	
	
	
	@PutMapping(value = "{id}", consumes = {"application/json"})
	ResponseEntity<?> updatePolitis(@PathVariable String id, @Valid @RequestBody Politis politis) {
		if (!politis.getLegalId().equals(id))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to update Politis with wrong id!");
		else return repo.findById(id)
	      .map(oldPolitis -> {
	    	  oldPolitis.setFirstName(politis.getFirstName());
	          oldPolitis.setLastName(politis.getLastName());
	          oldPolitis.setGender(politis.getGender());
	          oldPolitis.setBirthDate(politis.getBirthDate());
	          oldPolitis.setVat(politis.getVat());
	          oldPolitis.setAddress(politis.getAddress());
	          repo.save(oldPolitis);
	          return ResponseEntity.noContent().build();
	        })
	      .orElseThrow(() -> 
	      	new ResponseStatusException(HttpStatus.NOT_FOUND, "Politis with given id does not exist!"));
	}

	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletePolitis(@PathVariable String id) {
		return repo.findById(id)
			    .map(oldPolitis -> {
			         repo.deleteById(id);
			         return ResponseEntity.noContent().build();
			    })
			    .orElseThrow(() -> 
			      	 new ResponseStatusException(HttpStatus.NOT_FOUND, "Politis with given id does not exist!"));
	}
	
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        System.out.println("Fieldname is: " + fieldName + " ErrorMessage:" + errorMessage);
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}
