package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Puzzle2304 extends AbstractPuzzle {


    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */
    public Puzzle2304(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        var input = getPuzzleInput().split("\n");
        long sum = 0;
        log.info("input {}", input.length);
        for (String line : input) {
            List<String> numbers = StringUtilities.splitStringIntoList(StringUtilities.splitStringIntoList(line, ": ").get(1), " | ");
            List<String> winningNumberStrings = StringUtilities.splitStringIntoList(numbers.get(0), " ");
            List<String> pulledNumberStrings = StringUtilities.splitStringIntoList(numbers.get(1), " ");
            final Set<Long> winningNumbers = new HashSet<>();
            final List<Long> pulledNumbers = new ArrayList<>();
            for (final String winningNumber : winningNumberStrings) {
                if (!winningNumber.isEmpty()) {
                    winningNumbers.add(Long.parseLong(winningNumber));
                }
            }
            for (final String pulledNumber : pulledNumberStrings) {
                if (!pulledNumber.isEmpty()) {
                    pulledNumbers.add(Long.parseLong(pulledNumber));
                }
            }
            long count = 0;
            for (final Long pulledNumber : pulledNumbers) {
                if (winningNumbers.contains(pulledNumber)) {
                    if (count == 0) {
                        count += 1;
                    } else {
                        count = count * 2;
                    }
                }
            }
            sum += count;
        }
        log.info("sum {}", sum);
        return sum
                + "";
        // 26218
    }


    @Override
    public String solvePart2() {
        var input = getPuzzleInput().split("\n");
        final Map<Integer, Integer> cards = new HashMap<>();
        int cardNumber = 0;
        for (final String line : input) {
            List<String> numbers = StringUtilities.splitStringIntoList(StringUtilities.splitStringIntoList(line, ": ").get(1), " | ");
            List<String> winningNumberStrings = StringUtilities.splitStringIntoList(numbers.get(0), " ");
            List<String> pulledNumberStrings = StringUtilities.splitStringIntoList(numbers.get(1), " ");
            final Set<Long> winningNumbers = new HashSet<>();
            final List<Long> pulledNumbers = new ArrayList<>();
            for (final String winningNumber : winningNumberStrings) {
                if (!winningNumber.isEmpty()) {
                    winningNumbers.add(Long.parseLong(winningNumber));
                }
            }
            for (final String pulledNumber : pulledNumberStrings) {
                if (!pulledNumber.isEmpty()) {
                    pulledNumbers.add(Long.parseLong(pulledNumber));
                }
            }
            int count = 0;
            for (final Long pulledNumber : pulledNumbers) {
                if (winningNumbers.contains(pulledNumber)) {
                    count += 1;
                }
            }
            cardNumber += 1;
            cards.put(cardNumber, count);
        }

        final Map<Integer, Long> cardCounts = new HashMap<>();
        for (int currentCardNumber = cardNumber; currentCardNumber > 0; currentCardNumber--) {
            final int cardCount = cards.get(currentCardNumber);
            long totalCards = 1;
            for (int cardOffset = 1; cardOffset <= cardCount; cardOffset++) {
                final int nextCardNumber = currentCardNumber + cardOffset;
                totalCards += cardCounts.get(nextCardNumber);
            }
            cardCounts.put(currentCardNumber, totalCards);
        }

        long numberOfCards = 0;
        for (final Long value : cardCounts.values()) {
            numberOfCards += value;
        }
        log.info("numberOfCards {}", numberOfCards);
        return numberOfCards + "";
        // 9997537
    }
}
