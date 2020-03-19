import java.lang.Math.*;
import java.time.Year;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

class Play {

    // Card drawing method
    public static int drawCard (int score) {
        String cards[] = {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        int earnedPoints = 0;
        Random r = new Random();
        int randomIndex = r.nextInt(13);
        String drawnCard = cards[randomIndex];
        if (drawnCard == "Ace" && score < 11) { //Possible optimization: bypass this when the dealer plays.
            System.out.println("An Ace has been drawn. Which value do you want to asign: 1 or 11?");
            int aceAnswer = input.nextInt();
            if (aceAnswer == 1) {
                earnedPoints = 1;
                System.out.println("1 point is earned.");
            } else if (aceAnswer == 11) {
                earnedPoints = 11;
                System.out.println("11 points are earned.");
            } else { 
                System.out.println("Your answer isn't valid! 11 points are earned.");
                earnedPoints = 11;
            }
        } else if (drawnCard == "10" || drawnCard == "Jack" || drawnCard == "Queen" || drawnCard == "King") {
                earnedPoints = 10;
                System.out.println("A " + drawnCard + " has been drawn. 10 points are earned.");
        } else {
            earnedPoints = Integer.parseInt(drawnCard);
            System.out.println("A " + drawnCard + " has been drawn. " + drawnCard + " points are earned.");
        }
        return earnedPoints;
    }


    // Gestion de l'as et du 11
    // public static int aceChecker(int card, int scoreBefore) {
    //     if (card == 1 && scoreBefore < 11) {
    //         System.out.println("A 1 has been selected. Which value do you want to choose: 1 or 11?");
    //         int newAceValue = input.nextInt();
    //         return newAceValue;
    //      } else
    //          return card;
    // }

    public static Scanner input = new Scanner(System.in);

    // public static int takeCard(int scoreBefore) {
    //     double nbRandom = Math.random() * 10;   // ajouter le traitement des têtes
    //     int nbRandomInt = (int) (nbRandom + 1);
    //     int checkCard = aceChecker(nbRandomInt, scoreBefore);
    //     return checkCard;
    // }

    // The player takes tow cards and he reads the result
    public static int playerCardsDistribution() {
        System.out.println("Player's turn. Two cards are drawn.");
        int playerCard1 = drawCard(0);
        int playerCard2 = drawCard(0);
        int playerScore = playerCard1 + playerCard2;
        System.out.println("The Player's score is " + playerScore + ".");
        return playerScore;
    }

    // he dealer gives one card face up and one card face down to himself.
    public static int[] dealerCardsDistribution() {
        System.out.println("Dealer's turn. Two cards are drawn: one face-up and one face-down.");
        int dealerCardFaceUp = drawCard(0);
        int dealerCardFaceDown = drawCard(0);
        int dealerScore = dealerCardFaceUp + dealerCardFaceDown;
        System.out.println("The Dealer's score is  " + dealerCardFaceUp + ".");
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
        int newCard = drawCard(playerScore);
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
        int playerScore = 0;
        int dealerScore = 0;
        char answer;

        // Player's turn. Two cards are given to the Player.
        playerScore = playerCardsDistribution();
        System.out.println();

        //The dealer gives one card face up and one card face down to himself.
        int [] tableScoreDealer = dealerCardsDistribution();
        System.out.println();

        //The player must decide whether to ask for another card in an attempt to get closer to 21, or to stop
        answer = askContinue();
        System.out.println();

        //Until the player score is 21 or under, he can decide to ask for additional cards, one at a time.
        int lastScorePlayer = newCardProcess(playerScore , answer);
        System.out.println("Your final score is " + lastScorePlayer);
        System.out.println();

        if( lastScorePlayer <= 21) {
            //The dealer will turn up his face-down card
            dealerScore = tableScoreDealer[2];
            System.out.println("The dealer's card face up is " + tableScoreDealer[0]);
            System.out.println("The dealer's card face down is " + tableScoreDealer[1]);
            System.out.println("His score is " + dealerScore + "\n");
        
            //The dealer plays
            while (dealerScore < 17 ) {
                dealerScore = takeNewCard(dealerScore);
                if ( dealerScore > 21) {
                    System.out.println("The dealer looses");
                } else if ( dealerScore == 21 ) {
                    System.out.println("The dealer has 21");
                } else {
                    System.out.println("The new dealer's score is " + dealerScore);
                }
            }
        }
        System.out.println();

        //And the winner is
        winnerSelection(lastScorePlayer, dealerScore);
        System.out.println();        
    }
}