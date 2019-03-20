public class Strategy extends Player {
    private String name;

    public Strategy(String name) {
        // La on met un bon gros switch sa maman pour instantier au hasard
        // Ou on instantie "name"
    }

    public void Play(Memory memory) {
        action = Action.cooperate;
        memory.MemoriseBotAction(action);
    }
}