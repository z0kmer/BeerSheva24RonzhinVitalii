package telran.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTasks {
    public static void displayOccurrences(String[] strings) {
        // input {"lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm"}
        // output:
        // lpm -> 3
        // c -> 2
        // cb -> 2
        // a -> 1
        // ab -> 1
        HashMap<String, Long> occurrencesMap = getMapOccurrences(strings);
        TreeMap<Long, TreeSet<String>> sortedOccurrencesMap = getSortedOccurrencesMap(occurrencesMap);
        displaySortedOoccurrencesMap(sortedOccurrencesMap);
    }
    public static void displayOccurrencesStream(String[] strings) {
        Arrays.stream(strings).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream().sorted((e1, e2) -> {
            int res = Long.compare(e2.getValue(), e1.getValue());
            return res == 0 ? e1.getKey().compareTo(e2.getKey()) : res;
        }).forEach(e -> System.out.printf("%s -> %d\n", e.getKey(), e.getValue()));
    }

    private static void displaySortedOoccurrencesMap(TreeMap<Long, TreeSet<String>> sortedOccurrencesMap) {
        sortedOccurrencesMap.forEach(
                (occurrency, treeSet) -> treeSet.forEach(s -> System.out.printf("%s -> %d \n", s, occurrency)));
    }

    private static TreeMap<Long, TreeSet<String>> getSortedOccurrencesMap(HashMap<String, Long> occurrencesMap) {
        TreeMap<Long, TreeSet<String>> result = new TreeMap<>(Comparator.reverseOrder());
        occurrencesMap.entrySet().forEach(e -> result.computeIfAbsent(e.getValue(),
                k -> new TreeSet<>()).add(e.getKey()));
        return result;
    }

    private static HashMap<String, Long> getMapOccurrences(String[] strings) {
        HashMap<String, Long> result = new HashMap<>();
        Arrays.stream(strings).forEach(s -> result.merge(s, 1l, Long::sum));
        return result;
    }

    public static Map<Integer, Integer[]> getGroupingByNumberOfDigits(int[][] array) {

        Map<Integer, List<Integer>> map = streamOfNumbers(array)
                .collect(Collectors.groupingBy(n -> Integer.toString(n).length()));
        return map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(),
                e -> e.getValue().toArray(Integer[]::new)));

    }

    private static Stream<Integer> streamOfNumbers(int[][] array) {
        return Arrays.stream(array).flatMapToInt(Arrays::stream).boxed();
    }

    public static Map<Integer, Long> getDistributionByNumberOfDigits(int[][] array) {

        return streamOfNumbers(array).collect(Collectors.groupingBy(n -> Integer.toString(n).length(), 
        Collectors.counting()));
    }

    public static void displayDigitsDistribution() {
        int[] randomNumbers = new int[1_000_000];
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = (int) (Math.random() * Integer.MAX_VALUE);
        }
        Map<Integer, Long> digitDistribution = Arrays.stream(randomNumbers)
                .mapToObj(Integer::toString)
                .flatMapToInt(String::chars)
                .map(Character::getNumericValue)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        digitDistribution.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.printf("%d -> %d\n", entry.getKey(), entry.getValue()));
    }

    public static ParenthesesMaps getParenthesesMaps(Character[][] openCloseParentheses) {
        Map<Character, Character> openCloseMap = new HashMap<>();
        Map<Character, Character> closeOpenMap = new HashMap<>();
        for (Character[] pair : openCloseParentheses) {
            openCloseMap.put(pair[0], pair[1]);
            closeOpenMap.put(pair[1], pair[0]);
        }
        return new ParenthesesMaps(openCloseMap, closeOpenMap);
    }
}