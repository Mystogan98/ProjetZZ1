public enum StrategyNames {
	Cheater("Cheater"), 
	Copycat("Copycat"),
	Cooperater("Cooperater"),
	Resentful("Resentful"),
	Detective("Detective"),
	Alternater("Alternater"),
	Wary("Wary");

	private String name;

	StrategyNames(String name)
	{
		this.name = name;
	}

	public String getName() { return name; }
	public String ToString() { return name; }
}