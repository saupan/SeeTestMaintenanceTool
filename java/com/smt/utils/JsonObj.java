/**
 * Description : JSON object for request and response
 * @author Sauveer Pandey
 * Date : 17 June 2016
 * 
 */
package com.smt.utils;

import java.util.List;
import java.util.Map;

public class JsonObj {

	private List<DevicePOJO> cloudAndroidDeviceList;
	private List<UserPOJO> userDataList;
	private String messages;
	private int avlCount;
	private long filePos;
	private int reserveCount;
	private List<List<Map<String,String>>> dataForDeviceStatistics;
	
	public int getAvlCount() {
		return avlCount;
	}

	public void setAvlCount(int avlCount) {
		this.avlCount = avlCount;
	}

	public int getReserveCount() {
		return reserveCount;
	}

	public void setReserveCount(int reserveCount) {
		this.reserveCount = reserveCount;
	}

	/**
	 * @return the dataList
	 */
	public List<DevicePOJO> getDeviceDataList() {
		return cloudAndroidDeviceList;
	}

	/**
	 * @param cloudAndroidDeviceList the dataList to set
	 */
	public void setDeviceDataList(List<DevicePOJO> cloudAndroidDeviceList) {
		this.cloudAndroidDeviceList = cloudAndroidDeviceList;
	}

	public List<UserPOJO> getUserDataList() {
		return userDataList;
	}

	public void setUserDataList(List<UserPOJO> userDataList) {
		this.userDataList = userDataList;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public long getFilePos() {
		return filePos;
	}

	public void setFilePos(long filePos) {
		this.filePos = filePos;
	}

	public List<List<Map<String,String>>> getDataForDeviceStatistics() {
		return dataForDeviceStatistics;
	}

	public void setDataForDeviceStatistics(List<List<Map<String,String>>> dataForDeviceStatistics) {
		this.dataForDeviceStatistics = dataForDeviceStatistics;
	}

}
