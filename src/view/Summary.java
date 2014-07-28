package view;

import java.util.Iterator;

import states.City;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

public class Summary extends Popup {
	Button close = new Button("Close");
	HBox sum = new HBox(5);

	public Summary(City city) {

		showIncome(city);
		showEvent(city);

		sum.setStyle("-fx-background-color: white;"
				+ "-fx-padding: 20 20 20 20;" + "-fx-border-radius: 20;"
				+ "-fx-opacity:0.95");

		this.getContent().add(sum);
	}

	/**
	 * Displays last turn's event.
	 * 
	 * @param city
	 *            City object used as model.
	 */
	private void showEvent(City city) {
		VBox eventshow = new VBox(10);
		ImageView img = new ImageView(new Image(getClass().getResourceAsStream(
				city.event.imagePath())));

		img.setFitWidth(200);
		img.setFitHeight(200);

		Label name = new Label(city.event.getName());
		Text desc = new Text(city.event.getDesc());
		desc.setWrappingWidth(200);

		eventshow.getChildren().addAll(img, name, desc);

		if (city.event.getCost() != 0) {
			Label cost = new Label("Money Lost " + city.event.getCost());
			eventshow.getChildren().add(cost);
		}

		sum.getChildren().add(eventshow);

	}

	/**
	 * Shows the transactions of the past turn.
	 * 
	 * @param city
	 *            City object used as model.
	 */
	private void showIncome(City city) {
		// Getting the cost and selling prices of all items.
		VBox income = new VBox(10);

		// Title for displays.
		HBox title = new HBox();
		title.getChildren().addAll(new Label(spaceOut("Item", 10)),
				new Label(spaceOut("Cost Price", 10)),
				new Label(spaceOut("Selling", 10)),
				new Label(spaceOut("Quantity", 10)),
				new Label(spaceOut("Sale", 10)));
		income.getChildren().add(title);

		Iterator<String> items = city.market.priceOf.keySet().iterator();

		int turnover = 0;
		while (items.hasNext()) {
			String it = items.next();
			HBox itemview = new HBox();
			int cp = city.market.priceOf.get(it);
			int sp = city.market.selling.get(it);
			Integer q = city.family.stock.get(it);
			if (q == null)
				q = 0;
			turnover += sp * q;
			itemview.getChildren().addAll(new Label(spaceOut(it, 10)),
					new Label(spaceOut(Integer.toString(cp), 10)),
					new Label(spaceOut(Integer.toString(sp), 15)),
					new Label(spaceOut(Integer.toString(q), 10)),
					new Label(spaceOut(Integer.toString(q * sp), 10)));

			income.getChildren().add(itemview);
		}

		// Total sales.
		HBox total = new HBox();
		total.getChildren()
				.add(new Label(" Sales for the month:  " + turnover));

		// Food, family expenses and transportation costs.
		VBox costs = new VBox();
		costs.getChildren().add(new Label("Other Expenses"));
		costs.getChildren().add(
				new Label("Living cost    :         " + city.family.expense()));
		costs.getChildren().add(
				new Label("Transportation :     " + city.family.transport()));

		HBox rem = new HBox();
		rem.getChildren().add(
				new Label("Money Left     : " + city.family.getMoney()));

		income.getChildren().addAll(total, costs, rem, close);
		sum.getChildren().add(income);
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
