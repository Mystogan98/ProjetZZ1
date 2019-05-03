import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Input {
    private static Action action;
    private static char actionToken = ':', commentToken = '#';
    private static HashMap<String,Action> script;  // f(ctc):t => ("ctc",Action.cheat)

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
        if(script == null) {
            // arette tout
        }
        
        // recuperer toute les lignes dans un tableau
        // foreach
        // découper selon startToken
        // découper selon endToken
        // découper selon actionToken
        // new hashmap(tab[0],tab[1]);


		return Action.error;
    }
    
    private static getScript(String filename)
    {
        // switch(char)
        // case 'c' ou 't' :
        //   if(PasActionToken)
		//      History.add('c')
		//	 else if (action == null)
		//		action = cooperate
		//	 else
		//		erreur
		// break;
		// case actionToken :
		//	 actionToken = vrai;
		// break;
		// case commentToken :
		//	 if(ActionToken || HistoryPasVide)
		//		erreur
		//	 passe la ligne
		// break;
    }
    
}