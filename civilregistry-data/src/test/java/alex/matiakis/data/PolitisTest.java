package alex.matiakis.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;



public class PolitisTest {


	@Test
	void checkEmptyConstructor() {
		Politis politis = new Politis();
		assertNull(politis.getLegalId());
		assertNull(politis.getFirstName());
		assertNull(politis.getLastName());
		assertNull(politis.getGender());
		assertNull(politis.getBirthDate());
		assertNull(politis.getVat());
		assertNull(politis.getAddress());
	}
	
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforLegalId(String legalId) {
		Politis politis = new Politis();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> politis.setLegalId(legalId));
		assertEquals("Legal ID cannot be null or empty", e.getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = { "AB123", "A123456789" })
	void checkLengthForLegalId(String legalId) {
		Politis politis = new Politis();
		Exception e = assertThrows(IllegalArgumentException.class, ()-> politis.setLegalId(legalId));
		assertEquals("Legal ID should be 8 characters", e.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "ABCD1234", "ABCDEFGH", "12345678",})
	void checkProperStringsforLegalID(String legalId) {
		Politis politis = new Politis();
		politis.setLegalId(legalId);
		assertEquals(legalId,politis.getLegalId());
	}


	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforFirstName(String firstName) {
	    Politis politis = new Politis();
	    Exception e = assertThrows(IllegalArgumentException.class, () -> politis.setFirstName(firstName));
	    assertEquals("First Name cannot be null or empty", e.getMessage());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforLastName(String lastName) {
	    Politis politis = new Politis();
	    Exception e = assertThrows(IllegalArgumentException.class, () -> politis.setLastName(lastName));
	    assertEquals("Last Name cannot be null or empty", e.getMessage());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforgender(String gender) {
	    Politis politis = new Politis();
	    Exception e = assertThrows(IllegalArgumentException.class, () -> politis.setGender(gender));
	    assertEquals("Gender cannot be null or empty", e.getMessage());
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   ", "\t", "\n" })
	void nullEmptyAndBlankStringsforbirthdate(String birthdate) {
	    Politis politis = new Politis();
	    Exception e = assertThrows(IllegalArgumentException.class, () -> politis.setBirthDate(birthdate));
	    assertEquals("BirthDate cannot be null or empty", e.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "12-12-1212", "22-12-2222" })
	void checkPositiveValsforBirthDate(String birthdate) {
	    Politis politis = new Politis();
	    politis.setBirthDate(birthdate);
		assertEquals(birthdate,politis.getBirthDate());
	}
	
	
}
