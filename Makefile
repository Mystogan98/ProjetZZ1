CC = javac

main:$(wildcard *.java)
	$(CC) $^

clean:
	rm *.class