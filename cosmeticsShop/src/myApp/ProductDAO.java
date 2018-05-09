package myApp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import utils.ConnectionHelper;

public enum ProductDAO {
	instance;

	public List<Product> getProducts() {
		List<Product> products = new ArrayList<>();
		Connection c = null;
		String sql = "SELECT * FROM product";
	    try {
	        c = ConnectionHelper.getConnection();
	        Statement s = c.createStatement();
	        ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
            	products.add(processRow(rs));
            }

	    }  catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
	    return products;
	}

	public Product getProduct(int id) {
		Product product = null;
		Connection c = null;
		String sql = "SELECT * FROM product WHERE id='"+id+"'";
	    try {
	        c = ConnectionHelper.getConnection();
	        Statement s = c.createStatement();
	        ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
            	product=processRow(rs);
            }

	    }  catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
	    return product;
	}

	public void create(Product product) {
		product.setStatus("added/updated");
		Connection c = null;
		PreparedStatement ps = null;
	    try {
	        c = ConnectionHelper.getConnection();
	        ps= c.prepareStatement("INSERT INTO product (clientID, id, name, manufacturer, capacity, status, date) VALUES (?,?,?,?,?,?,?)");
	        ps.setString(1, product.getClientID());
	        ps.setInt(2, product.getId());
	        ps.setString(3, product.getName());
	        ps.setString(4, product.getManufacturer());
	        ps.setString(5, product.getCapacity());
	        ps.setString(6, product.getStatus());
	        ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
	        ps.executeUpdate();

	    }  catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
	}

	public void delete(int id, String clientID) {
		Product product = new Product();
		product.setStatus("deleted");
		Connection c = null;
		PreparedStatement ps = null;
	    try {
	        c = ConnectionHelper.getConnection();
	        ps= c.prepareStatement("UPDATE product SET status='"+product.getStatus()+"', clientID='"+clientID+"', name='"+
	        product.getName()+"', manufacturer='"+product.getManufacturer()+ "', capacity='"+product.getCapacity()+ "', date='"+
	        new Timestamp(System.currentTimeMillis())+"' WHERE id="+id);
	        ps.executeUpdate();

	    }  catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
	}
	
    protected Product processRow(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setClientID(rs.getString("clientID"));
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setManufacturer(rs.getString("manufacturer"));
        product.setCapacity(rs.getString("capacity"));
        product.setStatus(rs.getString("status"));
        return product;
    }
}