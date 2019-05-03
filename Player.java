public class Player {
    protected int score;
	protected Action action;
	private boolean readFromTerminal = true;

    public void Play(Memory memory)
    {
		if(readFromTerminal) {
			action = Input.TerminalInput();
		} else {
			action = Input.FileInput(memory);
		}
        
        memory.MemorisePlayerAction(action);
    }

    public void IncreaseScore(int score) { this.score += score; }
    public void ResetScore() { score = 0; }

    public int GetScore() { return score; }
    public Action GetAction() { return action; }
}