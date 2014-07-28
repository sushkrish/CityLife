package states;

public class WorkPlace {
	// safety from harrassment on a scale of 0 to 10. 
	int safety;

	// Rent of the location or medium.
	int rent;

	// name or type
	String name;

	// description;
	String desc;
	
	//path for image
	String imgpath;

	public WorkPlace(String name, int rent, int safety, String d,String path) {
		this.name = name;
		this.rent = rent;
		this.safety = safety;
		this.desc = d;
		imgpath = path;
	}

	public int getRent() {
		return rent;
	}

	public int getSafety() {
		return safety;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getPath() {
		return imgpath;
	}

}
