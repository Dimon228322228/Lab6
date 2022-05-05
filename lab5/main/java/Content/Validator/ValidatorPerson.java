package Content.Validator;

import java.time.LocalDateTime;

/**
 * a class check field owner
 */
public class ValidatorPerson {

    /**
     * @return true if name not null and not empty
     */
    public boolean namePersonValid(String name){
        return( name != null && !name.trim().equals(""));
    }

    /**
     * for parser
     * @return true if birthday not null
     */
    public boolean birthdayValid(LocalDateTime time){
        return (time != null);
    }

    /**
     * for console
     * @return true if birthday not null
     */
    public boolean birthdayValid(String time){
        return (time != null);
    }

    /**
     * @return true if weight grater than 0
     */
    public boolean weightValid(int weight){
        return weight > 0;
    }

    /**
     * @return true if weight grater than 0
     */
    public boolean heightValid(long height){
        return height > 0;
    }

    /**
     * @return true if passport id grater than 6, lower than 41 and may be null
     */
    public boolean passportidValid(String passportid){
        if (passportid != null) return (passportid.length() >= 6 && passportid.length() <= 41);
        else return true;
    }

}
