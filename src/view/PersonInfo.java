package view;

import states.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class PersonInfo extends Popup{

	Person p;
	Button close = new Button("Close");
	
	public PersonInfo(Person p){
		this.p = p;
		formDisp();
	}
	
	public PersonInfo(){
		
	}
	
	/**
	 * Change the person whose atttributes are being displayed.
	 * @param p
	 */
	public void changePerson(Person p){
		this.p = p;
		this.getContent().clear();
		formDisp();
	}
	
	/**
	 * Set up display of the person's attributes.
	 */
	private void formDisp(){
		Label n = new Label("NAME       " + p.getName());
		Label g = new Label("GENDER     " + p.getGender());
		Label a = new Label("AGE        " + ((Integer)p.getAge()).toString() + " years");
		Label s = new Label("SPOUSE     " + p.spouseName());
		final Label h = new Label("HEALTH     " + ((Integer)p.getHealth()).toString() + " / 100");
		Label e = new Label("EDUCATION  " + ((Integer)p.getEdu()).toString() + " years");
		final Button health = new Button("Get Medicine");
		if(p.getHealth() == 100)
			health.setDisable(true);
		
		VBox holder = new VBox(5);
		
		holder.setStyle("-fx-background-color: white;"
				+ "-fx-padding: 20 20 20 20;"
				+ "-fx-border-radius: 20;"
				+ "-fx-opacity:0.8");
		
		holder.getChildren().addAll(n,g,a,s,h,health,e);
		
		//Handling the go to school option.
		if(p.getAge()>4 && p.getAge()<21){
			final Button school = new Button("Send to School");
			holder.getChildren().add(school);
			
			if(p.school)
				school.setDisable(true);
				
			school.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					p.sendToSchool();
					school.setDisable(true);
				}				
			});
		}
		
		//Clicking on get medicine.
		health.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				try {
					p.getMedicine();
					h.setText("HEALTH     " + ((Integer)p.getHealth()).toString() + " / 100");
				} catch (Exception e) {
					e.printStackTrace();
				}
				health.setDisable(true);
			}
			
		});
	
		holder.getChildren().add(close);
		this.getContent().add(holder);
	}
	
	/**
	 * Closes this popup.
	 */
	public void closeDialog(){
		this.hide();
	}
	
	
}
