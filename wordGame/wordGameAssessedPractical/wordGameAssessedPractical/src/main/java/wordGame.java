/*
- Title: Word Game Assessed Practical 3
- Files: wordGame.java
- Author: Jack Machin
- Student Number: 2101423901
- CS login: x5z47
- Description: The program is a Java Word Game which prompts a user for input, applies rules to the input 
               and then gives the correct output based on this. The game ends when the user enters 30 correct
               inputs(win) or enters a validated word that has been entered previously. If the word is not 
               validated, by either having a first character that does not match the previous word's last 
               character or by not being contained in the dictionary.txt file included, the user will be 
               prompted to enter another word until a valid input is received. 
- Last Modified: 10th December 2021
 */

import java.io.*;
import java.util.Arrays;
import java.util.Locale;

public class wordGame {
    static boolean valid = true;
    static boolean validRule1 = true;
    static int score = 0;

    public static void main(String[] args) throws IOException {
        String[] words = new String[31]; //String array to store all validated words
        words[0] = "empathy";
        int gameRunInt = 0;
        gameStart(gameRunInt); //Calls the method that prompts the user to start the game
        System.out.println(words[0]);
        for (int i = 0; i < 31; i++) { //Loop is complete when 31(starting word + 30 validated inputs) words have been entered
            int gameRunInt2 = 0;
            while (gameRunInt2 < 2) {
                if(words[words.length - 1] != null){
                    gameWin(); //Calls the method that announces that the game has been beaten
                }
                System.out.println("Please enter a word: ");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader newWord = new BufferedReader(isr);
                String enteredWord = newWord.readLine(); //Stores the input from the prompt in the variable enteredWord
                enteredWord = enteredWord.trim(); //Removes all spaces before and after the entered word
                if(enteredWord.equals("")){
                    gameRestart(gameRunInt, words); //Calls the method that restarts the game
                    break;
                }
                else{
                    wordValid(enteredWord, words); //if the value is anything other than nothing then the word validation method is called
                }
                if(!valid || !validRule1){ //Checks if the variable valid or validRule1 has been set as false for breaking the rules 
                    System.out.println("Entered word is invalid or does not exist in the dictionary, please try again");
                }
                if(valid && validRule1){ //If the variables valid and validRule1 are both true then the validated word is added to the array of correct words
                    words[i + 1] = enteredWord;
                    gameRunInt2 = 4;
                    score++; 
                }
            }
            for (int j = 0; j < words.length; j++) {
                if ((words[j] != null)){
                    System.out.print(words[j] + " "); //prints out all the correct words entered so far
                }
            }
            System.out.print("\n");
        }
    }

    static void gameStart(int gameRunInt) throws IOException { //method that starts the game
        int runWhile = 0;
         do{
             InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(isr);
             System.out.println("Would you like to play (yes/no): ");
             String startInput = br.readLine();
             startInput = startInput.trim(); //Removes all spaces before and after the entered word
            if (startInput.equals("yes")) {
                System.out.println("Rules:\n1. You must enter a word which begins with the same character" +
                        " as the previous word ended with.\n2. You must not enter a word that you have entered before, if so thew game will end.\n3. " +
                        "You can only use characters A-Z and a-z, no numbers or special characters.\n" +
                        "4. If you enter nothing, the game will restart. \n" +
                        "5. If you successfully enter in 30 words you win the game!" + "\nThe game is also case sensitive" +
                        "so hello and hEllo can be used separately without errors" +  "\nYour score increases for every correct word entered" +
                        "\nThe game starts here:" + "\n-------------");
                gameRunInt = 1;
                runWhile = 10;
            }
            else if (startInput.equals("no")) {
                System.out.println("Goodbye");
                System.exit(0);
                runWhile = 10;
            }
            else {
                System.out.println("You did not enter yes/no, please try again");
            }
        }
         while(runWhile < 9);
    }
    
    public static boolean wordValid(String enteredWord, String[] words) throws IOException { //This method validates each word, entered by the user, and allows further action if valid
        String currentLine;
        int wordCounter = 0;
        int dictionaryLines = 0; //The number of words/non-empty lines in the dictionary.txt file
        if(Arrays.asList(words).contains(enteredWord)){
            System.out.println("Game over.");
            System.out.println("You scored: " + score);
            System.out.println("The program will now close.");
            System.exit(0); //Closes the program
        }
        for(int i = 1; i < words.length; i++) { //Loop to check the last string index in the array "words" that is not null and assigns this index to the integer "wordCounter"
            if(words[i] == null){
                wordCounter = i - 1;
                break;
            }
            else{
                wordCounter = 0;
            }
        }
        if(enteredWord.charAt(0) == words[wordCounter].charAt(words[wordCounter].length() - 1)){ //This enforces rule 1 which is checking if the first digit of the entered word is the same as the last digit of the previous word
            validRule1 = true;
        }
        else{
            validRule1 = false;
        }
        try{
            File file = new File("dictionary.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            if(file.exists()){ //Checks if the file "dictionary.txt" exists
                while((currentLine = br.readLine()) != null){
                    dictionaryLines++;
                }
            }
        } catch(FileNotFoundException f){
            System.out.println(f.toString());
        } catch (IOException e){
            System.out.println(e.toString());
        }
        File file = new File("dictionary.txt");
        int counter = 0;
        BufferedReader br1 = new BufferedReader(new FileReader(file));
        while(counter <= dictionaryLines){
            currentLine = br1.readLine();//Assigns the value of the current line of text, being read from dictionary.txt, to the string "currentLine"
            try {
                if (currentLine.equals(enteredWord.toLowerCase(Locale.ROOT).trim())){
                    valid = true;
                    break;
                } else{
                    valid = false;
                }
            }
            catch(Exception e){        
            }
            counter++;
        }
    return valid;
    }
    
    static void gameRestart(int gameRunInt, String[] words) throws IOException { //Method to restart the game by calling the main method 
        System.out.println("Game restarted");
        for(int k = 0; k < words.length; k++){
            words[k] = null; //Sets each element in the array containing the previous words used to null
        }
        words[0] = "empathy";
        score = 0;
        main(null);
    }
    
    static void gameWin(){ //This method is called when the user correctly inputs 30 valid words 
        System.out.println("Congratulations you have won the game!");
        System.out.println("Your score is " + score + "!");
        System.out.println("Thank you for playing!");
        System.out.println("The program will now end");
        System.exit(0);
    }
}