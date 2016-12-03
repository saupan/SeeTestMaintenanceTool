/**
 * Description : Maintenance activity final script
 * @author Sauveer Pandey
 * Date : 13 June 2016
 * 
 */
package com.smt.serviceImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.smt.service.PerformActivityService;
import com.smt.utils.DevicePOJO;
import com.smt.utils.MaintenanceException;
import com.smt.utils.MyClient;

@Service
public class PerformActivityServiceImpl implements PerformActivityService {

	public String host = "localhost";
	public int port = 8889;
	public MyClient client = new MyClient(host,port);



	public String callPerformActivityAndroid(DevicePOJO currentDevice, String manufacturer){
		boolean pinRequired = false;
		String notesInfo = currentDevice.getNotes();
		String simPin = null;
		if(notesInfo.contains("PIN:") && (notesInfo.substring(notesInfo.indexOf("PIN:")+4, (notesInfo.indexOf("OPCO:"))).trim().length()>0)){
			int beginIndex = notesInfo.indexOf("PIN:")+4;
			simPin = notesInfo.substring(beginIndex, (notesInfo.indexOf("OPCO:"))).trim();
			pinRequired = true;
		}
		String simPinXpath = "xpath=//*[@id='simPinEntry' or @id='pinEntry' or @id='keyguard_title_message_area']";
		String okPath = "xpath=//*[@id='key_enter' or @id='key_enter_text' or @id='ok']";
		long startTime = System.nanoTime();
		Integer duration = 0;
		String deviceName = "adb:"+currentDevice.getDeviceName();

		try {
			writeLogToFile("Device picked for maintenance : "+ currentDevice.getDeviceName());
			writeLogToFile("--------------------------------------------------------------------------------------");
			//Reserve Device
			try{
				
				Thread.sleep(5000);
				client.setDevice(deviceName);
				Thread.sleep(10000);
				writeLogToFile("Device reserved");
			}catch(Exception e){
				throw new MaintenanceException("Error reserving device. The device could be offline or In use! Maintenance skipped");
			}

			//Unlock device
			try{
				if(client.waitForElement("NATIVE", simPinXpath, 0, 3000)){ //check if locked
					writeLogToFile("Device is locked! Unlocking device using SIM PIN :" + simPin);
					client.click("NATIVE", simPinXpath, 0, 1);
					client.sendText(simPin);
					client.click("NATIVE", okPath, 0, 1);
					if(client.waitForElement("NATIVE", simPinXpath, 0, 2000)){ // check if unlocked
						throw new MaintenanceException("Error : Device could not be unlocked. Maintenance skipped!");
					}
					writeLogToFile("Device unlocked");
				}	
			}catch(Exception e){
				throw new MaintenanceException("Error : Device could not be unlocked. Maintenance skipped!");
			}

			String selectedActivityStringArray = Arrays.toString(currentDevice.getSelectedActivityId());
			if(selectedActivityStringArray.contains("1")){
				clearDeviceCache(deviceName, manufacturer);
			}
			if(selectedActivityStringArray.contains("2")){
				rebootDevice(deviceName, pinRequired, simPinXpath, simPin, okPath);
			}
			if(selectedActivityStringArray.contains("3")){
				manageBrightnessLevel(deviceName, currentDevice.getDeviceOs());
			}
		} catch (MaintenanceException e) {
			try {
				writeLogToFile(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return "ERROR";
		} catch (IOException e) {
			return "ERROR";
		} finally{
			//Release device
			duration = testDuration(startTime);
			client.releaseDevice(deviceName, true, true, true);
			try{
				writeLogToFile("Device released to cloud");
				writeLogToFile("Total execution time(ms) : " + duration + " ms.");
			} catch(Exception e){
				e.printStackTrace();
				return "ERROR";
			}
			System.out.println("########### Device released to cloud ###########");
		}
		return "SUCCESS";

	}

	public String callPerformActivityIOS(DevicePOJO currentDevice, String manufacturer){
		boolean pinRequired = false;
		String notesInfo = currentDevice.getNotes();
		String simPin = null;
		if(notesInfo.contains("PIN:") && (notesInfo.substring(notesInfo.indexOf("PIN:")+4, (notesInfo.indexOf("OPCO:"))).trim().length()>0)){
			int beginIndex = notesInfo.indexOf("PIN:")+4;
			simPin = notesInfo.substring(beginIndex, (notesInfo.indexOf("OPCO:"))).trim();
			pinRequired = true;
		}
		long startTime = System.nanoTime();
		Integer duration = 0;
		String deviceName = "ios_app:"+currentDevice.getDeviceName();

		try {
			writeLogToFile("Device picked for maintenance : "+ currentDevice.getDeviceName());
			writeLogToFile("--------------------------------------------------------------------------------------");

			//Reserve Device
			try{
				Thread.sleep(5000);
				client.setDevice(deviceName);
				Thread.sleep(10000);
				writeLogToFile("Device reserved");
			}catch(Exception e){
				throw new MaintenanceException("Error reserving device. The device could be offline or In use! Maintenance skipped");
			}

			//Unlock device
			try{
				if(client.isElementFound("NATIVE", "xpath=//*[@text='Unlock']"))
				{
					writeLogToFile("Device is locked! Unlocking device using SIM PIN :" + simPin);
					client.click("NATIVE","xpath=//*[@text='Unlock']",0, 0);
					char[] s = simPin.toCharArray();
					for(char c:s)
					{
						if(client.isElementFound("NATIVE", "xpath=//*[@text='"+c+"']"));
						client.click("NATIVE","xpath=//*[@text='"+c+"']",0, 0);
					}
					if(client.elementGetProperty("NATIVE", "//*[@text='Passcode field']", 0, "value").contains("4 values"))
						client.click("NATIVE","xpath=//*[@text='OK']",0, 0);
					client.sleep(3000);
					if(client.waitForElement("NATIVE", "xpath=//*[@text='Unlock']", 0, 2000)){ // check if unlocked
						throw new MaintenanceException("Error : Device could not be unlocked. Maintenance skipped!");
					}
					writeLogToFile("Device unlocked");
				}
			}catch(Exception e){
				throw new MaintenanceException("Error : Device could not be unlocked. Maintenance skipped!");
			}

			String selectedActivityStringArray = Arrays.toString(currentDevice.getSelectedActivityId());

			if(selectedActivityStringArray.contains("2")){
				rebootDeviceIOS(deviceName, pinRequired,simPin);
			}

		} catch (MaintenanceException e) {
			try {
				writeLogToFile(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return "ERROR";
		} catch (IOException e) {
			return "ERROR";
		} finally{
			//Release device
			duration = testDuration(startTime);
			client.releaseDevice(deviceName, true, true, true);
			try{
				writeLogToFile("Device released to cloud");
				writeLogToFile("Total execution time(ms) : " + duration + " ms.");
			} catch(Exception e){
				e.printStackTrace();
				return "ERROR";
			}
			System.out.println("########### Device released to cloud ###########");
		}
		return "SUCCESS";

	}

	private void rebootDeviceIOS(String deviceName, boolean pinRequired, String simPin) throws MaintenanceException {

		try{
			System.out.println("########### Rebooting device.. ###########");
			writeLogToFile("Rebooting device..");
			client.reboot(120000);
			boolean isAvailiable = false;
			int count = 0;
			Thread.sleep(5000);
			writeLogToFile("Waiting for device to be availiable....");
			while(!isAvailiable){
				isAvailiable = isDeviceAvailable(client,deviceName);
				Thread.sleep(10000);
				count ++;
				if(count == 20)
					break;
			}

			if(isAvailiable){
				Thread.sleep(10000);
				System.out.println("Device is availiable!");
				writeLogToFile("Device is availiable!");
				client.setDevice(deviceName);
				//Enter SIM PIN if required
				if(pinRequired){
					System.out.println("########### SIM PIN required ###########");
					writeLogToFile("SIM PIN required for the device");
					if(client.isElementFound("NATIVE", "xpath=//*[@text='Unlock']"))
					{
						client.click("NATIVE","xpath=//*[@text='Unlock']",0, 0);
						char[] s = simPin.toCharArray();
						for(char c:s)
						{
							if(client.isElementFound("NATIVE", "xpath=//*[@text='"+c+"']"));
							client.click("NATIVE","xpath=//*[@text='"+c+"']",0, 0);
						}
						if(client.elementGetProperty("NATIVE", "//*[@text='Passcode field']", 0, "value").contains("4 values"))
							client.click("NATIVE","xpath=//*[@text='OK']",0, 0);
						client.sleep(3000);
						if(client.waitForElement("NATIVE", "xpath=//*[@text='Unlock']", 0, 2000)){ // check if unlocked
							throw new MaintenanceException("Error occurred while SIM PIN entry!!"+ simPin);
						}
						writeLogToFile("SIM PIN entered successfully : "+ simPin);
					}else{
						throw new MaintenanceException("Error occurred while SIM PIN entry!!"+ simPin);
					}
				}else{
					writeLogToFile("SIM PIN not required for this device.");
				}
			}else{
				throw new MaintenanceException("Device is taking too much time to reboot! Maintenance activity skipped for this device!!");
			}
		}catch(Exception e){
			throw new MaintenanceException("Error occurred while rebooting! Maintenance activity skipped for this device!!");
		}



	}

	public void manageBrightnessLevel(String deviceName, String deviceOS) throws IOException, MaintenanceException{
		try{
			if(deviceOS.equalsIgnoreCase("android")){
				client.run("adb shell settings put system screen_brightness 0");
				Thread.sleep(5000);
				writeLogToFile("Brigtness level set to 0.");
			}else{
				writeLogToFile("Brightness level configuration not allowed for IOS.");
				throw new Exception();
			}
		}catch(Exception e){
			throw new MaintenanceException("Error ocurred while changing brightness level! Maintenance skipped");
		}
	}


	public void clearDeviceCache(String deviceName,String manufacturer) throws IOException, MaintenanceException{
		if(!manufacturer.equalsIgnoreCase("htc") && !manufacturer.equalsIgnoreCase("huawei")){
			try{
				//Clear device Cache
				System.out.println("########### Clearing device cache ###########");
				writeLogToFile("Clearing device cache");
				client.run("am start -n com.android.settings/.Settings\\$StorageSettingsActivity");
				Thread.sleep(5000);
				if(client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", "xpath=//*[@text='Cached data']", 0, 1000, 5, true)){
					client.sleep(3000);
					client.click("NATIVE", "xpath=//*[@id='button1']", 0, 1);
					client.sleep(10000);
					System.out.println("########### Device cache cleared ###########");
					writeLogToFile("Device cache cleared");
				}
			}catch(Exception e){
				throw new MaintenanceException("Error ocurred while clearing cache! Maintenance skipped");
			}
		}
	}


	public void rebootDevice(String deviceName, boolean pinRequired, String simPinXpath, String simPin, String okPath) throws IOException, MaintenanceException{
		try{
			System.out.println("########### Rebooting device.. ###########");
			writeLogToFile("Rebooting device..");
			client.reboot(120000);
			boolean isAvailiable = false;
			int count = 0;
			Thread.sleep(5000);
			writeLogToFile("Waiting for device to be availiable....");
			while(!isAvailiable){
				isAvailiable = isDeviceAvailable(client,deviceName);
				Thread.sleep(10000);
				count ++;
				if(count == 15)
					break;
			}

			if(isAvailiable){
				Thread.sleep(10000);
				System.out.println("Device is availiable!");
				writeLogToFile("Device is availiable!");
				client.setDevice(deviceName);
				//Enter SIM PIN if required
				if(pinRequired){
					System.out.println("########### SIM PIN required ###########");
					writeLogToFile("SIM PIN required for the device");
					if(client.waitForElement("NATIVE", simPinXpath, 0, 10000)){
						client.click("NATIVE", simPinXpath, 0, 1);
						System.out.println("SIM PIN::"+ simPin);
						client.sendText(simPin);
						client.click("NATIVE", okPath, 0, 1);
						System.out.println("########### SIM PIN entered ###########");
						writeLogToFile("SIM PIN entered successfully : "+ simPin);
					}else{
						throw new MaintenanceException("Error occurred while SIM PIN entry!!"+ simPin);
					}
				}else{
					writeLogToFile("SIM PIN not required for this device.");
				}
			}else{
				throw new MaintenanceException("Device is taking too much time to reboot! Maintenance activity skipped for this device!!");
			}
		}catch(Exception e){
			throw new MaintenanceException("Error occurred while rebooting! Maintenance activity skipped for this device!!");
		}

	}

	public boolean isDeviceAvailable(MyClient client,String deviceName) {
		boolean isAvailiable = false;
		String connectedDevices = client.getConnectedDevices();
		if(connectedDevices!= null){
			String[] devicesAvailable = connectedDevices.split("\n"); 
			for(String device:devicesAvailable){
				if(deviceName.equalsIgnoreCase(device))
					isAvailiable= true;
			}
		}
		return isAvailiable;
	}


	public void writeLogToFile(String message) throws IOException{
		BufferedWriter out = new BufferedWriter (new FileWriter("testLogs.txt", true));
		if(message.contains("picked") || message.contains("------")){
			out.write(message + "<br>");
		}else{
			out.write("["+ new Date() + "] "+message + "<br>");
		}
		out.close();
	}

	public Integer testDuration(long startTime){
		Integer duration = 0;
		long endTime = System.nanoTime();
		duration = (int) ((endTime - startTime)/1e6);
		return duration;
	}

}
