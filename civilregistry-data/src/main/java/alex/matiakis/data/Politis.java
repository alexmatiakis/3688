package alex.matiakis.data;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity 
public class Politis {
	@Id
    @NotBlank(message = "Legal ID cannot be blank!")
    @Pattern(regexp = "^.{8}$", message = "Legal ID must be exactly 8 characters!")
	private String legalId= null;
   
	@Basic(optional = false)
    @NotBlank(message = "First name is mandatory!")
    private String firstName = null;
	
	@Basic(optional = false)
    @NotBlank(message = "Last name is mandatory!")
    private String lastName = null;

    @Basic(optional = false)
    @NotBlank(message = "Gender is mandatory!")
    private String gender = null;
	
    @Basic(optional = false)
    @NotBlank(message = "Birth date is mandatory!")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{2}-[0-9]{4}$", message = "Birth date must be in DD-MM-YYYY format!")
    private String birthDate = null;
	
    @Pattern(regexp = "^[0-9]{9}$", message = "VAT must be exactly 9 digits!")
    private String vat = null;
    
    private String address = null;
    
    public Politis() {}
    
    private Politis(Builder builder) {
        this.legalId = builder.legalId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.birthDate = builder.birthDate;
        setVat(builder.vat);
        setAddress(builder.address);
    }
    
    
    public static class Builder {
        private String legalId = null;
        private String firstName = null;
        private String lastName = null;
        private String gender = null;
        private String birthDate = null;
        private String vat = null;
        private String address = null;
        
        
        private static void checkSingleValue(String value, String message) throws IllegalArgumentException {
            if (value == null || value.trim().equals("")) {
                throw new IllegalArgumentException(message + " cannot be null or empty");
            }
        }
        
        public Builder(String legalId, String firstName, String lastName, String gender, String birthDate) throws IllegalArgumentException {
            checkSingleValue(legalId, "Legal ID");
            checkSingleValue(firstName, "First Name");
            checkSingleValue(lastName, "Last Name");
            checkSingleValue(gender, "Gender");
            checkSingleValue(birthDate, "Birth Date");
            
            this.legalId = legalId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.birthDate = birthDate;
        }
        
        public Builder vat(String value) { this.vat = value; return this; }
        public Builder address(String value) { this.address = value; return this; }

        public Politis build() {
            return new Politis(this);
        }
    }
    
    
    public String toString() {
        return "Politis(" + legalId + ", " + firstName + " " + lastName + ")";
    }
    
    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) throws IllegalArgumentException {
        Builder.checkSingleValue(legalId, "Legal ID");
        if (legalId.length() != 8) {
            throw new IllegalArgumentException("Legal ID should be 8 characters");
        }
        this.legalId = legalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IllegalArgumentException {
        Builder.checkSingleValue(firstName, "First Name");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IllegalArgumentException {
        Builder.checkSingleValue(lastName, "Last Name");
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws IllegalArgumentException {
        Builder.checkSingleValue(gender, "Gender");
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) throws IllegalArgumentException {
        Builder.checkSingleValue(birthDate, "Birth Date");
        this.birthDate = birthDate;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String value) {
        if (value == null || value.trim().equals("")) this.vat = null;
        else this.vat = value.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String value) {
        if (value == null || value.trim().equals("")) this.address = null;
        else this.address = value.trim();
    }
    
    
    
        

	
}
