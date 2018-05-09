package myApp;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.util.Base64;

import utils.ConnectionHelper;

public class RegistrationDAO {

	public UsersTable registerUser(UsersTable user) throws NoSuchAlgorithmException {
		Connection c = null;
		PreparedStatement ps = null;
	    try {
	        c = ConnectionHelper.getConnection();
	        ps= c.prepareStatement("INSERT INTO usersTable (clientId, clientSK) VALUES (?,?)");
	        ps.setString(1, user.getClientId());
	        ps.setString(2, generateKey());
	        ps.executeUpdate();

	    }  catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return user;
	}
	
	private String generateKey() throws NoSuchAlgorithmException {
	      KeyGenerator kg = KeyGenerator.getInstance("HmacSHA256");
	      SecretKey sk = kg.generateKey();
	      String encodedKey = Base64.getEncoder().encodeToString(sk.getEncoded());      
	      return encodedKey;
	}
	
	
}
