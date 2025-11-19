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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
                            .collect(Collectors.toUnmodifiableList())
                    )
                );
    }

    @PostMapping(
        value = "/person",
        produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTO> addPerson(
        @RequestBody PersonDTO person
    ) {
        log.info("addPerson {}", person);
        person.setId(
            csvService.addPerson(
                PersonMapper.INSTANCE.dto2Model(person)
            )
        );
        log.info("addPerson answer {}", person);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .body(person);
    }

    @GetMapping(
        value = "/{id}",
        produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonDTO> personById(
        @PathVariable String id
    ) {
        log.info("personsById id {}", id);
        Person person = csvService.getPersonById(
            Integer.parseInt(id)
        );
        log.info("personsById person {}", person);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .body(
                    PersonMapper.INSTANCE.model2Dto(person)
                );
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
                            .collect(Collectors.toUnmodifiableList())
                    )
                );
    }

}