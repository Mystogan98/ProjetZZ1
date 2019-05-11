public class Resentful extends Strategy {
    private String name = StrategyNames.Resentful.getName();
    private Boolean fault = false;

    public void Play(Memory memory) {
        Action last = memory.GetLastPlayerAction();
        if (fault == false)
        {
            if (last == Action.cheat)
            {
                action = Action.cheat;
                fault = true;
            } 
            else
            {
                action = Action.cooperate;
            }
        }
        else
        {
            action = Action.cheat;
        }
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}