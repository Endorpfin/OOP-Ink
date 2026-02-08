package org.skypro.skyshop;

import org.skypro.skyshop.article.Article;
import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.FixPriceProduct;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.search.SearchEngine;
import org.skypro.skyshop.search.Searchable;
import org.skypro.skyshop.search.BestResultNotFound;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // ----- Демонстрация проверок при создании продуктов (неверные данные) -----
        System.out.println("=== Проверки при создании продуктов ===\n");

        try {
            new SimpleProduct("", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (пустое название): " + e.getMessage());
        }

        try {
            new SimpleProduct("   ", 30);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (название из пробелов): " + e.getMessage());
        }

        try {
            new SimpleProduct(null, 100);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (null название): " + e.getMessage());
        }

        try {
            new SimpleProduct("Молоко", 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (цена 0): " + e.getMessage());
        }

        try {
            new SimpleProduct("Хлеб", -10);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (отрицательная цена): " + e.getMessage());
        }

        try {
            new DiscountedProduct("Сыр", 0, 25);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (базовая цена 0): " + e.getMessage());
        }

        try {
            new DiscountedProduct("Чай", 80, 150);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (процент скидки > 100): " + e.getMessage());
        }

        try {
            new DiscountedProduct("Кофе", 100, -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка (отрицательный процент скидки): " + e.getMessage());
        }

        System.out.println();
        SimpleProduct milk = new SimpleProduct("Молоко", 50);
        SimpleProduct bread = new SimpleProduct("Хлеб", 30);
        DiscountedProduct cheese = new DiscountedProduct("Сыр", 120, 25);
        DiscountedProduct tea = new DiscountedProduct("Чай", 80, 10);
        FixPriceProduct coffee = new FixPriceProduct("Кофе");
        FixPriceProduct water = new FixPriceProduct("Вода");

        ProductBasket basket = new ProductBasket();

        basket.addProduct(milk);
        basket.addProduct(bread);
        basket.addProduct(cheese);
        basket.addProduct(tea);
        basket.addProduct(coffee);
        basket.addProduct(water);

        basket.printBasket();

        System.out.println("Стоимость корзины: " + basket.getTotalPrice());
        System.out.println("Есть ли 'Хлеб' в корзине? " + basket.hasProductByName("Хлеб"));
        System.out.println("Есть ли 'Яблоко' в корзине? " + basket.hasProductByName("Яблоко"));

        // ----- Демонстрация удаления по имени -----
        System.out.println("\n=== Удаление продукта по имени ===\n");
        List<Product> removed = basket.removeByName("Хлеб");
        System.out.println("Удалённые продукты: " + removed);
        basket.printBasket();

        List<Product> removedNone = basket.removeByName("Яблоко");
        if (removedNone.isEmpty()) {
            System.out.println("Список пуст");
        }
        basket.printBasket();

        basket.clear();

        basket.printBasket();

        System.out.println("Стоимость пустой корзины: " + basket.getTotalPrice());
        System.out.println("Есть ли 'Молоко' в пустой корзине? " + basket.hasProductByName("Молоко"));

        SearchEngine engine = new SearchEngine(20);
        engine.add(milk);
        engine.add(bread);
        engine.add(cheese);
        engine.add(tea);
        engine.add(coffee);
        engine.add(water);

        Article article1 = new Article("Как выбрать молоко", "Молоко бывает разной жирности и от разных производителей.");
        Article article2 = new Article("Рецепты с сыром", "Сыр отлично подходит для пасты и салатов.");
        Article article3 = new Article("Чай и кофе", "Чай и кофе — популярные напитки по утрам.");
        engine.add(article1);
        engine.add(article2);
        engine.add(article3);

        System.out.println("\nПоиск по \"Молоко\": " + engine.search("Молоко"));
        System.out.println("Поиск по \"Сыр\": " + engine.search("Сыр"));
        System.out.println("Поиск по \"Чай\": " + engine.search("Чай"));
        System.out.println("Поиск по \"рецепт\": " + engine.search("рецепт"));

        // ----- Демонстрация findBest -----
        System.out.println("\n=== Поиск наиболее подходящего (findBest) ===\n");

        try {
            Searchable best = engine.findBest("Молоко");
            System.out.println("Наиболее подходящий по запросу \"Молоко\": " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println("Исключение: " + e.getMessage());
        }

        try {
            Searchable best = engine.findBest("Чай");
            System.out.println("Наиболее подходящий по запросу \"Чай\": " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println("Исключение: " + e.getMessage());
        }

        try {
            Searchable best = engine.findBest("НесуществующийЗапросXYZ");
            System.out.println("Наиболее подходящий: " + best.getStringRepresentation());
        } catch (BestResultNotFound e) {
            System.out.println("Ошибка поиска: " + e.getMessage());
        }
    }
}