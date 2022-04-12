package Content.Caster;

import Content.Validator.ValidatorPerson;
import Exception.InvalidNamePersonException;
import Exception.InvalidBirthdayPersonException;
import Exception.InvalidHeightPersonException;
import Exception.InvalidWeightPersonException;
import Exception.InvalidPassportIDPersonException;

import java.time.LocalDateTime;
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
            throw new InvalidNamePersonException();
        }
    }

    /**
     * @return checked birthday from the string
     */
    public LocalDateTime castBirthday(String inputStr){
        if (valPer.birthdayValid(inputStr)){
            String [] data = inputStr.split("-");
            LocalDateTime date;
            if (data.length == 3) {
                date = LocalDateTime.of(Integer.parseInt(data[0]),
                                        Integer.parseInt(data[1]),
                                        Integer.parseInt(data[2]),
                                        0,
                                        0);
                return date;
            } else {
                throw new InvalidBirthdayPersonException();
            }
        } else {
            throw new InvalidBirthdayPersonException();
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
            throw new InvalidHeightPersonException();
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
            throw new InvalidWeightPersonException();
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
            throw new InvalidPassportIDPersonException();
        }
    }
}
