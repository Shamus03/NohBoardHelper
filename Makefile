.PHONY: clean run

all: jar

jar: NohBoardHelper.jar

clean:
	rm -rf *.class NohBoardHelper.jar

run: jar
	java -jar NohBoardHelper.jar

NohBoardHelper.jar: NohBoardHelper.class
	jar cfe NohBoardHelper.jar NohBoardHelper *.class keytranslations.txt

NohBoardHelper.class: NohBoardHelper.java KeyPanel.class OptionPanel.class
	javac NohBoardHelper.java

KeyPanel.class: KeyPanel.java KeyBox.class
	javac KeyPanel.java

OptionPanel.class: OptionPanel.java
	javac OptionPanel.java

KeyBox.class: KeyBox.java KeyDictionary.class
	javac KeyBox.java

KeyDictionary.class: KeyDictionary.java
	javac KeyDictionary.java
