import java.util.ArrayList;
import java.util.List;

public class Memory {
    private List<Action> playerHistory;
    private List<Action> botHistory;

    private Action botAction, playerAction;

    // Faut faire gaffe ici, celui qui joue en deuxième aura accés a une info en plus dans les listes
    // faudrais faire une fonction "nextTurn()" avec des variables de temporisation ou un truc du genre

    public Memory() {
        playerHistory = new ArrayList<>();
        botHistory = new ArrayList<>();
    }

    public void Show() {
        System.out.print("Player : ");
        for (Action action : playerHistory) {
            System.out.print(action.toString() + " / ");
        }
        System.out.println();

        System.out.print("Bot : ");
        for (Action action : botHistory) {
            System.out.print(action.toString() + " / ");
        }
        System.out.println();
    }

    public void Reset() {
        playerHistory = new ArrayList<Action>();
        botHistory = new ArrayList<Action>();
    }

    public void MemorisePlayerAction(Action action) {
        playerAction = action;
    }

    public void MemoriseBotAction(Action action) {
        botAction = action;
    }

    public void EndTurn() {
        playerHistory.add(playerAction);
        botHistory.add(botAction);
    }

    public List<Action> GetPlayerHistory() { return playerHistory; }
    public List<Action> GetBotHistory() { return botHistory; }
}