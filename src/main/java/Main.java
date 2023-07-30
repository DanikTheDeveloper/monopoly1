import java.util.Random;

/**
 * The Main class serves as the entry point for starting the Monopoly game.
 * It creates an instance of the game with custom settings and initializes the user interface.
 */
public class Main {
    public static void main(String[] args) {
        // Create the game
        int numPlayers = 4;
        int numOfAiPlayers = 2;
        int cash = 2000;
        String boardStyle = "Classic";

        GUI2 a = new GUI2(false); // must add to the factory

        GameFactory factory = new CustomGameFactory(numPlayers, numOfAiPlayers, cash, boardStyle, a);
        Game game = new Game(factory, a);

/*
        for(int i = 0;i < 4;i++)
            System.out.println(game.players.get(i).getType());
            
 */
        Random random = new Random();
        a.initializeTheBoard(game);
        game.subscribeToPlayers(a);
    }
}
