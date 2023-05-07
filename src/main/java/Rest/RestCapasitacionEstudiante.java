
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
public class RestCapasitacionEstudiante {
	private static final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	private static final String serviceURL = "http://localhost:8081/CapasitacionEstudiante/";
	public CapasitacionEstudiante findById(int id) throws Exception{
		CapasitacionEstudiante capasitacionestudiante = null;
		HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"find/"+id)).GET().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
		try {
			//pq por encima de este numero es una peticion incorrecta
			if(response.get().statusCode() > 299){
				response.join();
				return null;
			}else {
				try {
					capasitacionestudiante = JSONUtils.covertFromJsonToObject(response.get().body(), CapasitacionEstudiante.class);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				response.join();
				return capasitacionestudiante;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return capasitacionestudiante;
	}
	//sending request to retrieve all capasitacionestudiantes available.
	public List<CapasitacionEstudiante> findAll() throws Exception{
		HttpRequest req = HttpRequest.newBuilder(URI.create(serviceURL+"findAll")).GET().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(req, HttpResponse.BodyHandlers.ofString());
		List<CapasitacionEstudiante> list_capasitacionestudiantes = null;
		try {
			list_capasitacionestudiantes = JSONUtils.convertFromJsonToList(response.get().body(), new
					TypeReference<List<CapasitacionEstudiante>>() {});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		response.join();
		return list_capasitacionestudiantes;
	}
	//send request to add the product details.
	public boolean create(CapasitacionEstudiante capasitacionestudiante)throws Exception{
		String inputJson = null;
		inputJson = JSONUtils.covertFromObjectToJson(capasitacionestudiante);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"create"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			//pq por encima de este numero es una peticion incorrecta
			if(response.get().statusCode() > 299){
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
	//send request to update a CapasitacionEstudiante details.
	public boolean update(CapasitacionEstudiante capasitacionestudiante)throws Exception{
		String inputJson= null;
		inputJson = JSONUtils.covertFromObjectToJson(capasitacionestudiante);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"update"))
				.header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(inputJson)).build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			//pq por encima de este numero es una peticion incorrecta
			if(response.get().statusCode() > 299){
				response.join();
				return false;
			} else {
				capasitacionestudiante = JSONUtils.covertFromJsonToObject(response.get().body(), CapasitacionEstudiante.class);
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
	//send request to delete the capasitacionestudiante by its capasitacionestudiantename
	public boolean delete(Integer id)throws Exception {
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"delete/"+id)).DELETE().build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		try {
			//pq por encima de este numero es una peticion incorrecta
			if(response.get().statusCode() > 299){
				response.join();
				return false;
			} else {
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
	public CapasitacionEstudiante createAndGet(CapasitacionEstudiante capasitacionestudiante)throws Exception{
		String inputJson = null;
		inputJson = JSONUtils.covertFromObjectToJson(capasitacionestudiante);
		HttpRequest request = HttpRequest.newBuilder(URI.create(serviceURL+"create"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(inputJson)).build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
		capasitacionestudiante=null;
		try {
			//pq por encima de este numero es una peticion incorrecta
			if(response.get().statusCode() > 299){
				response.join();
				return null;
			}else {
				try {
					capasitacionestudiante = JSONUtils.covertFromJsonToObject(response.get().body(), CapasitacionEstudiante.class);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				response.join();
				return capasitacionestudiante;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return capasitacionestudiante;
	}
}
