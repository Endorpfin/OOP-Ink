package org.skypro.skyshop.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SearchEngine {
    // Храним объекты в Set, чтобы не было дубликатов
    private final Set<Searchable> items;

    // Компаратор для результатов поиска:
    // 1) по длине имени: длиннее — раньше;
    // 2) если длина одинакова — по алфавиту (натуральный порядок)
    private static final Comparator<Searchable> SEARCH_RESULT_COMPARATOR =
            (a, b) -> {
                int lengthCompare = Integer.compare(
                        b.getName().length(),     // сначала более длинные
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
        // благодаря equals/hashCode в Product и Article
        // в Set не попадут дубликаты с тем же именем
        items.add(item);
    }

    /**
     * Возвращает все объекты, в getSearchTerm() которых содержится строка query,
     * отсортированные по длине имени (от самой длинной к самой короткой),
     * при равной длине — в натуральном порядке имени.
     */
    public Set<Searchable> search(String query) {
        Set<Searchable> result = new TreeSet<>(SEARCH_RESULT_COMPARATOR);
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