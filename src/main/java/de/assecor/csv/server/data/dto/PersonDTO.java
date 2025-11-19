package de.assecor.csv.server.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonDTO {

    private int id;
    private String name;
    private String lastName;
    private String zip;
    private String city;
    private String color;

}