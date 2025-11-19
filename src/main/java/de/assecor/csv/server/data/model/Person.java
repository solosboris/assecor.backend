package de.assecor.csv.server.data.model;

import de.assecor.csv.server.data.enums.ColorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    private int id;
    private String name;
    private String lastName;
    private String zip;
    private String city;
    private ColorEnum color;

    public ColorEnum getColor() {
        return color != null ? color : ColorEnum.WHITE;
    }

}