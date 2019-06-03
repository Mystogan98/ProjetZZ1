import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

import javafx.scene.shape.Ellipse;

public class Deceitful extends Strategy {
    private String name = StrategyNames.Deceitful.getName();

    public void Play(Memory memory) {
        ArrayList<Action> Souvenir = memory.GetPlayerHistory();
        int size = Souvenir.size();
        switch(size)
        {
            case 0:
                action = Action.cooperate;
                break;
            case 1:
                action = Action.cooperate;
                break;
            case 2:
                if(Souvenir.get(1) == Action.cooperate && Souvenir.get(0) == Action.cooperate)
                {
                    action = Action.cheat;
                }
                else
                {
                    action = Action.cooperate;
                }
                break;
            default:
                if(Souvenir.get(size-1) == Action.cooperate)
                {
                    if(Souvenir.get(size-2) == Action.cooperate || Souvenir.get(size - 3) == Action.cooperate)
                    {
                        action = Action.cheat;
                    }
                    else
                    {
                        action = Action.cooperate;
                    }
                }
                else
                {
                    if(Souvenir.get(size - 2) == Action.cheat && Souvenir.get(size-3) == Action.cheat)
                    {
                        action = Action.cheat;
                    }
                    else 
                    {
                        action = Action.cooperate;
                    }
                }
                break;

        }
    }
    public String GetName() { return name; }
}