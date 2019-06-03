import java.io.BufferedReader;

public class Game {
    private Strategy bot;
    private Player player;
    private Memory memory;
    
    public Game() {
        // Instantiate bot, player and memory
        bot = Strategy.Instantiate(null);
        player = new Player();
        memory = new Memory();
    }

    public void ComputeTurn() {
        player.ResetScore();
        memory.Reset();
        System.out.println(bot.GetName());
        for(int i = 0 ; i < 10 ; i++)
			ComputeOneTurn();
        System.out.println();
	}
	
	private void ComputeOneTurn() {
		bot.Play(memory);
		player.Play(memory);
		ComputeScore();
		memory.EndTurn();
		ShowScore();
	}

    public void Tournament() {
        int botScore = 0;
        player.ResetScore();

        for (StrategyNames name : StrategyNames.values()) {
            memory.Reset();
            bot = Strategy.Instantiate(name.getName());
            bot.IncreaseScore(botScore);    // We keep the score through all the AI

            System.out.println(bot.GetName());
            for(int i = 0 ; i < 10 ; i++)
                ComputeOneTurn();
            botScore = bot.GetScore();
            System.out.println();
        }

        Main.ClearScreen();
        System.out.println("Final scores : ");
        System.out.println("Player : " + player.GetScore());
        System.out.println("Bot    : " + bot.GetScore());
        System.out.println();
	}
	
	public void Mystery(BufferedReader in) {
		int maxScore = 0;
		int choix;
		String file;

		for(int i = 0 ; i < 3 ; i++) {
			player.ResetScore();
			memory.Reset();
			bot = Strategy.Instantiate(StrategyNames.Deceitful.getName());
			System.out.println(bot.GetName());
			for(int j = 0 ; j < 10 ; j++)
				ComputeOneTurn();
			if(player.score > maxScore)
				maxScore = player.score;

			System.out.println("Score ce tour : " + player.score);
			System.out.println("Score maximum : " + maxScore);
			System.out.println();
			if(i < 2) {
				System.out.println("1 - Charger un nouveau script (Actuel : " + Main.scriptName + ")");
				System.out.println("2 - Round suivant");
				try {
					choix = Integer.parseInt(in.readLine());
				} catch(Exception e) {
					System.err.println("Problème lors de la sélection.");
					choix = 0;
				}
				switch(choix) {
					case 1:
						Main.ClearScreen();
						System.out.println("Indiquez le nom du script à charger :");
						try {
							file = in.readLine();
						} catch(Exception e) {
							System.err.println("Problème lors de l'entrée du nom de fichier.");
							return;
						}
						Input.getScript(file);
					break;
				}
				Main.ClearScreen();
			}
		}
		System.out.println();
	}

    private void ShowScore() {
        String result = "Bot : ";

        result += ShowTextFormating(bot.GetScore(), memory.GetLastBotAction());
        result += " // Player : ";
        result += ShowTextFormating(player.GetScore(), memory.GetLastPlayerAction());

        System.out.println(result);
    }

    private String ShowTextFormating(int score, Action action)
    {
        String result = "";

        if (Math.abs(score) >= 10)
            result += score;
        else if (Math.abs(score) >= 0)
            result += score + " ";

        if(score >= 0)
            result += " ";

        result += " (";
        if(action == Action.cooperate)
            result += "cooperate)";
        else
            result += "  cheat  )";

        return result;
    }

    // En gros la boucle de gameplay ca va etre ça :
    // bot.play(memory);
    // player.play(memory);
    // computeScore();
    // Memory.EndTurn();
    // Afficher();
    // Et on boucle.
    // On pourra faire tout ce qu'on veux entre, donc pas de soucis pour étendre le jeu.

    // Dans player on pourra faire ce qu'on veut dans play(), ici on n'utilise que la variable action.
    // Du coup on pourra facilement faire un jeu à la main ou par algo.

    // Les bot (Strategy) devront etre des implementation de Strategy, comme ca on fait "play" comme on veut pour chaque.
    // D'ailleurs la classe "Strategy" est l'implémentation du pattern "Strategy". Ah ah.

    private void ComputeScore() {
        if(bot.GetAction() == Action.cooperate)
        {
            if(player.GetAction() == Action.cooperate) {
                bot.IncreaseScore(2);
                player.IncreaseScore(2);
            } else if (player.GetAction() == Action.cheat) {
                bot.IncreaseScore(-1);
                player.IncreaseScore(3);
            }
        } else if (bot.GetAction() == Action.cheat) {
            if(player.GetAction() == Action.cooperate) {
                bot.IncreaseScore(3);
                player.IncreaseScore(-1);
            } else if (player.GetAction() == Action.cheat) {
                // Il se passe rien ici, je laisse le if mais en vrai on pourrait même l'enlever.
            }
        }
        if(bot.GetAction() == Action.error || player.GetAction() == Action.error)
        {
            System.out.println("Y'a une erreur là Jean-Paul.");
        }
    }

    // If name == null, then it will instantiate an AI randomly
    public void ChooseAI(String name)
    {
        bot = Strategy.Instantiate(name); 
    }
}