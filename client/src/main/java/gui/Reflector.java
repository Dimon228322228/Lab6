package gui;

import content.Coordinates;
import content.Person;
import content.Product;
import utilites.LanguageManager;

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
                .map(Field::getType)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> columnNames(Class<?> anyClass){
        return Arrays.stream(anyClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> getColumnNamesForProduct(){
        ArrayList<String> allClasses = new ArrayList<>();
        ArrayList<String> productColumnNames = columnNames(Product.class);
        for (String str: productColumnNames){
            if ("coordinates".equals(str)) {
                allClasses.addAll(columnNames(Coordinates.class));
            } else if ("owner".equals(str)) {
                allClasses.addAll(columnNames(Person.class));
            } else {
                allClasses.add(str);
            }
        }
        allClasses = allClasses.stream().map(language::getString).collect(Collectors.toCollection(ArrayList::new));
        return allClasses;
    }

    public ArrayList<Class<?>> getColumnTypesForProduct(){
        ArrayList<Class<?>> allClasses = new ArrayList<>();
        ArrayList<Class<?>> helped = new ArrayList<>();
        ArrayList<Class<?>> productColumnTypes = columnClasses(Product.class);
        for (Class<?> clas: productColumnTypes){
            if (Coordinates.class.equals(clas)) {
                helped.addAll(columnClasses(Coordinates.class));
            } else if (Person.class.equals(clas)) {
                helped.addAll(columnClasses(Person.class));
            } else {
                helped.add(clas);
            }
        }
        for (Class<?> clas: helped){
            if (clas.equals(int.class)) allClasses.add(Integer.class);
            else if (clas.equals(float.class)) allClasses.add(Float.class);
            else if (clas.equals(long.class)) allClasses.add(Long.class);
            else if (clas.equals(double.class)) allClasses.add(Double.class);
            else allClasses.add(clas);
        }
        allClasses.stream().forEach(System.out::println);
        return allClasses;
    }
}
