package content;

import java.io.Serializable;

@SuppressWarnings("unused")
public enum UnitOfMeasure  implements Serializable {
    KILOGRAMS("kilograms"),
    CENTIMETERS("centimeters"),
    PCS("pcs"),
    MILLILITERS("milliliters"),
    GRAMS("grams");

    /**
     * description field of enum
     */
    private final String title;

    UnitOfMeasure(String title){
        this.title = title;
    }

    /**
     * @return description all field as string
     */
    public static String getTitleInString(){
        return System.lineSeparator() + KILOGRAMS.title + " " +
                CENTIMETERS.title + " " +
                PCS.title + " " +
                MILLILITERS.title + " " +
                GRAMS.title + System.lineSeparator();
    }

    public static String getTitleInColumn(){
        return System.lineSeparator() + KILOGRAMS.title + System.lineSeparator() +
                                        CENTIMETERS.title + System.lineSeparator() +
                                        PCS.title + System.lineSeparator() +
                                        MILLILITERS.title + System.lineSeparator() +
                                        GRAMS.title + System.lineSeparator();
    }

    /**
     * @return description of called field
     */
    public String getTitle(){
        return title;
    }

    /**
     * @return translates the string to enum
     */
    public static UnitOfMeasure fromString(String text) {
        for (UnitOfMeasure b : UnitOfMeasure.values()) {
            if (b.title.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
