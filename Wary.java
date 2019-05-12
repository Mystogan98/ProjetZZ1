public class Wary extends Strategy {
    private String name = StrategyNames.Wary.getName();
    private Boolean fault = false;

    public void Play(Memory memory) {
        Action last = memory.GetLastPlayerAction();
        if (fault == false)
        {
            if (last == Action.cooperate)
            {
                action = Action.cooperate;
                fault = true;
            } 
            else
            {
                action = Action.cheat;
            }
        }
        else
        {
            action = Action.cooperate;
        }
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}