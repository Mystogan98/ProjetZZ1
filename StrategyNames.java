public enum StrategyNames {
	Cheater("Cheater"), 
	Alternater("Alternater");

	private string name;

	StrategyNames(String name)
	{
		this.name = name;
	}

	public string getName() { return name; }
	public string ToString() { return name; }
}