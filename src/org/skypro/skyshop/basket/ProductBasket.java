package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ProductBasket {
    private final Map<String, List<Product>> productsByName = new HashMap<>();

    public void addProduct(Product product) {
        productsByName
                .computeIfAbsent(product.getName(), k -> new ArrayList<>())
                .add(product);
    }

    public int getTotalPrice() {
        return productsByName.values().stream()
                .flatMap(List::stream)
                .mapToInt(Product::getPrice)
                .sum();
    }

    public void printBasket() {
        if (productsByName.isEmpty()) {
            System.out.println("в корзине пусто");
        } else {
            // Печать всех продуктов
            productsByName.values().stream()
                    .flatMap(List::stream)
                    .forEach(product -> System.out.println(product.toString()));

            System.out.println("Итого: " + getTotalPrice());
            System.out.println("Специальных товаров: " + getSpecialCount());
        }
    }

    public boolean hasProductByName(String name) {
        List<Product> products = productsByName.get(name);
        return products != null && !products.isEmpty();
    }

    /**
     * Удаляет из корзины все продукты с указанным именем.
     *
     * @param name имя продукта
     * @return список удалённых продуктов; пустой список, если таких продуктов не было
     */
    public List<Product> removeByName(String name) {
        List<Product> removed = productsByName.remove(name);
        return removed == null ? new ArrayList<>() : removed;
    }

    public void clear() {
        productsByName.clear();
    }

    // Подсчёт количества специальных товаров через Stream API
    private int getSpecialCount() {
        return (int) productsByName.values().stream()
                .flatMap(List::stream)
                .filter(Product::isSpecial)
                .count();
    }
}