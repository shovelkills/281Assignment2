package nz.ac.auckland.se281.a2;

import nz.ac.auckland.se281.a2.cli.Menu.SIZE;

public class Combo extends MenuItems {
	private Burger burger;
	private Snack snack;
	private Drink drink;
	private SIZE size;
	private float totalPrice;

	public Combo(String nameBurger, float priceBurger, String nameSnack, float priceSnack, String nameDrink,
			float priceDrink, SIZE size) {
		this.burger = new Burger(nameBurger, priceBurger);
		this.snack = new Snack(nameSnack, priceSnack, size);
		this.drink = new Drink(nameDrink, priceDrink, size);
		this.size = size;
		this.totalPrice = burger.getPrice() + snack.getPrice() + drink.getPrice() / 2;

	}

	@Override
	protected String orderMessage() {

		return "COMBO : (" + burger.getName() + ", " + snack.orderMessage() + ", " + drink.orderMessage() + ")";
	}

	@Override
	// getter method
	protected float getPrice() {
		return totalPrice;
	}

	protected SIZE getSize() {
		return size;
	}

	protected int getInitialTime() {
		return 0;
	}

	protected int getAddTime() {
		return 0;
	}

	public Burger getBurger() {
		return burger;
	}

	public Snack getSnack() {
		return snack;
	}

	public Drink getDrink() {
		return drink;
	}

}
