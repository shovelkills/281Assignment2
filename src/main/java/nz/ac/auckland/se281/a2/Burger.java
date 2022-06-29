package nz.ac.auckland.se281.a2;

import nz.ac.auckland.se281.a2.cli.Menu.SIZE;

public class Burger extends MenuItems {
	// instance methods
	private String name;
	private float price;
	private int initialTime = 300;
	private int addTime = 60;

	// Constructor
	public Burger(String name, float price) {
		this.name = name;
		this.price = price;
	}

	// Prints the order message to console
	public String orderMessage() {
		return this.name;
	}

	// getter methods
	public float getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public SIZE getSize() {
		return null;
	}

	public int getInitialTime() {
		return initialTime;
	}

	public int getAddTime() {
		return addTime;
	}

}
