package myApp;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/registration")
public class RegistrationResource {
	
	RegistrationDAO registrationDao = new RegistrationDAO();
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void regiterUser ( @FormParam("clientId") String clientId, @Context HttpServletResponse servletResponse) throws IOException, NoSuchAlgorithmException {
		UsersTable user = new UsersTable();
		user.setClientId(clientId);
		registrationDao.registerUser(user);
		(servletResponse).sendRedirect("http://localhost:8080/cosmeticsShop/welcome.html");
		
	}
	
}
