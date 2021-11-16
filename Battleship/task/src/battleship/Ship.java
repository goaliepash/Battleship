package battleship;

/**
 * Перечисление для кораблей, которые используются в игре "Морской бой".
 *
 * @author Иванов Павел Александрович
 */
public enum Ship {

    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int decks;

    Ship(String name, int decks) {
        this.name = name;
        this.decks = decks;
    }

    /**
     * Метод-геттер, отвечающий за получение имени корабля.
     *
     * @return имя корабля
     */
    public String getName() {
        return this.name;
    }

    /**
     * Метод-геттер, отвечающий за получение количества палуб у корабля.
     *
     * @return количество палуб
     */
    public int getDecks() {
        return this.decks;
    }
}
