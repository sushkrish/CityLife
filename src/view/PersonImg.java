package view;

import states.Person;
import javafx.scene.image.Image;

public class PersonImg {
	public final Image image;
	
	int x;
	int y;
	int width;
	int height;
	Person p;

	public PersonImg(Person person, int x, int y) {
		p = person;
		this.x = x;
		this.y = y;
		String path;
		
		if(p.getGender().equals("m"))
			if(p.getAge()<18){
				path = "boy.png";
				width = 100 + 3* (p.getAge()-4);
				height = 200 + 11 * (p.getAge()-4); 
			}
			else{
				path = "man.png";
				width = 130;
				height = 400;
			}
		else{
			if(p.getAge()<18){
				path = "girl.png";
				width = 100 + 3* (p.getAge()-4);
				height = 200 + 11 * (p.getAge()-4); 

			}
			else{
				path = "woman.png";
				width = 160;
				height = 410;
			}
		}
		
		image = new Image(getClass().getResourceAsStream(
					path));
		
		
	}
	
	
	public Image getImage(){
		return image;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getW(){
		return width;
	}
	
	public int getH(){
		return height;
	}
	
	public void setY(int y){
		this.y = y;
	}
	public Person getPerson(){
		return p;
	}
}
