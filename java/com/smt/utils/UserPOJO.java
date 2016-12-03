/**
 * Description : User POJO
 * @author Sauveer Pandey
 * Date : 12 August 2016
 * 
 */
package com.smt.utils;

public class UserPOJO {
	private String id;
	private String lastName;
	private String email;
	private String userName;
	private String role;
	private String firstName;
	private String password;
	
	//Fields for admin config details
	private String smtpPort;
	private String smtpHost;
	private String outlookEmail;
	private String outlookPassword;
	
	//Fields for reserveDevice
	private String user;
	private String selectedDeviceUdid;
	
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSelectedDeviceUdid() {
		return selectedDeviceUdid;
	}
	public void setSelectedDeviceUdid(String selectedDeviceUdid) {
		this.selectedDeviceUdid = selectedDeviceUdid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getOutlookEmail() {
		return outlookEmail;
	}
	public void setOutlookEmail(String outlookEmail) {
		this.outlookEmail = outlookEmail;
	}
	public String getOutlookPassword() {
		return outlookPassword;
	}
	public void setOutlookPassword(String outlookPassword) {
		this.outlookPassword = outlookPassword;
	}

}
