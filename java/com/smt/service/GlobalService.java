package com.smt.service;

import java.io.IOException;

import com.smt.domain.AdminConfigDetails;
import com.smt.utils.DevicePOJO;
import com.smt.utils.JsonObj;
import com.smt.utils.UserPOJO;

public interface GlobalService {

	JsonObj  getDevicesOnCloud(String string) throws IOException;

	JsonObj triggerDeviceMaintenance(DevicePOJO devicePOJO);

	JsonObj getAllUsers() throws IOException;

	JsonObj reserveDeviceForUser(UserPOJO userPOJO);

	JsonObj releaseDevice(DevicePOJO devicePOJO);

	JsonObj readLogFile(long pos) throws Exception;

	JsonObj downloadConsoleLogPDF();

	JsonObj getUSBChargingInfo();

	JsonObj exportToExcelDeviceInfo();

	JsonObj getDeviceDetailsForStatistics() throws IOException;

	JsonObj sendBroadcastMail(DevicePOJO devicePOJO);

	JsonObj saveAdminConfigDetails(UserPOJO userPOJO);

	JsonObj validateMailConfigData(UserPOJO userPOJO);

	AdminConfigDetails getMailConfigData(UserPOJO userPOJO);


}
