package alex.matiakis.data;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ServiceConfiguration.class)


public class ConfigCheckTest {

    @Autowired
    private ServiceConfiguration config;

    @Test
    void givenUserDefinedPOJO_whenBindingPropertiesFile_thenAllFieldsAreSet() {
    	assertNotEquals(config,null);
        assertEquals("checkhost", config.getHost());
        assertEquals("8080", config.getPort());
        assertEquals("api/politis2", config.getApi());
    }
}
