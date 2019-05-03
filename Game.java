public class Game {
    private Strategy bot;
    private Player player;
    private Memory memory;
    
    public Game() {
        // Instantiate bot, player and memory
        bot = Strategy.Instantiate();
        player = new Player();
        memory = new Memory();
    }

    public void ComputeTurn() {
        System.out.println(bot.GetName());
        for(int i = 0 ; i < 10 ; i++) {        
            bot.Play(memory);
            player.Play(memory);
            ComputeScore();
            memory.EndTurn();
            memory.Show();
            System.out.println("Bot : " + bot.GetScore() + " // Player : " + player.GetScore());
        }
    }

    // En gros la boucle de gameplay ca va etre ça :
    // bot.play(memory);
    // player.play(memory);
    // computeScore();
    // Memory.EndTurn();
    // Afficher();
    // Et on boucle.
    // On pourra faire tout ce qu'on veux entre, donc pas de soucis pour étendre le jeu.

    // Dans player on pourra faire ce qu'on veut dans play(), ici on n'utilise que la varible action.
    // Du coup on pourra facilement faire un jeu a la main ou par algo.

    // Les bot (Strategy) devront etre des implementation de Strategy, comme ca on fait "play" comme on veut pour chaque.
    // D'ailleurs la classe "Strategy" est l'implémentation du pattern "Strategy". Ah ah.
    // Faudra prévoir pour les générer aléatoirement, caller un bon gros switch quelque part.
    
    // Y'a pas de fonction d'affichage ni vraiment de boucle principale encore, mais c'pas très grave, ca viendra.

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
                // Il se passe rien ici, je laisse le if mais en vrai on pourrait meme l'enlever.
            }
        }
        if(bot.GetAction() == Action.error || player.GetAction() == Action.error)
        {
            System.out.println("Y'a une erreur là Jean-Paul.");
        }
    }

    private void Menu() {
        // On en as besoin ? Pas sur
    }
}