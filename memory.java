import java.util.ArrayList;
import java.util.List;

public class Memory {
    private List<Action> playerHistory;
    private list<Action> botHistory;

    // Faut faire gaffe ici, celui qui joue en deuxième aura accés a une info en plus dans les listes
    // faudrais faire une fonction "nextTurn()" avec des variables de temporisation ou un truc du genre

    public Memory() {
        playerHistory = new ArrayList<Action>();
        botHistory = new ArrayList<Action>();
    }

    public void Reset() {
        playerHistory = new ArrayList<Action>();
        botHistory = new ArrayList<Action>();
    }

    public void MemorisePlayerAction(Action action) {
        playerHistory.add(action);
    }

    public void MemoriseBotAction(Action action) {
        botHistory.add(action);
    }

    public list<Action> GetPlayerHistory() { return playerHistory; }
    public list<Action> GetBotHistory() { return botHistory; }
}