package view;

import states.City;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		final City city = new City();

		final GameScene game = new GameScene(city, stage);
		Group root = new Group();
		root.getChildren().add(game.getCanvas());
		final Scene gamescene = new Scene(root);

		StartScene pane = new StartScene();
		final Scene start = new Scene(pane);
		stage.setScene(start);
		stage.show();

		// When the start button is pressed.
		pane.start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				game.reset();
				game.redraw(city);

				stage.setScene(gamescene);

				// When the game ends.
				game.end.close.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						game.end.closeDialog();
						stage.setScene(start);
						city.newGame();
					}

				});

			}

		});

		pane.exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}

		});

	}
}
