package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;


public class ItemView extends FlowPane {
	String item;
	int cost;
	Label name;
	Label price;
	Button inc;
	Button dec;
	TextField quantity;

	public ItemView(String name, int price) {
		item = name;
		cost = price;
		this.name = new Label(spaceOut(name,12));
		this.price = new Label(spaceOut(Integer.toString(cost),5));
		quantity = new TextField();
		quantity.setText("0");
		quantity.setPrefColumnCount(5);
		inc = new Button("+");
		dec = new Button("-");

		formRegion();
		events();
	}
	
	/**
	 * Get name of the item
	 * @return name of the item represented.
	 */
	public String getName(){
		return item;
	}
	
	/**
	 * Changes prices of the item
	 * @param n the new price to be set.
	 */
	public void changePrice(int n){
		price.setText(spaceOut(Integer.toString(n),5));
		cost = n;
	}

	void formRegion() {
		this.getChildren().addAll(name ,price, dec, quantity, inc);
	}

	/**
	 * @return the value given as quantity.
	 * @throws Exception
	 *             for invalid input.
	 */
	public int getQuantity() throws Exception {
		int num = 0;
		String input = quantity.getText();
		if (input != null) {
			try {
				num = num * 10 + Integer.parseInt(input);
			} catch (NumberFormatException e) {
				quantity.setText("0");
				throw new Exception("Type does not match. Only integers.");
			}
		}
		if (num < 0) {
			quantity.setText("Must be >0");
			throw new Exception("Quantity cannot be negative.");
		}

		return num;
	}
	
	
	/**
	 * Get the total price of the object currently. 
	 * @return
	 */
	public int getTotalPrice(){
		try {
			return getQuantity() * cost;
		} catch (NumberFormatException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * Handles repsonses when buttons are pressed. 
	 */
	private void events() {
		inc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					int q = getQuantity();
					quantity.setText(Integer.toString(q + 1));
				} catch (Exception e) {
					quantity.setText("0");
				}

			}

		});

		dec.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					int q = getQuantity();
					quantity.setText(Integer.toString(q == 0 ? 0 : q - 1));
				} catch (Exception e) {
					quantity.setText("0");
				}
			}

		});
	}
	
	/**
	 * Resets Quantity to zero.
	 */
	public void resetQuantity(){
		quantity.setText("0");
	}
	
	/**
	 * Given a string, this method makes it of length 'space' and center aligns
	 * the original string.
	 * 
	 * @param s
	 *            String to be aligned.
	 * @param space
	 *            length of new string.
	 * @return the new string.
	 */
	private String spaceOut(String s, int space) {
		String result = "";
		int length = s.length();
		int dif = space - length;
		if (dif < 0)
			return null;
		else {
			String spaces = "";
			for (int i = 0; i < dif / 2; i++)
				spaces = spaces + " ";
			result = spaces + s + spaces;
			return result;
		}

	}

}
