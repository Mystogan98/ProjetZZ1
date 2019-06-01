import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;

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
				if(action != null) {	// Line has been read correctly
					if(history == "") {
						script.put(history, action);
					} else if (history != null) {
						AddToScript(history);
					}
				}	
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du script");
			return;
		}
		
		System.out.println(script);
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
			} else if(letter == '*' || letter == '?') {
				if(!ActionTokenHasBeenRead)
					history += letter;
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

	private static void AddToScript(String history) {
		// **t => cct, ctt, tct, ttt
		// ??t => cct, ctc, tcc, ttc, tct, ctt, ttt

		// { 'ttt?', 'ccc?' } => { 'tttc', 'ttct', 'tctt', 'cttt', 'tttt'*4; Pareil }
		// 2 => 16 (10)
		// nb de ligne => nb de ligne * somme de lettre (- (somme de lettre - nb de ligne))
		// { 'tt?', 'cc?' } => { 'ctt', 'tct', 'ttc', 'ttt', 'ttt , 'ttt'; 'ccc', 'ccc', 'ccc', 'cct', 'ctc', 'tcc' }
		// 2 => 12 (8)
		// { 't?', 'c?' } => { 'tc', 'ct', 'tt', 'tt'; 'ct', 'tc', 'cc', 'cc' }
		// 2 => 8 (6)
		// { '?', 't?', 'c?' } => { 'c', 't'; 'ct', 'tc', 'tt', 'tt'; 'cc', 'cc', 'ct', 'tc' }
		// 3 => 10 (8)

		String[] historyTab = { "" };
		int nbWildcard = 0;

		for (char letter : history.toCharArray()) {
			switch(letter)
			{
				case 'c': case 't':
					historyTab = AddLetterToHistory(historyTab, letter);
				break;
				case '*':
					historyTab = AddFixedWildcardToHistory(historyTab);
				break;
				case '?':
					nbWildcard++;
				break;
			}
		}

		for(int i = 0 ; i < nbWildcard ; i++) {
			historyTab = AddWildcardToHistory(historyTab);
			System.out.println(script);
		}
		
		for (String line : historyTab) {
			//System.out.println(script);
			script.put(line, action);
		}
	}

	// Ajoute la lettre 'letter' à la fin de chaque ligne de 'historyTab'
	private static String[] AddLetterToHistory(String[] historyTab, char letter) {
		String[] result = new String[historyTab.length];
		int i = 0;

		for (String line : historyTab) {
			line += letter;
			result[i] = line;
			i++;
		}

		return result;
	}

	// Ajoute 'c' et 't' à la fin de chaque ligne de 'historyTab' (le tableau en sorti est donc 2 fois plus grand)
	private static String[] AddFixedWildcardToHistory(String[] historyTab) {
		String[] result = new String[historyTab.length * 2];
		int i = 0;

		for (String line : historyTab) {
			result[i] = line + 'c';
			i++;
			result[i] = line + 't';
			i++;
		}

		return result;
	}

	// Ajoute 'c' et 't' à chaque position dans la chaine de chaque ligne de 'historyTab'
	private static String[] AddWildcardToHistory(String[] historyTab) {
		// (nb de ligne * somme de lettre) - (somme de lettre - nb de ligne) => taille sans doublon		FAUX?
		// (nb de ligne * somme de lettre) => taille avec doublons	FAUX?

		// (nb de lettre + nb de mot) * 2 => Taille avec doublons
		int nbOfLetters = CountLetters(historyTab), i = 0;
		String[] result = new String[(historyTab.length + nbOfLetters) * 2];
		String resultLine, tmp;

		// On doit gerer tout les caractères AVANT de s'occuper des '?'
		// Sinon ca seras pas vraiment à chaque emplacement possible
		// Trouver pourquoi on a des null qui s'affiche, pourquoi action est null et pourquoi il nous manque des cas

		for (String line : historyTab) {
			for (int j = 0 ; j <= line.length() ; j++)
			{
				tmp = line.substring(0,j);
				resultLine = (tmp == null)?"":tmp;
				resultLine += 'c';
				resultLine += ((tmp = line.substring(j,line.length())) == null)?"":tmp;
				result[i] = resultLine;
				i++;

				tmp = line.substring(0,j);
				resultLine = (tmp == null)?"":tmp;
				resultLine += 't';
				resultLine += ((tmp = line.substring(j,line.length())) == null)?"":tmp;
				result[i] = resultLine;
				i++;
			}
		}

		result = RemoveDoubles(result);
		return result;
	}

	private static int CountLetters(String[] tab) {
		int sum = 0;

		for (String line : tab) {
			if(line != null)
				sum += line.length();
		}

		return sum;
	}

	private static String[] RemoveDoubles(String[] tab) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(tab));
         
        return linkedHashSet.toArray(new String[] {});
	}
}