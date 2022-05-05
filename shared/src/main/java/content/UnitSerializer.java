package content;
import exceptions.InvalidProductFieldException;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.stream.Stream;

public class UnitSerializer extends XmlAdapter<String, UnitOfMeasure> {
    private int count = 0;
    @Override
    public UnitOfMeasure unmarshal(String v) {
        if (v == null) return null;
        boolean check = Stream.of(UnitOfMeasure.getTitleInColumn().split(System.lineSeparator()))
                .filter(x -> !x.equals(""))
                .map(String::toLowerCase)
                .anyMatch(v.toLowerCase()::equals);
        if (check){
            return UnitOfMeasure.fromString(v);
        } else {
            count++;
            System.err.println("Invalid unit product in file. Check it. "+ count +" fields of unit have been incorrectly set null. Unit should be: " + UnitOfMeasure.getTitleInString().toUpperCase());
            throw new InvalidProductFieldException("Invalid value has been entered. Choose unit of measurement include list!");
        }
    }

    @Override
    public String marshal(UnitOfMeasure v){
        return v.getTitle().toUpperCase();
    }
}
