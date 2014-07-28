package view;

import java.util.ArrayList;
import java.util.Iterator;
import states.City;
import states.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameScene {

	int STATE = 0;
	static final int STATE_START = 1;
	static final int STATE_CHOICE = 2;
	static final int STATE_NEXT = 3;
	static final int STATE_END = 4;
	public boolean gameon = true;
	
	

	public final Image background = new Image(getClass().getResourceAsStream(
			"market6.jpg"));

	public endGame end = new endGame();
	PersonInfo person = new PersonInfo();
	MarketView marketview;
	SelectHome homeview;
	SelectWork workview;
	Canvas canvas = new Canvas(1000, 600);
	GraphicsContext scene = canvas.getGraphicsContext2D();
	ArrayList<PersonImg> persons = new ArrayList<PersonImg>();
	MenuImg next = new MenuImg("Next", "time.jpg", 900, 500, 60, 60);
	MenuImg market = new MenuImg("Market", "buy.jpg", 820, 10, 60, 60);
	MenuImg choice = new MenuImg("choice", "choose.jpg", 900, 10, 60, 60);
	Stage stage;
	Person selected = null;
	String money;

	GameScene(City city, Stage st) {
		money = ("MONEY: " + city.getMoney());
		getInfo(city);
		drawPeople(city);
		
		stage = st;
		marketview = new MarketView(city);
		homeview = new SelectHome(city);
		workview = new SelectWork(city);
		STATE = STATE_START;
	}

	/**
	 * Resets the game attributes STATE and gameon.
	 */
	public void reset() {
		STATE = STATE_START;
		gameon = true;
	}

	/**
	 * redraws the game scene.
	 * 
	 * @param city
	 *            City object used as model.
	 */
	void redraw(City city) {
		getInfo(city);
		drawPeople(city);
		selected = null;
		marketview.update();
	}

	/**
	 * Presents series of windows to choose home, workplace and buy stock.
	 * 
	 * @param city
	 *            City object used as model
	 */
	private void makeChoice(final City city) {
		homeview.show(stage);

		// Selecting a home.
		homeview.close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (homeview.choose.isDisabled()) {
					updateMoney(city);
					homeview.closeDialog();
					workview.show(stage);
				}
			}

		});

		// Selecting workplace.
		workview.close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (workview.choose.isDisabled()) {
					updateMoney(city);
					workview.closeDialog();
					marketview.show(stage);
				}
			}

		});

		// Buying stock in the whole sale market.
		marketview.close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				updateMoney(city);
				marketview.hide();
				STATE = STATE_NEXT;
				scene.drawImage(next.getImage(), next.getX(), next.getY(),
						next.getW(), next.getH());
				marketview.resetQuantity();
			}

		});

	}

	/**
	 * Updates the label money with the current value of money owned by family.
	 */
	private void updateMoney(City city) {
		money = "MONEY: " + city.getMoney();

		scene.setStroke(Color.WHITE);
		scene.setFill(Color.WHITE);

		scene.fillRoundRect(10, 10, 100, 30, 10, 10);
		scene.setFill(Color.BLACK);
		scene.fillText(money, 20, 30);
	}

	/**
	 * Returns the canvas drawn upon.
	 * 
	 * @return
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	/**
	 * Gets relevant attributes of family members.
	 * 
	 * @param city
	 */
	private void getInfo(City city) {
		persons.clear();
		Iterator<Person> it = city.getPeople().iterator();
		int i = 0;
		while (it.hasNext()) {
			PersonImg p = new PersonImg(it.next(), 120 + 150 * i,200);
			p.setY(600 - p.getH() -30);
			persons.add(p);
			
			i++;
		}
		
		if(i == 0 ){
			STATE = STATE_END;
			end.death();
			end.show(stage);
		}
	}

	/**
	 * Draws the family on the game scene.
	 * 
	 * @param city
	 *            City object used as model.
	 */
	private void drawPeople(City city) {
		// Drawing background
		scene.drawImage(background, 0, 0, 1000, 600);

		// Drawing people.
		Iterator<PersonImg> p = persons.iterator();
		while (p.hasNext()) {
			PersonImg pi = p.next();
			scene.drawImage(pi.getImage(), pi.getX(), pi.getY(), pi.getW(),
					pi.getH());
		}

		// Drawing money display
		updateMoney(city);

		// Drawing menu options.
		scene.drawImage(choice.getImage(), choice.getX(), choice.getY(),
				choice.getW(), choice.getH());

		scene.drawImage(market.getImage(), market.getX(), market.getY(),
				market.getW(), market.getH());

		// Activating mouse response handlers.
		mouseEvents(city);
	}

	/**
	 * Handles mouse activity - selecting people or menu buttons.
	 * 
	 * @param city
	 */
	public void mouseEvents(final City city) {

		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {
				int x = (int) me.getX();
				int y = (int) me.getY();

				// Looking for a mouseclick on a person.
				Iterator<PersonImg> people = persons.iterator();
				while (people.hasNext()) {
					PersonImg pi = people.next();
					if (x > pi.getX() && x < (pi.getX() + pi.getW()))
						if (y > pi.getY() && y < (pi.getY() + pi.getH())) {
							selected = pi.getPerson();
							break;
						}
				}

				if (selected != null)
					dispAttributes(city);

				if (STATE == STATE_START && x > choice.getX()
						&& x < (choice.getX() + choice.getW())) {
					if (y > choice.getY()
							&& y < (choice.getY() + choice.getH())) {
						STATE = STATE_CHOICE;
						makeChoice(city);
						updateMoney(city);
					}
				}
				
				//Cannot buy using this invocation of the market. This is only to view.
				if (STATE != STATE_END && STATE!=STATE_CHOICE && x > market.getX()
						&& x < (market.getX() + market.getW())) {
					if (y > market.getY()
							&& y < (market.getY() + market.getH())) {
						marketview.buy.setDisable(true);
						marketview.show(stage);
						marketview.close
								.setOnAction(new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent arg0) {
										updateMoney(city);
										marketview.hide();
										marketview.resetQuantity();
										marketview.buy.setDisable(false);
									}

								});

					}
				}

				if (STATE == STATE_NEXT && x > next.getX()
						&& x < (next.getX() + next.getW()))
					if (y > next.getY() && y < (next.getY() + next.getH())) {
						scene.drawImage(choice.getImage(), choice.getX(),
								choice.getY(), choice.getW(), choice.getH());
						summary(city);

					}
			}
		});
	}

	/**
	 * Displays the attributes of the current person selected.
	 * 
	 * @param city
	 *            City object used as model.
	 */
	private void dispAttributes(final City city) {

		person.changePerson(selected);
		person.show(stage);

		person.close.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				updateMoney(city);
				person.closeDialog();
			}

		});
		selected = null;
	}

	/**
	 * If STATE is STATE_NEXT, then time step happens and a summary of the last
	 * month is shown.
	 * 
	 * @param city
	 *            City object used as model.
	 */
	private void summary(final City city) {
		if (STATE == STATE_NEXT) {
			gameon = city.timeStep();

			final Summary s = new Summary(city);

			if (gameon == false) {
				STATE = STATE_END;
				end.money();
				end.show(stage);
			}

			s.show(stage);
			s.close.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					if (STATE == STATE_NEXT) {
						city.newTurn();
						redraw(city);
						updateMoney(city);
						STATE = STATE_START;
					}
					s.hide();
				}

			});

		}
	}

}
