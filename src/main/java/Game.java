import java.util.List;
/**
 * The Game class represents a game instance with players, a board, and dice.
 */
public class Game {
    public static final int STARTMONEY = 2000;
    private Board board;
    private Die die;
    public static List<Player> players;
    private int currentPlayer, numOfPlayers;
    private GUI2 gui;

    public void cleanProperty(){
        for (Player p : players)
            p.nullCityUtility();
    }

    public Game(GameFactory factory, GUI2 gui) {
        this.board = factory.createBoard(gui);
        this.players = factory.createPlayers(this);
        this.gui = gui;
        board = new Board(gui);
        die = new Die();
        currentPlayer = 0; // Player 1 starts the game
        numOfPlayers = factory.getNumPlayers();
    }

    public void subscribeToPlayers(PlayerObserver o){
        for (Player p : players){
            p.subscribe(o);
        }
    }

    // unused
    public void rollDiceAndMove() {
        int roll = die.roll();
        players.get(currentPlayer).move(roll);
        System.out.println("You rolled: " + die.diceOne + " + " + die.diceTwo + " = " + roll);
        if (die.isDouble()) {
            System.out.println("You rolled a double!");
        }

        // Check if player's new position is a city and it's owned by someone else
        int position = players.get(currentPlayer).getPosition();
        // action is decided depending on position of the player
        board.getPosition(position).action(players.get(currentPlayer));
    }



    /*
        public void buyCurrentCity() {
            int position = players.get(currentPlayer).getPosition();
            City city = board.getCity(position);
            players.get(currentPlayer).buyCity(city);
        }
    */
    public int switchTurn() {
        if(numOfPlayers == currentPlayer + 1)
            currentPlayer = -1;
        return ++currentPlayer;
    }

    public void makeMove(Die roll) {
        gui.getTextArea().setText(getCurrentPlayer().getName() + " rolled a " + (roll.diceOne + roll.diceTwo) + "\n");
        players.get(currentPlayer).move(roll.diceOne+ roll.diceTwo);

        // Check if player's new position is a city and it's owned by someone else
        int position = players.get(currentPlayer).getPosition();

        // if current player is not an AI, call the action() function
        // otherwise, call the AI makeDecision() function later
        //if (getCurrentPlayer().getType().equals("Player"))
        board.getPosition(position).action(getCurrentPlayer());

/*
        if(board.getPosition(position) instanceof City) {
            City city = (City) board.getPosition(position);
            if(city.getOwner() != null && city.getOwner() != players.get(currentPlayer)) {
                players.get(currentPlayer).payRent(city.getRent());
                city.getOwner().receiveRent(city.getRent());
            }
        }

 */


        // Switch the turn to the next player
        for(Player pl : players) {
            if (pl.getIsBankrupted())
                return;
        }
        switchTurn();
    }


    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public Player getPrevPlayer() {
        if(currentPlayer != 0)
            return players.get(currentPlayer-1);
        else return players.get(3);
    }

    public int getNumPlayers() {
        return numOfPlayers;
    }

    public Board getBoard() {
        return board;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayer;
    }
    public int getPrevPlayerIndex() {
        if(currentPlayer != 0)
            return currentPlayer-1;
        else return 3;
    }
    public static void gameOver() {
        // Determine the player with the highest value
        Player winner = getPlayerWithHighestValue();
        int propertyMoney =calculateTotalValue(winner) - winner.getMoney();
        // Print the winner and other players in descending order of value
        System.out.println("Game Over!");
        System.out.println("Player with the highest value: " + winner.getName());
        System.out.println(winner.getName() + " has total $" + calculateTotalValue(winner) +
                "($" + winner.getMoney() + " in money and $" + propertyMoney + "in properties)");
        System.out.println("Other players:");
        players.stream()
                .filter(player -> player != winner)
                .sorted((p1, p2) -> Integer.compare(calculateTotalValue(p2), calculateTotalValue(p1)))
                .forEach(p -> System.out.println(p.getName() + "-- Total Value(Including properties and money): "
                        + calculateTotalValue(p)));
        System.exit(0);
    }
    private static Player getPlayerWithHighestValue() {
        Player winner = players.get(0);
        int highestValue = calculateTotalValue(winner);

        for (int i = 1; i < players.size(); i++) {
            Player player = players.get(i);
            int playerValue = calculateTotalValue(player) ;

            if (playerValue > highestValue) {
                winner = player;
                highestValue = playerValue;
            }
        }

        return winner;
    }
    private static int calculateTotalValue(Player player) {
        int totalValue = player.getMoney();

        for (City city : player.getOwnedCities()) {
            totalValue += city.getPrice();
        }

        for (Utility utility : player.getOwnedUtilities()) {
            totalValue += utility.getPrice();
        }

        return totalValue;
    }
}
