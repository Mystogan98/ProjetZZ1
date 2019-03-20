import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Player {
    protected int score;
    protected Action action;

    public void Play(Memory memory)
    {
        System.out.println("Entre 'cooperate' ou 'cheat'.");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try { // Nique ta race java de mes couilles avec tes try/catch partout
            String s = in.readLine();
            if(s.compareTo("cooperate") == 0) {
                action = Action.cooperate;
            } else if (s.compareTo("cheat") == 0) {
                action = Action.cheat;
            } else {
                action = Action.error;
            }
        } catch(Exception e) {
            // On catch et on fait rien parce que rique ta race
        }
        memory.MemorisePlayerAction(action);
    }

    public void IncreaseScore(int score) { this.score += score; }
    public void ResetScore() { score = 0; }

    public int GetScore() { return score; }
    public Action GetAction() { return action; }
}