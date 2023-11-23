import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BlackJack {

	private Deck deck; // The deck used in the game
	private Hand dealer; // The hand of the dealer
	private Hand player; // The hand of the player

	/*
	 * Setters and getters. Mainly needed for testing functionality.
	 * 
	 * DO NOT MODIFY THEM!
	 */

	public void setPlayerHand(Hand hand) {
		player = hand;
	}

	public void setDealerHand(Hand hand) {
		dealer = hand;
	}

	public void setDeck(Deck playingDeck) {
		deck = playingDeck;
	}

	public Hand getDealerHand() {
		return dealer;
	}

	public Hand getPlayerHand() {
		return player;
	}

	public Deck getDeck() {
		return deck;
	}

	/**
	 * Helper method to get user input on whether to hit (draw one more card) or
	 * stand (stop drawing cards). In order to draw another card, the user should
	 * reply with hit (case insensitive). Any other reply will be interpreted as
	 * stand
	 * 
	 * @return Returns true if the user wants another card, otherwise false
	 */
	public boolean hitOrStand() {
		String inputLine = null;
		System.out.print("Hit or stand? ");
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
			inputLine = is.readLine();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
		}
		if (inputLine != null && inputLine.toLowerCase().equals("hit")) {
			return true;
		}
		return false;
	}

	/*
	 * TODO: Implement the methods below.
	 */

	/**
	 * This method should set up the game. This means that objects for the deck and
	 * the hands should be created, the deck should be shuffled, and both the player
	 * and the dealer should be given their cards. Remember that one of the dealer
	 * cards should be face down initially. Make sure that the face down card is the
	 * first card given to the dealer, i.e., the card at index 0 in the hand.
	 */
	public void initializeGame() {

		deck = new Deck();
		player = new Hand();
		dealer = new Hand();

		deck.shuffle();

		setDeck(deck);

		player.addCards(deck.drawCards(3));
		dealer.addCards(deck.drawCards(3));

		setPlayerHand(player);
		setDealerHand(dealer);

		Card firstDealerCard = dealer.getCard(0);
		firstDealerCard.turnDown();

	}

	/**
	 * This method should check the value of the given hand according to the rules
	 * of blackjack
	 * 
	 * @param hand the hand whose value will be checked
	 * @return an integer representing the value of the hand
	 */
	public int checkHandValue(Hand hand) {

		int value = 0;
		int numAces = 0;

		for (Card card : hand.getCards()) {

			if (card.getValue() == 1) {
				value += 11;
				numAces++;
			} else if (card.getValue() > 10) {
				value += 10;
			} else {
				value += card.getValue();
			}
		}

		while (numAces > 0 && value > 31) {
			value -= 10;
			numAces--;
		}

		return value;
	}

	/**
	 * This method should display the hand in the console. Use the toString() method
	 * in the class Hand for getting the contents of the hand as a String. If
	 * showHandValue is set to false, the value of the hand shall not be displayed.
	 * If it is set to true, then the value of the hand shall be displayed.
	 * 
	 * Important! The hand value, if displayed, must be surrounded by spaces! Do not
	 * display the value next to another character. The tests for this method will
	 * fail if the value isn't surrounded by spaces
	 * 
	 * @param hand          The hand to display
	 * @param showHandValue If true, the value of the hand will be displayed.
	 *                      Otherwise, the value will not be displayed.
	 */
	public void displayHand(Hand hand, boolean showHandValue) {

		System.out.println(hand.toString());

		if (showHandValue) {
			System.out.println(" " + checkHandValue(hand) + " ");

		}
	}

	/**
	 * The method should contain the logic for making the complete player turn. This
	 * involves allowing the player to choose whether to draw more cards or not,
	 * adding newly drawn cards to the hand and checking whether the hand value has
	 * exceeded 31 or not.
	 * 
	 * @return Returns true if the players hand value did not exceed 31, otherwise
	 *         false
	 */
	public boolean makePlayerTurn() {

		System.out.println("Dealer's hand:");
		displayHand(dealer, false);

		while (true) {
			System.out.println("Player's hand:");
			displayHand(player, true);

			if (checkHandValue(player) > 31) {
				System.out.println("Player's hand value exceeded 31." + "\n");
				return false;
			} // else if (checkHandValue(player) == 31) {
				// System.out.println("Player has blackjack!" + "\n");
				// return true;
				// }

			if (!hitOrStand()) {
				System.out.println("Player chose to stand." + "\n");
				return true;
			}

			Card drawnCard = deck.drawCard();
			player.addCard(drawnCard);
			System.out.println("Player drew: " + drawnCard + "\n");

		}
	}

	/**
	 * The method should contain the logic for making the complete dealer turn. The
	 * dealer follows specific rules on when to draw more cards and when to stand.
	 * This method should implement those rules.
	 * 
	 * @return Returns true if the dealers hand value did not exceed 31, otherwise
	 *         false
	 */
	public boolean makeDealerTurn() {

		Card firstDealerCard = dealer.getCard(0);
		firstDealerCard.turnUp();

		while (checkHandValue(dealer) < 27) {
			System.out.println("Dealer's hand:");
			displayHand(dealer, true);
			Card drawnCard = deck.drawCard();
			dealer.addCard(drawnCard);
			System.out.println("Dealer drew: " + drawnCard);
		}

		System.out.println("Dealer's hand:");
		displayHand(dealer, true);

		return checkHandValue(dealer) <= 31;
	}

	/**
	 * The method should check if the player wins or not. This should be done by
	 * comparing the players hand value with the dealers hand value. Remember that
	 * in case of a tie, the dealer wins.
	 * 
	 * @return Returns true if the player wins, otherwise false
	 */
	public boolean playerWins() {
		int playerValue = checkHandValue(player);
		int dealerValue = checkHandValue(dealer);

		if (playerValue <= 31 && (playerValue > dealerValue || dealerValue > 31)) {
			System.out.println("The player wins!");
			return true;
		}

		System.out.println("The dealer wins!");
		return false;
	}

	/**
	 * The method should contain the logic for playing one round of blackjack. Upon
	 * completion, it should return true if the player won, otherwise false.
	 * 
	 * @return Returns true if the player won, otherwise false
	 */
	public boolean playOneRound() {

		if (checkHandValue(dealer) == 31) {
			return false;
		}
		
		if (checkHandValue(player) == 31) {
			return true;
		}

		makePlayerTurn();
		makeDealerTurn();

		if (playerWins()) {
			return true;
		}

		return false;
	}

}
