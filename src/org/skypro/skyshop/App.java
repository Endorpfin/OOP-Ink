package org.skypro.skyshop;

import org.skypro.skyshop.product.Product;
import org.skypro.skyshop.basket.ProductBasket;

class App {
    public static void main(String[] args) {
        Product milk = new Product("Молоко", 50);
        Product bread = new Product("Хлеб", 30);
        Product cheese = new Product("Сыр", 120);
        Product tea = new Product("Чай", 80);
        Product coffee = new Product("Кофе", 150);
        Product extra = new Product("Шоколад", 70);

        ProductBasket basket = new ProductBasket();

        // 1. Добавление продуктов в корзину
        basket.addProduct(milk);
        basket.addProduct(bread);
        basket.addProduct(cheese);
        basket.addProduct(tea);
        basket.addProduct(coffee);

        // 2. Попытка добавить продукт в заполненную корзину
        basket.addProduct(extra);

        // 3. Печать содержимого корзины
        basket.printBasket();

        // 4. Получение стоимости корзины
        System.out.println("Стоимость корзины: " + basket.getTotalPrice());

        // 5. Поиск товара, который есть в корзине
        System.out.println("Есть ли 'Хлеб' в корзине? " + basket.hasProductByName("Хлеб"));

        // 6. Поиск товара, которого нет в корзине
        System.out.println("Есть ли 'Яблоко' в корзине? " + basket.hasProductByName("Яблоко"));

        // 7. Очистка корзины
        basket.clear();

        // 8. Печать содержимого пустой корзины
        basket.printBasket();

        // 9. Получение стоимости пустой корзины
        System.out.println("Стоимость пустой корзины: " + basket.getTotalPrice());

        // 10. Поиск товара по имени в пустой корзине
        System.out.println("Есть ли 'Молоко' в пустой корзине? " + basket.hasProductByName("Молоко"));
    }
}