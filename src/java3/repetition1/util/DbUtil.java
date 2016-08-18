package java3.repetition1.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Diese Klasse sorgt für die Verbindungsaufbau mit dem Datenbank.  
 * Die Klasse enthält hauptsächlich Methoden die mehrfach verwendet werden.
 * 
 * @author Parz
 * @version 1.0
 * 
 */
public class DbUtil {

	/**
	 * Diese Methode baut eine neue Verbindung zu Datenbank auf und liefert die Verbindung zurueck.
	 * 
	 * @param dataSourceName
	 * 		  Name der Datenquelle
	 * @return eine neue Datenbankverbindung
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static Connection getConnection(String dataSourceName)
			throws SQLException, NamingException {
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) context.lookup(dataSourceName);

		System.out.println("Connecting to database...");

		return ds.getConnection();
	}
	
	/**
	 * Diese Methode schliesst alle Datenbank Resourcen
	 * 
	 * @param con
	 *            zu schliessende Datenbank Connection
	 * @param stmt
	 *            zu schliessendes SQL Statement
	 * @param rs
	 *            zu schliessendes Result Set
	 */
	public static void close(Connection con, Statement stmt, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException se1) {
				se1.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException se3) {
				se3.printStackTrace();
			}
		}
	}

}
