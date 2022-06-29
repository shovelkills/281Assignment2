package nz.ac.auckland.se281.a2.cli;

/**
 * 
 * You cannot modify this class!
 * 
 */

import java.util.Scanner;

import nz.ac.auckland.se281.a2.BurgerShop;
import nz.ac.auckland.se281.a2.cli.Menu.SIZE;

public class Main {

	public static final String USAGE = "usage";
	public static final String SHOW_MENU = "menu";
	public static final String ADD = "add";
	public static final String ADD_COMBO = "add-combo";
	public static final String REMOVE = "remove";
	public static final String CLEAR_CART = "clear-cart";
	public static final String SHOW_CART = "cart";
	public static final String ORDER = "order";
	public static final String EXIT = "exit";

	private static final String usageMessage = SHOW_MENU + "         [no args]                shows the Menu"
			+ System.lineSeparator() + ADD
			+ "          [1 arg, Menu item ID]    adds in the CART the Menu item with the specified ID"
			+ System.lineSeparator() + ADD_COMBO
			+ "    [no args]                asks to the user to choose the elements of a combo meal"
			+ System.lineSeparator() + REMOVE + "       [1 arg, cart position]   removes the ith element in the cart"
			+ System.lineSeparator() + CLEAR_CART + "   [no args]                removes all elements in the cart"
			+ System.lineSeparator() + SHOW_CART + "         [no args]                shows the content of the cart"
			+ System.lineSeparator() + ORDER + "        [no args]                confirms the order"
			+ System.lineSeparator() + EXIT + "         [no args]                exit the terminal";

	private final BurgerShop restaurant;
	private final Menu menu;
	public static Scanner scanner = new Scanner(System.in);

	protected Main(BurgerShop restaurant, Menu menu) {
		this.restaurant = restaurant;
		this.menu = menu;
	}

	public static void main(String[] args) {
		Main cli = new Main(new BurgerShop(), new Menu());
		cli.start();
	}

	protected void start() {
		printBanner();
		System.out.println(usageMessage);
		String command;
		do {
			System.out.print("burger-shop>");
			command = scanner.nextLine().trim();
		} while (processCommand(command));
	}

	public static void printBanner() {
		StringBuilder buf = new StringBuilder();
		buf.append("\n").append(System.lineSeparator());
		buf.append(
				"                 ________  _______     _______  ________    _____                                             \n"
						+ "                |\\   ____\\|\\  ___ \\   /  ___  \\|\\   __  \\  / __  \\                                            \n"
						+ "                \\ \\  \\___|\\ \\   __/| /__/|_/  /\\ \\  \\|\\  \\|\\/_|\\  \\                                           \n"
						+ "                 \\ \\_____  \\ \\  \\_|/_|__|//  / /\\ \\   __  \\|/ \\ \\  \\                                          \n"
						+ "                  \\|____|\\  \\ \\  \\_|\\ \\  /  /_/__\\ \\  \\|\\  \\   \\ \\  \\                                         \n"
						+ "                    ____\\_\\  \\ \\_______\\|\\________\\ \\_______\\   \\ \\__\\                                        \n"
						+ "                   |\\_________\\|_______| \\|_______|\\|_______|    \\|__|                                        \n"
						+ "                   \\|_________|                                                                               \n"
						+ " ________  ___  ___  ________  ________  _______   ________          ________  ___  ___  ________  ________   \n"
						+ "|\\   __  \\|\\  \\|\\  \\|\\   __  \\|\\   ____\\|\\  ___ \\ |\\   __  \\        |\\   ____\\|\\  \\|\\  \\|\\   __  \\|\\   __  \\  \n"
						+ "\\ \\  \\|\\ /\\ \\  \\\\\\  \\ \\  \\|\\  \\ \\  \\___|\\ \\   __/|\\ \\  \\|\\  \\       \\ \\  \\___|\\ \\  \\\\\\  \\ \\  \\|\\  \\ \\  \\|\\  \\ \n"
						+ " \\ \\   __  \\ \\  \\\\\\  \\ \\   _  _\\ \\  \\  __\\ \\  \\_|/_\\ \\   _  _\\       \\ \\_____  \\ \\   __  \\ \\  \\\\\\  \\ \\   ____\\\n"
						+ "  \\ \\  \\|\\  \\ \\  \\\\\\  \\ \\  \\\\  \\\\ \\  \\|\\  \\ \\  \\_|\\ \\ \\  \\\\  \\|       \\|____|\\  \\ \\  \\ \\  \\ \\  \\\\\\  \\ \\  \\___|\n"
						+ "   \\ \\_______\\ \\_______\\ \\__\\\\ _\\\\ \\_______\\ \\_______\\ \\__\\\\ _\\         ____\\_\\  \\ \\__\\ \\__\\ \\_______\\ \\__\\   \n"
						+ "    \\|_______|\\|_______|\\|__|\\|__|\\|_______|\\|_______|\\|__|\\|__|       |\\_________\\|__|\\|__|\\|_______|\\|__|   \n"
						+ "                                                                       \\|_________|                           \n"
						+ "                                                                                                              \n"
						+ "                                                                                                                         ")
				.append(System.lineSeparator());
		buf.append("                            |\\ /| /|_/|\n" + "                          |\\||-|\\||-/|/|\n"
				+ "                           \\\\|\\|//||///\n" + "          _..----.._       |\\/\\||//||||\n"
				+ "        .'     o    '.     |||\\\\|/\\\\ ||\n" + "       /   o       o  \\    | './\\_/.' |\n"
				+ "      |o        o     o|   |          |\n" + "      /'-.._o     __.-'\\   |          |\n"
				+ "      \\      `````     /   |          |\n" + "      |``--........--'`|    '.______.'\n"
				+ "       \\              /\n" + "        `'----------'`");
		buf.append("\n=======================================").append(System.lineSeparator());
		System.out.println(buf.toString());
	}

	private void getComboFromUser() {
		String command;
		int[] dishesId = new int[3];
		String[] menuChoice = new String[] { "Burger", "Snack", "Drink" };
		int i = 0;

		for (String dish : menuChoice) {
			boolean isFirstTry = true;
			do {
				if (!isFirstTry) {
					MessagesCLI.NOT_VALID_CHOICE.printMessage();
				}
				isFirstTry = false;
				System.out.print("Choose a " + dish + " ID : ");
				command = scanner.nextLine().trim();
			} while (!isComboCommandOk(command, dish));
			dishesId[i] = Integer.parseInt(command);
			i++;
		}
		restaurant.addCombo(menu.getItemName(dishesId[0]), menu.getItemPrice(dishesId[0]),
				menu.getItemName(dishesId[1]), menu.getItemPrice(dishesId[1]), menu.getItemName(dishesId[2]),
				menu.getItemPrice(dishesId[2]), getSizeFromUser());
	}

	private SIZE getSizeFromUser() {
		String command;
		boolean isFirstTry = true;

		do {
			if (!isFirstTry) {
				MessagesCLI.NOT_VALID_SIZE.printMessage();
			}
			isFirstTry = false;

			System.out.print(MessagesCLI.CHOSE_SIZE.getMessage());

			command = scanner.nextLine().trim();
		} while (!SIZE.isValidSize(command));
		return SIZE.valueOf(command);

	}

	private boolean isComboCommandOk(String command, String dish) {
		if (!isInteger(command)) {
			MessagesCLI.NOT_VALID_NUMBER.printMessage();
			return false;
		}

		int id = Integer.parseInt(command);
		if (!menu.isValidId(id)) {
			MessagesCLI.NOT_VALID_MENU_ID.printMessage();
			return false;
		}

		switch (dish) {
		case ("Burger"):
			return menu.isMeal(id);
		case ("Snack"):
			return menu.isSnack(id);
		case ("Drink"):
			return menu.isDrink(id);
		}

		return false;
	}

	private boolean processCommand(String command) {
		if (command.contains(" ")) {
			return processCommand(command.split(" "));
		}

		switch (command) {
		case USAGE:
			System.out.println(usageMessage);
			break;
		case SHOW_MENU:
			menu.printMenu();
			break;
		case SHOW_CART:
			restaurant.showCart();
			break;
		case ADD_COMBO:
			getComboFromUser();
			break;
		case CLEAR_CART:
			restaurant.clearCart();
			break;
		case ORDER:
			restaurant.confirmOrder();
			break;
		case EXIT:
			MessagesCLI.END.printMessage();
			return false;
		case ADD:
		case REMOVE:
			MessagesCLI.ONE_ARGUMENT.printMessage();
			break;
		default:
			MessagesCLI.COMMAND_NOT_FOUND.printMessage();
		}
		return true;
	}

	private boolean processCommand(String... commandWithArgs) {
		switch (commandWithArgs[0]) {
		case USAGE:
		case SHOW_MENU:
		case SHOW_CART:
		case ADD_COMBO:
		case CLEAR_CART:
		case ORDER:
		case EXIT:
			MessagesCLI.NO_ARGUMENT.printMessage();
			return true;
		}

		if (commandWithArgs.length > 2) {
			MessagesCLI.ONE_ARGUMENT.printMessage();
			return true;
		}

		if (!commandWithArgs[0].equals(ADD) && !commandWithArgs[0].equals(REMOVE)) {
			MessagesCLI.COMMAND_NOT_FOUND.printMessage();
			return true;
		}

		String arg = commandWithArgs[1];
		if (!isInteger(arg)) {
			MessagesCLI.NOT_VALID_NUMBER.printMessage();
			return true;
		}

		int num = Integer.parseInt(arg);

		switch (commandWithArgs[0]) {
		case ADD:
			if (!menu.isValidId(num)) {
				MessagesCLI.NOT_VALID_MENU_ID.printMessage();
				return true;
			}
			if (menu.isMeal(num)) {
				restaurant.addBurger(menu.getItemName(num), menu.getItemPrice(num));
			} else if (menu.isSnack(num)) {
				restaurant.addSnack(menu.getItemName(num), menu.getItemPrice(num), getSizeFromUser());
			} else if (menu.isDrink(num)) {
				restaurant.addDrink(menu.getItemName(num), menu.getItemPrice(num), getSizeFromUser());
			} else {
				return true;
			}
			break;
		case REMOVE:
			restaurant.removeItem(num);
			break;
		}
		return true;
	}

	public static boolean isInteger(String s) {
		if (s == null) {
			return false;
		}
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
