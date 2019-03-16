public class Player {
    private int score;
    private Action action;

    public void Play(Memory memory)
    {

    }

    public void IncreaseScore(int score) { this.score += score; }
    public void ResetScore() { score = 0; }

    public int GetScore() { return score; }
    public Action GetAction() { return action; }
}