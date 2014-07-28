package states;

public class Event {
	
	//Name of the event.
	String name;
	
	//DESCRIPTION of the event.
	String desc;
	
	//Cost born by the family. 
	int cost;
	
	//Image path
	String img;

	public Event(String name,String desc,String img){
		this.name = name;
		this.desc = desc;
		cost = 0;
		this.img = img;
	}
	
	/**
	 * Get name of the event
	 * @return String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get description of the event
	 * @return String
	 */
	public String getDesc(){
		return desc;
	}
	
	/**
	 * set the cost if this event occurs
	 * @param cost int
	 */
	public void setCost(int cost){
		this.cost = cost;
	}
	
	/**
	 * Get the cost if this event occurs
	 * @return int
	 */
	public int getCost(){
		return cost;
	}
	
	/**
	 * Get the path of the image for this event
	 * @return String - image path.
	 */
	public String imagePath(){
		return img;
	}
}
