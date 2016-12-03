/**
 * Description : Global controller to handle all http requests and pass onto service
 * @author Sauveer Pandey
 * Date : 17 June 2016
 * 
 */
package com.smt.controller;


import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smt.domain.AdminConfigDetails;
import com.smt.service.GlobalService;
import com.smt.utils.DevicePOJO;
import com.smt.utils.JsonObj;
import com.smt.utils.UserPOJO;


@Controller
public class SMTController {

	@Autowired
	private GlobalService globalService;

	
	@RequestMapping(value="/userHome",method=RequestMethod.POST)
	public String renderUserHome(HttpServletRequest request) {
		UserPOJO userPOJO = new UserPOJO();
		userPOJO.setId(request.getParameter("userName"));
		userPOJO.setPassword(request.getParameter("password"));
		return "userHome";
	}
	
	@RequestMapping(value="/dashboard",method=RequestMethod.GET)
	public String renderDashboard() {
		return "dashboard";
	}
	
	@RequestMapping(value="/invenDashboard",method=RequestMethod.GET)
	public String renderinvenDashboard() {
		return "invenDashboard";
	}
	
	@RequestMapping(value="/deviceStatistics",method=RequestMethod.GET)
	public String renderDeviceStatistics() {
		return "deviceStatistics";
	}
	
	@RequestMapping(value="/maintenanceConsole",method=RequestMethod.GET)
	public String renderMaintenanceConsole() {
		return "maintenanceConsole";
	}

	@RequestMapping(value="/scheduleForMaintenance",method=RequestMethod.GET)
	public String renderCreateNewTaskPage() {
		return "scheduleForMaintenance";
	}
	
	@RequestMapping(value="/reserveDevice",method=RequestMethod.GET)
	public String renderReserveDevicePage() {
		return "reserveDevice";
	}
	
	@RequestMapping(value="/broadcastMessage",method=RequestMethod.GET)
	public String renderBroadcastMessagePage() {
		return "broadcastMessage";
	}
	
	@RequestMapping(value="/usbChargingControl",method=RequestMethod.GET)
	public String renderUsbChargingControlPage() {
		return "usbChargingControl";
	}
	
	@RequestMapping(value="/adminControlPanel")
	public String renderAdminControlPanelPage(@RequestParam("id") String id, ModelMap modelMap) {
		AdminConfigDetails adminConfigDetails = null;
		UserPOJO userPOJO = new UserPOJO();
		try{
			userPOJO.setId(id);
			adminConfigDetails = globalService.getMailConfigData(userPOJO);
			modelMap.addAttribute("smtpHost", adminConfigDetails.getSmtpHost());
			modelMap.addAttribute("smtpPort", adminConfigDetails.getSmtpPort());
			modelMap.addAttribute("userMail", adminConfigDetails.getOutlookMail());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "adminControlPanel";
	}
	
	@RequestMapping(value="/getAllDevicesDetails",method=RequestMethod.POST)
	public @ResponseBody JsonObj getDevicesOnCloud(@RequestBody DevicePOJO devicePOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.getDevicesOnCloud(devicePOJO.getDisplayStatus());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value="/getAllUsers",method=RequestMethod.POST)
	public @ResponseBody JsonObj getAllUsers(@RequestBody DevicePOJO devicePOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/validateMailConfigData",method=RequestMethod.POST)
	public @ResponseBody JsonObj validateMailConfigData(@RequestBody UserPOJO userPOJO) {
		JsonObj result = null;
		try {
			result = globalService.validateMailConfigData(userPOJO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/getDeviceDetailsForStatistics",method=RequestMethod.POST)
	public @ResponseBody JsonObj getDeviceDetailsForStatistics(@RequestBody DevicePOJO devicePOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.getDeviceDetailsForStatistics();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/reserveDeviceForUser",method=RequestMethod.POST)
	public @ResponseBody JsonObj reserveDeviceForUser(@RequestBody UserPOJO userPOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.reserveDeviceForUser(userPOJO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/releaseDevice",method=RequestMethod.POST)
	public @ResponseBody JsonObj releaseDevice(@RequestBody DevicePOJO devicePOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.releaseDevice(devicePOJO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/readLogFile",method=RequestMethod.POST)
	public @ResponseBody JsonObj readLogFile(@RequestBody JsonObj jsonObj,
			Map<String,Object> contextMap) {
		long pos = jsonObj.getFilePos();
		JsonObj result = null;
		try {
			result = globalService.readLogFile(pos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/getUSBChargingInfo",method=RequestMethod.POST)
	public @ResponseBody JsonObj getUSBChargingInfo(@RequestBody DevicePOJO devicePOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.getUSBChargingInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/exportToExcelDeviceInfo",method=RequestMethod.POST)
	public @ResponseBody JsonObj exportToExcelDeviceInfo(@RequestBody JsonObj jsonObj,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.exportToExcelDeviceInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/downloadConsoleLogPDF",method=RequestMethod.POST)
	public @ResponseBody JsonObj downloadConsoleLogPDF(@RequestBody JsonObj jsonObj,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.downloadConsoleLogPDF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/sendBroadcastMail",method=RequestMethod.POST)
	public @ResponseBody JsonObj sendBroadcastMail(@RequestBody DevicePOJO devicePOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.sendBroadcastMail(devicePOJO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/saveAdminConfigDetails",method=RequestMethod.POST)
	public @ResponseBody JsonObj saveAdminConfigDetails(@RequestBody UserPOJO userPOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			result = globalService.saveAdminConfigDetails(userPOJO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/triggerDeviceMaintenance",method=RequestMethod.POST)
	public @ResponseBody JsonObj triggerDeviceMaintenance(@RequestBody DevicePOJO devicePOJO,
			Map<String,Object> contextMap) {
		JsonObj result = null;
		try {
			if(devicePOJO.getUdidArray().length == 0)
				return null;
			result = globalService.triggerDeviceMaintenance(devicePOJO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
