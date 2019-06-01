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
				if(action != null) {	// Si la ligne a été lue correctement
					if(history == "") {	// Si l'historique est vide, on l'ajoute directement
						script.put(history, action);
					} else if (history != null) {	// Sinon on le passe dans la fonction AddToScript qui va s'occuper de gérer les jokers
						AddToScript(history);
					}
				}	
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Problème lors de la lecture du script");
			return;
		}
		
		// On modifie le nom de fichier dans Main afin qu'il soit affiché dans la menu (indication qu'il a bien été chargé)
		Main.scriptName = filename;
		// Et on modifie le mode de jeu afin que le script soit utilisé
		readFromTerminal = false;
	}
	
	// Prend en entrée une ligne du script et retourne l'historique et l'action (ou rien si ligne de commentaire)
	private static String computeScriptLine(char[] line) {
		boolean ActionTokenHasBeenRead = false;	// Est-ce que le token d'action (':') a été lu ?
		String history = "";
		action = null;

		// On analyse et traite chaque caratère
		for (char letter : line) {
			if(letter == 'c') {
				if(!ActionTokenHasBeenRead)	// Si le token d'action n'a pas encore été lu, il s'agit de l'historique
					history += 'c';
				else if (action == null)	// Sinon si le token a été lu mais pas l'action, il s'agit de l'action a éffectué
					action = Action.cooperate;
				else {	// Si l'action a déjà été lu, il y a une erreur dans le script
					System.err.println("Problème dans la lecture du script :" + line.toString());
					return null;
				}
			} else if (letter == 't') {
				if(!ActionTokenHasBeenRead) // Si le token d'action n'a pas encore été lu, il s'agit de l'historique
					history += 't';
				else if (action == null)	// Sinon si le token a été lu mais pas l'action, il s'agit de l'action a éffectué
					action = Action.cheat;
				else {	// Si l'action a déjà été lu, il y a une erreur dans le script
					System.err.println("Problème dans la lecture du script :" + line.toString());
					return null;
				}
			} else if(letter == '*' || letter == '?') {
				if(!ActionTokenHasBeenRead) // Si le token d'action n'a pas encore été lu, il s'agit de l'historique
					history += letter;
				else {	// Sinon il y a une erreur dans le script (un joker ne peut pas être utilisé comme action à effectuer)
					System.err.println("Problème dans la lecture du script :" + line.toString());
					return null;
				}
			} else if (letter == actionToken) {	// Si on lit le token d'action, alors le token d'action à été lu
				if(!ActionTokenHasBeenRead)	// Si il n'avait pas encore été lu
					ActionTokenHasBeenRead = true;
				else {	// Si il avait deja été lu
					System.err.println("Problème dans la lecture du script :" + line.toString());
					return null;
				}
			} else if (letter == commentToken) {	// Si il s'agit d'un commentaire
				if(ActionTokenHasBeenRead || history != "")	// Mais que nous avons lu des caractère d'historique non suivis par une action, alors il y a une erreur
				{
					System.err.println("Problème dans la lecture du script :" + line.toString());
					return null;
				}
				return null;	// Sinon il s'agit simplement d'une ligne de commentaire
			}
		}
		return history;	// On retourne l'historique, ainsi que l'action dans la variable Action.
	}

	// Cette fonction est chargée de traiter l'historique avant de l'ajouter au script.
	// Sa fonction principale est de gérer les jokers.
	private static void AddToScript(String history) {
		String[] historyTab = { "" };
		int nbWildcard = 0;

		// On construit ici un tableau avec la totalité des clés générées par la ligne en cours.
		for (char letter : history.toCharArray()) {
			switch(letter)
			{
				case 'c': case 't':
					// Si on recontre un 'c' ou un 't', on l'ajoute simplement à la suite de toute les clés.
					historyTab = AddLetterToHistory(historyTab, letter);
				break;
				case '*':
					// Si on rencontre un '*', on ajoute 'c' ET 't' a la fin de toute les clés.
					historyTab = AddFixedWildcardToHistory(historyTab);
				break;
				case '?':
					nbWildcard++;	// On traite les '?' a la fin car ils ajoute 'c' et 't' a CHAQUE emplacement possible
				break;
			}
		}

		// Une fois qu'on a traiter les autres caratères, on gérer les '?', qui ajoute 'c' ET 't' a tout les emplacement possible de toute les clés
		for(int i = 0 ; i < nbWildcard ; i++) {
			historyTab = AddWildcardToHistory(historyTab);
		}
		
		// Et enfin on ajoute toute les clés dans le script.
		// Il est important de noter que si les clés existent deja, elle seront remplacées; c'est donc la ligne la plus basse dans le script qui a la priorité
		for (String line : historyTab) {
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
		// (nb de lettre + nb de mot) * 2 => Taille avec doublons
		String[] result = new String[(historyTab.length + CountLetters(historyTab)) * 2];
		String resultLine;
		int i = 0;

		// Pour chaque ligne
		for (String line : historyTab) {
			// Et chaque emplacement de chaque ligne
			for (int j = 0 ; j <= line.length() ; j++)
			{
				// On ajoute la clé constituée du début de la ligne, suivit de 'c' suivit du reste de la ligne.
				resultLine = line.substring(0, j); 	// Le début de la chaine, de 0 à j
				resultLine += 'c';
				resultLine += line.substring(j);	// La fin de la chaine, à partir de j
				result[i] = resultLine;
				i++;

				// On fait pareil avec la lettre 't'
				resultLine = line.substring(0,j);	// Le début de la chaine, de 0 à j
				resultLine += 't';
				resultLine += line.substring(j);	// La fin de la chaine, à partir de j
				result[i] = resultLine;
				i++;
			}
			// Et on répéte ca pour tout les emplacement possible. (= en premier caractère, entre chaque caratère, et en dernier caratère)
		}

		// On obtien donc un gros tableau qui contient des doubles, et vu que l'on appelle cette fonction plusieurs fois d'affilé pour chaque '?', il est important de supprimer les doublons entre chaque
		result = RemoveDoubles(result);
		return result;
	}

	// Simple fonction qui compte le total de lettre d'un tableau de String
	private static int CountLetters(String[] tab) {
		int sum = 0;

		for (String line : tab) {
			if(line != null)
				sum += line.length();
		}

		return sum;
	}

	// Simple fonction qui utilise la propriété d'unicité des Set afin de supprimer les doublons efficacement.
	private static String[] RemoveDoubles(String[] tab) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(Arrays.asList(tab));
         
        return linkedHashSet.toArray(new String[] {});
	}
}