package de.assecor.csv.server.data.repo;

import de.assecor.csv.server.data.enums.ColorEnum;
import de.assecor.csv.server.data.model.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.StringTokenizer;

@Repository
@Slf4j
@Getter
@Setter
public class CSVDataRepository {

    private final List<Person> persons;

    @Autowired
    public CSVDataRepository(
        @Value("${data.file}") String dataFile
    ) {
        log.info("CSVDataRepository dataFile {}", dataFile);
        persons = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(
                            ResourceUtils.getFile(
                                "classpath:".concat(dataFile)
                            )
                        )
                    )
                )
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                persons.add(
                    getPerson(line)
                );
            }
        } catch (Exception e) {
            log.error("DataRepository {}", e, e.toString());
        }
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Person getPersonById(int id) {
        Optional<Person> optPers =
                persons.stream()
                    .filter(p -> p.getId() == id)
                    .findFirst();
        return optPers.orElse(null);
    }

    public List<Person> getPersonsByColor(String color) {
        return persons.stream()
                .filter(
                    p -> p.getColor().getColor().equals(color)
                ).toList();
    }

    private Person getPerson(String line) {
        StringTokenizer tok = new StringTokenizer(line, ",");
        Person person = new Person();
        person.setName(tok.nextToken().trim());
        person.setLastName(tok.nextToken().trim());
        person.setZip(tok.nextToken().trim());
        person.setCity(tok.nextToken().trim());
        person.setColor(
            ColorEnum.getByColor(
                tok.nextToken().trim()
            )
        );
        person.setId(
            Integer.parseInt(
                tok.nextToken().trim()
            )
        );
        return person;
    }

    public int getMaxId() {
        OptionalInt optId =
                persons.stream()
                    .mapToInt(Person::getId)
                    .max();
        return optId.isPresent() ?
                optId.getAsInt() : 0;
    }

    public int addPerson(Person p) {
        p.setId(getMaxId() + 1);
        persons.add(p);
        return p.getId();
    }

}