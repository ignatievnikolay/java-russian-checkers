package checkers;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> gameNotation = new ArrayList<>();
        while (scanner.hasNext()) {
            gameNotation.add(scanner.nextLine());
        }
        Game game = new Game(gameNotation);
        try {
            game.run();
        } catch (IllegalMoveException e) {
            System.out.println(e.getMessage());
            return;
        }
        for (ArrayList<String> colorPosition : game.getPosition()) {
            for (String coordsNotation : colorPosition) {
                System.out.print(coordsNotation + " ");
            }
            System.out.println();
        }
        scanner.close();
    }
}
