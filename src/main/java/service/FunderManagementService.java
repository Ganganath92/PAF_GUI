package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.FunderManagement;



@Path("/Funder")
public class FunderManagementService {
	FunderManagement funder_ob = new FunderManagement();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPosts() {
		return funder_ob.readPost();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createPost(
			@FormParam("title") String title, 
			@FormParam("content") String content,
			@FormParam("pdate") String pdate, 
			@FormParam("ptime") String ptime) {
		
		String output = funder_ob.createPost(title, content, pdate, ptime);

		return output;
	}

	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateItem(String postData) {
		// Convert the input string to a JSON object
		JsonObject postObject = new JsonParser().parse(postData).getAsJsonObject();

		// Read the values from the JSON object

		String id = postObject.get("id").getAsString();
		String title = postObject.get("title").getAsString();
		String content = postObject.get("content").getAsString();
		String pdate = postObject.get("pdate").getAsString();
		String ptime = postObject.get("ptime").getAsString();

		String output = funder_ob.updateItem(id, title, content, pdate, ptime);

		return output;

	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteItem(String itemData) 
	{ 
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
	 
	//Read the value from the element <itemID>
	 String id = doc.select("id").text(); 
	 String output = funder_ob.deleteItem(id); 
	return output; 
	}
}

