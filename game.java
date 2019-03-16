 
public class Game {
    Strategy bot;
    Player player;
    Memory memory;
    
    public Game() {
        // Instantiate bot, player and memory
    }

    // En gros la boucle de gameplay ca va etre ca :
    // bot.play(memory);
    // player.play(memory);
    // computeScore();
    // Afficher();
    // Et on boucle.
    // On pourra caller tout ce qu'on veux entre, donc pas de soucis pour étendre le jeu.

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
    }

    private void Menu() {
        // On en as besoin ? Pas sur
    }
}