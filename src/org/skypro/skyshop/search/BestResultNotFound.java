package org.skypro.skyshop.search;

//Проверяемое исключение: подходящий объект по поисковому запросу не найден.

public class BestResultNotFound extends Exception {

    public BestResultNotFound(String searchQuery) {
        super("По подходящему запросу не найден результат: \"" + searchQuery + "\"");
    }
}