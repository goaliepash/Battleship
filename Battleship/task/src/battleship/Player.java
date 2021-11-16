package battleship;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс, отвечающий за игрока и действия с ним.
 *
 * @author Иванов Павел Александрович
 */
public class Player {

    private final char[][] map = new char[10][10];
    private final char[][] shots = new char[10][10];
    private static final char[] rows = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    public static final int ALL_DECKS = 17;

    /**
     * Метод, который размещает корабли перед началом боя.
     */
    public void placeShips() {
        for (char[] chars : map) {
            Arrays.fill(chars, '~');
        }

        for (char[] chars : shots) {
            Arrays.fill(chars, '~');
        }

        printMap();

        Ship[] ships = Ship.values();
        for (int i = 0; i < ships.length; i++) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n", ships[i].getName(), ships[i].getDecks());
            if (!placeShip(ships[i])) {
                i--;
            } else {
                System.out.println();
                printMap();
            }
        }
    }

    /**
     * Метод для нанесения удара по игроку (объекту класса Player).
     *
     * @param numberOfShot номер точного выстрела (начинается с 0)
     * @return true, если корабль подбит, иначе - false
     */
    public boolean hitShip(int numberOfShot) {
        boolean hit = false;

        Scanner scanner = new Scanner(System.in);
        String position = scanner.next();
        int row = Arrays.binarySearch(rows, position.charAt(0));
        int col = Integer.parseInt(position.substring(1)) - 1;

        if (row < 0 || row > 9 || col < 0 || col > 9) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:");
        } else {
            if (map[row][col] == 'O') {
                map[row][col] = 'X';
                shots[row][col] = 'X';

                if (checkOccupied(row, col)) {
                    System.out.println("\nYou hit a ship! Try again:");
                } else {
                    if (numberOfShot == ALL_DECKS - 1) {
                        System.out.println("\nYou sank the last ship. You won. Congratulations!");
                    } else {
                        System.out.println("\nYou sank a ship! Specify a new target:");
                    }
                }
                hit = true;

            } else {
                if (map[row][col] != 'X') {
                    map[row][col] = 'M';
                    shots[row][col] = 'M';
                }
                System.out.println();
                System.out.println("You missed!");
            }
        }

        return hit;
    }

    /**
     * Метод печатает карту игрока.
     */
    public void printMap() {
        System.out.print(" ");
        for (int i = 1; i <= 10; i++) {
            System.out.printf(" %s", i);
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print(rows[i]);
            for (int j = 0; j < map[i].length; j++) {
                System.out.printf(" %s", map[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Метод печатает карту выстрелов по карте игрока.
     */
    public void printShots() {
        System.out.print(" ");
        for (int i = 1; i <= 10; i++) {
            System.out.printf(" %s", i);
        }
        System.out.println();
        for (int i = 0; i < shots.length; i++) {
            System.out.print(rows[i]);
            for (int j = 0; j < shots[i].length; j++) {
                System.out.printf(" %s", shots[i][j]);
            }
            System.out.println();
        }
    }

    private boolean placeShip(Ship ship) {
        Scanner scanner = new Scanner(System.in);
        String start = scanner.next();
        String end = scanner.next();

        int startRow = Arrays.binarySearch(rows, start.charAt(0));
        int startCol = Integer.parseInt(start.substring(1)) - 1;
        int endRow = Arrays.binarySearch(rows, end.charAt(0));
        int endCol = Integer.parseInt(end.substring(1)) - 1;

        if (startRow > endRow) {
            int temp = endRow;
            endRow = startRow;
            startRow = temp;
        }

        if (startCol > endCol) {
            int temp = endCol;
            endCol = startCol;
            startCol = temp;
        }

        if (startRow == endRow) {
            if (endCol - startCol == ship.getDecks() - 1) {
                if (checkPlace(startRow, startCol, endRow, endCol)) {
                    for (int i = startCol; i <= endCol; i++) {
                        map[startRow][i] = 'O';
                    }
                } else {
                    System.out.println("\nError! You placed it too close to another one. Try again:");
                    return false;
                }
            } else {
                System.out.printf("\nError! Wrong length of the %s! Try again:\n", ship.getName());
                return false;
            }
        } else if (startCol == endCol) {
            if (endRow - startRow == ship.getDecks() - 1) {
                if (checkPlace(startRow, startCol, endRow, endCol)) {
                    for (int i = startRow; i <= endRow; i++) {
                        map[i][startCol] = 'O';
                    }
                } else {
                    System.out.println("\nError! You placed it too close to another one. Try again:");
                    return false;
                }
            } else {
                System.out.printf("\nError! Wrong length of the %s! Try again:\n", ship.getName());
                return false;
            }
        } else {
            System.out.println("\nError! Wrong ship location! Try again:");
            return false;
        }

        return true;
    }

    private boolean checkPlace(int startRow, int startCol, int endRow, int endCol) {
        if (startRow - 1 >= 0) {
            for (int i = startCol; i <= endCol; i++) {
                if (map[startRow - 1][i] == 'O') {
                    return false;
                }
            }
        }

        if (endRow + 1 < 10) {
            for (int i = startCol; i <= endCol; i++) {
                if (map[endRow + 1][i] == 'O') {
                    return false;
                }
            }
        }

        if (startCol - 1 > 0) {
            for (int i = startRow; i <= endRow; i++) {
                if (map[i][startCol - 1] == 'O') {
                    return false;
                }
            }
        }

        if (endCol + 1 < 10) {
            for (int i = startRow; i <= endRow; i++) {
                if (map[i][endCol + 1] == 'O') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkOccupied(int row, int col) {
        if (row == 0 && col == 0) {
            return map[row + 1][col] == 'O' || map[row][col + 1] == 'O';
        } else if (row == 0 && col == 9) {
            return map[row + 1][col] == 'O' || map[row][col - 1] == 'O';
        } else if (row == 9 && col == 0) {
            return map[row - 1][col] == 'O' || map[row][col + 1] == 'O';
        } else if (row == 9 && col == 9) {
            return map[row - 1][col] == 'O' || map[row][col - 1] == 'O';
        } else if (row == 0) {
            return map[row][col - 1] == 'O' || map[row][col + 1] == 'O' || map[row + 1][col] == 'O';
        } else if (row == 9) {
            return map[row][col - 1] == 'O' || map[row][col + 1] == 'O' || map[row - 1][col] == 'O';
        } else if (col == 0) {
            return map[row - 1][col] == 'O' || map[row + 1][col] == 'O' || map[row][col + 1] == 'O';
        } else if (col == 9) {
            return map[row - 1][col] == 'O' || map[row + 1][col] == 'O' || map[row][col - 1] == 'O';
        }

        return map[row - 1][col] == 'O' || map[row + 1][col] == 'O' || map[row][col - 1] == 'O' || map[row][col + 1] == 'O';
    }
}
