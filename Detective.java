import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

import com.sun.corba.se.spi.orbutil.fsm.Action;

public class Cheater extends Strategy {
    private String name = StrategyNames.Cheater.getName();
    private Boolean cheat = true;

    public void Play(Memory memory) {
        ArrayList<Action> last = memory.GetPlayerHistory();
        int size = last.size();
        switch(size)
        {
            case 0:
                action = Action.cooperate;
                break;
            case 1:
                action = Action.cheat;
                break;
            case 2:
                action = Action.cooperate;
                break;
            case 3:
                action = Action.cooperate;
                break;
            case 4:
                if (memory.GetLastPlayerAction() == Action.cooperate)
                {
                    cheat = false;
                    action = Action.cheat;
                }
                else
                {
                    action = Action.cheat;
                }
                break;
            default:
                if(cheat)
                {
                    if(memory.GetLastPlayerAction() == Action.cooperate)
                    {
                        action = Action.cooperate;
                    }
                    else
                    {
                        action = Action.cheat;
                    }
                }
                else
                {
                    action = Action.cheat;
                }
                break;
        }
        action = Action.cheat;
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}