public enum StrategyNames {
	Cheater("Cheater"), 
	Copycat("Copycat"),
	Cooperater("Cooperater"),
	Alternater("Alternater");

	private string name;

	StrategyNames(String name)
	{
		this.name = name;
	}

	public string getName() { return name; }
	public string ToString() { return name; }
}