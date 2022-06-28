package content.caster;

import content.validator.ValidatorPerson;
import exceptions.InvalidProductFieldException;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A class which creates field of person from different classes
 */
public class CasterPersonFromString {
    /**
     * checker correctness of field
     */
    ValidatorPerson valPer = new ValidatorPerson();

    /**
     * @return checked name from the string
     */
    public String castName(String inputStr){
        if (valPer.namePersonValid(inputStr)){
            return inputStr.trim();
        } else {
            throw new InvalidProductFieldException("Invalid name person has been entered. Name isn't being null or empty!");
        }
    }

    /**
     * @return checked birthday from the string
     */
    public LocalDateTime castBirthday(String inputStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (valPer.birthdayValid(inputStr)){
            try{
                return LocalDate.from(formatter.parse(inputStr)).atStartOfDay();
            } catch (DateTimeParseException e){
                throw new InvalidProductFieldException("Invalid birthday date has been entered. ");
            }
        } else {
            throw new InvalidProductFieldException("Invalid birthday date has been entered. Value isn't being null!");
        }
    }

    public LocalDateTime castBirthday(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        try{
            return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).atStartOfDay();
        } catch (DateTimeParseException e){
            throw new InvalidProductFieldException("Invalid birthday date has been entered. ");
        }
    }

    /**
     * @return checked height from the string
     */
    public long castHeight(String inputStr){
        long height = Long.parseLong(inputStr);
        if (valPer.heightValid(height)){
            return height;
        } else {
            throw new InvalidProductFieldException("Invalid height has been entered. Value have to grated 0!");
        }
    }

    /**
     * @return checked weight from the string
     */
    public int castWeight(String inputStr){
        int weight = Integer.parseInt(inputStr);
        if (valPer.weightValid(weight)){
            return weight;
        } else {
            throw new InvalidProductFieldException("Invalid weight has been entered. Value have to grated 0!");
        }
    }

    /**
     * @return checked passport id from the string
     */
    public String castPassportID(String inputStr){
        if (inputStr.trim().equals("")) return null;
        if (valPer.passportidValid(inputStr)){
            return inputStr;
        } else {
            throw new InvalidProductFieldException("Invalid passport id has been entered. Value isn't null, it is lowered 41 and grated 6!");
        }
    }
}
