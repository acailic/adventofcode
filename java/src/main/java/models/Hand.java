package models;

import com.google.common.base.Joiner;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
@AllArgsConstructor
@Slf4j
public class Hand {
    private final String cardsValue;
    private static final Map<String, Integer> typeValue = Map.of(
            "5", 7,
            "41", 6,
            "32", 5,
            "311", 4,
            "221", 3,
            "2111", 2,
            "11111", 1
    );
    final static List<Character> weights1 = List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    final static List<Character> weights2 = List.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');
    static List<Character> replacements = List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');

    public Hand(String cards, boolean adjustForPart2) {
        var value = new StringBuilder();

        for (var card: cards.toCharArray()) {
            value.append(Integer.toString(adjustForPart2 ? weights2.indexOf(card) : weights1.indexOf(card), 16));
        }

        if (adjustForPart2 && cards.indexOf('J') != -1) {
            var max = replacements.stream()
                    .map(r -> cards.replace('J', r))
                    .map(Hand::getType)
                    .max(Comparator.comparingInt(a -> a))
                    .get();

            cardsValue = max.toString() + value;
            return;
        }

        cardsValue = getType(cards) + value.toString();
    }

    public static int compare(Hand hand1, Hand hand2) {
        return hand1.cardsValue.compareTo(hand2.cardsValue);
    }

    public static int getType(String cards) {
        var cardCountsValues = new ArrayList<>(tally(cards).values());
        cardCountsValues.sort(Comparator.reverseOrder());
        return typeValue.get(Joiner.on("").join(cardCountsValues));
    }

    public static Map<Character, Integer> tally(String string) {
        var characterCount = new HashMap<Character, Integer>();

        for (var c: string.toCharArray()) {
            characterCount.put(c, characterCount.getOrDefault(c, 0) + 1);
        }

        return characterCount;
    }

}
