/**
 * Description : Device POJO
 * @author Sauveer Pandey
 * Date : 17 June 2016
 * 
 */
package com.smt.utils;

public class DevicePOJO {

	private String currentUser;
	private String iosUdid;
	private String notes;
	private String msisdn;
	private String simPin;
	private String deviceOpco;
	private String osVersion;
	private String deviceOs;
	private String model;
	private String id;
	private String udid;
	private String deviceName;
	private String manufacturer;
	private String deviceCategory;
	private String statusAgeInMinutes;
	private String lastUsedDateTime;
	private String previousStatus;
	private String uptime;
	private String agentName;
	private String profiles;
	private String isCleanupEnabled;
	private String displayStatus;
	private String[] udidArray;
	private String[] selectedActivityId;
	private String agentIp;
	private String project;
	
	//USB charging related
	private String usbPowered;
	private String wirelessPowered;
	private String chargingLevel;
	private String voltage;
	private String temperature;
	private String technology;
	
	//Broadcast Mail related
	private String subject;
	private String messageContent;
	private String fromMail;
	private String passwordMail;
	private String toMail;
	
	
	
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getFromMail() {
		return fromMail;
	}
	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}
	public String getPasswordMail() {
		return passwordMail;
	}
	public void setPasswordMail(String passwordMail) {
		this.passwordMail = passwordMail;
	}
	public String getToMail() {
		return toMail;
	}
	public void setToMail(String toMail) {
		this.toMail = toMail;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getSimPin() {
		return simPin;
	}
	public void setSimPin(String simPin) {
		this.simPin = simPin;
	}
	public String[] getSelectedActivityId() {
		return selectedActivityId;
	}
	public void setSelectedActivityId(String[] selectedActivityId) {
		this.selectedActivityId = selectedActivityId;
	}
	
	public String getUsbPowered() {
		return usbPowered;
	}
	public void setUsbPowered(String usbPowered) {
		this.usbPowered = usbPowered;
	}
	public String getWirelessPowered() {
		return wirelessPowered;
	}
	public void setWirelessPowered(String wirelessPowered) {
		this.wirelessPowered = wirelessPowered;
	}
	public String getChargingLevel() {
		return chargingLevel;
	}
	public void setChargingLevel(String chargingLevel) {
		this.chargingLevel = chargingLevel;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getIosUdid() {
		return iosUdid;
	}
	public void setIosUdid(String iosUdid) {
		this.iosUdid = iosUdid;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getDeviceOs() {
		return deviceOs;
	}
	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDeviceCategory() {
		return deviceCategory;
	}
	public void setDeviceCategory(String deviceCategory) {
		this.deviceCategory = deviceCategory;
	}
	public String getStatusAgeInMinutes() {
		return statusAgeInMinutes;
	}
	public void setStatusAgeInMinutes(String statusAgeInMinutes) {
		this.statusAgeInMinutes = statusAgeInMinutes;
	}
	public String getLastUsedDateTime() {
		return lastUsedDateTime;
	}
	public void setLastUsedDateTime(String lastUsedDateTime) {
		this.lastUsedDateTime = lastUsedDateTime;
	}
	public String getPreviousStatus() {
		return previousStatus;
	}
	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getProfiles() {
		return profiles;
	}
	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}
	public String getIsCleanupEnabled() {
		return isCleanupEnabled;
	}
	public void setIsCleanupEnabled(String isCleanupEnabled) {
		this.isCleanupEnabled = isCleanupEnabled;
	}
	public String getDisplayStatus() {
		return displayStatus;
	}
	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}
	public String[] getUdidArray() {
		return udidArray;
	}
	public void setUdidArray(String[] udidArray) {
		this.udidArray = udidArray;
	}
	public String getAgentIp() {
		return agentIp;
	}
	public void setAgentIp(String agentIp) {
		this.agentIp = agentIp;
	}
	public String getDeviceOpco() {
		return deviceOpco;
	}
	public void setDeviceOpco(String deviceOpco) {
		this.deviceOpco = deviceOpco;
	}
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	
}
