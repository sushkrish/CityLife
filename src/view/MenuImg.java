package view;

import javafx.scene.image.Image;

public class MenuImg {
	String name;
	int x;
	int y;
	int width;
	int height;
	Image image;
	
	public MenuImg(String n, String path, int x, int y, int w, int h){
		this.name = n;
		this.x = x;
		this.y = y;
		width = w;
		height = h;
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
	
	public String getName(){
		return name;
	}
	
}
