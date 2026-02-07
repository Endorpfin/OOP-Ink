package org.skypro.skyshop.search;

public class SearchEngine {
    private final Searchable[] items;
    private int size;

    public SearchEngine(int capacity) {
        this.items = new Searchable[capacity];
        this.size = 0;
    }

    public void add(Searchable item) {
        if (size < items.length) {
            items[size] = item;
            size++;
        }
    }

    public Searchable[] search(String query) {
        Searchable[] result = new Searchable[5];
        int found = 0;
        for (int i = 0; i < size && found < 5; i++) {
            Searchable item = items[i];
            if (item != null && item.getSearchTerm().contains(query)) {
                result[found] = item;
                found++;
            }
        }
        return result;
    }
}