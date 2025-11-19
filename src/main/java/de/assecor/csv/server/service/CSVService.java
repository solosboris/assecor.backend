package de.assecor.csv.server.service;

import de.assecor.csv.server.data.model.Person;
import de.assecor.csv.server.data.repo.CSVDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CSVService {

    private final CSVDataRepository dataRepository;

    public List<Person> getPersons() {
        return dataRepository.getPersons();
    }

    public Person getPersonById(int id) {
        return dataRepository.getPersonById(id);
    }

    public List<Person> getPersonsByColor(String color) {
        return dataRepository.getPersonsByColor(color);
    }

    public int addPerson(Person p) {
        return dataRepository.addPerson(p);
    }

}