import java.lang.Math.*;
import java.time.Year;
import java.util.Scanner;
import java.util.Arrays;

class Play {

    // Gestion de l'as et du 11
    public static int aceChecker(int card, int scoreBefore) {
        if ((card == 1) && (scoreBefore < 11)) {
            System.out.println("A 1 has been selected. Which value do you want to choose: 1 or 11?");
            int newAceValue = input.nextInt();
            return newAceValue;
         } else
             return card;
    }

    public static Scanner input = new Scanner(System.in);

    public static int takeCard(int scoreBefore) {

        double nbRandom = Math.random() * 10;
        int nbRandomInt = (int) (nbRandom + 1);
        int checkCard = aceChecker(nbRandomInt, scoreBefore);
        return checkCard;
    }

    // The player takes tow cards and he reads the result
    public static int playerCardsDistribution() {
        int playerCard1 = takeCard(0);
        int playerCard2 = takeCard(0);
        int playerScore = playerCard1 + playerCard2;
        System.out.println("The first card is " + playerCard1);
        System.out.println("The second card is " + playerCard2);
        System.out.println("The score is " + playerScore);
        return playerScore;
    }

    // he dealer gives one card face up and one card face down to himself.
    public static int[] dealerCardsDistribution() {
        int dealerCardFaceUp = takeCard(0);
        int dealerCardFaceDown = takeCard(0);
        int dealerScore = dealerCardFaceUp + dealerCardFaceDown;
        System.out.println("The dealer has a  " + dealerCardFaceUp);
        int[] tab = new int[3];
        tab[0] = dealerCardFaceUp;
        tab[1] = dealerCardFaceDown;
        tab[2] = dealerScore;
        return tab;
    }

    // Ask the player if he would like continue
    public static char askContinue() {
        System.out.println("Would you like another card ? y or n");
        char continuePlayer = input.next().charAt(0);
        System.out.println();
        return continuePlayer;
    }

    public static int takeNewCard(int score) {
        int playerScore = score;
        int newCard = takeCard(playerScore);
        playerScore += newCard;
        System.out.println("The new card is " + newCard);
        System.out.println("The new score is " + playerScore);
        return playerScore;
    }

    public static int newCardProcess(int score, char answer) {
        int currentScore = score;   
        while ( currentScore < 21 && answer == 'y' ) {
            currentScore = takeNewCard(currentScore);
            if ( currentScore > 21) {
                System.out.println("You lost, sorry.");
                System.out.println();
            } else if ( currentScore == 21 ) {
                System.out.println("Good");
                System.out.println();
            } else {
                answer = askContinue();
            }
        }
        return currentScore;
    }

    public static void winnerSelection(int score1, int score2) {

        if ((score1 > 21 || score1 < score2) && (score2 < 22)) {
            System.out.println("The dealer wins");
        } else if ((score2 > 21 || score1 > score2) && (score1 < 22)) {
            System.out.println("The player wins");
        } else if ((score1 == score2) && (score1 < 22) && (score2 < 22)) {
            System.out.println("The player and the dealer are ex aequo");
        }
    }

    public static void main(String[] args) {        
        int currentScore = 0;
        int scoreDealer = 0;
        char answer;

        //The dealer gives twos cards face up to the player.
        currentScore = playerCardsDistribution();
        System.out.println();

        //The dealer gives one card face up and one card face down to himself.
        int [] tableScoreDealer = dealerCardsDistribution();
        System.out.println();

        //The player must decide whether to ask for another card in an attempt to get closer to 21, or to stop
        answer = askContinue();
        System.out.println();

        //Until the player score is 21 or under, he can decide to ask for additional cards, one at a time.
        int lastScorePlayer = newCardProcess(currentScore , answer);
        System.out.println("Your final score is " + lastScorePlayer);
        System.out.println();

        if( lastScorePlayer <= 21) {
            //The dealer will turn up his face-down card
            scoreDealer = tableScoreDealer[2];
            System.out.println("The dealer's card face up is " + tableScoreDealer[0]);
            System.out.println("The dealer's card face down is " + tableScoreDealer[1]);
            System.out.println("His score is " + scoreDealer);
            System.out.println();
        
            //The dealer plays
            while (scoreDealer < 17 ) {
                scoreDealer = takeNewCard(scoreDealer);
                if ( scoreDealer > 21) {
                    System.out.println("The dealer looses");
                } else if ( scoreDealer == 21 ) {
                    System.out.println("The dealer has 21");
                } else {
                    System.out.println("The new dealer's score is " + scoreDealer);
                }
            }
        }
        System.out.println();

        //And the winner is
        winnerSelection(lastScorePlayer, scoreDealer);
        System.out.println();        
    }
}