import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Input {
    private static Action action;	// Stocke l'action du joueur pour ce tour
    private static char actionToken = ':', commentToken = '#';	// Les différents tokens utilisés dans le script
	private static HashMap<String,Action> script = null;	// Dictionnaire chargé de stocker la totalité des instructions du script sous la forme <"historique", Action>
	// Exemple : f(ctc):t => ("ctc", Action.cheat)
	public static boolean readFromTerminal = true;	// Est-ce que le joueur joue au clavier (= true) ou via un script (= false) ?

	// Fonction appelée dans Player determinant l'action à jouer.
	public static Action getInput(Memory memory) {
		if(readFromTerminal)
			return Input.TerminalInput();
		else
			return Input.FileInput(memory);
	}

	// Lecture de l'action via le terminal.
    private static Action TerminalInput()
    {
		System.out.println("Entrez 'cooperer' ou 'tricher'.");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String s = in.readLine();
            if(s.compareTo("cooperer") == 0) {
                action = Action.cooperate;
            } else if (s.compareTo("tricher") == 0) {
                action = Action.cheat;
            } else {
                action = Action.error;
            }
        } catch(Exception e) {
			action = Action.error;
        }

        return action;
    }

	// Détermine l'action a jouer selon le script actuellement chargé et l'historique de l'IA
	private static Action FileInput(Memory memory) {
		ArrayList<Action> history = memory.GetBotHistory();
		String line = "";

		if(script == null) {	// Si le script n'existe pas
			System.err.println("Aucun script n'est actuellement chargé.");
			return null;
		}
		
		// Transforme l'historique de l'IA en chaine de caractère de forme "ct" afin d'etre utilisé comme clé dans le dictionnaire Script.
		for (Action act : history) {
			if(act == Action.cooperate)
				line += "c";
			else
				line += 't';
		}
		
		// On essaye d'obtenir l'action liée à cette clé, si on ne trouve pas on supprime le premier caractère de la clé (l'action la plus ancienne) et on rééssaye
		while(script.get(line) == null && line.length() > 0)
			line = line.substring(1);
		
		return script.get(line);
    }
	
	// Fonction chargée de créer le dictionnaire Script.
    public static void getScript(String filename)
    {
		File file = new File(filename);
		BufferedReader reader;
		String line = "", history = "";
		script = new HashMap<>();

		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
			return;
		}
		
		try {
			// Tant qu'on lit des lignes dans le fichier...
			while ((line = reader.readLine()) != null)
			{
				// On traite cette ligne dans "computeScriptLine" qui retourne l'historique (ou null) ainsi que l'action qui y est liée dans la variable Action.
				history = computeScriptLine(line.toCharArray());
				if(history != null) {
					script.put(history, action);
				}
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du script");
			return;
		}
		
		Main.scriptName = filename;
		readFromTerminal = false;
	}
	
	private static String computeScriptLine(char[] line) {
		boolean ActionTokenHasBeenRead = false;
		String history = "";
		action = null;

		for (char letter : line) {
			if(letter == 'c') {
				if(!ActionTokenHasBeenRead)
					history += 'c';
				else if (action == null)
					action = Action.cooperate;
				else {
					System.err.println("Problème dans la lecture du script :" + line.toString());
					return null;
				}
			} else if (letter == 't') {
				if(!ActionTokenHasBeenRead)
					history += 't';
				else if (action == null)
					action = Action.cheat;
				else {
					System.err.println("Problème dans la lecture du script :" + line.toString());
					return null;
				}
			} else if (letter == actionToken) {
				ActionTokenHasBeenRead = true;
			} else if (letter == commentToken) {
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