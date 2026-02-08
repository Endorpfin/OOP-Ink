package org.skypro.skyshop.search;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
    private final List<Searchable> items;

    public SearchEngine(int capacity) {
        this.items = new ArrayList<>(capacity);
    }

    public void add(Searchable item) {
        items.add(item);
    }

    /**
     * Возвращает все объекты, в getSearchTerm() которых содержится строка query.
     */
    public List<Searchable> search(String query) {
        List<Searchable> result = new ArrayList<>();
        for (Searchable item : items) {
            if (item != null && item.getSearchTerm().contains(query)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Подсчёт количества непересекающихся вхождений подстроки в строку.
     */
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
     * Находит среди объектов Searchable наиболее подходящий к поисковой строке:
     * тот, в getSearchTerm() которого строка search встречается максимальное число раз.
     *
     * @param search поисковая строка
     * @return объект с максимальным числом вхождений search
     * @throws BestResultNotFound если нет ни одного объекта или ни у одного нет вхождений search
     */
    public Searchable findBest(String search) throws BestResultNotFound {
        Searchable bestItem = null;
        int bestCount = -1;

        for (Searchable item : items) {
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