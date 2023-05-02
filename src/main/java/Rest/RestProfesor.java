
package Rest;
import Entity.*;
import Utils.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
public class RestProfesor {
	private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	private static final String serviceURL = "http://localhost:8081/Profesor/";
	//sending request to retrieve all profesors available.
	public List<Profesor> findAll() {
		HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"findAll")).GET().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
		List<Profesor> list_profesors = null;
		try {
			list_profesors = JSONUtils.convertFromJsonToList(response.get().body(), new
					TypeReference<List<Profesor>>() {});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		response.join();
		return list_profesors;
	}
	//send request to add the product details.
	public boolean create(Profesor profesor){
		String inputJson = null;
		inputJson = JSONUtils.covertFromObjectToJson(profesor);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"create"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			if(response.get().statusCode() == 500){
				return false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//send request to update a Profesor details.
	public boolean update(Profesor profesor){
		String inputJson= null;
		inputJson = JSONUtils.covertFromObjectToJson(profesor);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"update"))
				.header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			if(response.get().statusCode() == 500){
				response.join();
				return false;
			} else {
				profesor = JSONUtils.covertFromJsonToObject(response.get().body(), Profesor.class);
				response.join();
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
	//send request to delete the profesor by its profesorname
	public boolean delete(String profesorname) {
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"delete/"+profesorname)).DELETE().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			if(response.get().statusCode() == 500) {
				response.join();
				return false;
			} else {
				Profesor profesor = JSONUtils.covertFromJsonToObject(response.get().body(), Profesor.class);
				response.join();
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
