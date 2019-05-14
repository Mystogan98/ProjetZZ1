import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    private static Game game;
    public static String scriptName = "None";

    public static void main(String[] args) {
        game = new Game();
        Menu();
    }

    private static void Menu() {
        int choix = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ClearScreen();

        while(choix != 6)
        {
            System.out.println("1 - Jouer contre une IA");
            System.out.println("2 - Mode Tournoi");
            System.out.println("3 - Aide");
            System.out.println("4 - Changer le mode de jeu (Actuel : " + ((Input.readFromTerminal) ? "Clavier)" : "Script)"));
            System.out.println("5 - Charger un nouveau script (Actuel : " + scriptName + ")");
            System.out.println("6 - Quitter");
            
            try {
                choix = Integer.parseInt(in.readLine());
            } catch(Exception e) {
                System.err.println("Problème lors de la sélection.");
                return;
            }

            switch(choix)
            {
                case 1:
                    // sous-menu
                break;
                case 2:
                    if(Input.readFromTerminal)
                    {
                        ClearScreen();
                        System.out.println("Impossible de jouer au clavier en mode tournoi. Veuillez changer le mode de jeu ou charger un nouveau script.");
                    }
                    // else
                        // modetournoi();
                break;
                case 3:
                    // sousmenu
                break;
                case 4:
                    ClearScreen();
                    System.out.println("Choissez votre mode de jeu :");
                    System.out.println("\033[31mAttention\033[0m - Il est impossible de jouer au clavier en mode tournoi.");
                    System.out.println();
                    System.out.println("1 - Jouer au clavier");
                    System.out.println("2 - Jouer à l'aide d'un script");
                    try {
                        choix = Integer.parseInt(in.readLine());
                    } catch(Exception e) {
                        System.err.println("Problème lors de la sélection.");
                        return;
                    }
                    if(choix == 1)
                        Input.readFromTerminal = true;
                    else
                        Input.readFromTerminal = false;
                    ClearScreen();
                break;
                case 5:
                    // Indiquer le nom du script a charger
                    // Input.getScript
                break;
            }
            System.out.println();
        }
    }

    private static void ClearScreen() {
        System.out.print("\033[H\033[2J");      // '\033[H' means 'move to the top of the screen' ; '\033[2J' means 'clear the entire screen'
    }
}