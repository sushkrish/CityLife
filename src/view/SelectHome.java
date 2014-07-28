package view;

import states.City;
import states.Home;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

public class SelectHome extends Popup {
	ImageView[] img;
	RadioButton[] radio;
	final ToggleGroup choice = new ToggleGroup();
	Home[] homes;
	
	Button choose = new Button("Rent");
	Button close = new Button("Close");
	
	Label rent;
	Text desc;
	
	public SelectHome(City city){
		 homes = city.homes;
		 img = new ImageView[homes.length];
		 radio = new RadioButton[homes.length];

		 formRegion();
		 events(city);
	}
	
	/**
	 * sets up the display for this popup.
	 */
	private void formRegion(){
		HBox display = new HBox(20);
		
		for(int i=0;i<homes.length; i++){
			img[i] = new ImageView(new Image(getClass().getResourceAsStream(
					homes[i].getPath())));
			img[i].setFitWidth(200);
			img[i].setFitHeight(200);
			radio[i] = new RadioButton(homes[i].getName());
			radio[i].setToggleGroup(choice);
			VBox v = new VBox(10);
			v.getChildren().addAll(img[i],radio[i]);
			display.getChildren().add(v);
		}
		
		radio[0].setSelected(true);
		
		rent = new Label("RENT:  " + homes[0].getRent());
		desc = new Text(homes[0].getDesc());
		desc.setWrappingWidth(500);
		
		HBox buttons = new HBox(20);
		buttons.getChildren().addAll(choose,close);
		
		
		VBox info = new VBox(10);
		info.getChildren().addAll(rent,desc,buttons);
		
		VBox all = new VBox(40);
		all.getChildren().addAll(display,info);
		
		all.setStyle("-fx-background-color:white;"
				+ "-fx-opacity:0.9;"
				+ "-fx-padding:30 30 30 30;");
		
		this.getContent().add(all);
	}
	
	/**
	 * Handles selection and pressing buttons.
	 * @param city City object used as model.
	 */
	private void events(final City city){
		choice.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0,
					Toggle arg1, Toggle new_toggle) {
				
				for(int i = 0; i<radio.length; i++){
					if(new_toggle == radio[i]){
						rent.setText("RENT:  " +homes[i].getRent());
						desc.setText(homes[i].getDesc());
					}
				}
				
			}
		});
		

		choose.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				Toggle selected = choice.getSelectedToggle();
				for(int i =0; i<homes.length; i++)
					if(selected == radio[i])
						if(city.setHome(homes[i]))
							choose.setDisable(true);
				
			}
			
		});
		
			}

	/**
	 * Closes the popup.
	 */
	public void closeDialog() {
		choose.setDisable(false);
		this.hide();
	}
	
	
	
}
