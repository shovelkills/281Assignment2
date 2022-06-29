package nz.ac.auckland.se281.a2;

import java.util.ArrayList;

import nz.ac.auckland.se281.a2.cli.Menu.SIZE;
import nz.ac.auckland.se281.a2.cli.MessagesCLI;

public class BurgerShop {
	// instance variables
	private ArrayList<MenuItems> cartItems = new ArrayList<>();
	private float totalCost = 0;

	private int totalTime = 0;
	private int totalTimeSec = 0;
	private int totalTimeMin = 0;
	private int totalTimeHour = 0;

	private int burgerTime = 0;
	private int drinkTime = 0;
	private int snackTime = 0;

	public BurgerShop() {

	}

	/**
	 * Add a burger in the cart
	 * 
	 * @param name
	 * @param price
	 */
	public void addBurger(String name, float price) {// Creates a new Burger class to store new burger order
		cartItems.add(new Burger(name, price));
	}

	/**
	 * add a snack in the cart, if size is L the price should be incremented by $3
	 * if the size is XL the price should be incremented by $4 (@see
	 * nz.ac.auckland.se281.a2.cli.Menu.SIZE)
	 * 
	 * 
	 * @param name
	 * @param price
	 * @param size
	 */
	public void addSnack(String name, float price, SIZE size) {// Creates a new Snack class to store new snack order
		cartItems.add(new Snack(name, price, size));
	}

	/**
	 * 
	 * add a drink in the cart
	 * 
	 * if size is L the price should be incremented by $3 if the size is XL the
	 * price should be incremented by $4 (@see
	 * nz.ac.auckland.se281.a2.cli.Menu.SIZE)
	 * 
	 *
	 * @param name
	 * @param price
	 * @param size
	 */
	public void addDrink(String name, float price, SIZE size) {// Creates a new Drink class to store new drink order
		cartItems.add(new Drink(name, price, size));
	}

	/**
	 * print the content of the cart, or print MessagesCLI.CART_EMPTY if the cart is
	 * empty
	 *
	 *
	 */
	public void showCart() {
		int count = 0;
		totalCost = 0;
		if (cartItems.size() == 0) {// Checks if cart is empty
			System.out.println(MessagesCLI.CART_EMPTY.getMessage());
		} else {
			for (MenuItems order : cartItems) {// Goes through all the orders and prints out the messages
				System.out.println(
						count + " - " + order.orderMessage() + ": $" + String.format("%.02f", order.getPrice()));
				count++;
				totalCost += order.getPrice();
			}

			if (totalCost >= 100) {// Checks for discount
				totalCost *= 0.75;
				System.out.println(MessagesCLI.DISCOUNT.getMessage());
			}
			System.out.println("Total: $" + String.format("%.02f", totalCost));

		}
	}

	// Method to add price based on size

	/**
	 * add a combo in the cart.
	 * 
	 * The price of a combo is the sum of all the items, but the drink would be half
	 * price. Note that in a combo, both snacks and drinks have the same size, and
	 * the combo price must consider the size (@see addSnack and addDrink methods).
	 * 
	 * @param nameBurger
	 * @param priceBurger
	 * @param nameSnack
	 * @param priceSnack
	 * @param nameDrink
	 * @param priceDrink
	 * @param size
	 */
	public void addCombo(String nameBurger, float priceBurger, String nameSnack, float priceSnack, String nameDrink,
			float priceDrink, SIZE size) {
		cartItems.add(new Combo(nameBurger, priceBurger, nameSnack, priceSnack, nameDrink, priceDrink, size));
	}

	/**
	 * remove the item in the cart specified by the position posCart. Note that the
	 * position of the cart start from zero. if postCart is invalid: posCart < 0 OR
	 * posCart >= size of the cart the method prints
	 * MessagesCLI.NOT_VALID_CART_POSITION
	 * 
	 * @param posCart
	 */
	public void removeItem(int posCart) {
		if (posCart < 0 || posCart >= cartItems.size()) {// Checks if position is a valid input
			System.out.println(MessagesCLI.NOT_VALID_CART_POSITION.getMessage());
		} else {
			cartItems.remove(posCart);
		}
	}

	/**
	 * removes all elements in the cart
	 */
	public void clearCart() {// Clears all elements in the cart
		cartItems.clear();
	}

	/**
	 * This method confirms the order, showing the estimated pick up time. It also
	 * give a warning if there are missing opportunities for COMBO menus
	 * 
	 */
	public void confirmOrder() {
		boolean containBurger = false;
		boolean sameSize = false;
		if (cartItems.size() == 0) {// Checks if cart is empty
			System.out.println(MessagesCLI.ORDER_INVALID_CART_EMPTY.getMessage());
		} else {
			for (MenuItems cartItem : cartItems) {// For loop to check if there is a missed combo and sets a boolean
													// sameSize
													// to true
				if (cartItem instanceof Burger) {
					containBurger = true;
				} else if (cartItem instanceof Drink) {// Checks all snacks if the cartitem is a drink
					for (MenuItems isSnack : cartItems) {
						if (isSnack instanceof Snack && (cartItem.getSize() == isSnack.getSize())) {
							sameSize = true;
						}
					}
				} else if (cartItem instanceof Snack) {// Checks all drinks if the cartitem is a snack
					for (MenuItems isDrink : cartItems) {
						if (isDrink instanceof Drink && (cartItem.getSize() == isDrink.getSize())) {
							sameSize = true;
						}
					}
				} else {
					continue;
				}
			}
			if (containBurger && sameSize) {// Checks if the user has missed a combo
				System.out.println(MessagesCLI.MISSED_COMBO.getMessage());
			} else {
				showCart();
				for (MenuItems cartItem : cartItems) {// For loop to add the time it takes to prepare the order
					checkPrepTime(cartItem);
				}
				totalTime = burgerTime + drinkTime + snackTime;
				totalTimeHour = totalTime / 3600;
				totalTimeMin = (totalTime % 3600) / 60;
				totalTimeSec = totalTime % 60;
				System.out.println(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + totalTimeHour + " hours "
						+ totalTimeMin + " minutes " + totalTimeSec + " seconds");
			}
			// Calculates the time it takes for the order in hours, minutes and seconds.

			resetOrder();
		}
	}

	public void checkPrepTime(MenuItems menuItem) {// Checks what type of the cart item being ordered and adds the time
													// accordingly
		if (menuItem instanceof Burger) {
			if (burgerTime == 0) {
				burgerTime += menuItem.getInitialTime();
			} else {
				burgerTime += menuItem.getAddTime();
			}
		} else if (menuItem instanceof Snack) {
			if (snackTime == 0) {
				snackTime += menuItem.getInitialTime();
			} else {
				snackTime += menuItem.getAddTime();
			}

		} else if (menuItem instanceof Drink) {
			if (drinkTime == 0) {
				drinkTime += menuItem.getInitialTime();
			} else {
				drinkTime += menuItem.getAddTime();
			}
		} else {// If it is a combo it will add a burger, snack and drink to the timer
			Combo combo = (Combo) menuItem;
			checkPrepTime(combo.getBurger());
			checkPrepTime(combo.getSnack());
			checkPrepTime(combo.getDrink());
		}

	}

	public void resetOrder() {// Resets all the values
		totalCost = 0;
		burgerTime = 0;
		drinkTime = 0;
		snackTime = 0;
		totalTime = 0;
		totalTimeHour = 0;
		totalTimeMin = 0;
		totalTimeSec = 0;
		clearCart();
	}
}
