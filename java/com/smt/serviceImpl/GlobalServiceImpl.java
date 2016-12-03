/**
 * Description : Service implementation comprising API call methods
 * @author Sauveer Pandey
 * Date : 17 June 2016
 * 
 */

package com.smt.serviceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.smt.dao.GlobalDAO;
import com.smt.domain.AdminConfigDetails;
import com.smt.domain.UserDetails;
import com.smt.service.GlobalService;
import com.smt.service.PerformActivityService;
import com.smt.utils.DevicePOJO;
import com.smt.utils.JsonObj;
import com.smt.utils.MyClient;
import com.smt.utils.UserPOJO;

@Service
public class GlobalServiceImpl implements GlobalService {

	@Autowired
	PerformActivityService perform;

	@Autowired
	private GlobalDAO globalDAO;

	private static final String DEVICES_URL = "/devices";
	private static final String USERS_URL = "/users";
	private String charset = StandardCharsets.UTF_8.name();
	private String cloudHost = "10.2.151.186";
	//private String cloudHost = "92.79.130.103";
	private String cloudPort = "80";
	private String webPage= "http://" + cloudHost + ":" + cloudPort + "/api/v1";
	private String authStringEnc;

	//public MyClient client = null;
	public String host = "localhost";
	public int port = 8889;
	public MyClient client = new MyClient(host,port);


	@Override
	public JsonObj getDevicesOnCloud(String displayStatus) throws IOException {
		JsonObj result = new JsonObj();
		List<DevicePOJO> cloudDeviceList = new ArrayList<>();
		List<DevicePOJO> cloudDeviceListFiltered = new ArrayList<>();
		initalCloudSetup();
		Gson gson = new Gson();
		String urlResponse = doGet(DEVICES_URL);
		JSONObject jsonObj = new JSONObject(urlResponse);
		String deviceListString =jsonObj.get("data").toString();
		System.out.println(deviceListString);
		deviceListString = deviceListString.substring(1, deviceListString.length()-2);
		String[] str = deviceListString.split("},");
		for(String jsonStr: str){
			DevicePOJO obj = gson.fromJson(jsonStr + "}", DevicePOJO.class);
			//			if(obj.getDisplayStatus().equalsIgnoreCase(displayStatus)){
			//				cloudDeviceList.add(obj);
			//			}
			cloudDeviceList.add(obj);
		}
		int avlCount = 0;
		int reserveCount = 0;
		for(DevicePOJO dp : cloudDeviceList){
			try{
				String msisdn = dp.getNotes().substring(dp.getNotes().indexOf("MSISDN:")+7, dp.getNotes().indexOf("PIN:")).trim();
				String simPin = dp.getNotes().substring(dp.getNotes().indexOf("PIN:")+4, dp.getNotes().indexOf("OPCO:")).trim();
				dp.setMsisdn(msisdn);
				dp.setSimPin(simPin);
			}catch(Exception e){
				dp.setMsisdn("NA");
				dp.setSimPin("NA");
			}

			if(dp.getDisplayStatus().equalsIgnoreCase("Available")){
				avlCount++;
			}else if(dp.getDisplayStatus().equalsIgnoreCase("In Use")){
				reserveCount++;
			}
			if(!displayStatus.equalsIgnoreCase("All")){
				if(dp.getDisplayStatus().equalsIgnoreCase(displayStatus)){
					cloudDeviceListFiltered.add(dp);
				}
			}else{
				cloudDeviceListFiltered = cloudDeviceList;
			}
		}
		result.setDeviceDataList(cloudDeviceListFiltered);
		result.setAvlCount(avlCount);
		result.setReserveCount(reserveCount);
		return result;
	}

	@Override
	public JsonObj getAllUsers() throws IOException {
		JsonObj result = new JsonObj();
		List<UserPOJO> userList = new ArrayList<>();
		initalCloudSetup();
		Gson gson = new Gson();
		String urlResponse = doGet(USERS_URL);
		JSONObject jsonObj = new JSONObject(urlResponse);
		String userListString =jsonObj.get("data").toString();
		System.out.println(userListString);
		userListString = userListString.substring(1, userListString.length()-2);
		String[] str = userListString.split("},");
		for(String jsonStr: str){
			UserPOJO obj = gson.fromJson(jsonStr + "}", UserPOJO.class);
			userList.add(obj);
		}
		result.setUserDataList(userList);
		return result;
	}

	public void initalCloudSetup(){
		
		String name = "sauveer_pandey";
		String password = "Vodafone1";
		
		//String name = "TCS" ;//"admin";
		//String password = "TCStest1508" ;//"TCStest1508";	
		String authString = name + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		authStringEnc = new String(authEncBytes);
	}


	private String doGet(String entity) throws IOException {
		URL url = new URL(webPage+entity);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
		InputStream is = urlConnection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		int numCharsRead;
		char[] charArray = new char[1024];
		StringBuffer sb = new StringBuffer();
		while ((numCharsRead = isr.read(charArray)) > 0) {
			sb.append(charArray, 0, numCharsRead);
		}
		String result = sb.toString();
		if (((HttpURLConnection)urlConnection).getResponseCode() < 300) {
			return result;
		} else {
			throw new RuntimeException(result);
		}
	}

	@Override
	public JsonObj triggerDeviceMaintenance(DevicePOJO devicePOJO) {
		JsonObj result = new JsonObj();
		String message = "";
		List<DevicePOJO> cloudDeviceList = new ArrayList<>();
		try {
			PrintWriter pw = new PrintWriter("testLogs.txt");
			pw.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try{
			for(String udid: devicePOJO.getUdidArray()){
				boolean deviceFoundOnCloud = false;
				cloudDeviceList = getDevicesOnCloud("All").getDeviceDataList();
				for(DevicePOJO seeTestDevice: cloudDeviceList){
					if(udid.equals(seeTestDevice.getUdid())){
						seeTestDevice.setSelectedActivityId(devicePOJO.getSelectedActivityId());
						System.out.println("########### Device picked for maintenance : "+ seeTestDevice.getDeviceName()+" ###########");
						Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","start cloudauto://localhost?id="+seeTestDevice.getUdid()});
						Thread.sleep(10000);
						switch(seeTestDevice.getManufacturer().toLowerCase()){
						case "samsung":
						case "htc" :
						case "sony" :
						case "lge" :
						case "huawei" :
						case "nexus" :
						case "motorola" :
						case "vodafone" :
							message = perform.callPerformActivityAndroid(seeTestDevice, seeTestDevice.getManufacturer().toLowerCase());
							break;
						case "apple" :
							message = perform.callPerformActivityIOS(seeTestDevice, seeTestDevice.getManufacturer().toLowerCase());
							break;
						}
						deviceFoundOnCloud = true;
						break;
					}
				}if(!deviceFoundOnCloud){
					System.out.println("Device selected for maintenance not found on cloud:" + udid);
				}
			}
			result.setMessages(message);
		}catch(Exception e){
			result.setMessages("ERROR");
			System.out.println(e);
		}
		return result;
	}

	public List<DevicePOJO> dummyResult(){
		List<DevicePOJO> dummyList = new ArrayList<DevicePOJO>();
		DevicePOJO obj = new DevicePOJO();
		obj.setDeviceName("dName");
		obj.setId("51");
		dummyList.add(obj);
		obj.setId("52");
		dummyList.add(obj);
		obj.setId("53");
		dummyList.add(obj);
		return dummyList;
	}

	@Override
	public JsonObj releaseDevice(DevicePOJO devicePOJO){
		JsonObj result = new JsonObj();
		try{
			String entity = "/devices/"+devicePOJO.getId()+"/release";
			String query = "";
			String response = doPost(entity, query);
			if(StringUtils.containsIgnoreCase(response, "SUCCESS")){
				result.setMessages("SUCCESS");
			}else{
				result.setMessages("FAILURE");
			}		
		}catch(Exception e){
			result.setMessages("FAILURE");		
		}
		return result;
	}

	@Override
	public JsonObj reserveDeviceForUser(UserPOJO userPOJO) {
		JsonObj result = new JsonObj();
		try{
			String deviceUdid = userPOJO.getSelectedDeviceUdid().split("~")[0];
			String deviceId = userPOJO.getSelectedDeviceUdid().split("~")[1];
			String deviceName = userPOJO.getSelectedDeviceUdid().split("~")[2];
			if(userPOJO.getUser().equalsIgnoreCase("-Select-")){
				System.out.println("########### Reserving Device : "+ deviceUdid +" for current user ###########");
				Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","start cloudauto://localhost?id="+deviceUdid});
				boolean isAvailiable = false;
				Thread.sleep(15000);
				String connectedDevices = client.getConnectedDevices();
				System.out.println("#####"+connectedDevices);
				if(connectedDevices!= null){
					String[] devicesAvailable = connectedDevices.split("\n"); 
					for(String device:devicesAvailable){
						if(deviceName.equalsIgnoreCase(device.split(":")[1]))
							isAvailiable= true;
					}
				}
				if(isAvailiable){
					result.setMessages("SUCCESS");
				}else{
					result.setMessages("Device not available!");
				}

				Thread.sleep(10000);
			}else{
				String userName = userPOJO.getUser().split("~")[1];
				String userId = userPOJO.getUser().split("~")[0];
				System.out.println("########### Reserving Device : "+ deviceUdid +" for user : " + userName +" ###########");
				String entity = "/devices/"+deviceId+"/reservations/new";
				String charset = StandardCharsets.UTF_8.name();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				Date dateTimeStart = new Date();
				Date dateTimeEnd = DateUtils.addMinutes(dateTimeStart, 30); 
				String reserveFrom = sdf.format(dateTimeStart);
				String reserveTill = sdf.format(dateTimeEnd);
				System.out.println("start:: "+ reserveFrom);
				System.out.println("reserveTill "+ reserveTill);

				String query = String.format("start=%s&end=%s&clientCurrentTimestamp=%s&userId=%s",
						URLEncoder.encode(reserveFrom, charset), //start
						URLEncoder.encode(reserveTill, charset), //end
						URLEncoder.encode(reserveFrom, charset), //clientCurrentTimestamp
						URLEncoder.encode(userId, charset)); //userId

				String response = doPost(entity, query);
				if(StringUtils.containsIgnoreCase(response, "SUCCESS")){
					result.setMessages("SUCCESS");
				}else{
					result.setMessages("FAILURE");
				}
			}
		}catch(Exception e){
			String msg = null;
			String excpt = e.getMessage().replace("{", " ").replace("}", " ");
			String[] str = excpt.split(",");
			for(String str1 : str){
				if(str1.contains("code")){
					msg = str1.split(":")[1];
				}
			}
			result.setMessages(msg);
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * @param entity can be "/users" / "/projects" / "/devices" etc
	 * String query = String.format("param1=%s&param2=%s", URLEncoder.encode(param1, charset), URLEncoder.encode(param2, charset));
	 */
	private String doPost(String entity , String query) throws IOException {
		URL url = new URL(webPage+entity);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());
		urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

		OutputStream output = urlConnection.getOutputStream();
		output.write(query.getBytes(StandardCharsets.UTF_8.name()));
		HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
		printPost(url, httpURLConnection);
		InputStream stream = null;
		if (httpURLConnection.getResponseCode() >= 400) {
			stream = httpURLConnection.getErrorStream();
		} else {
			stream = httpURLConnection.getInputStream();       
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String inputLine;
		StringBuffer responseBuffer = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			responseBuffer.append(inputLine);
		}
		in.close();
		System.out.println(responseBuffer.toString());
		if (httpURLConnection.getResponseCode() < 300) {
			return responseBuffer.toString();
		} else {
			throw new RuntimeException(responseBuffer.toString());
		}
	}

	private void printPost(URL url, HttpURLConnection httpURLConnection) throws IOException {
		int responseCode = httpURLConnection.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
	}

	@Override
	public JsonObj readLogFile(long pos) throws Exception {
		JsonObj result = new JsonObj();
		StringBuilder logMessage = new StringBuilder();
		File f = new File("testLogs.txt");
		FileInputStream fis = new FileInputStream( f );
		BufferedInputStream bis = new BufferedInputStream( fis );
		FileChannel fc = fis.getChannel();
		fc.position( pos );
		long newpos = fc.size();  // Monitor new position
		while( pos < newpos ){    // new data?
			int c = bis.read();   // read and print
			logMessage.append((char)c);
			System.out.print( (char)c );
			pos++;
		}
		pos = newpos;
		result.setMessages(logMessage.toString());
		result.setFilePos(pos);
		return result;
	}

	@Override
	public JsonObj downloadConsoleLogPDF() {
		JsonObj result = new JsonObj();
		try{
			String fileName = System.getProperty("user.home") + "\\Downloads\\PdfLogFromFile"+System.currentTimeMillis()+".pdf"; 
			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();
			doc.addPage(page);
			PDPageContentStream content = new PDPageContentStream(doc, page);
			content.beginText();
			content.setFont(PDType1Font.HELVETICA, 12);
			content.moveTextPositionByAmount(220, 750);
			content.drawString("SeeTest Maintenance Logs");
			content.endText();
			content.drawLine(15, 740, 600, 740); 
			BufferedReader in = new BufferedReader(new FileReader("testLogs.txt"));
			String str;
			StringBuilder logContent = new StringBuilder();
			while ((str = in.readLine()) != null){
				logContent.append(str);
			}
			in.close();
			String[] str1 = logContent.toString().split("<br>");
			float x = 20;
			float y = 730;
			float fontSize = 5;
			content.setFont(PDType1Font.HELVETICA, fontSize);
			for(String log: str1){
				if(y<20){
					page = new PDPage();
					doc.addPage(page);
					content = new PDPageContentStream(doc, page);
					content.setFont(PDType1Font.HELVETICA, fontSize);
					x= 20;
					y= 750;
				}
				content.beginText();
				content.moveTextPositionByAmount(x, y-=10);
				content.drawString(log);
				content.endText();
				content.close();
			}
			doc.save(fileName);
			doc.close();
			result.setMessages("SUCCESS");
		}
		catch(IOException | COSVisitorException e){
			System.out.println(e.getMessage());
			result.setMessages("ERROR");
		}
		return result;
	}

	@Override
	public JsonObj exportToExcelDeviceInfo() {
		JsonObj result = new JsonObj();
		JsonObj deviceList = new JsonObj();
		try{


			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Device details");
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
			data.put("udid", new Object[] {"S.No.", "DEVICE NAME", "DEVICE OS","OS VERSION","MSISDN","SIM PIN", "NOTES", "DEVICE STATUS", "MANUFACTURER" , "MODEL", "AGENT IP"});
			deviceList = getDevicesOnCloud("All");
			Integer i=1;
			for(DevicePOJO device: deviceList.getDeviceDataList()){
				data.put(device.getUdid(),  new Object[] {i, device.getDeviceName(), device.getDeviceOs(),device.getOsVersion(),device.getMsisdn()
					,device.getSimPin(), device.getNotes(), device.getDisplayStatus(), device.getManufacturer(), device.getModel(), device.getAgentIp()});
				i++;
			}
			Set<String> keyset = data.keySet();
			int rownum = 0;
			for (String key : keyset) {
				Row row = sheet.createRow(rownum++);
				Object [] objArr = data.get(key);
				int cellnum = 0;
				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					if(key.equalsIgnoreCase("udid")){
						cell.setCellStyle(style);
					}
					if(obj instanceof Date) 
						cell.setCellValue((Date)obj);
					else if(obj instanceof Boolean)
						cell.setCellValue((Boolean)obj);
					else if(obj instanceof String)
						cell.setCellValue((String)obj);
					else if(obj instanceof Double)
						cell.setCellValue((Double)obj);
					else if(obj instanceof Integer)
						cell.setCellValue((Integer)obj);
				}
			}
			try {
				FileOutputStream out = 
						new FileOutputStream(new File(System.getProperty("user.home") + "\\Downloads\\SeeTest Cloud Devices_"+System.currentTimeMillis()+".xls"));
				workbook.write(out);
				out.close();
				System.out.println("Excel exported successfully..");
				result.setMessages("SUCCESS");
			} catch (FileNotFoundException e) {
				result.setMessages("ERROR");
				e.printStackTrace();
			} catch (IOException e) {
				result.setMessages("ERROR");
				e.printStackTrace();
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			result.setMessages("ERROR");
		}
		return result;
	}

	@Override
	public JsonObj getUSBChargingInfo() {
		JsonObj result = new JsonObj();
		List<DevicePOJO> chargingInfo = new ArrayList<DevicePOJO>();
		try{
			String devicesOnCloud = client.run("adb devices").replace("List of devices attached", "").replace("\n", "").replace("device", "").trim();
			String[]  deviceArray = devicesOnCloud.split("\t");
			for(int i=0;i<deviceArray.length;i++)
			{
				String properties = client.run("adb -s "+deviceArray[i]+" shell dumpsys battery").trim();
				String[] prop = properties.split("\n");
				Map<String, String> deviceProperties =  new HashMap<>();
				for(int j=3;j<prop.length;j++)
				{
					if(!prop[j].isEmpty())
					{
						deviceProperties.put(prop[j].split(":")[0].trim(), prop[j].split(":")[1].trim());        	   
					}
				}
				//chargingInfo.add(e);
			}
			result.setDeviceDataList(chargingInfo);
		}catch(Exception e){
			System.out.println(e);
		}
		return result;
	}

	@Override
	public JsonObj getDeviceDetailsForStatistics() throws IOException {
		JsonObj result = new JsonObj();
		List<List<Map<String,String>>> dataForDeviceStatistics = new ArrayList<>();
		List<DevicePOJO> cloudDeviceList = new ArrayList<>();
		initalCloudSetup();
		Gson gson = new Gson();
		String urlResponse = doGet(DEVICES_URL);
		JSONObject jsonObj = new JSONObject(urlResponse);
		String deviceListString =jsonObj.get("data").toString();
		deviceListString = deviceListString.substring(1, deviceListString.length()-2);
		String[] str = deviceListString.split("},");
		for(String jsonStr: str){
			DevicePOJO obj = gson.fromJson(jsonStr + "}", DevicePOJO.class);
			cloudDeviceList.add(obj);
		}


		/* Code for OPCO based data graph*/
		Set<String> opcoList = new HashSet<String>();
		Set<String> OSSet = new HashSet<String>();
		List<String> deviceManufacturerList = new ArrayList<>();
		for(DevicePOJO device : cloudDeviceList){
			try{
				String opco ="";
				if(device.getNotes().contains("OPCO1:")){
					String opco1 = device.getNotes().substring(device.getNotes().indexOf("OPCO1:")+6, device.getNotes().indexOf("MSISDN2:")).trim();
					String opco2 = device.getNotes().substring(device.getNotes().indexOf("OPCO2:")+6, device.getNotes().indexOf("RACK:")).trim();
					opco = opco1+"&"+opco2;
				}else{
					opco = device.getNotes().substring(device.getNotes().indexOf("OPCO:")+5, device.getNotes().indexOf("RACK:")).trim();
				}
				device.setDeviceOpco(opco);
			}catch(Exception e){
				device.setDeviceOpco("NA");
			}

			if(!"".equals(device.getManufacturer()) && !deviceManufacturerList.contains(device.getManufacturer().toUpperCase())){
				//System.out.println(device.getManufacturer());
				deviceManufacturerList.add(device.getManufacturer().toUpperCase());
			}

			if(device.getDeviceOpco()!="NA" && !device.getDeviceOpco().isEmpty() && !opcoList.contains(device.getDeviceOpco().toUpperCase())){
				//System.out.println(device.getDeviceOpco().toUpperCase());
				if(device.getDeviceOpco().contains("&")){
					opcoList.add(device.getDeviceOpco().split("&")[0].toUpperCase());
					opcoList.add(device.getDeviceOpco().split("&")[1].toUpperCase());
				}else{
					opcoList.add(device.getDeviceOpco().toUpperCase());
				}
			}

			if(device.getDeviceOs()!="NA" && !device.getDeviceOs().isEmpty() && !OSSet.contains(device.getDeviceOs().toUpperCase())){
				OSSet.add(device.getDeviceOs().toUpperCase());
			}
		}

		List<Map<String,String>> dataForOPCOBasedGraph = new ArrayList<Map<String,String>>();
		String[] colorArray = {"#005CDE", "#00A36A", "#7D0096", "#992B00", "#FF8000" , "#BDBDBD", "#2EFEF7", "#F2F2F2" , 
				"#424242", "#80FF00", "#DE000F", "#AEB404", "#FA58D0", "#FE9A2E" , "#2EFEF7", "#CED8F6" , "#D8D8D8" ,
				"#088A85" , "#886A08" , "#0B3B17", "#380B61", "#F6CEEC", "#FF0000", "#FFFF00" , "#D8F6CE", "#071019",
				"#A9F5A9", "#F6CECE", "#9FF781", "#3104B4"};
		int colorIndex = 0;
		for(String opco :opcoList){
			Integer count =0 ;
			HashMap<String, String> data = new HashMap<String,String>();
			for(DevicePOJO device : cloudDeviceList){
				if(device.getDisplayStatus().equalsIgnoreCase("Available") || device.getDisplayStatus().equalsIgnoreCase("In Use")){
					if(StringUtils.containsIgnoreCase(device.getDeviceOpco(), opco)){
						count++;
					}
				}
			}

			if(count > 0){
				data.put("indexLabel", opco);
				data.put("y", count.toString());
				dataForOPCOBasedGraph.add(data);
			}
			//data.put("label", opco);
			//data.put("data", count.toString());
			//data.put("color", colorArray[colorIndex]);

			colorIndex++;
			if(colorIndex > 30){
				colorIndex = 0;
			}
		}

		String[] colorArrayOS = {"#7D0096", "#00A36A","#DE000F", "#005CDE", "#FF8000", "#AEB404","#AEB404", "#992B00", "#071019", "#D8F6CE"};
		/* Code for OS specific graph*/
		List<Map<String,String>> dataForProjectBasedGraph = new ArrayList<Map<String,String>>();
		colorIndex = 0;
		for(String os :OSSet){
			Integer count =0;
			HashMap<String, String> data = new HashMap<String,String>();
			for(DevicePOJO device : cloudDeviceList){
				if(device.getDisplayStatus().equalsIgnoreCase("Available") || device.getDisplayStatus().equalsIgnoreCase("In Use")){
					if(device.getDeviceOs().equalsIgnoreCase(os)){
						count++;
					}
				}
			}
			if(count > 0){
				data.put("indexLabel", os);
				data.put("y", count.toString());
				data.put("legendText", os);
				dataForProjectBasedGraph.add(data);
			}

			//			data.put("label", os);
			//			data.put("data", count.toString());
			//			data.put("color", colorArrayOS[colorIndex]);

			colorIndex++;
			if(colorIndex > 10){
				colorIndex = 0;
			}
		}


		/* Code for Model specific graph*/
		List<Map<String,String>> dataForDeviceModelGraph = new ArrayList<Map<String,String>>();
		for(String manufacturer :deviceManufacturerList){
			HashMap<String, String> data = new HashMap<String,String>();
			Integer phoneCount =0 ; Integer tabletCount = 0;
			for(DevicePOJO device : cloudDeviceList){
				if(device.getDisplayStatus().equalsIgnoreCase("Available") || device.getDisplayStatus().equalsIgnoreCase("In Use")){
					if(manufacturer.equalsIgnoreCase(device.getManufacturer()) && "PHONE".equalsIgnoreCase(device.getDeviceCategory())){
						phoneCount++;
					}else if(manufacturer.equalsIgnoreCase(device.getManufacturer()) && "TABLET".equalsIgnoreCase(device.getDeviceCategory())){
						tabletCount++;
					}
				}
			}
			if(phoneCount >0 || tabletCount >0){
				data.put("a", phoneCount.toString());
				data.put("b", tabletCount.toString());
				data.put("y", manufacturer);
				dataForDeviceModelGraph.add(data);
			}

		}

		dataForDeviceStatistics.add(dataForDeviceModelGraph);
		dataForDeviceStatistics.add(dataForOPCOBasedGraph);
		dataForDeviceStatistics.add(dataForProjectBasedGraph);

		result.setDataForDeviceStatistics(dataForDeviceStatistics);
		return result;
	}

	@Override
	@Transactional
	public JsonObj sendBroadcastMail(DevicePOJO devicePOJO) {
		JsonObj result = new JsonObj();
		UserPOJO userPOJO = new UserPOJO();
		userPOJO.setId(devicePOJO.getId());

		final String fromMail = "sauveer.pandey@vodafone.com";
		final String pwd = "nov@2016";
		StringBuilder toMailList = new StringBuilder("sauveer.pandey@vodafone.com");

		try {
			AdminConfigDetails adminConfigDetails = new AdminConfigDetails();
			adminConfigDetails = globalDAO.getMailConfigData(userPOJO);
			if(isNotEmptyorNull(adminConfigDetails.getSmtpHost()) && isNotEmptyorNull(adminConfigDetails.getSmtpPort()) 
					&& isNotEmptyorNull(adminConfigDetails.getOutlookMail()) && isNotEmptyorNull(adminConfigDetails.getOutlookPassword())){
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", adminConfigDetails.getSmtpHost());
				props.put("mail.smtp.port", adminConfigDetails.getSmtpPort());
				//props.put("mail.smtp.host", "north-mail01.internal.vodafone.com");
				//props.put("mail.smtp.port", "25");

				Session session = Session.getInstance(props, new javax.mail.Authenticator() { 
					protected PasswordAuthentication getPasswordAuthentication(){ 
						return new PasswordAuthentication(fromMail, pwd);
					}
				});
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(fromMail));
				List<UserPOJO> allUserObj = getAllUsers().getUserDataList();
				toMailList = toMailList.append(",").append("palla.mounika@vodafone.com");
				//				for(UserPOJO obj : allUserObj){
				//					//toMailList = toMailList.append(",").append(obj.getEmail());
				//					
				//				}
				message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toMailList.toString()));
				message.setSubject(devicePOJO.getSubject());
				message.setText(devicePOJO.getMessageContent());
				System.out.println("Sending Mail..");
				Transport.send(message);
				System.out.println("Mail sent successfully");
				result.setMessages("Mail sent successfully");
			}else{
				result.setMessages("Mailbox data not configured properly. Broadcast cannot be done!");
			}
		} catch (MessagingException e) {
			result.setMessages("Mailbox data not configured properly. Broadcast cannot be done!");
			System.out.println(e);
		} catch (IOException e) {
			result.setMessages("System encountered a problem, please try again later");
			System.out.println(e);
		}
		return result;
	}

	@Override
	@Transactional
	public JsonObj saveAdminConfigDetails(UserPOJO userPOJO) {
		JsonObj result = new JsonObj();
		AdminConfigDetails adminConfigDetails = new AdminConfigDetails();
		try{
			if(isNotEmptyorNull(userPOJO.getSmtpHost()) && isNotEmptyorNull(userPOJO.getSmtpPort()) 
					&& isNotEmptyorNull(userPOJO.getOutlookEmail()) && isNotEmptyorNull(userPOJO.getOutlookPassword())){
				adminConfigDetails.setSmtpHost(userPOJO.getSmtpHost());
				adminConfigDetails.setSmtpPort(userPOJO.getSmtpPort());
				adminConfigDetails.setUserId(userPOJO.getId());
				adminConfigDetails.setOutlookMail(userPOJO.getOutlookEmail());
				adminConfigDetails.setOutlookPassword(userPOJO.getOutlookPassword());
				globalDAO.saveAdminConfigDetails(adminConfigDetails);
				result.setMessages("SUCCESS");
			}else{
				result.setMessages("ERROR");
			}
		}catch(Exception e){
			result.setMessages("ERROR"); 
			e.printStackTrace();
		}
		return result; 
	}

	@Override
	@Transactional
	public JsonObj validateMailConfigData(UserPOJO userPOJO) {
		JsonObj result = new JsonObj();
		AdminConfigDetails adminConfigDetails = new AdminConfigDetails();
		try{
			adminConfigDetails = globalDAO.getMailConfigData(userPOJO);
			if(isNotEmptyorNull(adminConfigDetails.getSmtpHost()) && isNotEmptyorNull(adminConfigDetails.getSmtpPort()) 
					&& isNotEmptyorNull(adminConfigDetails.getOutlookMail()) && isNotEmptyorNull(adminConfigDetails.getOutlookPassword())){
				result.setMessages("SUCCESS");
			}else{
				result.setMessages("ERROR");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setMessages("ERROR");
		}
		return result;
	}

	@Override
	@Transactional
	public AdminConfigDetails getMailConfigData(UserPOJO userPOJO) {
		AdminConfigDetails adminConfigDetails = new AdminConfigDetails();
		try{
			adminConfigDetails = globalDAO.getMailConfigData(userPOJO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return adminConfigDetails;
	}

	private boolean isNotEmptyorNull(String value){
		if(null!= value && ""!=value && !value.trim().isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
