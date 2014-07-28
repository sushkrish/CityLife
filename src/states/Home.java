package states;

public class Home{
	
	//Level of hygiene of neighborhood on a sale of 0 to 100.
	int hygiene; 
	
	//Rent of the place.
	int rent;
	
	//Name/type of neighborhood.
	String name;
	
	//Description
	String desc;
	
	//Path of image for this home.
	String imgpath;
	
	public Home(String name, int rent, int h, String description, String path){
		this.name = name;
		this.rent = rent;
		this.hygiene = h;
		this.desc = description;
		this.imgpath = path;
	}
	
	/**
	 * Increase rent by given percentage.
	 * @param perc float percentage by which price will increase
	 */
	public void priceRise(float perc){
		rent = (int)((1.0+perc) *rent);
	}
	
	/**
	 * @Return the hygiene level of the place. 
	 */
	 public int getHyg(){
		return hygiene;
	}
	 
	 public int getRent(){
		 return rent;
	 }
	 
	 public String getName(){
			return name;
	}

	public String getPath() {
		return imgpath;
	}
	
	public String getDesc(){
		return desc;
	}
}
