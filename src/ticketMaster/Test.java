package ticketMaster;

import java.util.List;

import getEntity.Item;

public class Test {

	public static void main(String[] args) {
		TicketMasterClient client = new TicketMasterClient();
		List<Item> events = client.search(37.38, -122.08, "Sports");
		
		for (Item event : events) {
			System.out.println(event.toJSONObject());
		}
	}
}
