package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StartScene extends StackPane {

	public Button start = new Button(" Play ");
	Button about = new Button("About ");
	Button guide = new Button("Guide");
	Button credits = new Button("Credits");
	Button exit = new Button("  Exit   ");
	HBox content = new HBox(60);
	Text msg;

	String intro = "CityLife is a simulation game on urban poverty in third world countries."
			+ " Tired of rural poverty, you decided to pursue the urban dream."
			+ " A distant relative helped you start out as a vegetable seller in the Big City."
			+ " You must strive for the survival of your family.";

	String about_game = "CityLife was inspired by a series of interactions"
			+ " with slum residents in New Delhi, India. "
			+ "The aim of the simulation is to make players think about"
			+ " the role of the the urban poor and the challenges faced by them. "
			+ "Although a city functions only because of the innumerous residents "
			+ "who service it, where is their place in the city?";

	String help = "Click on family members to learn about them. "
			+ "Use the top right menu to see the market "
			+ "rates and to commence the month. Think about your decisions."
			+ " Every choice has a consequence!";

	String credit = "Created and developed by Sushmitha Krishnamoorthy. "
			+ "Photographs used in the graphics - credits to ________";

	public final Image background = new Image(getClass().getResourceAsStream(
			"city.jpg"));

	StartScene() {
		Canvas image = new Canvas(1000, 600);
		image.getGraphicsContext2D().drawImage(background, 0, 0, 1000, 600);
		this.getChildren().add(image);
		this.getChildren().add(content);
		formMenu();
		content.setMinWidth(500);
		content.setMaxWidth(500);
		content.setPrefWidth(500);

		content.setMinHeight(200);
		content.setMaxHeight(200);
		content.setPrefHeight(200);
		content.setStyle(" -fx-opacity:1.0; -fx-padding:50 10 10 10");

		menuEvents();
	}

	/**
	 * Displays the menu of the start screen.
	 */
	private void formMenu() {

		VBox menu = new VBox(20);
		menu.getChildren().addAll(start,guide, about, credits, exit);
		content.getChildren().add(menu);
		menu.setStyle("-fx-padding: 0 0 0 20");

		HBox text = new HBox();
		msg = new Text(intro);
		msg.setWrappingWidth(250);
		text.getChildren().add(msg);
		text.setStyle("-fx-font-size:14px; -fx-background-color:WHITE; -fx-opacity:0.9; -fx-padding: 20 20 20 20;");
		content.getChildren().add(text);
	}

	/**
	 * Handles response to the menu buttons.
	 */
	private void menuEvents() {

		about.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				msg.setWrappingWidth(300);
				msg.setText(about_game);
			}

		});
		
		guide.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				msg.setWrappingWidth(250);
				msg.setText(help);
			}

		});
		
		credits.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				msg.setText(credit);
				msg.setWrappingWidth(250);
			}

		});
	}

}
