package tw23;


import lombok.Data;

@Data
public class Game {
    private int id;
    private int blue;
    private int green;
    private int red;


    // check if game is possible

    boolean isPossible() {
        return red <= 12 && green <= 13 && blue <= 14;
    }

    int power() {
        return red * green * blue;
    }

}
