package org.skypro.skyshop.search;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SearchEngine {
    private final Map<String, Searchable> items;

    public SearchEngine(int capacity) {
        this.items = new HashMap<>(capacity);
    }

    public void add(Searchable item) {
        items.put(item.getName(), item);
    }

    /**
     * Возвращает все объекты, в getSearchTerm() которых содержится строка query,
     * отсортированные по имени (ключу мапы).
     */
    public Map<String, Searchable> search(String query) {
        Map<String, Searchable> result = new TreeMap<>();
        for (Searchable item : items.values()) {
            if (item != null && item.getSearchTerm().contains(query)) {
                result.put(item.getName(), item);
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

        for (Searchable item : items.values()) {
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
