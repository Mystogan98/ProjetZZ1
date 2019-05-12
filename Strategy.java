public class Strategy extends Player {
    private String name;
    private static int nbStrategy = StrategyNames.values().length;

    public static Strategy Instantiate() {
        // La on met un bon gros switch sa maman pour instantier au hasard
		int rng = (int) Math.floor(Math.random() * nbStrategy) + 1;   // Retourne un entier entre 1 et nbStrategy

        switch(rng)
        {
            case 1:
                return new Cheater();
            case 2:
                return new Alternater(null);
            case 3:
                return new Copycat();
            case 4:
                return new Cooperater();
            case 5:
                return new Resentful();
            case 6:
                return new Detective();
            case 7:
                return new Wary();
            default:
                System.out.println("Y'a une erreur la Jean-Paul");
                return new Alternater(null);
        }
    }

    public void Play(Memory memory) {
        action = Action.error;
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}