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

    //Подсчёт количества непересекающихся вхождений подстроки в строку.

    private static int countOccurrences(String str, String substring) {
        if (substring == null || substring.isEmpty()) {
            return 0;
        }
        int count = 0;
        int index = 0;
        int foundIndex = str.indexOf(substring, index);
        while (foundIndex != -1) {
            count++;
            index = foundIndex + substring.length();
            foundIndex = str.indexOf(substring, index);
        }
        return count;
    }

    /**
     * Находит среди объектов Searchable наиболее подходящий к поисковой строке.
     *
     * @param search поисковая строка
     * @return объект с максимальным числом вхождений search в getSearchTerm()
     * @throws BestResultNotFound если нет ни одного объекта или ни у одного нет вхождений search
     */
    public Searchable findBest(String search) throws BestResultNotFound {
        Searchable bestItem = null;
        int bestCount = -1;

        for (int i = 0; i < size; i++) {
            Searchable item = items[i];
            if (item != null) {
                String term = item.getSearchTerm();
                int count = countOccurrences(term, search);
                if (count > bestCount) {
                    bestCount = count;
                    bestItem = item;
                }
            }
        }

        if (bestItem == null || bestCount == 0) {
            throw new BestResultNotFound(search);
        }
        return bestItem;
    }
}