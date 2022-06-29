package nz.ac.auckland.se281.a2;

import nz.ac.auckland.se281.a2.cli.Menu.SIZE;

public class Snack extends MenuItems {
	// instance methods
	private String name;
	private float price;
	private SIZE size;
	private int initialTime = 180;
	private int addTime = 30;

	public Snack(String name, float price, SIZE size) {// Constructor
		this.name = name;
		this.price = priceWithSize(price, size);
		this.size = size;
	}

	public String orderMessage() {// Prints the order message to console
		return this.name + " (" + this.size + ")";
	}

	// getter methods
	@Override
	public float getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public SIZE getSize() {
		return size;
	}

	public int getInitialTime() {
		return initialTime;
	}

	public int getAddTime() {
		return addTime;
	}

}
