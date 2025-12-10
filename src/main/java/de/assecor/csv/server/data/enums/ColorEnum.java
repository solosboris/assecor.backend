package de.assecor.csv.server.data.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ColorEnum {

    BLUE(1,"blue"),
    GREEN(2,"green"),
    PURPLE(3, "purple"),
    RED(4, "red"),
    YELLOW(5, "yellow"),
    TURQUOISE(6, "turquoise"),
    WHITE(7, "white");

    private final int id;
    private final String color;

    ColorEnum(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public String toString() {
        return Integer.toString(id)
                .concat(":")
                .concat(color);
    }

    public static ColorEnum getByColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("empty color value");
        }
        String trimmedColor = color.trim();
        Optional<ColorEnum> optEnum =
            Arrays.stream(ColorEnum.values())
                .filter(
                    c -> c.getColor().equalsIgnoreCase(trimmedColor)
                ).findAny();
        if (optEnum.isPresent()) {
            return optEnum.get();
        }
        throw new IllegalArgumentException(
            ColorEnum.class.getName()
                .concat("#getByColor false color ")
                .concat(color)
        );
    }

}