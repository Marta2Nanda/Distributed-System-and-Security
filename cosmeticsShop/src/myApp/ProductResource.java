package myApp;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import utils.ConnectionHelper;

@Path("/products")
public class ProductResource {
	static ProductDAO productDAO;

	
	public static String getSecretKey(String clientId) {
		Connection c = null;
		String sql = "SELECT clientSK FROM usersTable WHERE clientId='"+clientId+"'";
		String clientSK = null;
	    try {
	        c = ConnectionHelper.getConnection();
	        Statement s = c.createStatement();
	        ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
            	clientSK=rs.getString("clientSK");
            }
            return clientSK;
	    }  catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
	}

	private static boolean calculateHmac(String clientID, String authentication) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] decodedKey = Base64.getDecoder().decode(getSecretKey(clientID));
		SecretKey sk = new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(sk);
		byte[] messageFromServer = mac.doFinal("Success".getBytes());
		byte[] messagePassed = Base64.getDecoder().decode(authentication);
		boolean isCorrect = false;
		if(Arrays.equals(messageFromServer, messagePassed)) {
			isCorrect = true;
		} else {
			isCorrect = false;
		}
		return isCorrect;
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Product> getProduct(@HeaderParam("clientId") String clientID, @HeaderParam("authentication") String authentication, @Context HttpServletResponse servletResponse ) throws IOException, InvalidKeyException, NoSuchAlgorithmException{
		if(!calculateHmac(clientID, authentication)) {
			return null;
		}else {
			return ProductDAO.instance.getProducts();

		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
	@Path("/id/{productId}")
	public Product getProduct(@PathParam("productId") String id, @HeaderParam("clientId") String clientID, @HeaderParam("authentication") String authentication, @Context HttpServletResponse servletResponse ) throws IOException, InvalidKeyException, NoSuchAlgorithmException{
		if(!calculateHmac(clientID, authentication)) {
			System.out.println("in error");
			return null;
		}else {
			return ProductDAO.instance.getProduct(Integer.parseInt(id));
		
		}
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postProduct(@FormParam("id") String id, @FormParam("name") String name,
			@FormParam("manufacturer") String manufacturer, @FormParam("capacity") String capacity, @HeaderParam ("clientId") String clientID,
			@Context HttpServletResponse servletResponse, @HeaderParam("authentication") String authentication)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException {

		if(!calculateHmac(clientID, authentication)) {
			return;
		} else {
			Product product = new Product();
			product.setClientID(clientID);
			product.setId(Integer.parseInt(id));
			product.setName(name);
			product.setManufacturer(manufacturer);
			product.setCapacity(capacity);
			ProductDAO.instance.create(product);
		}
		
	}

	@DELETE
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/delete/{productId}")
	public void deleteProduct(@PathParam("productId") String id, @HeaderParam ("clientId") String clientID, @HeaderParam("authentication") String authentication, @Context HttpServletResponse servletResponse) throws IOException, InvalidKeyException, NumberFormatException, NoSuchAlgorithmException {
		if(!calculateHmac(clientID, authentication)) {
			return;
		} else {
		ProductDAO.instance.delete(Integer.parseInt(id), clientID);
		}
	}
}
