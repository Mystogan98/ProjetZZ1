import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Input {
    private static Action action;
    private static char actionToken = ':', commentToken = '#';
    private static HashMap<String,Action> script = null;  // f(ctc):t => ("ctc",Action.cheat)

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
			// On catch et on fait rien parce que nique ta race
			action = Action.error;
        }

        return action;
    }

	public static Action FileInput(Memory memory) {
		ArrayList<Action> history = memory.GetBotHistory();
		String line = "";

		if(script == null) {	// Si le script n'existe pas, le lire
			script = new HashMap<>();
			getScript("script.isis");
			System.out.println(script);

			if(script == null) {	// Si le script n'existe toujours pas, erreur
				System.err.println("Le script n'as pas pu etre lu");
				return null;
			}
		}
		
		for (Action act : history) {
			if(act == Action.cooperate)
				line += "c";
			else
				line += 't';
		}
		
		while(script.get(line) == null && line.length() > 0)
			line = line.substring(1);
		
		return script.get(line);
    }
    
    private static void getScript(String filename)
    {
		File file = new File(filename);
		BufferedReader reader;
		String line = "", history = "";

		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			return;
		}
		
		try {
			while ((line = reader.readLine()) != null)
			{
				history = computeScriptLine(line.toCharArray());
				if(history != null) {
					script.put(history, action);
				}
			}
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du script");
			return;
		}
	}
	
	private static String computeScriptLine(char[] line) {
		boolean ActionTokenHasBeenRead = false;
		String history = "";
		action = null;

		for (char letter : line) {
			switch(letter)
			{
				case 'c':
					if(!ActionTokenHasBeenRead)
						history += 'c';
					else if (action == null)
						action = Action.cooperate;
					else {
						System.err.println("Problème dans la lecture du script :" + line.toString());
						return null;
					}
				break;
				case 't':
					if(!ActionTokenHasBeenRead)
						history += 't';
					else if (action == null)
						action = Action.cheat;
					else {
						System.err.println("Problème dans la lecture du script :" + line.toString());
						return null;
					}
				break;
				case ':':	//actionToken
					ActionTokenHasBeenRead = true;
				break;
				case '#':	//commentToken
					if(ActionTokenHasBeenRead || history != "")
					{
						System.err.println("Problème dans la lecture du script :" + line.toString());
						return null;
					}
					return null;
			}
		}
		return history;
	}
    
}