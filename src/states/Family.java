package states;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

public class Family {
	// Keep a list of family members.
	ArrayList<Person> persons = new ArrayList<Person>();

	// The money they currently have.
	private Integer money = 0;

	// Monthly expenses of the family.
	private int expenses = 0;

	// Transportation cost for the vegetable stock.
	private int transportation = 0;

	// Current house
	public Home home;

	// Current work location
	public WorkPlace work;

	// Stock held by family.
	public Hashtable<String, Integer> stock = new Hashtable<String, Integer>();

	// has there been a death?
	public boolean death = false;

	public Family() {
		Person m = new Person("Raju", 28, "m", this);
		Person f = new Person("Ritu", 26, "f", this);
		Person c1 = new Person("Neeti", 8, "f", this);
		Person c2 = new Person("Harsh", 6, "m", this);
		try {
			this.addPerson(m);
			m.setSpouse(f);
			this.addPerson(f);
			this.addPerson(c1);
			this.addPerson(c2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		money = 8000;
	}

	/**
	 * Get the number of family members.
	 * 
	 * @return int
	 */
	public int familySize() {
		return persons.size();
	}

	/**
	 * Set the home of the family
	 * 
	 * @param h
	 *            new Home to be set.
	 */
	public void setHome(Home h) {
		home = h;
		reduceMoney(h.getRent());
		expenses = familySize() * (home.getRent() / 15);
	}

	/**
	 * Set the workplace of the family
	 * 
	 * @param wp
	 *            new WorkPlace to be set.
	 */
	public void setWork(WorkPlace wp) {
		work = wp;
		reduceMoney(wp.getRent());
	}

	/**
	 * Adds a person to the family
	 * 
	 * @param p
	 *            Person to be added.
	 * @throws Exception
	 *             if family is greater than 6 people.
	 */
	void addPerson(Person p) throws Exception {
		if (familySize() >= 6)
			throw new Exception("Family is too big.");
		persons.add(p);
	}

	/**
	 * Deletes a given person from the family
	 * 
	 * @param p
	 *            Person to be deleted.
	 */
	void deletePerson(Person p) {
		persons.remove(p);
	}

	public int getMoney() {
		return money;
	}

	/**
	 * Increase money that family has
	 * 
	 * @param m
	 *            amount to increase by
	 */
	void addMoney(int m) {
		money += m;
	}

	/**
	 * Decrease money that family has
	 * 
	 * @param m
	 *            amount to decrease by
	 */
	void reduceMoney(int m) {
		money -= m;
	}

	/**
	 * Get the total quantity of stock held by the family.
	 * 
	 * @return int sum of all stock quantities.
	 */
	private int stockQuantity() {
		Iterator<Integer> q = stock.values().iterator();
		int sum = 0;
		while (q.hasNext()) {
			sum += q.next();
		}
		return sum;
	}

	/**
	 * When a time step happens, a person's age increase by a month health
	 * decreases according to hygiene level of home, income for the month is
	 * added.
	 */
	public void timeStep() {

		transportation = stockQuantity();
		reduceMoney(transportation);

		reduceMoney(expenses);
		ArrayList<Person> deadPeople = new ArrayList<Person>();
		death = false;

		Iterator<Person> peeps = persons.iterator();
		while (peeps.hasNext()) {
			Person p = peeps.next();

			Random random = new Random();
			p.decHealth((100 - home.getHyg()) / 15 + random.nextInt(4));

			// checking if dead.
			if (p.getHealth() == 0) {
				deadPeople.add(p);
				death = true;
			}

			// increasing age.
			p.incAge();

			// Handles education choices and conditions.
			p.goToSchool();
		}

		Iterator<Person> p = deadPeople.iterator();
		while (p.hasNext()) {
			Person per = p.next();
			System.out.println(per.getName());
			deletePerson(per);
			if (per.isMarried())
				per.spouse.removeSpouse();

		}

	}

	/**
	 * Drastically reduce the health of the family members. To be used only in
	 * case of the 'disease' event.
	 */
	public void reduceHealth() {

		Iterator<Person> peeps = persons.iterator();
		while (peeps.hasNext()) {
			Person p = peeps.next();
			p.decHealth((int) (p.getHealth() * .5));
		}
	}

	/**
	 * Get an Arraylist of all the family members.
	 * 
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> getPersons() {
		return persons;
	}

	/**
	 * Add the stock given to the family's monthly stock.
	 * 
	 * @param stock2
	 *            new stock to be added.
	 */
	public void addStock(Hashtable<String, Integer> stock2) {
		Iterator<String> it = stock2.keySet().iterator();
		while (it.hasNext()) {
			String item = it.next();
			Integer ex = stock.get(item);
			if (ex != null)
				stock.put(item, ex + stock2.get(item));
			else
				stock.put(item, stock2.get(item));
		}
	}

	/**
	 * Living costs for the month.
	 * 
	 * @return int
	 */
	public int expense() {
		return expenses;
	}

	/**
	 * Cost of transportation of stock for the month.
	 * 
	 * @return int
	 */
	public int transport() {
		return transportation;
	}

}
