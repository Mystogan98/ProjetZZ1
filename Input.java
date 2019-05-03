import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Input {
    private static Action action;

    public static Action TerminalInput()
    {
		System.out.println("Entre 'cooperate' ou 'cheat'.");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try { // Nique ta race java de mes couilles avec tes try/catch partout
            String s = in.readLine();
            if(s.compareTo("cooperate") == 0) {
                action = Action.cooperate;
            } else if (s.compareTo("cheat") == 0) {
                action = Action.cheat;
            } else {
                action = Action.error;
            }
        } catch(Exception e) {
			// On catch et on fait rien parce que rique ta race
			action = Action.error;
        }

        return action;
    }

	public static Action FileInput(Memory memory) {
		return Action.error;
	}
    
}