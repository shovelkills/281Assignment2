package nz.ac.auckland.se281.a2.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * You cannot modify this class!
 * 
 */
public class Menu {

	public static enum SIZE {
		M, L, XL;

		public static boolean isValidSize(String sizeStr) {
			try {
				valueOf(sizeStr);
				return true;
			} catch (IllegalArgumentException ex) {
				return false;
			}
		}
	}

	private int lastIndexMeal;
	private int lastIndexSnacks;
	private final List<String> menu;
	private final List<Float> prices;

	protected Menu() {
		menu = new ArrayList<String>();
		prices = new ArrayList<Float>();
		// Burgers
		add("Cheese Burger", 15.5f);
		add("Avocado Burger", 17f);
		add("Vegan Burger", 18.5f);
		add("Fisherman Burger", 17f);
		add("Buffalo Chicken Burger", 13f);
		add("Black Truffle Burger", 27.5f);
		add("Crispy Foie Gras Burger", 34f);
		lastIndexMeal = menu.size();
		// Snacks
		add("Chips", 7.5f);
		add("Sweet Potato Chips", 10f);
		add("Onion Rings", 5f);
		add("Buffalo Chicken Wings", 12f);
		add("Fish Fingers", 8f);
		lastIndexSnacks = menu.size();
		// Drinks
		add("Coke", 2f);
		add("Sprite", 2f);
		add("Fanta", 2f);
		add("Milkshake", 4f);

	}

	protected String getItemName(int id) {
		return menu.get(id);
	}

	protected float getItemPrice(int id) {
		return prices.get(id);
	}

	protected boolean isDrink(int id) {
		return (id >= lastIndexSnacks) && (id < menu.size());
	}

	protected boolean isMeal(int id) {
		return (id >= 0) && (id < lastIndexMeal);
	}

	protected boolean isSnack(int id) {
		return (id >= lastIndexMeal) && (id < lastIndexSnacks);
	}

	protected boolean isValidId(int id) {
		return (id >= 0) && (id < menu.size());

	}

	private void add(String name, float f) {
		menu.add(name);
		prices.add(f);
	}

	private static String addLines(int length, char c) {
		final int space = 30;
		if (length >= space) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < (space - length); i++) {
			sb.append(c);
		}
		sb.append(" ");
		return sb.toString();
	}

	protected void printMenu() {
		System.out.println("ID - ITEM" + addLines("ID - ITEM".length() - 4, ' ') + "PRICE NZD");
		System.out.println("---------------------------------------");
		for (int i = 0; i < menu.size(); i++) {
			if (i == 0) {
				System.out.println("                 BURGERS");
			} else if (i == lastIndexMeal) {
				System.out.println("                 SNACKS");
			} else if (i == lastIndexSnacks) {
				System.out.println("                 DRINKS");
			}
			System.out.println(i + (i < 10 ? " " : "") + " - " + menu.get(i) + addLines(menu.get(i).length(), '-')
					+ (prices.get(i) < 10 ? " " : "") + String.format("%.02f", prices.get(i)));
		}
	}

}
