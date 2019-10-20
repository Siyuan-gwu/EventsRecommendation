package recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import db.DBConnection;
import db.DBConnectionFactory;
import getEntity.Item;

public class GeoRecommendation {


//	 public static void main(String[] args) {
//		 GeoRecommendation rec = new GeoRecommendation();
//		 List<Item> lists = rec.recommendItems("1111", 37.38, -122.08);
//		 System.out.println(lists.size());
//	 }

//	content-based method
	public List<Item> recommendItems(String userId, double lat, double lon) {
		
		List<Item> recommendationItems = new ArrayList<>();

		// step 1, get all favorited itemIds
		DBConnection conn = DBConnectionFactory.getConnection();
		Set<String> favoritedItemIds = conn.getFavoriteItemIds(userId);
		
		// step 2, get all categories, sort by count
		// {"sports": 5, "music": 3, "art": 2}
		Map<String, Integer> allCategories = new HashMap<>();
		//put all categories and the count to hash map.
		for (String itemId : favoritedItemIds) {
			Set<String> categories = conn.getCategories(itemId);
			for (String category : categories) {
				allCategories.put(category, allCategories.getOrDefault(category, 0) + 1);
			}
		}
		List<Entry<String, Integer>> categoryList = new ArrayList<>(allCategories.entrySet());
		Collections.sort(categoryList, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				if (e1.getValue() == e2.getValue()) {
					return 0;
				}
				return e1.getValue() < e2.getValue() ? 1 : -1;
			}
		});
		
		// step 3, search based on category, filter out favorite items
		//visited used to avoid recommending items repeatly
		Set<String> visitedItemIds = new HashSet<>();
		for (Entry<String, Integer> category : categoryList) {
			List<Item> items = conn.searchItems(lat, lon, category.getKey());
			
			for (Item item : items) {
				if (!favoritedItemIds.contains(item.getItemId()) && !visitedItemIds.contains(item.getItemId())) {
					recommendationItems.add(item);
					visitedItemIds.add(item.getItemId());
				}
			}
		}
		conn.close();
		return recommendationItems;
	}
}
