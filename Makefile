
CC = javac

main:$(wildcard *.java)
	$(CC) $^
