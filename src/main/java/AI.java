import java.util.Random;
/**
 * The AI class represents an AI player in a game.
 * It extends the Player class and provides additional functionality specific to AI players.
 */
enum AIStrategy {
    AGGRESSIVEBUY, SETPRIORITY, CAUTIOUS
}

public class AI extends Player{
    private Random random;
    private AIStrategy currentStrategy;
    private Game game;

    public AI(String name, int money, GUI2 gui, Game game) {
        super(name, money, gui);
        this.game = game;
        random = new Random();
        int strategy = random.nextInt(3);
        switch (strategy) {
            case 0:
                currentStrategy = AIStrategy.AGGRESSIVEBUY;
                break;
            case 1:
                currentStrategy = AIStrategy.SETPRIORITY;
                break;
            case 2:
                currentStrategy = AIStrategy.CAUTIOUS;
        }
    }
    @Override
    public String getType(){
        return "AI";
    }
    // to implement: AI decision making master function
//    public void makeDecision() {
//        float probability = 0.8f;
//
//        if (ownsCurrentSet(getOnCity())) {
//
//        }
//
//    }
    @Override
    public boolean makeDecision() {
        float probability;
        float discourage = 0.0f;
        int numOfRicherPlayers = 0;

        if (getMoney() < Game.STARTMONEY / 2) discourage += 0.2f;
        if (getOwnedCities().size() + getOwnedUtilities().size() > 10) discourage += 0.1f;

        for (Player i : Game.players) {
            if (i.getMoney() > this.getMoney()) {
                numOfRicherPlayers++;
            }
        }

        switch (currentStrategy) {
            case AGGRESSIVEBUY -> {
                probability = 0.8f;
                int propertyCount = getOwnedCities().size() + getOwnedUtilities().size();
                // AI will always purchase if they own less than 3 properties
                if (propertyCount < 3) return true;
                if (propertyCount < 10) probability += 0.1f;
            }
            case SETPRIORITY -> {
                probability = 0.7f;

                // if AI owns another city of this set
                if (ownsSameColor(getOnCity())) probability += 0.2f;
            }
            case CAUTIOUS -> {
                probability = 0.6f;

                // if current AI is most poor player in the game
                if (numOfRicherPlayers == game.getNumPlayers() - 1) probability -= 0.2f;
                if (ownsSameColor(getOnCity())) probability += 0.1f;
            }
            default -> probability = 0.5f;
        }

        probability -= discourage;

        return Math.random() < probability;
    }
}

