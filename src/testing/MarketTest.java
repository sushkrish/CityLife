package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import states.WholeSale;

public class MarketTest {

	@Test
	/**
	 * Tests the function getPrices()
	 */
	public void test() {
		WholeSale market = new WholeSale();
		String[] items = {"Onion", "Paneer", "Tomato", "Mango"};
		String[] itemswrong = {"Onion", "Paneeer", "Tomato" , "Mango"};
		int[] q = {1,2,3,4};
		int[] r ={1,2,3,4,5};
		
		
		int cost = market.getPrices(items,q);
		assertTrue(cost==668);
		System.out.println(market.getPrices(items,q));
		cost = market.getPrices(items,r);
		assertTrue(cost == -1);
		
	
		try{
			market.getPrices(itemswrong,q);
			fail("Should have thrown a null pointer exception");
		}
		catch (NullPointerException e ){
			System.out.println("Perfect.");
		}
	}

}
