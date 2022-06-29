package nz.ac.auckland.se281.a2;

import static nz.ac.auckland.se281.a2.cli.Main.ADD;
import static nz.ac.auckland.se281.a2.cli.Main.ADD_COMBO;
import static nz.ac.auckland.se281.a2.cli.Main.CLEAR_CART;
import static nz.ac.auckland.se281.a2.cli.Main.ORDER;
import static nz.ac.auckland.se281.a2.cli.Main.REMOVE;
import static nz.ac.auckland.se281.a2.cli.Main.SHOW_CART;
import static nz.ac.auckland.se281.a2.cli.Main.scanner;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import nz.ac.auckland.se281.a2.cli.Main;
import nz.ac.auckland.se281.a2.cli.MessagesCLI;

@RunWith(Suite.class)
@SuiteClasses({ BurgerShopTestSuite.Task1Test.class, //
		BurgerShopTestSuite.Task2Test.class, //
		BurgerShopTestSuite.Task3Test.class, //
		BurgerShopTestSuite.Task4Test.class, //
// BurgerShopTestSuite.YourTest.class   //
})

public class BurgerShopTestSuite {
	public static String nl = System.getProperty("line.separator");

	public abstract static class TaskTest {

		private ByteArrayOutputStream captureOut;
		private ByteArrayOutputStream captureErr;
		private PrintStream origOut;
		private PrintStream origErr;
		private static String delimiterRun = "---<END RUN>---";

		@Before
		public void setUp() {
			origOut = System.out;
			origErr = System.err;
			captureOut = new ByteArrayOutputStream();
			System.setOut(new PrintStream(captureOut));
			captureErr = new ByteArrayOutputStream();
			System.setErr(new PrintStream(captureErr));
		}

		@After
		public void tearDown() {
			System.setOut(origOut);
			System.setErr(origErr);
			if (captureOut.toString().length() > 1) {
				System.out.println(System.lineSeparator() + "the System.out.print was :" + System.lineSeparator()
						+ captureOut.toString());
			}
			if (captureErr.toString().length() > 1) {
				System.out.println(System.lineSeparator() + "the System.err.print was :" + System.lineSeparator()
						+ captureErr.toString());
			}
		}

		void runCommands(String... commands) {

			StringBuilder sb = new StringBuilder();
			for (String cmdString : commands) {
				sb.append(cmdString);
				sb.append(nl);
			}
			sb.append("exit");
			scanner = new Scanner(sb.toString());
			Main.main(new String[0]);
			System.out.println(nl);
			System.out.println(delimiterRun);
		}

		void assertContains(String s) {

			if (!captureOut.toString().contains(s)) {
				fail("The test is expecting the following output in the console but was not there " + nl + s);
			}
		}

		void assertDoesNotContain(String s) {
			if (captureOut.toString().contains(s)) {
				fail("The test is expecting that the following output WAS NOT in the console but is was there " + nl
						+ s);
			}
		}

		private void checkRun(String s, int run) {
			if (run < 0 || !captureOut.toString().contains(delimiterRun)
					|| run > captureOut.toString().split(delimiterRun).length) {
				throw new RuntimeException("Something is wrong with the test case!");
			}
		}

		void assertContains(String s, int run) {
			checkRun(s, run);
			if (!captureOut.toString().split(delimiterRun)[run].contains(s)) {
				fail("The test is expecting the following output in the console but was not there " + nl + s);
			}
		}

		void assertDoesNotContain(String s, int run) {
			checkRun(s, run);
			if (captureOut.toString().split(delimiterRun)[run].contains(s)) {
				fail("The test is expecting that the following output WAS NOT in the console but is was there " + nl
						+ s);
			}
		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class Task1Test extends TaskTest {

		@Test
		public void T1_01_add_one_burger() {
			runCommands(ADD + " 1", SHOW_CART);
			assertContains("0 - Avocado Burger: $17.00");
		}

		@Test
		public void T1_02_add_two_veg_burger() {
			runCommands(ADD + " 2", ADD + " 2", SHOW_CART);
			assertContains("0 - Vegan Burger: $18.50" + nl + "1 - Vegan Burger: $18.50");
		}

		@Test
		public void T1_03_add_two_veg_burger_cart_twice() {
			runCommands(ADD + " 2", SHOW_CART, ADD + " 2", SHOW_CART);
			assertContains("0 - Vegan Burger: $18.50" + nl + "1 - Vegan Burger: $18.50");
		}

		@Test
		public void T1_04_add_two_diff_burgers() {
			runCommands(ADD + " 1", ADD + " 4", SHOW_CART);
			assertContains("0 - Avocado Burger: $17.00" + nl + "1 - Buffalo Chicken Burger: $13.00");
		}

		@Test
		public void T1_05_add_two_diff_burgers_total() {
			runCommands(ADD + " 1", ADD + " 4", SHOW_CART);
			assertContains(
					"0 - Avocado Burger: $17.00" + nl + "1 - Buffalo Chicken Burger: $13.00" + nl + "Total: $30.00");
		}

		@Test
		public void T1_06_add_one_snack() {
			runCommands(ADD + " 8", "XL", SHOW_CART);
			assertContains("0 - Sweet Potato Chips");
		}

		@Test
		public void T1_07_add_one_snack_size() {
			runCommands(ADD + " 8", "XL", SHOW_CART);
			assertContains("0 - Sweet Potato Chips (XL)");
		}

		@Test
		public void T1_08_add_one_snack_size_price() {
			runCommands(ADD + " 8", "XL", SHOW_CART);
			assertContains("0 - Sweet Potato Chips (XL): $14.00");
		}

		@Test
		public void T1_09_add_one_drink() {
			runCommands(ADD + " 12", "M", SHOW_CART);
			assertContains("0 - Coke (M)");
		}

		@Test
		public void T1_10_show_cart_total_one_item() {
			runCommands(ADD + " 12", "M", SHOW_CART);
			assertContains("0 - Coke (M): $2.00" + nl + "Total: $2.00");
		}

		@Test
		public void T1_11_show_cart_total_two_items() {
			runCommands(ADD + " 12", "M", ADD + " 12", "M", SHOW_CART);
			assertContains("0 - Coke (M): $2.00" + nl + "1 - Coke (M): $2.00" + nl + "Total: $4.00");
		}

		@Test
		public void T1_12_show_cart_total_two_diff_items() {
			runCommands(ADD + " 12", "M", ADD + " 1", SHOW_CART);
			assertContains("0 - Coke (M): $2.00" + nl + "1 - Avocado Burger: $17.00" + nl + "Total: $19.00");
		}

		@Test
		public void T1_13_empty_cart() {
			runCommands(SHOW_CART);
			assertContains(MessagesCLI.CART_EMPTY.getMessage());
		}

		@Test
		public void T1_14_show_cart_discount() {
			runCommands(ADD + " 6", ADD + " 6", SHOW_CART);
			assertContains("0 - Crispy Foie Gras Burger: $34.00" + nl + "1 - Crispy Foie Gras Burger: $34.00" + nl
					+ "Total: $68.00");
			assertDoesNotContain(MessagesCLI.DISCOUNT.getMessage());
			runCommands(ADD + " 6", ADD + " 6", ADD + " 5", ADD + " 4", SHOW_CART);
			assertContains("0 - Crispy Foie Gras Burger: $34.00" + nl + "1 - Crispy Foie Gras Burger: $34.00" + nl
					+ "2 - Black Truffle Burger: $27.50" + nl + "3 - Buffalo Chicken Burger: $13.00" + nl
					+ MessagesCLI.DISCOUNT.getMessage() + nl + "Total: $81.38");
		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class Task2Test extends TaskTest {

		@Test
		public void T2_01_add_combo() {
			runCommands(ADD_COMBO, "1", "7", "12", "M", SHOW_CART);
			assertContains("0 - COMBO ");
		}

		@Test
		public void T2_02_add_combo_M() {
			runCommands(ADD_COMBO, "1", "7", "12", "M", SHOW_CART);
			assertContains("0 - COMBO : (Avocado Burger, Chips (M), Coke (M))");
		}

		@Test
		public void T2_03_add_combo_XL() {
			runCommands(ADD_COMBO, "5", "11", "12", "XL", SHOW_CART);
			assertContains("0 - COMBO : (Black Truffle Burger, Fish Fingers (XL), Coke (XL))");
		}

		@Test
		public void T2_04_add_combo_L_price() {
			runCommands(ADD_COMBO, "6", "9", "14", "L", SHOW_CART);
			assertContains("0 - COMBO : (Crispy Foie Gras Burger, Onion Rings (L), Fanta (L)): $44.50");
		}

		@Test
		public void T2_05_add_combo_L_price_total() {
			runCommands(ADD_COMBO, "6", "9", "14", "L", SHOW_CART);
			assertContains("0 - COMBO : (Crispy Foie Gras Burger, Onion Rings (L), Fanta (L)): $44.50");
		}

		@Test
		public void T2_06_add_two_combos_price() {
			runCommands(ADD_COMBO, "5", "11", "12", "XL", ADD_COMBO, "6", "9", "14", "L", SHOW_CART);
			assertContains("0 - COMBO : (Black Truffle Burger, Fish Fingers (XL), Coke (XL)): $42.50" + nl
					+ "1 - COMBO : (Crispy Foie Gras Burger, Onion Rings (L), Fanta (L)): $44.50");
		}

		@Test
		public void T2_07_add_combo_XL_price_total() {
			runCommands(ADD_COMBO, "5", "11", "12", "XL", SHOW_CART);
			assertContains("0 - COMBO : (Black Truffle Burger, Fish Fingers (XL), Coke (XL)): $42.50");
		}

		@Test
		public void T2_08_show_cart_discount_combo() {
			runCommands(ADD_COMBO, "0", "9", "14", "M", ADD_COMBO, "6", "9", "14", "L", SHOW_CART);
			assertDoesNotContain(MessagesCLI.DISCOUNT.getMessage());
			runCommands(ADD_COMBO, "0", "9", "14", "M", ADD_COMBO, "6", "9", "14", "L", ADD_COMBO, "0", "9", "14", "M",
					ADD_COMBO, "5", "10", "13", "XL", ADD_COMBO, "6", "9", "14", "XL", SHOW_CART);
			assertContains("0 - COMBO : (Cheese Burger, Onion Rings (M), Fanta (M)): $21.50" + nl
					+ "1 - COMBO : (Crispy Foie Gras Burger, Onion Rings (L), Fanta (L)): $44.50" + nl
					+ "2 - COMBO : (Cheese Burger, Onion Rings (M), Fanta (M)): $21.50" + nl
					+ "3 - COMBO : (Black Truffle Burger, Buffalo Chicken Wings (XL), Sprite (XL)): $46.50" + nl
					+ "4 - COMBO : (Crispy Foie Gras Burger, Onion Rings (XL), Fanta (XL)): $46.00" + nl
					+ MessagesCLI.DISCOUNT.getMessage() + nl + "Total: $135.00");
		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class Task3Test extends TaskTest {

		@Test
		public void T3_01_remove_item() {
			runCommands(ADD_COMBO, "6", "9", "14", "L", SHOW_CART);
			assertContains("0 - COMBO : (Crispy Foie Gras Burger, Onion Rings (L), Fanta (L))");
			runCommands(ADD_COMBO, "6", "9", "14", "L", REMOVE + " 0", SHOW_CART);
			assertContains(MessagesCLI.CART_EMPTY.getMessage(), 1);
		}

		@Test
		public void T3_02_remove_item_correct_cart_update() {
			runCommands(ADD + " 6", ADD + " 2", SHOW_CART);
			assertContains("0 - Crispy Foie Gras Burger:");
			assertContains("1 - Vegan Burger:");
			runCommands(ADD + " 6", ADD + " 2", REMOVE + " 0", SHOW_CART);
			assertContains("0 - Vegan Burger:", 1);
			assertDoesNotContain("Crispy Foie Gras Burger:", 1);
		}

		@Test
		public void T3_03_remove_item_correct_cart_update_2() {
			runCommands(ADD + " 8", "L", ADD + " 3", SHOW_CART);
			assertContains("0 - Sweet Potato Chips (L):");
			assertContains("1 - Fisherman Burger:");
			runCommands(ADD + " 8", "L", ADD + " 3", REMOVE + " 1", SHOW_CART);
			assertContains("0 - Sweet Potato Chips (L):", 1);
			assertDoesNotContain("Fisherman Burger:", 1);
		}

		@Test
		public void T3_04_remove_two_items() {

			runCommands(ADD + " 1", ADD + " 2", ADD + " 3", ADD + " 4", ADD + " 5", SHOW_CART);
			assertContains("0 - Avocado Burger:");
			assertContains("1 - Vegan Burger:");
			assertContains("2 - Fisherman Burger:");
			assertContains("3 - Buffalo Chicken Burger:");
			assertContains("4 - Black Truffle Burger:");
			runCommands(ADD + " 1", ADD + " 2", ADD + " 3", ADD + " 4", ADD + " 5", REMOVE + " 0", REMOVE + " 0",
					SHOW_CART);
			assertContains("0 - Fisherman Burger:", 1);
			assertContains("1 - Buffalo Chicken Burger:", 1);
			assertContains("2 - Black Truffle Burger:", 1);
			assertDoesNotContain("Avocado Burger:", 1);
			assertDoesNotContain("Vegan Burger:", 1);
		}

		@Test
		public void T3_05_check_boundary() {
			runCommands(ADD + " 1", REMOVE + " 0");
			assertDoesNotContain(MessagesCLI.NOT_VALID_CART_POSITION.getMessage());
			runCommands(ADD + " 1", REMOVE + " -1");
			assertContains(MessagesCLI.NOT_VALID_CART_POSITION.getMessage(), 1);
		}

		@Test
		public void T3_06_clear_all() {
			runCommands(ADD + " 1", ADD + " 2", ADD + " 3", ADD + " 4", ADD + " 5", SHOW_CART);
			assertDoesNotContain(MessagesCLI.CART_EMPTY.getMessage());
			runCommands(ADD + " 1", ADD + " 2", ADD + " 3", ADD + " 4", ADD + " 5", CLEAR_CART, SHOW_CART);
			assertContains(MessagesCLI.CART_EMPTY.getMessage(), 1);
		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class Task4Test extends TaskTest {

		@Test
		public void T4_01_warning_combo_false() {
			runCommands(ADD_COMBO, "6", "9", "14", "L", ORDER);
			assertDoesNotContain(MessagesCLI.MISSED_COMBO.getMessage());
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage());
		}

		@Test
		public void T4_02_warning_combo_false() {
			runCommands(ADD + " 6", ADD + " 9", "L", ORDER);
			assertDoesNotContain(MessagesCLI.MISSED_COMBO.getMessage());
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage());
		}

		@Test
		public void T4_03_warning_combo_true() {
			runCommands(ADD + " 6", ADD + " 9", "L", ADD + " 13", "L", ORDER);
			assertContains(MessagesCLI.MISSED_COMBO.getMessage());
		}

		@Test
		public void T4_04_check_print_cart() {
			runCommands(ADD + " 8", "L", ADD + " 3", ADD + " 3", ORDER);
			assertContains("0 - Sweet Potato Chips (L): $13.00" + nl + "1 - Fisherman Burger: $17.00" + nl
					+ "2 - Fisherman Burger: $17.00" + nl + "Total: $47.00");
		}

		@Test
		public void T4_05_estimate_time_5_minutes() {

			runCommands(ADD + " 1", ORDER);
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + "0 hours 5 minutes 0 seconds");
		}

		@Test
		public void T4_06_estimate_time_6_minutes() {
			runCommands(ADD + " 1", ADD + " 3", ORDER);
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + "0 hours 6 minutes 0 seconds");
		}

		@Test
		public void T4_07_estimate_time_7_minutes() {
			runCommands(ADD + " 1", ADD + " 3", ADD + " 1", ORDER);
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + "0 hours 7 minutes 0 seconds");
		}

		@Test
		public void T4_08_estimate_time_7_minutes_burger_snack() {
			runCommands(ADD + " 1", ADD + " 9", "L", ORDER);
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + "0 hours 8 minutes 0 seconds");
		}

		@Test
		public void T4_09_estimate_time_3_minutes_45_seconds_snack_drink() {
			runCommands(ADD + " 9", "L", ADD + " 13", "M", ORDER);
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + "0 hours 3 minutes 45 seconds");
		}

		@Test
		public void T4_10_estimate_time_9_minutes_15_seconds_burger_snack_drink() {
			runCommands(ADD + " 1", ADD + " 9", "L", ADD + " 13", "M", ADD + " 9", "L", ORDER);
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + "0 hours 9 minutes 15 seconds");
		}

		@Test
		public void T4_11_estimate_time_8_minutes_45_seconds_combo() {
			runCommands(ADD_COMBO, "1", "9", "13", "M", ORDER);
			assertContains(MessagesCLI.ESTIMATE_WAITING_TIME.getMessage() + "0 hours 8 minutes 45 seconds");
		}

		@Test
		public void T4_12_clear_cart() {
			runCommands(ADD_COMBO, "1", "9", "13", "M", ORDER, SHOW_CART);
			assertContains(MessagesCLI.CART_EMPTY.getMessage());
		}

		@Test
		public void T4_13_cannot_order_cart_empty() {
			runCommands(ORDER);
			assertContains(MessagesCLI.ORDER_INVALID_CART_EMPTY.getMessage());
		}

	}

	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public static class YourTest extends TaskTest {

	}

}
