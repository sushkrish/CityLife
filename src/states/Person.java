package states;

import java.lang.Math;

public class Person {
	Family family;
	
	// Name assigned to the person.
	String name;

	// Represents gender. male = 'm' , female = 'f';
	String gender;

	// Age of the person, in years
	int age;

	// Age of the person in months after years.
	int months;

	// Health from 0 to 100.
	int health = 100;

	// Spouse of the person. If none, then spouse = null.
	Person spouse = null;

	// Years of education out of 14.
	int edu = 0;
		
	// For a person attending school, keeps track of consecutive months of
	// school.
	int schoolmonths;
	
	//If true, person will go to school next month. If false, will not go to school.
	public boolean school;

	public Person(String name, int age, String gender, Family family) {
		this.name = name;
		this.age = age;
		this.months = 0;
		this.gender = gender;
		school = false;
		this.family = family;
	}
	
	public void sendToSchool(){
		this.school = true;
	} 
	
	/**
	 * Only a person between the ages of 4 and 21 (excluded) can go to school.
	 * 8 months of consecutive schooling counts as one year of formal education. 
	 */
	public void goToSchool() {
		if (age > 4 && age < 21)
			if (school) {
				schoolmonths++;
				if (schoolmonths == 8) {
					edu++;
					schoolmonths = 0;
				}
			} else
				schoolmonths = 0;
		
		school = false;
	}
	
	public int getEdu(){
		return edu;
	}

	/**
	 * Set the name of a person.
	 * 
	 * @param n
	 *            Name to be set.
	 */
	public void setName(String n) {
		this.name = n;
	}

	public int getAge(){
		return age;
	}
	
	/**
	 * Increase age one month at a time.
	 */
	public void incAge() {
		if (++months == 13) {
			months = 0;
			age++;
		}
	}

	/**
	 * Get current health of the person
	 * 
	 * @return int representing health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Increase health by p points
	 * 
	 * @param p
	 *            number of health points to increase by.
	 */
	public void incHealth(int p) {
		health += p;
		if (health > 100)
			health = 100;
	}

	/**
	 * Decrease health points
	 * 
	 * @param p
	 *            number of points to decrease by.
	 */
	public void decHealth(int p) {
		health -= p;
		if (health < 0)
			health = 0;
	}

	/**
	 * Checks if person is married.
	 * 
	 * @return true if yes, false if not.
	 */
	public boolean isMarried() {
		if (spouse == null)
			return false;
		else
			return true;
	}

	/**
	 * Sets the spouse of this person
	 * 
	 * @param p
	 *            object of Person type: the spouse.
	 * @throws Exception
	 *             if this person is already married.
	 */
	public void setSpouse(Person p) throws Exception {
		if (spouse == null){
			spouse = p;
			p.setSpouse(this);
		} 
	}
	
	/**
	 * Set spouse to null.
	 */
	public void removeSpouse(){
		spouse = null;
	}

	/**
	 * Get gender of the person
	 * 
	 * @return 'm' for male and 'f' for female.
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Get name of the person
	 * @return String name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get the name of the spouse
	 * @return String - name of spouse if married, else "Unmarried".
	 */
	public String spouseName(){
		if(isMarried())
			return spouse.getName();
		else
			return "Unmarried";
	}
	
	/**
	 * Get spouse of this person.
	 * @return Person spouse
	 */
	public Person spouse(){
		return spouse;
	}
	
	/**
	 * Get cost of medicine required to full health, depending on current health level.
	 * @return int cost
	 */
	public int getMedCost(){
		int mod = (100 - health)/10;
		return (int) (Math.pow(2,mod)*50);
	}
	
	/**
	 * to restore the health of the person, at a cost.
	 * @throws Exception
	 */
	public void getMedicine() throws Exception {
		int mod = (100 - health)/10;
		int cost = (int) (Math.pow(2,mod)*50);
		if(family.getMoney()>cost){
			family.reduceMoney(cost);
			System.out.println(cost);
			health = 100;
		}
		else
			throw new Exception("Not enough money to get medicine.");
	}

	
}
