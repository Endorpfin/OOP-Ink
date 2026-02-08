package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductBasket {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public int getTotalPrice() {
        int sum = 0;
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    public void printBasket() {
        if (products.isEmpty()) {
            System.out.println("в корзине пусто");
        } else {
            int specialCount = 0;
            for (Product product : products) {
                System.out.println(product.toString());
                if (product.isSpecial()) {
                    specialCount++;
                }
            }
            System.out.println("Итого: " + getTotalPrice());
            System.out.println("Специальных товаров: " + specialCount);
        }
    }

    public boolean hasProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет из корзины все продукты с указанным именем.
     *
     * @param name имя продукта
     * @return список удалённых продуктов; пустой список, если таких продуктов не было
     */
    public List<Product> removeByName(String name) {
        List<Product> removed = new ArrayList<>();
        Iterator<Product> it = products.iterator();
        while (it.hasNext()) {
            Product product = it.next();
            if (product.getName().equals(name)) {
                removed.add(product);
                it.remove();
            }
        }
        return removed;
    }

    public void clear() {
        products.clear();
    }
}