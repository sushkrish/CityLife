package states;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class City {

	// The Protagonist.
	public Family family = new Family();

	// The market to buy stuff from.
	public WholeSale market = new WholeSale();

	// The options in homes.
	public Home[] homes;

	// The options in Work Locations
	public WorkPlace[] locations;

	// Possible events.
	public Event[] events;

	// event for this turn.
	public Event event;

	//number of timesteps/months passed.
	int time;
	
	public City() {
		initHomes();
		initLocations();
		initEvents();
		market.setPrice();
	}

	/**
	 * Buying from the market.
	 * 
	 * @param cost
	 *            int value of all the stock bought.
	 * @return true if shopping is successful, false otherwise.
	 */
	public boolean buyStock(int cost, Hashtable<String, Integer> stock)
			throws Exception {
		if (family.getMoney() - cost >= 0) {
			family.reduceMoney(cost);
			family.addStock(stock);
			return true;
		} else
			return false;
	}

	/**
	 * Initializes the options of homes.
	 */
	public void initHomes() {
		homes = new Home[3];
		homes[0] = new Home(
				"Slum",
				1000,
				45,
				"A one room brick house in a dense slum within the city. Only communal toilets are available, "
						+ "water quality cannot be trusted and electricity outage is frequent.",
				"slum.jpg");
		homes[1] = new Home(
				"Old Apartment",
				4000,
				70,
				"A decent apartment in the periphery of the city. Government water and power supply is available. "
						+ "However, the air and roads are polluted due to industrial activities nearby. ",
				"oldapartment.jpg");
		homes[2] = new Home(
				"Modern Apartment",
				7500,
				95,
				"A relatively new apartment in a residential colony within the city. Basic services and sanitation is excellent. ",
				"newapartment.jpg");
	}

	/**
	 * Initiliazes the options in workplaces.
	 */
	public void initLocations() {
		locations = new WorkPlace[4];
		locations[0] = new WorkPlace(
				"Floor Mat",
				500,
				0,
				" Set up shop using a neat, cheap mattress on the grounds of a local market.",
				"ground.jpg");
		locations[1] = new WorkPlace(
				"Hand Cart",
				2000,
				4,
				" Sell your produce on a convenient hand cart, easily moving from neighborhood to neighborhood.",
				"cart.jpg");
		locations[2] = new WorkPlace("Temporary Shop", 5000, 7,
				" Rent a temporary shack in a local market to run your shop.",
				"shack.jpg");
		locations[3] = new WorkPlace(
				"Rented Space",
				8000,
				10,
				" Run your shop from a rental commercial space in a local market.",
				"shop.jpg");
	}

	/**
	 * initializes the possible events that could occur.
	 */
	private void initEvents() {
		events = new Event[6];
		// Death, harrassment, accident, no event

		events[0] = new Event("Uneventful",
				"It was a regular month, quite uneventful.", "shack.jpg");

		events[1] = new Event(
				"Police Harrassment",
				"The local police claim that your business is making the streets congested. "
						+ "Since you don't have a permit or a legal location to sell from,"
						+ " you had no choice but to pay the bribe.",
				"shack.jpg");

		events[2] = new Event("Death",
				"A family member has died due to poor health.", "shack.jpg");

		events[3] = new Event("Accident",
				"When you were transporting your vegetable stock to the market, "
						+ "a car collided with your cycle drawn cart. "
						+ "You ended up losing a lot of your money",
				"shack.jpg");

		events[4] = new Event("Disease",
				"A contagious disease spread through your neighborhood. "
						+ "Your family's health has seriously deprecated.",
				"shack.jpg");
		events[5] = new Event("Inherited Money",
				"A relative died and left you a little money.", "shack.jpg");
	}

	/**
	 * Based on conditions of the family and some elements of randomness, sets
	 * the event for the turn.
	 */
	private void setEvent() {
		Random r = new Random();
		if (family.death == true)
			event = events[2];
		else if (family.work.getSafety() < 10 && r.nextInt(4) == 1) {
			event = events[1];
			int s = family.work.getSafety();

			double perc = 0;
			if (s < 4)
				perc = 0.2;
			else if (s < 7)
				perc = 0.1;
			else
				perc = 0.05;

			event.setCost((int) (family.getMoney() * perc));
			family.reduceMoney(event.getCost());

		} else if (family.home.getName().equals("Slum") && (r.nextInt(10) == 3)) {
			event = events[4];
			family.reduceHealth();
		} else if (family.getMoney() < 3000 && r.nextInt(4) == 2) {
			event = events[5];
			event.setCost(-1 * 3000);
			family.addMoney(3000);
		} else if (r.nextInt(5) == 2) {
			event = events[3];
			event.setCost(family.getMoney() / 4);
			family.reduceMoney(event.getCost());
		} else
			event = events[0];

	}

	/**
	 * Set the current home of the family.
	 * 
	 * @param home
	 *            Home to be set as current home.
	 * @return true if it has been successfully set, false otherwise.
	 */
	public boolean setHome(Home home) {

		if (family.getMoney() >= home.getRent()) {
			family.setHome(home);
			return true;
		}
		return false;

	}

	/**
	 * Set the current workplace of the family.
	 * 
	 * @param work
	 *            WorkPlace to be set as current.
	 * @return true if successfully set, false otherwise.
	 */
	public boolean setWork(WorkPlace work) {
		if (work.getRent() <= family.getMoney()) {
			family.setWork(work);
			return true;
		}
		return false;
	}

	/**
	 * Get an arraylist of the people in the family.
	 * 
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> getPeople() {
		return family.getPersons();
	}

	/**
	 * Get the amount of money owned by the family.
	 * 
	 * @return int - money owned.
	 */
	public int getMoney() {
		return family.getMoney();
	}

	/**
	 * Updates attributes of the family members, handles sale of the stock and
	 * event for the turn.
	 * @return false is game is over, true if game is still on.
	 */
	public boolean timeStep() {
		family.timeStep();
		int turnover = market.sellAll(family.stock);
		family.addMoney(turnover);
		setEvent();
		time++;
		
		return family.getMoney() < 1500 ? false : true;
	}
	
	/**
	 * Resets elements like family stock and market prices.
	 */
	public void newTurn() {
		family.stock.clear();
		market.setPrice();
	}
	
	/**
	 * Restart the game.
	 */
	public void newGame() {
		family = new Family();
		time = 0;
	}

}
