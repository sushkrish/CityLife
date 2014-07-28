package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

public class endGame extends Popup {

	String money = "There's no money left!"
			+ "You are now homeless and have no livelihood. "
			+ "The urban dream is shattered and "
			+ "You are forced to return to your village.";

	String death = "None of your family members survived in the city."
			+ " The dream ends here.";
	
	Text msg;
	
	Button close = new Button("Close");
	Image image = new Image(getClass().getResourceAsStream("end.jpg"));

	/**
	 * Set the content of the popup window.
	 */
	public endGame() {
		VBox content = new VBox(10);
		ImageView iv = new ImageView(image);
		iv.setFitWidth(200);
		iv.setFitHeight(150);
		msg = new Text("");

		content.getChildren().addAll(iv, msg, close);
		this.getContent().add(content);
		content.setStyle("-fx-background-color:WHITE; -fx-opacity:0.9; -fx-padding: 20 20 20 20");
		content.setAlignment(Pos.CENTER);
		msg.setWrappingWidth(150);

	}
	
	public void money(){
		msg.setText(money);
	}
	
	public void death(){
		msg.setText(death);
	}

	/**
	 * Closes the popup window
	 */
	public void closeDialog() {
		this.hide();
	}
}
