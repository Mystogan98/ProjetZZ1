
public class Copycat extends Strategy {
    private String name = StrategyNames.Copycat.getName();

    public void Play(Memory memory) {
        Action last = memory.GetLastPlayerAction();
        if(last == null) 
        {
            action = Action.cooperate;
        }
        else
        {
            if(last == Action.cooperate)
            {
                action = Action.cooperate;
            }
            else
            {
                action = Action.cheat;
            }
        }
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}