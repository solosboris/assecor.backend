package de.assecor.csv.server.data.enums;

public enum ColorEnum {

    BLUE(1,"blue"),
    GREEN(2,"green"),
    PURPLE(3, "purple"),
    RED(4, "red"),
    YELLOW(5, "yellow"),
    TURQUOISE(6, "turquoise"),
    WHITE(7, "white");

    private int id;
    private String color;

    ColorEnum(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String toString() {
        return Integer.toString(id)
                .concat(":")
                .concat(color);
    }

    public static ColorEnum getByColor(String color) {
        if (color == null || color.length() == 0) {
            return ColorEnum.WHITE;
        }
        ColorEnum [] enums = ColorEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i]
                    .getColor()
                    .toLowerCase()
                    .equals(
                        color.toLowerCase().trim()
                    )
            ) {
                return enums[i];
            }
        }
        throw new IllegalArgumentException(
                "false color ".concat(color)
        );
    }

}