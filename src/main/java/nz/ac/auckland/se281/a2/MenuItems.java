package nz.ac.auckland.se281.a2;

import nz.ac.auckland.se281.a2.cli.Menu.SIZE;

public abstract class MenuItems {
	// instance fields

	public MenuItems() {

	}

	public float priceWithSize(float price, SIZE size) {// method to add price by finding what size it is
		float totalPrice = 0;
		switch (size) {
		case L:
			totalPrice += 3;
			break;
		case XL:
			totalPrice += 4;
			break;
		default:
			break;
		}
		return totalPrice += price;
	}

	// Abstract methods for subclasses
	protected abstract String orderMessage();

	protected abstract float getPrice();

	protected abstract SIZE getSize();

	protected abstract int getInitialTime();

	protected abstract int getAddTime();

}
