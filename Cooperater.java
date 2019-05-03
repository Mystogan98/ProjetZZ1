public class Cooperater extends Strategy {
    private String name = StrategyNames.Cooperater.getName();

    public void Play(Memory memory) {
        action = Action.cooperate;
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}