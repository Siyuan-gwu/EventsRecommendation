package rpc;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import getEntity.Item;


//write JSONArray and JSONObject to the response body
public class RpcHelper {

	// Writes a JSONArray to http response.
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException{
		response.setContentType("application/json");
		response.getWriter().print(array);
	}

	// Writes a JSONObject to http response.
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {		
		response.setContentType("application/json");
		response.getWriter().print(obj);
	}
	
	public static JSONObject readJSONObject(HttpServletRequest request) {

		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return new JSONObject(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new JSONObject();
	}
	
	public static JSONArray getJSONArray(List<Item> items) {
		JSONArray array = new JSONArray();
		try {
			for (Item item : items) {
				array.put(item.toJSONObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return array;
	}

}
