package states;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

/**
 * Represents the wholesale vegetable market where produce can be bought.
 * 
 * @author Sush
 * 
 */
public class WholeSale {

	/**
	 * Inventory of wholesale prices of vegetables,fruits - per kg.
	 */
	public Hashtable<String, Integer> priceOf = new Hashtable<String, Integer>();
	Hashtable<String, Integer> standard = new Hashtable<String, Integer>();
	public Hashtable<String, Integer> selling = new Hashtable<String, Integer>();

	public WholeSale() {
		initPrice(standard);
		initPrice(priceOf);
		initPrice(selling);
	}

	/**
	 * Initializes the prices of the items with default values.
	 */
	void initPrice(Hashtable<String, Integer> h) {
		h.put("Spinach", 10);
		h.put("Onion", 20);
		h.put("Tomato", 16);
		h.put("Paneer", 200);
		h.put("Carrot", 40);
		h.put("Mango", 100);
		h.put("Cherry", 500);
	}

	/**
	 * Setting the cost price for each item.
	 */
	public void setPrice() {
		Random r = new Random();
		Iterator<String> it = standard.keySet().iterator();
		while (it.hasNext()) {
			String item = it.next();
			int cost = standard.get(item);

			int perc = -4 + r.nextInt(10);
			cost = (int) ((perc / 10.0 + 1) * cost);

			priceOf.put(item, cost);
		}

		setSellingPrice();
	}

	/**
	 * Sets the selling price for the new turn.
	 */
	private void setSellingPrice() {
		Random r = new Random();
		Iterator<String> it = standard.keySet().iterator();
		while (it.hasNext()) {
			String item = it.next();
			int cost = standard.get(item);

			int perc = -2 + r.nextInt(8);
			cost = (int) ((perc / 10.0 + 1) * cost);

			selling.put(item, cost);
		}

	}

	/**
	 * Get a list of all items available in the market.
	 * 
	 * @return array of type Object, containing Strings.
	 */
	public Object[] getItemNames() {
		return priceOf.keySet().toArray();
	}

	/**
	 * Returns the total cost of buying x amount of one item.
	 * 
	 * @param item
	 *            String - name of item
	 * @param quantity
	 *            int - quantity of that item
	 * @return total cost if the item is valid, else
	 * @Throws NullPointerException if item does not exist
	 */
	public int getPrice(String item, int quantity) {
		Integer cost = priceOf.get(item);
		if (cost != null)
			return cost * quantity;
		else
			throw new NullPointerException();
	}

	/**
	 * To buy products from the wholesale market.
	 * 
	 * @param items
	 *            An array of Strings containing names of items to buy
	 * @param quantity
	 *            An array of integers which have the quantities of the
	 *            corresponding items.
	 * @Requires items.length == quantity.length
	 * @Returns total cost of all items to be bought. -1 if requires clause is
	 *          not matched.
	 * @Throws NullPointerException if the given item does not exist in the
	 *         inventory.
	 */
	public int getPrices(String[] items, int quantity[]) {
		assert items.length == quantity.length;

		if (items.length == quantity.length) {
			int sum = 0;
			for (int i = 0; i < items.length; i++) {
				Integer p = priceOf.get(items[i]);
				if (p == null)
					throw new NullPointerException("This item does not exist.");
				else
					sum += p * quantity[i];
			}

			return sum;
		}

		return -1;
	}

	/**
	 * Given a stock, calculates the selling price for all of it.
	 * 
	 * @param stock
	 *            Stock to be sold.
	 * @return selling price of all the stock.
	 */
	public int sellAll(Hashtable<String, Integer> stock) {
		int value = 0;

		Iterator<String> it = stock.keySet().iterator();
		while (it.hasNext()) {
			String item = it.next();
			value += (selling.get(item) * stock.get(item));
			System.out.println(item + " " + value);
		}

		return value;
	}
}
