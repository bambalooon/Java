package Communication;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DB {
  private Connection conn = null;
  private Statement stmt = null;
  private ResultSet rs = null;
  private String url;
  private String dbName;
  private String username;
  private String password; 
  
  
  public DB(String url, String dbName, String username, String password) {
	  this.url = url;
	  this.dbName = dbName;
	  this.username = username;
	  this.password = password; 
	  connect();
  }
  public void connect(){
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      conn = 
        DriverManager.getConnection("jdbc:mysql://"+url+"/"+dbName,
                                    username, password);
 
    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }catch(Exception e){e.printStackTrace();}	
  }
  public long addUser(String username, String passwd) throws RegisterException {
	try {
		stmt = conn.createStatement();
		String query = "INSERT INTO users (username, password) "
	            + "values ('"+username+"', '"+passwd+"')";
		stmt.executeUpdate(query);
	}
    catch(SQLException ex){
    	System.out.print(ex.getMessage());
        // handle any errors	 
    }finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException sqlEx) { } // ignore
 
        stmt = null;
      }
    }
	try {
      stmt = conn.createStatement();
 
      rs = stmt.executeQuery("SELECT max(id) FROM users");
      if(!rs.next()){
    	  throw new RegisterException();
      }
      long id = rs.getLong(1);
	  return id;
    }catch (SQLException ex){
      // handle any errors
    	throw new RegisterException();
    } catch(RegisterException ex) {
    	throw ex;
    }
	finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException sqlEx) { } // ignore
        rs = null;
      }
 
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException sqlEx) { } // ignore
 
        stmt = null;
      }
    }
  }
  public boolean login(long id, String passwd) throws LoginException {
	    try {
	      stmt = conn.createStatement();
	 
	      rs = stmt.executeQuery("SELECT * FROM users WHERE id="+id+" AND password='"+passwd+"'");
	      if(rs.next()){
	    	  return true;
	      }
	      return false;
	    }catch (SQLException ex){
	      // handle any errors
	    	throw new LoginException();
	    }
		finally {
	      if (rs != null) {
	        try {
	          rs.close();
	        } catch (SQLException sqlEx) { } // ignore
	        rs = null;
	      }
	 
	      if (stmt != null) {
	        try {
	          stmt.close();
	        } catch (SQLException sqlEx) { } // ignore
	 
	        stmt = null;
	      }
	    }
  }
  public Map<Long,String> search(String nick) {
	    Map<Long, String> result = new HashMap<Long, String>();
	    try {
	      stmt = conn.createStatement();
	 
	      rs = stmt.executeQuery("SELECT * FROM users WHERE username LIKE '%"+nick+"%'");
	      
	      while(rs.next()){
	    	  long id = rs.getLong(1);
	    	  String nick2 = rs.getString(2);
	    	  result.put(id, nick2);
	      }
	    }catch (SQLException ex){
	      // handle any errors
	    }
		finally {
	      if (rs != null) {
	        try {
	          rs.close();
	        } catch (SQLException sqlEx) { } // ignore
	        rs = null;
	      }
	 
	      if (stmt != null) {
	        try {
	          stmt.close();
	        } catch (SQLException sqlEx) { } // ignore
	 
	        stmt = null;
	      }
	    }
	    return result;
  }
}
