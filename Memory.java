import java.util.ArrayList;
import java.util.List;

public class Memory {
    private ArrayList<Action> playerHistory;
    private ArrayList<Action> botHistory;

    private Action botAction, playerAction;

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

    public ArrayList<Action> GetPlayerHistory() { return playerHistory; }
    public ArrayList<Action> GetBotHistory() { return botHistory; }
}