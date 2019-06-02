import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    private static Game game;
    public static String scriptName = "None";

    public static void main(String[] args) {
        game = new Game();
        Menu();
    }

    private static void Menu() {
        int choix = 0;
        String file;
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
                    AISelectionMenu(in);
                break;
                case 2:
                    if(Input.readFromTerminal)
                    {
                        ClearScreen();
                        System.out.println("Impossible de jouer au clavier en mode tournoi. Veuillez changer le mode de jeu ou charger un nouveau script.");
                        System.out.println();
                    } else {
                        ClearScreen();
                        game.Tournament();
                    }
                break;
                case 3:
                    HelpMenu(in);
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
                    ClearScreen();
                    System.out.println("Indiquez le nom de script à charger :");
                    try {
                        file = in.readLine();
                    } catch(Exception e) {
                        System.err.println("Problème lors l'entrée du nom de fichier.");
                        return;
                    }
                    Input.getScript(file);  
                    ClearScreen();
                break;
            }
        }
    }

    public static void ClearScreen() {
        System.out.print("\033[H\033[2J");      // '\033[H' means 'move to the top of the screen' ; '\033[2J' means 'clear the entire screen'
    }

    private static void AISelectionMenu(BufferedReader in) {
        int choix = 0, count = 1;
        StrategyNames[] names = StrategyNames.values();

        ClearScreen();
        while(choix != names.length + 1)
        {
            count = 1;
            for (StrategyNames name : names) {
                System.out.println(count + " - " + name.getName());
                count++;
            }
            System.out.println((names.length + 1) + " - Revenir au menu principal");
            
            try {
                choix = Integer.parseInt(in.readLine());
            } catch(Exception e) {
                System.err.println("Problème lors de la sélection.");
                return;
            }

            if(choix < names.length && choix > 0) {
                ClearScreen();
                game.ChooseAI(names[choix-1].getName());
                game.ComputeTurn();
            }
        }
        ClearScreen();
    }

    private static void HelpMenu(BufferedReader in) {
        int choix = 0;

        ClearScreen();
        while(choix != 6)
        {
            System.out.println("1 - Quel est le but du jeu ?");
            System.out.println("2 - Quelles sont les regles du jeu ?");
            System.out.println("3 - Comment jouer ?");
            System.out.println("4 - Comment créer un script ?");
            System.out.println("5 - A quoi correspond le mode 'tournoi' ?");
            System.out.println("6 - Revenir au menu principal");
            
            try {
                choix = Integer.parseInt(in.readLine());
            } catch(Exception e) {
                System.err.println("Problème lors de la sélection.");
                return;
            }

            ClearScreen();
            switch(choix) {
                case 1:
                    System.out.println("Le jeu vous met face à une intelligence artificielle doté d'une stratégie propre. Le but du jeu est alors d'obtenir le plus gros score possible contre chacune des IA dans un jeu inspiré du dilemne du prisonnier. A chaque tour vous devrez soit tricher et ainsi profiter de la naïveté de votre adversaire (qui pourra se venger !) ou coopérer et espérer que votre adversaire en face de même. Le but ultime du jeu étant de faire un script capable de maximiser son score contre tout type d'IA.");
                break;
                case 2:
                    System.out.println("A chaque tour vous pourrez soit tricher, soit coopérer. Selon l'action de votre adversaire, le résultat seras différent :");
                    System.out.println("Si vous coopérez tous les deux, tout le monde gagne 2 points");
                    System.out.println("Si vous coopérez mais que votre adversaire triche, il gagne 3 points et vous en perdez 1.");
                    System.out.println("Si vous trichez et que votre adversaire coopére, vous gagnez 3 points et il en perd 1.");
                    System.out.println("Enfin, si vous tricher tout les deux, personne ne gagne rien.");
                    System.out.println();
                    System.out.println("Pour maximiser votre score il faudra donc savoir tricher lorsque vous pouvez abuser de la naïveté de votre adversaire, mais aussi lorsque lui essaye de profiter de vous. Néanmoins, si vous tricher trop votre adversaire risque de tricher à son tour, vous empechant de gagner des points.");
                    System.out.println("Tout le but du jeu est donc de comprendre comment les IA fonctionne et de créer un script capable de les analyser et de jouer en conséquence afin d'obtenir le meilleur score possible.");
                break;
                case 3:
                    System.out.println("Pour jouer au clavier, il vous seras demander d'entrez 'cooperer' ou 'tricher' à chaque début de tour. Il vous suffit alors de rentrer l'option que vous souhaitez.");
                    System.out.println("Pour jouer avec un script, il vous faut d'abord l'écrire (voir n°4) puis le charger dans le menu principal. Une fois fait, le jeu se déroulera tout seul en suivant les directive écrite dans le script.");
                break;
                case 4:
                    System.out.println("Lorsque vous voulez créer un script, il vous faut vous munir d'un éditeur de texte et suivre ces quelques régles de syntaxes :");
                    System.out.println();
                    System.out.println("Tout ce qui se trouve avant les 2 points constitue l'historique de jeu de l'IA, 'c' pour 'coopérer', 't' pour 'tricher'");
                    System.out.println("Exemple : 'cctc' = si l'IA à coopérée 2 fois, puis trichée, puis coopérée à nouveau");
                    System.out.println("Après le ':', écrire 'c' si le script doit coopérer, ou 't' si il doit tricher");
                    System.out.println("Exemple : 'c:c' = si l'IA à coopérer au tour précédent, coopérer.");
                    System.out.println("On peut également utiliser le caractère '#' pour écrire des ligne de commentaires (non lues par le jeu).");
                    System.out.println();
                    System.out.println("La syntaxe autre que les symbole 'c', 't', ':' et '#' n'a aucune importance, on peut donc utiliser la syntaxe que l'on souhaite");
                    System.out.println("Exemple : ");
                    System.out.println("f():t             => Si l'IA n'as rien joué au dernier tour (= le premier tour de jeu), tricher");
                    System.out.println("tt:t              => Si l'IA a triché les deux derniers tours, tricher");
                    System.out.println("si cc alors : c   => Si l'IA a coopérer les deux derniers tours, coopérer");
                break;
                case 5:
                    System.out.println("Le mode tournoi ne peut se jouer que à l'aide d'un script, et permet d'affronter toutes les IA à la suite. Il permet de tester son script contre la totalité des statégies, et nous donne un score final après les avoirs toutes affrontée.");
                    System.out.println("Cela permet de tester l'éfficacité de son script, et ainsi pouvoir le comparer aux script des autres joueurs !");
                break;
            }
            System.out.println();
        }
        ClearScreen();
    }
}