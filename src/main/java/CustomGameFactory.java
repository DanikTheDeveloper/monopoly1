import java.util.ArrayList;
import java.util.List;
/**
 * The CustomGameFactory class implements the GameFactory interface and provides a custom game setup
 * by specifying the number of players, AI players, starting money, and board style.
 */
public class CustomGameFactory implements GameFactory {
    private int numPlayers;
    private int money;
    private String boardStyle;
    private int numOfAiPlayers;
    private GUI2 gui; // added gui data member

    public CustomGameFactory(int numPlayers, int numOfAiPlayers, int cash, String boardStyle, GUI2 gui) {
        this.numPlayers = numPlayers;
        this.numOfAiPlayers = numOfAiPlayers;
        this.money = cash;
        this.boardStyle = boardStyle;
        this.gui = gui; // added gui into contructor
    }

    @Override
    public Board createBoard(GUI2 gui) {
        return null;
    }

    @Override
    public Board createBoard() {
        return new Board(gui);
    }

    @Override
    public List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        /*for(int i=0; i<(numPlayers-numOfAiPlayers); i++) {
            String name = "Player " + i;
            players.add(new Player(name, money, gui)); // passing gui to Players for textArea
        }
        for(int i=0; i<numOfAiPlayers; i++) {
            String name = "AI " + i;
<<<<<<< HEAD
            players.add(new AI(name, money, gui));
        }
=======
            players.add(new AI(name, money));
        }*/
        players.add(new Player("Player 1", money, gui)); // ensure that each player/AI has unique number for ID
        players.add(new AI("AI 2 ", money, gui));
        players.add(new Player("Player 3", money, gui));
        players.add(new AI("AI 4", money, gui));

        return players;
    }

    @Override
    public int getNumPlayers(){
        return numPlayers;
    }
    @Override
    public int getNumOfAiPlayers(){
        return numOfAiPlayers;
    }

}