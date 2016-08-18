package java3.repetition1.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 * Klasse mit allen nötigen Methoden um die Verbindung zu Datenbank zu
 * gewährleisten.
 * 
 * @author Parz
 * @version 1.0
 *
 */
public class AddressbookDao {

	static final String DATA_SOURCE_NAME = "jdbc/simplePool";
	static final String SQL_SELECT_ALL_PARKING = "SELECT id, vorname, nachname, telefonnummer_1, telefonnummer_2, e_mail, website, adresse_1, adresse_2, adresse_3, besonderes_1, besonderes_2, besonderes_3, besonderes_4 FROM addressbook";
	static final String SQL_INSERT_PARKING = "";

	// Alle public Methoden

	/**
	 * 
	 * Methode liest alle Informationen zu Parkhaus von Datenbank aus.
	 * 
	 * @return ParkingBlockliste mit den aktuellen Datenbankinhalte.
	 */
	public List<Addressbook> getAllAddress() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Addressbook> addressList = new ArrayList<Addressbook>();

		try {
			con = getConnection(DATA_SOURCE_NAME);
			System.out.println("connected");
			stmt = con.createStatement();

			rs = stmt.executeQuery(SQL_SELECT_ALL_PARKING);

			while (rs.next()) {
				addressList.add(createAddressFromResultSet(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			close(con, stmt, rs);
		}

		return addressList;
	}

	/**
	 * 
	 * Methode liest anhand Name alle Informationen zu einer bestimmten Parkhaus
	 * von Datenbank aus.
	 * 
	 * @param name
	 * @return ParkingBlock Objekt mit den aktuellen Datenbankinhalte.
	 */
	public Addressbook findAddressByName(String vorname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Addressbook address = new Addressbook();
		;

		try {
			con = getConnection(DATA_SOURCE_NAME);

			String sql = "SELECT id, vorname, nachhname, telefonnummer_1, telefonnummer_2, e_mail, website, adresse_1, adresse_2, adresse_3, besonderes_1, besonderes_2, besonderes_3, besonderes_4 FROM addressbook  WHERE vorname = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vorname);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				address = createAddressFromResultSet(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}

		return address;
	}

	/**
	 * 
	 * Methode schreibt anhand Name alle Informationen zu einer bestimmten
	 * Parkhaus in den Datenbank.
	 * 
	 * @param name
	 *            des Parkhauses
	 * @param parking
	 *            Objekt
	 */
	public void insertAddress(String name, Addressbook address) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection(DATA_SOURCE_NAME);
			String sql = "UPDATE parkhaus SET vorname= ? , nachname= ? , telefonnummer_1= ? , telefonnummer_2= ? , e_mail= ? , website= ? , adresse_1= ? , adresse_2= ? , adresse_3= ? , besonderes_1= ? , besonderes_2= ? , besonderes_3= ? , besonderes_4= ?  WHERE vorname= ? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, address.getFirstName());
			pstmt.setString(2, address.getLastName());
			pstmt.setString(3, address.getTel1());
			pstmt.setString(4, address.getTel2());
			pstmt.setString(5, address.getEmail());
			pstmt.setString(6, address.getWebsite());
			pstmt.setString(7, address.getAddress1());
			pstmt.setString(8, address.getAddress2());
			pstmt.setString(9, address.getAddress3());
			pstmt.setString(10, address.getBesonderes1());
			pstmt.setString(11, address.getBesonderes2());
			pstmt.setString(12, address.getBesonderes3());
			pstmt.setString(13, address.getBesonderes4());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, null);
		}
	}

	// Alle private Methoden
	private Addressbook createAddressFromResultSet(ResultSet rs) throws SQLException {
		Addressbook address = new Addressbook();
		address.setId(rs.getInt("id"));
		address.setFirstName(rs.getString("vorname"));
		address.setLastName(rs.getString("nachname"));
		address.setTel1(rs.getString("telefonnummer_1"));
		address.setTel2(rs.getString("telefonnummer_2"));
		address.setEmail(rs.getString("e_mail"));
		address.setWebsite(rs.getString("website"));
		address.setAddress1(rs.getString("adresse_1"));
		address.setAddress2(rs.getString("adresse_2"));
		address.setAddress3(rs.getString("adresse_3"));
		address.setBesonderes1(rs.getString("besonderes_1"));
		address.setBesonderes2(rs.getString("besonderes_2"));
		address.setBesonderes3(rs.getString("besonderes_3"));
		address.setBesonderes4(rs.getString("besonderes_4"));

		return address;
	}

	private static Connection getConnection(String dataSourceName) throws SQLException, NamingException {
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) context.lookup(dataSourceName);

		System.out.println("Connecting to database...");

		return ds.getConnection();
	}

	private static void close(Connection con, Statement stmt, ResultSet rs) {

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
