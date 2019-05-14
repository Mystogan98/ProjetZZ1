public class Player {
    protected int score;
	protected Action action;

    public void Play(Memory memory)
    {
		action = Input.getInput(memory);
        memory.MemorisePlayerAction(action);
    }

    public void IncreaseScore(int score) { this.score += score; }
    public void ResetScore() { score = 0; }

    public int GetScore() { return score; }
    public Action GetAction() { return action; }
}