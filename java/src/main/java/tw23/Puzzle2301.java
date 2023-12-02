package tw23;

import aoc.AbstractPuzzle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;


@Slf4j
public class Puzzle2301 extends AbstractPuzzle {
    /**
     * Constructor which accepts the puzzle input to be solved.
     *
     * @param puzzleInput the puzzle input
     */

    public Puzzle2301(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public String solvePart1() {
        // read one line of input and get first number on left and first number on right
        var lines = getPuzzleInput().split("\n");
        ArrayList<Long> numbers = new ArrayList<>();
        for (String line : lines) {
            log.info("line: {}", line);
            // go through charachter from left and get first number
            int left = 0;
            int right = line.length()-1;
            while(right>=0){
                if (isNumber(line.charAt(right))){
                    break;
                }
                right--;
            }
            // check char is number
            while(left< right && left>=0){
                if (isNumber(line.charAt(left))){
                    break;
                }
                left++;
            }
            var leftChar = left== 0 && (line.isEmpty() || !isNumber(line.charAt(left))) ? "":line.charAt(left);
            log.info("left: {}", leftChar);
            // go through charachter from right and get first number

            var rightChar = right<= 0? "":line.charAt(right);
            log.info("right: {}", rightChar);
            if(!leftChar.equals("") || !rightChar.equals("")) {
                if(rightChar.equals("")){
                    rightChar = leftChar;
                }
                long parseLong = Long.parseLong("" + leftChar + "" + rightChar);
                log.info("added number:"+ parseLong +" to list");
                numbers.add(parseLong);
            }
        }
        // do sum of all numbers
        long sum = 0;
        for (Long number : numbers) {
            sum += number;
        }
        // return sum
        log.info("sum: {}", sum);
        return String.valueOf(sum);
    }

    private String parseLine(String line) {
        // for each text of number, remove it and put digit
        // detect text of number
        // replace text of number with digit
        // if line contains any digit text number, replace one with smallest index
        // get first appearance of text of number
        String[] textNumbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        boolean stop = false;
        for( int i=0; i< line.length(); i++){
            for (String numb : textNumbers) {
                if(line.substring(0, i).contains(numb)){
                    line = line.replaceFirst(numb,fromTextToDigit(numb).toString()+ numb.charAt(numb.length()-1) );
                    line = line.trim();
                    stop = true;
                    break;
                }
            }
            if(stop){
                break;
            }
        }
        // try backward
        boolean stop2 = false;
        for( int i=line.length()-1; i>=0; i--){
            for (String numb : textNumbers) {
                if(i>0 && line.substring(i).contains(numb)){
                    // replace from index i
                    line = replaceLast(line, numb, fromTextToDigit(numb).toString());
                    line = line.trim();
                    stop2 = true;
                    break;
                }
            }
            if(stop2){
                break;
            }
        }

        return line;
    }

    public static String replaceLast(String str, String oldValue, String newValue) {
        str = StringUtils.reverse(str);
        str = str.replaceFirst(StringUtils.reverse(oldValue), StringUtils.reverse(newValue));
        str = StringUtils.reverse(str);
        return str;
    }

    private Integer fromTextToDigit(String textNumber){
        switch (textNumber){
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            default:
                return 9;
        }
    }

    // check if char is number
    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    @Override
    public String solvePart2() {
        // read one line of input and get first number on left and first number on right
        var lines = getPuzzleInput().split("\n");
        ArrayList<Long> numbers = new ArrayList<>();
        for (String line : lines) {
            log.info("line: {}", line);
            line = parseLine(line);
            log.info(" parsed line: {}", line);
            // go through charachter from left and get first number
            int left = 0;
            int right = line.length()-1;
            while(right>=0){
                if (isNumber(line.charAt(right))){
                    break;
                }
                right--;
            }
            // check char is number
            while(left< right && left>=0){
                if (isNumber(line.charAt(left))){
                    break;
                }
                left++;
            }
            var leftChar = left== 0 && (line.isEmpty() || !isNumber(line.charAt(left))) ? "":line.charAt(left);
            log.info("left: {}", leftChar);
            // go through charachter from right and get first number

            var rightChar = right<= 0? "":line.charAt(right);
            log.info("right: {}", rightChar);
            if(!leftChar.equals("") || !rightChar.equals("")) {
                if(rightChar.equals("")){
                    rightChar = leftChar;
                }
                long parseLong = Long.parseLong("" + leftChar + "" + rightChar);
                log.info("added number:"+ parseLong +" to list");
                numbers.add(parseLong);
            }
        }
        // do sum of all numbers
        long sum = 0;
        for (Long number : numbers) {
            sum += number;
        }
        // return sum
        log.info("sum: {}", sum);
        return String.valueOf(sum);
    }
}
