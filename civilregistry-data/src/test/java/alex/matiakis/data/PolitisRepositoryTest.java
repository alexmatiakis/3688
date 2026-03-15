package alex.matiakis.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest


public class PolitisRepositoryTest {

	@Autowired
	PolitisRepository repo;
	
	
	@Test
	void checkPolitisRetrieval() {
		Politis politis1 = new Politis.Builder("ABCD1234", "Alexandros", "Matiakis", "Male", "23-03-1984").build();
		Politis politis2 = new Politis.Builder("ABCD1235", "Babis", "Osougias", "Female", "11-11-1980").build();
		repo.save(politis1);
		repo.save(politis2);
		List<Politis> Politis = repo.findAll();
	
		
		assertEquals(Politis.size(),2);
		int matches = 0;
		for (Politis politis: Politis) {
			if (politis.equals(politis1) || politis.equals(politis2) ) {
				matches++;
			}
		}
		assertEquals(matches,2);
	
		Politis = repo.findByLegalId("ABCD1234");
		assertEquals(Politis.size(),1);
		assertEquals(Politis.get(0),politis1);
		Politis = repo.findByLegalId("ABCD1235");
		assertEquals(Politis.size(),1);
		assertEquals(Politis.get(0),politis2);
		
		Politis = repo.findByFirstName("Alexandros");
		assertEquals(Politis.size(),1);
		assertEquals(Politis.get(0),politis1);
		Politis = repo.findByFirstName("Babis");
		assertEquals(Politis.size(),1);
		assertEquals(Politis.get(0),politis2);
	}
		@ParameterizedTest
		void checkPlitisDeletion(String legalId, String firstName, String lastname, String gender, String birthDate) {
			Politis politis = new Politis.Builder(legalId,firstName,lastname,gender,birthDate).build();
			repo.save(politis);
			repo.deleteById(legalId);
			politis = repo.findById(legalId).orElse(null);
			assertNull(politis);
		
		
		
}}
