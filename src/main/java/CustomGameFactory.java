import java.util.ArrayList;
import java.util.List;
/**
 * The CustomGameFactory class implements the GameFactory interface and provides a custom game setup
 * by specifying the number of players, AI players, starting money, and board style.
 */
public class CustomGameFactory implements GameFactory {
    private int numPlayers;
    private int money;
    private String boardStyle = "Classic";
    private int numOfAiPlayers;
    private GUI2 gui;

    public CustomGameFactory(int numPlayers, int numOfAiPlayers, int cash, String boardStyle, GUI2 gui) {
        this.numPlayers = numPlayers;
        this.numOfAiPlayers = numOfAiPlayers;
        this.money = cash;
        this.boardStyle = new String(boardStyle);
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
    public List<Player> createPlayers(Game game) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", money, gui)); // ensure that each player/AI has unique number for ID
        players.add(new AI("AI 2 ", money, gui, game));
        players.add(new Player("Player 3", money, gui));
        players.add(new AI("AI 4", money, gui, game));

        return players;
    }

    @Override
    public int getNumPlayers(){
        return numPlayers;
    }
    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }
    @Override
    public int getNumOfAiPlayers(){
        return numOfAiPlayers;
    }
    public void setNumOfAiPlayers(int numOfAiPlayers){
        this.numOfAiPlayers = numOfAiPlayers;
    }

    public int getCash() {
        return money;
    }

    public void setCash(int cash) {
        this.money = cash;
    }

    @Override
    public String getBoardStyle() {
        return boardStyle;
    }

    public void setBoardStyle(String style) {
        this.boardStyle = style;
    }
}