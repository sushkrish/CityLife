package view;

import java.util.Hashtable;
import java.util.ListIterator;
import states.City;
import states.WholeSale;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;


public class MarketView extends Popup {

	WholeSale market;
	Button close;
	VBox holder;
	Label price;
	Button buy;

	public MarketView(City city) {
		market = city.market;
		holder = getData(market);
		formRegion(city);
	}
	
	/**
	 * Form the display of the market popup.
	 * @param city City object used as model.
	 */
	private void formRegion(final City city) {

		HBox dec = new HBox(10);
		
		//Total Price display
		price =  new Label(
				((Integer) getTotalCost()).toString());
		price.setMinWidth(50);
		price.setMaxWidth(50);
		
		//Buttons at the end
		Button evaluate = new Button("Price");
		buy = new Button("Buy");
		Button reset = new Button("Reset");
		close = new Button("Close");
		dec.getChildren().addAll(price, evaluate, buy, reset);
		dec.setAlignment(Pos.CENTER);
		
		VBox controls = new VBox(5);
		controls.getChildren().addAll(dec, close);
		
		//Top description
		Label name = new Label("Wholesale Market");
		Label intro = new Label("Enter the quantity you want.");
		VBox text = new VBox(10);
		text.getChildren().addAll(name,intro);
		text.setAlignment(Pos.CENTER);
		
		//Overall container
		VBox v = new VBox(10);
		v.getChildren().addAll( text,holder, controls);
		v.setStyle("-fx-background-color:white;" + "-fx-opacity:0.95;"
				+ "-fx-padding:20 20 20 20");
		setDim(v,280,360);
		
		this.getContent().add(v);
		
		
		//Handling the responses when buttons are pressed.
		
		reset.setOnAction( new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				resetQuantity();
			}
			
		});
		
		evaluate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				price.setText(((Integer) getTotalCost()).toString());
			}

		});

		buy.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					if(!city.buyStock(getTotalCost(), getStock()))
						price.setText("$$$$");
				} catch (Exception e) {
					// Not enough money to buy.
					e.printStackTrace();
				}
			}

		});

	}
	
	/**
	 * Update the display to show new prices.
	 */
	public void update() {
		ListIterator<Node> li = holder.getChildren().listIterator();
		while (li.hasNext()) {
			ItemView iv = (ItemView) li.next();
			iv.changePrice(market.getPrice(iv.getName(), 1));
		}
	}

	/**
	 * initializes the display based on first turn prices.
	 * @param m WholeSale market.
	 * @returns a VBox containing updated data of prices.
	 */
	private VBox getData(WholeSale m) {
		final VBox holder = new VBox(10);
		Object[] items = m.getItemNames();
		ItemView[] iv = new ItemView[items.length];
		int count = 0;
		for (Object i : items) {
			iv[count] = new ItemView((String) i, m.getPrice((String) i, 1));
			iv[count].setMinWidth(400);
			iv[count].setMaxWidth(400);
			iv[count].setHgap(10);
			holder.getChildren().add(iv[count]);
			count++;
		}

		return holder;
	}
	
	/**
	 * Calculates the total cost, using the quantities in the text fields.
	 * @return
	 */
	private int getTotalCost() {
		ListIterator<Node> li = holder.getChildren().listIterator();
		int sum = 0;
		while (li.hasNext()) {
			ItemView iv = (ItemView) li.next();
			sum += iv.getTotalPrice();
		}
		return sum;
	}
	
	/**
	 * Creates a stock list out of the entries in the display.
	 * @return Hashtable<String,Integer> where key is the item name and value is quantity.
	 */
	private Hashtable<String, Integer> getStock() {
		Hashtable<String, Integer> stock = new Hashtable<String, Integer>();
		ListIterator<Node> li = holder.getChildren().listIterator();

		while (li.hasNext()) {
			ItemView iv = (ItemView) li.next();
			try {
				if (iv.getQuantity() != 0)
					stock.put(iv.getName(), iv.getQuantity());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return stock;
	}
	
	/**
	 * Resets all the quantities in the display to zero.
	 */
	void resetQuantity(){
		ListIterator<Node> li = holder.getChildren().listIterator();

		while (li.hasNext()) 
			((ItemView) li.next()).resetQuantity();
		
		price.setText("0");
	}
	
	/**
	 * Set the display dimensions of the popup.
	 * @param w
	 * @param h
	 */
	private void setDim(Region r, int w, int h){
		r.setMaxWidth(w);
		r.setMinWidth(w);
		r.setPrefWidth(w);
		
		r.setMinHeight(h);
		r.setMaxHeight(h);
		r.setPrefHeight(h);
	
	}

}