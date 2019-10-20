package rpc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import getEntity.Item;
import getEntity.Item.ItemBuilder;;

class RpcHelperTest {
	
	@Test
	public void testGetJSONArrayCornerCases() throws JSONException {
		Set<String> category = new HashSet<String>();
		category.add("category one");
		
		List<Item> list = new ArrayList<Item>();
		JSONArray array = new JSONArray();
		JSONAssert.assertEquals(array, RpcHelper.getJSONArray(list), true);

		Set<String> categories = new HashSet<>();
		categories.add("category one");
		ItemBuilder builder = new ItemBuilder();
		builder.setItemId("one");
		builder.setName("item");
		builder.setAddress("2000 S Eads St");
		builder.setCategories(categories);
		builder.setDistance(20.01);
		builder.setImageUrl("www.google.com");
		builder.setUrl("www.hahah.com");
		builder.setRating(4.5);
		Item one = builder.build();
		ItemBuilder builder1 = new ItemBuilder();
		builder1.setItemId("two");
		builder1.setName("testItem");
		builder1.setAddress("1900 S Eads St");
		builder1.setCategories(categories);
		builder1.setDistance(43.12);
		builder1.setImageUrl("www.test2.com");
		builder1.setUrl("www.rpchelpertest.com");
		builder1.setRating(3.8);
		Item two = builder1.build();
		list.add(one);
		list.add(two);
		
		array.put(one.toJSONObject());
		array.put(two.toJSONObject());	
		JSONAssert.assertEquals(array, RpcHelper.getJSONArray(list), true);
		
		Item empty = new ItemBuilder().build();
		array.put(empty.toJSONObject());
		list.add(empty);
		JSONAssert.assertEquals(array, RpcHelper.getJSONArray(list), true);
	}
}
