package nz.ac.auckland.se281.a2.cli;

/**
 * 
 * You cannot modify this class!
 * 
 */
public enum MessagesCLI {

	// ERRORS
	COMMAND_NOT_FOUND("!!!Error!!! Command not found! (run 'usage' for the list of available commands)"), //
	NO_ARGUMENT("!!!Error!!! This command takes in input no arguments!"), //
	ONE_ARGUMENT("!!!Error!!! This command takes in input one argument"), //
	NOT_VALID_NUMBER("!!!Error!!! You did not enter a number"), //
	NOT_VALID_MENU_ID("!!!Error!!! Not a valid Menu ID"), //
	NOT_VALID_SIZE("!!!Error!!! Not a valid Size, should be M, L or XL (case sensitive)"), //
	NOT_VALID_CHOICE("!!!Error!!! Wrong ID"), //
	NOT_VALID_CART_POSITION("!!!Error!!! Not a valid Cart item position"), //
	ORDER_INVALID_CART_EMPTY("!!!Error!!! Your Cart is empty!"), //
	// MESSAGES
	CHOSE_SIZE("Choose a size: 'M' or 'L' (+$3) or 'XL' (+$4): "), //
	CART_EMPTY("Cart is empty"), //
	DISCOUNT("You are spending $100 or more, we applied 25% discount!"), //
	ESTIMATE_WAITING_TIME("Order confirmed! The estimated waiting time for preparing your order is: "), //
	MISSED_COMBO(
			"WARNING! You added a Burger and Snacks and Drink of same size, you should change to a combo and save money!"), //
	END("You closed the terminal, thank you for Choosing Burger Shop!"); //

	private String msg;

	MessagesCLI(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return msg;
	}

	public void printMessage() {
		System.out.println(msg);
	}
}
