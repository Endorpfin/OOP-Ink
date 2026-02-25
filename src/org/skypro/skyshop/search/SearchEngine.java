package org.skypro.skyshop.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SearchEngine {
    private final Set<Searchable> items;

    // Компаратор для выдачи результатов поиска:
    // сначала по длине имени (длиннее — раньше), при равной длине — по алфавиту
    private static final Comparator<Searchable> SEARCH_RESULT_COMPARATOR =
            (a, b) -> {
                int lengthCompare = Integer.compare(
                        b.getName().length(),
                        a.getName().length()
                );
                if (lengthCompare != 0) {
                    return lengthCompare;
                }
                return a.getName().compareTo(b.getName());
            };

    public SearchEngine(int capacity) {
        this.items = new HashSet<>(capacity);
    }

    public void add(Searchable item) {
        items.add(item);
    }

    /**
     * Возвращает все объекты, в getSearchTerm() которых содержится строка query,
     * отсортированные по длине имени (от самой длинной к самой короткой),
     * при равной длине — в натуральном порядке имени.
     *
     * Реализация только через Stream API: filter + collect(toCollection()).
     */
    public Set<Searchable> search(String query) {
        return items.stream()
                .filter(item -> item != null && item.getSearchTerm().contains(query))
                .collect(Collectors.toCollection(() -> new TreeSet<>(SEARCH_RESULT_COMPARATOR)));
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