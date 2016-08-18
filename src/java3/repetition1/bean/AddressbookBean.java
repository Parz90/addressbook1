package java3.repetition1.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java3.repetition1.business.Addressbook;
import java3.repetition1.business.AddressbookDao;

@ManagedBean(name = "addressbookBean")
@SessionScoped

/**
 * 
 * Managed Bean Klasse mit allen relevanten Businesslogik.
 * 
 * @author Parz
 * @version 1.0
 *
 */
public class AddressbookBean {

	private String firstName;
	private String lastName;
	private String tel1;
	private String tel2;
	private String email;
	private String website;
	private String address1;
	private String address2;
	private String address3;
	private String besonderes1;
	private String besonderes2;
	private String besonderes3;
	private String besonderes4;
	private String pic;
	private String savedFName;
	private String savedLName;

	private List<Addressbook> addressbooks = new ArrayList<Addressbook>();

	private Addressbook addressbook = new Addressbook();

	/**
	 * Kontruktor mit Update Methode um alle Parkhaus Infos zu aktualisieren.
	 */
	public AddressbookBean() {
		// initialization of the customer list
		updateParkingInfo();
	}

	public List<Addressbook> getAddressbooks() {
		updateParkingInfo();
		return addressbooks;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getBesonderes1() {
		return besonderes1;
	}

	public void setBesonderes1(String besonderes1) {
		this.besonderes1 = besonderes1;
	}

	public String getBesonderes2() {
		return besonderes2;
	}

	public void setBesonderes2(String besonderes2) {
		this.besonderes2 = besonderes2;
	}

	public String getBesonderes3() {
		return besonderes3;
	}

	public void setBesonderes3(String besonderes3) {
		this.besonderes3 = besonderes3;
	}

	public String getBesonderes4() {
		return besonderes4;
	}

	public void setBesonderes4(String besonderes4) {
		this.besonderes4 = besonderes4;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSavedFName() {
		return savedFName;
	}

	public void setSavedFName(String savedFName) {
		this.savedFName = savedFName;
	}

	public String getSavedLName() {
		return savedLName;
	}

	public void setSavedLName(String savedLName) {
		this.savedLName = savedLName;
	}

	public Addressbook getAddressbook() {
		return addressbook;
	}

	public void setAddressbook(Addressbook addressbook) {
		this.addressbook = addressbook;
	}

	public void setParkingBlock(List<Addressbook> addressbooks) {
		this.addressbooks = addressbooks;
	}

	// Alle public Methoden dass von xhtml benötigt werden.

	/**
	 * 
	 * Methode um anhand Name in die Detailansicht des ausgewählten Parkhaues
	 * zugelangen.
	 * 
	 * @return String von des aktualisierten ParkingBlock Details Seite.
	 */
	public String show(String name) {
		updateParkingInfo();
		findParkingByName(name);
		updateEditPark();
		return "parkingBlockDetails.xhtml";
	}

	/**
	 * 
	 * Methode um anhand Name in die Editieransicht des ausgewählten Parkhaues
	 * zugelangen.
	 * 
	 * @return String von des aktualisierten ParkingBlock Edit Seite.
	 */
	public String edit(String fname, String lname) {
		updateParkingInfo();
		findParkingByName(fname);
		savedFName = fname;
		savedFName = lname;
		updateEditPark();
		return "parkingBlockEdit.xhtml";
	}

	/**
	 * 
	 * Methode um die Eingaben des editierten Parkhauses zu speichern und zurück
	 * in die Overview Seite zu gelangen.
	 * 
	 * @return String von des aktualisierten Overview Seite.
	 */
	public String save() {
		AddressbookDao dao = new AddressbookDao();
		insertEditPark();
		dao.insertAddress(savedFName, addressbook);
		return adminOverview();
	}

	/**
	 * 
	 * Methode um ohne die Eingaben des editierten Parkhauses zuberücksichtigen
	 * in die Overview Seite zu gelangen.
	 * 
	 * @return String von des aktualisierten Overview Seite.
	 */
	public String cancel() {
		AddressbookDao dao = new AddressbookDao();
		updateEditPark();
		addressbooks = dao.getAllAddress();
		return adminOverview();
	}

	/**
	 * Methode Um RSS Feed Infos zu aktualisieren und Overview aktualisiert
	 * darzustellen.
	 * 
	 * @return String von des aktualisierten Overview Seite.
	 */
	public String overview() {
		updateParkingInfo();
		return "overview.xhtml";
	}

	/**
	 * Methode Um RSS Feed Infos zu aktualisieren und Overview aktualisiert
	 * darzustellen.
	 * 
	 * @return String von des aktualisierten AdminOverview Seite.
	 */
	public String adminOverview() {
		updateParkingInfo();
		return "adminOverview.xhtml";
	}

	// Alle private Methoden mit Businesslogik.
	private void updateParkingInfo() {
		AddressbookDao dao = new AddressbookDao();
		addressbooks = dao.getAllAddress();
	}

	private void findParkingByName(String parkName) {
		AddressbookDao dao = new AddressbookDao();
		addressbook = dao.findAddressByName(parkName);
	}

	private void updateEditPark() {
		firstName = addressbook.getFirstName();
		lastName = addressbook.getLastName();
		tel1 = addressbook.getTel1();
		tel2 = addressbook.getTel2();
		email = addressbook.getEmail();
		website = addressbook.getWebsite();
		address1 = addressbook.getAddress1();
		address2 = addressbook.getAddress2();
		address3 = addressbook.getAddress3();
		besonderes1 = addressbook.getBesonderes1();
		besonderes2 = addressbook.getBesonderes2();
		besonderes3 = addressbook.getBesonderes3();
		besonderes4 = addressbook.getBesonderes4();
	}

	private void insertEditPark() {
		addressbook.setFirstName(firstName);
		addressbook.setLastName(lastName);
		addressbook.setTel1(tel1);
		addressbook.setTel2(tel2);
		addressbook.setEmail(email);
		addressbook.setWebsite(website);
		addressbook.setAddress1(address1);
		addressbook.setAddress2(address2);
		addressbook.setAddress3(address3);
		addressbook.setBesonderes1(besonderes1);
		addressbook.setBesonderes2(besonderes2);
		addressbook.setBesonderes3(besonderes3);
		addressbook.setBesonderes4(besonderes4);
	}

}
