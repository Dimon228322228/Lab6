package gui;

import util.LanguageManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Reflector {

    LanguageManager language;

    public Reflector(LanguageManager language){
        this.language = language;
    }

    public ArrayList<Class<?>> columnClasses(Class<?> anyClass){
        return Arrays.stream(anyClass.getDeclaredFields())
//                .sorted(Comparator.comparing(Field::getName))
                .map(Field::getType)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> columnNames(Class<?> anyClass){
        return Arrays.stream(anyClass.getDeclaredFields())
                .map(Field::getName)
                .map(language::getString)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
