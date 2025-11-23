package de.assecor.csv.server.controller;

import de.assecor.csv.server.data.dto.DTOsContainer;
import de.assecor.csv.server.data.dto.PersonDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TestCSVController {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testPersonById() {
        log.info("testPersonById");

        ResponseEntity<PersonDTO> response =
            testRestTemplate.getForEntity(
                "http://localhost:"
                .concat(Integer.toString(port))
                .concat("/persons/person/5"),
                PersonDTO.class
            );
        PersonDTO dto = response.getBody();
        assertTrue(dto.getId() == 5);
    }

    @Test
    public void testPersonsByColor() {
        log.info("testPersonsByColor");

        ResponseEntity<DTOsContainer> response =
            testRestTemplate.getForEntity(
                "http://localhost:"
                .concat(Integer.toString(port))
                .concat("/persons/color/blue"),
                DTOsContainer.class
            );
        DTOsContainer container = response.getBody();
        assertTrue(container.getDtos().size() == 2);
    }

    @Test
    public void testAddPerson() throws Exception {
        log.info("testAddPerson");

        JSONObject jsonPerson = new JSONObject();
        jsonPerson.put("id", "0");
        jsonPerson.put("name", "A1");
        jsonPerson.put("lastName", "B2");
        jsonPerson.put("zip", "12345");
        jsonPerson.put("city", "C3");
        jsonPerson.put("color", "white");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlPrefix = "http://localhost:"
                                .concat(Integer.toString(port))
                                .concat("/persons");

        PersonDTO person = testRestTemplate.postForObject(
                urlPrefix.concat("/person"),
            new HttpEntity<>(jsonPerson.toString(), headers),
            PersonDTO.class
        );
        assertTrue(person != null);
        assertTrue(person.getId() > 0);

        ResponseEntity<DTOsContainer> response =
            testRestTemplate.getForEntity(
                urlPrefix,
                DTOsContainer.class
            );
        DTOsContainer container = response.getBody();
        assertTrue(container.getDtos().size() == 11);

        response = testRestTemplate.getForEntity(
                urlPrefix.concat("/color/white"),
                DTOsContainer.class
        );
        container = response.getBody();
        assertTrue(container.getDtos().size() == 3);
    }

}