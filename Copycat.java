import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.Action;

public class Copycat extends Strategy {
    private String name = StrategyNames.Copycat.getName();

    public void Play(Memory memory) {
        ArrayList<Action> last = memory.GetPlayerHistory();
        int size = last.size();
        if(size == 0) 
        {
            action = Action.cooperate;
        }
        else
        {
            if(last.get(size - 1) == Action.cooperate)
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