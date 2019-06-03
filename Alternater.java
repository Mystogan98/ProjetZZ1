

public class Alternater extends Strategy {
    private String name = StrategyNames.Alternater.getName();
    private Action start;

    public Alternater(Action start) {
        if(start == null) {
            if(Math.random() >= 0.5) {
                start = Action.cooperate;
            } else {
                start = Action.cheat;
            }
        }
        this.start = start;
    }

    public void Play(Memory memory) {
        Action last = memory.GetLastBotAction();

        if(last == null) {
            action = start;
        } else {
            if(last == Action.cooperate){
                action = Action.cheat;
            } else {
                action = Action.cooperate;
            }
        }

        memory.MemoriseBotAction(action);
    }

    public String GetName() {
        String name;
        if(start == Action.cooperate) {
            name = this.name + " (Starts Cooperate)";
        } else {
            name = this.name + " (Starts Cheat)";
        }
        return name;
    }
}