public class Strategy extends Player {
    private String name;
    private static int nbStrategy = StrategyNames.values().length;

    public static Strategy Instantiate(String name) {
        if(name != null)
            return InstantiateByName(name);
        return InstantiateByRandom();

    }

    private static Strategy InstantiateByName(String name)
    {
        switch(name)
        {
            case "Cheater":
                return new Cheater();
            case "Alternater":
                return new Alternater(null);
            case "Copycat":
                return new Copycat();
            case "Cooperater":
                return new Cooperater();
            case "Resentful":
                return new Resentful();
            case "Detective":
                return new Detective();
            case "Wary":
                return new Wary();
            default:
                System.out.println("Y'a une erreur là Jean-Paul");
                return InstantiateByRandom();
        }
    }

    private static Strategy InstantiateByRandom()
    {
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
                System.out.println("Y'a une erreur là Jean-Paul");
                return new Alternater(null);
        }
    }

    public void Play(Memory memory) {
        action = Action.error;
        memory.MemoriseBotAction(action);
    }

    public String GetName() { return name; }
}