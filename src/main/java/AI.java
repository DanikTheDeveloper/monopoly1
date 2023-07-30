/**
 * The AI class represents an AI player in a game.
 * It extends the Player class and provides additional functionality specific to AI players.
 */
public class AI extends Player{
    public AI(String name, int money, GUI2 gui) {
        super(name, money, gui);
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
        float probability = 0.5f; // AI's probability of buying a property
        if (Math.random() < probability) {
            return true;
        }
        return false;
    }
}

