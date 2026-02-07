package org.skypro.skyshop;

import org.skypro.skyshop.article.Article;
import org.skypro.skyshop.basket.ProductBasket;
import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.FixPriceProduct;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.search.SearchEngine;
import org.skypro.skyshop.search.Searchable;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        SimpleProduct milk = new SimpleProduct("Молоко", 50);
        SimpleProduct bread = new SimpleProduct("Хлеб", 30);
        DiscountedProduct cheese = new DiscountedProduct("Сыр", 120, 25);
        DiscountedProduct tea = new DiscountedProduct("Чай", 80, 10);
        FixPriceProduct coffee = new FixPriceProduct("Кофе");
        SimpleProduct extra = new SimpleProduct("Шоколад", 70);

        ProductBasket basket = new ProductBasket();

        basket.addProduct(milk);
        basket.addProduct(bread);
        basket.addProduct(cheese);
        basket.addProduct(tea);
        basket.addProduct(coffee);

        basket.addProduct(extra);

        basket.printBasket();

        System.out.println("Стоимость корзины: " + basket.getTotalPrice());
        System.out.println("Есть ли 'Хлеб' в корзине? " + basket.hasProductByName("Хлеб"));
        System.out.println("Есть ли 'Яблоко' в корзине? " + basket.hasProductByName("Яблоко"));

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
        engine.add(extra);

        Article article1 = new Article("Как выбрать молоко", "Молоко бывает разной жирности и от разных производителей.");
        Article article2 = new Article("Рецепты с сыром", "Сыр отлично подходит для пасты и салатов.");
        Article article3 = new Article("Чай и кофе", "Чай и кофе — популярные напитки по утрам.");
        engine.add(article1);
        engine.add(article2);
        engine.add(article3);

        System.out.println("\nПоиск по \"Молоко\": " + Arrays.toString(engine.search("Молоко")));
        System.out.println("Поиск по \"Сыр\": " + Arrays.toString(engine.search("Сыр")));
        System.out.println("Поиск по \"Чай\": " + Arrays.toString(engine.search("Чай")));
        System.out.println("Поиск по \"рецепт\": " + Arrays.toString(engine.search("рецепт")));
    }
}