package alex.matiakis.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PolitisRepository extends JpaRepository<Politis, String> {
	List<Politis> findByLegalId(String legalId);
	List<Politis> findByFirstName(String firstName);
	List<Politis> findByLastName(String lastName);
	List<Politis> findByGender(String gender);
	List<Politis> findByBirthDate(String birthDate);
	List<Politis> findByVat(String vat);
	List<Politis> findByAddress(String address);
	List<Politis> findByLegalIdAndLastName(String legalId, String lastName);
	List<Politis> findByFirstNameAndLastName(String firstName, String lastName);
	List<Politis> findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, String birthDate);
}
