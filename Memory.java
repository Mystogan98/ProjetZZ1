import java.util.ArrayList;

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

    // Return a list with all the player's actions
    public ArrayList<Action> GetPlayerHistory() { return playerHistory; }
    // Return a list with all the Bot's actions
    public ArrayList<Action> GetBotHistory() { return botHistory; }

    // Return the last Player action or NULL if it's the first round
    public Action GetLastPlayerAction() { 
        if(playerHistory.size() == 0)
            return null;
        return playerHistory.get(playerHistory.size() -1);
    }

    // Return the last Bot action or NULL if it's the first round
    public Action GetLastBotAction() { 
        if(botHistory.size() == 0)
            return null;
        return botHistory.get(botHistory.size() -1); 
    }
}