package de.assecor.csv.server.controller;

import de.assecor.csv.server.data.dto.DTOsContainer;
import de.assecor.csv.server.data.dto.PersonDTO;
import de.assecor.csv.server.data.mapper.PersonMapper;
import de.assecor.csv.server.data.model.Person;
import de.assecor.csv.server.service.CSVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/persons")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
@Slf4j
public class CSVController {

    private final CSVService csvService;

    @GetMapping(
        produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DTOsContainer> allPersons() {
        log.info("allPersons");
        List<Person> persons = csvService.getPersons();
        log.info("allPersons size {}", persons.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .body(
                    new DTOsContainer(
                        persons.stream()
                            .map(PersonMapper.INSTANCE::model2Dto)
                            .toList()
                    )
                );
    }

    @PostMapping(
        value = "/person",
        produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> addPerson(
        @RequestBody PersonDTO person
    ) {
        log.info("addPerson {}", person);
        int storedId = csvService.addPerson(
            PersonMapper.INSTANCE.dto2Model(person)
        );
        log.info("addPerson answer {}", person);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .body(storedId);
    }

    @GetMapping(
        value = "/person/{id}",
        produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTO> personById(
        @PathVariable String id
    ) {
        log.info("personsById id {}", id);
        Person person = csvService.getPersonById(
            Integer.parseInt(id)
        );
        log.info("personsById person {}",
            (person != null ? person : "null")
        );
        return person != null ?
                ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin", "*")
                    .body(
                        PersonMapper.INSTANCE.model2Dto(person)
                    ) :
                ResponseEntity
                    .notFound()
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }

    @GetMapping(
            value = "/color/{color}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DTOsContainer> personsByColor(
        @PathVariable String color
    ) {
        log.info("personsByColor color {}", color);
        List<Person> persons = csvService.getPersonsByColor(color);
        log.info("personsByColor size {}", persons.size());
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .body(
                    new DTOsContainer(
                        persons.stream()
                            .map(PersonMapper.INSTANCE::model2Dto)
                            .toList()
                    )
                );
    }

    @GetMapping(
        value = "/colorcounter/{color}",
        produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> personsByColorCounter(
        @PathVariable String color
    ) {
        log.info("personsByColor color {}", color);
        int personsCounter =
                csvService.getPersonsByColorCounter(color);
        log.info("personsByColor color {}, cnt {}",
                color, personsCounter
        );
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .body(personsCounter);
    }

}