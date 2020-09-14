import java.util.Scanner;
import java.util.Random;

class Play {

    public static String drawCard (String visibility) {

        String cards[] = {"Ace","2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        Random r = new Random();
        int randomIndex = r.nextInt(13);
        String drawnCard = cards[randomIndex];
        if (visibility == "faceUp") {

            if (drawnCard == "Ace") {

                System.out.println("An Ace is drawn.");                               
            } else {

                System.out.println("A " + drawnCard + " is drawn.");
            }
        } else if (visibility == "faceDown") {

                System.out.println("A face-down card is drawn.");
            }        
        return drawnCard;  
    }

    public static int assignPoints (int score, String card) {

        int earnedPoints = 0;
        if (card == "Ace" && score < 11) {

            System.out.println("Which value do you want to asign: 1 or 11?");
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

        } else if (card == "10" || card == "Jack" || card == "Queen" || card == "King") {

                earnedPoints = 10;
                System.out.println("10 points are earned.");
        } else if (card == "Ace" && score >= 11) {

                earnedPoints = 1;
                System.out.println("1 point is earned.");
        } else {

            earnedPoints = Integer.parseInt(card);
            System.out.println(card + " points are earned.");
        }
        return earnedPoints;
    }

    public static Scanner input = new Scanner(System.in);

    public static int playerCardsDistribution() {

        int playerScore = 0;
        System.out.println("Player's turn. Two cards are drawn.");
        String playerCard1 = drawCard("faceUp");
        playerScore = assignPoints(playerScore, playerCard1);
        String playerCard2 = drawCard("faceUp");
        playerScore += assignPoints(playerScore, playerCard2);
        System.out.println("The Player's score is " + playerScore + ".\n");
        return playerScore;
    }

    public static String[] dealerCardsDistribution() {

        int dealerScore = 0;
        System.out.println("Dealer's turn. Two cards are drawn: one face-up and one face-down.");
        String dealerCardFaceUp = drawCard("faceUp");
        dealerScore = assignPoints(dealerScore, dealerCardFaceUp);
        String dealerCardFaceDown = drawCard("faceDown");
        System.out.println("The Dealer's score is " + dealerScore + ".\n");
        String[] tab = new String[3];
        tab[0] = dealerCardFaceUp;
        tab[1] = dealerCardFaceDown;
        tab[2] = String.valueOf(dealerScore);
        return tab;
    }

    public static char askContinue() {

        System.out.println("Would you like another card? y or n");
        char continuePlayer = input.next().charAt(0);
        System.out.println();
        return continuePlayer;
    }

    public static int drawNewCard(int score) {

        int playerScore = score;
        String newCard = drawCard("faceUp");
        int earnedPoints = assignPoints(playerScore, newCard);
        playerScore += earnedPoints;
        System.out.println("The new score is " + playerScore + ".");
        return playerScore;
    }

    public static int newCardProcess(int score, char answer) {

        int currentScore = score;   
        while ( currentScore <= 21 && answer == 'y' ) {
            currentScore = drawNewCard(currentScore);
            if ( currentScore > 21) {
                System.out.println("The Player loses.");
                System.out.println();
            } else if ( currentScore == 21 ) {
                System.out.println("The Player's score reaches 21.");
                System.out.println();
            } else {
                answer = askContinue();
            }
        }
        System.out.println("The Player's final score is " + currentScore + ".");
        return currentScore;
    }

    public static int endingProcess(int playerScore, int dealerScore, String faceUpCard, String faceDownCard) {

        int newDealerScore = dealerScore;
        if (playerScore <= 21) {

            System.out.println("\nThe Dealer's face-up card was " + faceUpCard + ".");
            System.out.println("The Dealer's face-down card is revealed: it's a " + faceDownCard + ".");
            int faceDownCardPoints = assignPoints (dealerScore, faceDownCard);
            newDealerScore = dealerScore + faceDownCardPoints;
            System.out.println("His score is " + newDealerScore + ".\n");
            while (newDealerScore < 17 ) {

                newDealerScore = drawNewCard(newDealerScore);
                if (newDealerScore > 21) {

                    System.out.println("The dealer loses.");
                } else if ( newDealerScore == 21 ) {

                    System.out.println("The Dealer's score reaches 21.");
                } else {

                    System.out.println("The new Dealer's score is " + newDealerScore + ".");
                }
            }
        }
        System.out.println();
        return newDealerScore;
    }

    public static void winnerSelection(int score1, int score2) {

        if ((score1 > 21 || score1 < score2) && (score2 < 22)) {

            System.out.println("The Dealer wins.\n");
        } else if ((score2 > 21 || score1 > score2) && (score1 < 22)) {

            System.out.println("The Player wins.\n");
        } else if ((score1 == score2) && (score1 < 22) && (score2 < 22)) {

            System.out.println("The Player and the Dealer are ex aequo.\n");
        }
    }
    
    public static void main(String[] args) {

        int playerScore = 0;
        int dealerScore = 0;
        char answer;

        // Player's turn. Two cards are given to the Player.
        playerScore = playerCardsDistribution();

        // Dealer's turn. Two cards are given to the Dealer: one face-up and one face-down.
        String [] tableScoreDealer = dealerCardsDistribution();
        String dealerFaceUpCard = tableScoreDealer[0];
        String dealerFaceDownCard = tableScoreDealer[1];
        dealerScore = Integer.parseInt(tableScoreDealer[2]);

        // Player's turn: he decides if he continues or not.
        answer = askContinue();

        // Until the player score is 21 or under, he can decide to ask for additional cards, one at a time.
        playerScore = newCardProcess(playerScore, answer);

        // The Dealer's face-down card is revealed and he plays.
        dealerScore = endingProcess(playerScore, dealerScore, dealerFaceUpCard, dealerFaceDownCard);

        // Winner selection.
        winnerSelection(playerScore, dealerScore);       
    }
}
