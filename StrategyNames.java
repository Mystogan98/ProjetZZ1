public enum StrategyNames {
	Cheater("Cheater"), 
	Copycat("Copycat"),
	Cooperater("Cooperater"),
	Alternater("Alternater");

	private String name;

	StrategyNames(String name)
	{
		this.name = name;
	}

	public String getName() { return name; }
	public String ToString() { return name; }
}