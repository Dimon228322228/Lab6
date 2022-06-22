package utilites;

import java.util.*;

public class LanguageManager {

    private Locale local = Locale.forLanguageTag("ru-RU");

    private ResourceBundle resourceBundle;

    public LanguageManager(){
        setLanguage(local);
    }

    public void setLanguage(Locale locale){
        try{
            resourceBundle = PropertyResourceBundle.getBundle("local", locale);
        } catch (NullPointerException | IllegalArgumentException | MissingResourceException e){
            System.out.println(e.getMessage());
        }
    }

    public String getString(String string){
        try{
            return resourceBundle.getString(string);
        } catch (MissingResourceException e){
            return "Nothing";
        }
    }

    public String getLocaleName(){
        switch (resourceBundle.getLocale().toString()) {
            case "ru_RU" -> {return "Русский";}
            case "el" -> {return "Ελληνική";}
            case "es_PA" -> {return "Español";}
            case "is_IS" -> {return "Íslenska";}
            default -> {return "English";}
        }
    }

    public void setLocalByString (String str){
        switch (str){
            case "Ελλην" -> {setLanguage(Locale.forLanguageTag("el"));}
            case "Español" -> {setLanguage(Locale.forLanguageTag("es-PA"));}
            case "íslenskt" -> {setLanguage(Locale.forLanguageTag("is-IS"));}
            case "Русский" -> {setLanguage(Locale.forLanguageTag("ru-RU"));}
            default -> {setLanguage(Locale.forLanguageTag("en-US"));}
        }
    }
}
