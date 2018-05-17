package client;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

public class ClientOne {

	static Scanner scanner = new Scanner(System.in);
	static ClientConfig config = new DefaultClientConfig();
	static Client client = Client.create(config);
	static WebResource service = client.resource(getBaseURI());
	static String message;
	static final String CORRECT_CLIENT_ID= "Marta";
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {

		String authenticationKey = args[0];
		byte[] decodedKey = Base64.getDecoder().decode(authenticationKey);
		SecretKey sk = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(sk);
		byte[] messageByteArr = mac.doFinal("Success".getBytes());
		message = Base64.getEncoder().encodeToString(messageByteArr);

		String userContinue = "y";
		while (userContinue.equalsIgnoreCase("y")) {
			switch (userChoice()) {
			case 1:
				addProduct();
				break;
			case 2:
				deleteProduct();
				break;
			case 3:
				getAllProducts();
				break;
			case 4:
				getProductById();
				break;
			case 5:
				System.out.println("Exiting...");
				userContinue = "n";
				break;
			}
		}
	}

	private static void getProductById() {
		System.out.println("Enter your client id");
		String clientId = scanner.next();
		if (clientId.equals(CORRECT_CLIENT_ID)) {
			System.out.println("Enter id you wish to view: ");
			String id = scanner.next();
			System.out.println(service.path("rest").path("products/id/" + id).header("clientId", clientId)
					.header("authentication", message).accept(MediaType.APPLICATION_XML).get(String.class));
		} else {
			System.out.println("Error by authentication occured!");
		}
	}

	private static void getAllProducts() {
		System.out.println("Enter your client id");
		String clientId = scanner.next();
		if (clientId.equals(CORRECT_CLIENT_ID)) {
			System.out.println(service.path("rest").path("products").header("clientId", clientId)
					.header("authentication", message).accept(MediaType.TEXT_XML).get(String.class));
		} else {
			System.out.println("Error by authentication occured!");
		}

	}

	private static void deleteProduct() {
		System.out.println("Enter your client id");
		String clientId = scanner.next();
		if (clientId.equals(CORRECT_CLIENT_ID)) {
			System.out.println("Enter id to delete: ");
			String id = scanner.next();
			service.path("rest").path("products/delete/" + id).header("clientId", clientId)
					.header("authentication", message).delete();
			System.out.println("Product sucessfully deleted!");
		} else {
			System.out.println("Error by authentication occured!");
		}
	}

	private static void addProduct() {
		System.out.println("Enter your client id");
		String clientId = scanner.next();
		if (clientId.equals(CORRECT_CLIENT_ID)) {
			Form form = new Form();
			System.out.println("Enter id: ");
			int id = scanner.nextInt();
			System.out.println("Enter name: ");
			String name = scanner.next();
			System.out.println("Enter manufacturer: ");
			String manufacturer = scanner.next();
			System.out.println("Enter capacity: ");
			String capacity = scanner.next();
			form.add("id", id);
			form.add("name", name);
			form.add("manufacturer", manufacturer);
			form.add("capacity", capacity);

			service.path("rest").path("products").header("clientId", clientId).header("authentication", message)
					.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, form);
			System.out.println("Thank you your product is added!");
		} else {
			System.out.println("Error by authentication occured!");
		}
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/cosmeticsShop").build();
	}
	
    public static int userChoice() {
		System.out.println("Please choose option: ");
		System.out.println("1: Add product");
		System.out.println("2: delete product");
		System.out.println("3. get all products");
		System.out.println("4. get product by product Id");
		System.out.println("5. exit");
        return scanner.nextInt();
    }
}
