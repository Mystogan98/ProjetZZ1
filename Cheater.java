public class Cheater extends Strategy {
    private String name = StrategyNames.Cheater.getName();

    public void Play(Memory memory) {
        action = Action.cheat;
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}