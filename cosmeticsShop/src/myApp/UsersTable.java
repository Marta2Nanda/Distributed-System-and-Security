package myApp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usersTable")
public class UsersTable {
	
	private String clientId;
	private String clientSK;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSK() {
		return clientSK;
	}
	public void setClientSK(String clientSK) {
		this.clientSK = clientSK;
	}
	
	
}
