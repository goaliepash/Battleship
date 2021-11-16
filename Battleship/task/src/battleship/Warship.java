package battleship;

import java.util.Scanner;

/**
 * Класс, отвечающий за игру "Морской бой"
 */
public class Warship {

    /**
     * Метод, который запускает игру "Морской бой".
     */
    public static void play() {
        Player player1 = new Player();
        System.out.println("Player 1, place your ships on the game field\n");
        player1.placeShips();

        System.out.print("\nPress Enter and pass the move to another player");
        new Scanner(System.in).nextLine();

        Player player2 = new Player();
        System.out.println("Player 2, place your ships on the game field\n");
        player2.placeShips();

        int shotsCount1 = 0;
        int shotsCount2 = 0;
        while (true) {
            System.out.print("\nPress Enter and pass the move to another player");
            new Scanner(System.in).nextLine();
            player2.printShots();
            System.out.println("---------------------");
            player1.printMap();
            System.out.println("\nPlayer 1, it's your turn:");
            if (player2.hitShip(shotsCount1)) {
                shotsCount1++;
                if (shotsCount1 == Player.ALL_DECKS) {
                    break;
                }
            }

            System.out.print("\nPress Enter and pass the move to another player");
            new Scanner(System.in).nextLine();
            player1.printShots();
            System.out.println("---------------------");
            player2.printMap();
            System.out.println("\nPlayer 2, it's your turn:");
            if (player1.hitShip(shotsCount2)) {
                shotsCount2++;
                if (shotsCount2 == Player.ALL_DECKS) {
                    break;
                }
            }
        }
    }
}
