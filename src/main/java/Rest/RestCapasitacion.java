
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
public class RestCapasitacion {
	private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	private static final String serviceURL = "http://localhost:8081/Capasitacion/";
	//sending request to retrieve all capasitacions available.
	public List<Capasitacion> findAll() {
		HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"findAll")).GET().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
		List<Capasitacion> list_capasitacions = null;
		try {
			list_capasitacions = JSONUtils.convertFromJsonToList(response.get().body(), new
					TypeReference<List<Capasitacion>>() {});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		response.join();
		return list_capasitacions;
	}
	//send request to add the product details.
	public boolean create(Capasitacion capasitacion){
		String inputJson = null;
		inputJson = JSONUtils.covertFromObjectToJson(capasitacion);
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
	//send request to update a Capasitacion details.
	public boolean update(Capasitacion capasitacion){
		String inputJson= null;
		inputJson = JSONUtils.covertFromObjectToJson(capasitacion);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"update"))
				.header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			if(response.get().statusCode() == 500){
				response.join();
				return false;
			} else {
				capasitacion = JSONUtils.covertFromJsonToObject(response.get().body(), Capasitacion.class);
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
	//send request to delete the capasitacion by its capasitacionname
	public boolean delete(String capasitacionname) {
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"delete/"+capasitacionname)).DELETE().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			if(response.get().statusCode() == 500) {
				response.join();
				return false;
			} else {
				Capasitacion capasitacion = JSONUtils.covertFromJsonToObject(response.get().body(), Capasitacion.class);
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
