package org.skypro.skyshop;

import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.product.SimpleProduct;
import org.skypro.skyshop.product.DiscountedProduct;
import org.skypro.skyshop.product.FixPriceProduct;
import org.skypro.skyshop.basket.ProductBasket;

public class App {
    public static void main(String[] args) {
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

        basket.clear();

        basket.printBasket();

        System.out.println("Стоимость пустой корзины: " + basket.getTotalPrice());
        System.out.println("Есть ли 'Молоко' в пустой корзине? " + basket.hasProductByName("Молоко"));
    }
}
