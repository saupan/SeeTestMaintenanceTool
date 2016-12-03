/**
 * Description : Common javascript for function and method calls
 * @author Sauveer Pandey
 * Date : 23 June 2016
 */

var timerForLogFile = null;
var pos = 0;
var contentId = 0;

function getAllDevices(){
	var value = "All";
	$("#dataTables-example").empty();
	var jsonObj = {
			displayStatus : value
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "getAllDevicesDetails",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			$("#availableCount").html(jsonObj.avlCount);
			$("#reservedCount").html(jsonObj.reserveCount);
			var html = generateTable(jsonObj.deviceDataList);
			$("#dataTables-example").append(html);
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function reloadTable(){
	var table = $('#dataTables-example').DataTable();
	var rows = table.rows().remove().draw();
	var e = document.getElementById("deviceStatus");
	if(e!=null){
		var value =e.options[e.selectedIndex].text;
	}	
	var jsonObj = {
			displayStatus : value
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "getAllDevicesDetails",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			$("#availableCount").html(jsonObj.avlCount);
			$("#reservedCount").html(jsonObj.reserveCount);
			var dataList = jsonObj.deviceDataList;
			for(i=0; i<dataList.length; i++){
				var uptimeContent = "";
				if(dataList[i].displayStatus != "Available" && dataList[i].displayStatus != "In Use"){
					uptimeContent= '<div class="progress progress-striped active" style="width: 20px; margin-left: 25px;"><div id="progressBar'+dataList[i].id+'" class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="40" style="width: 100%"></div></div>';
				}else {
					if(dataList[i].uptime.split(":")[0] > 160){
						uptimeContent= '<div class="progress progress-striped active" style="width: 20px; margin-left: 25px;"><div id="progressBar'+dataList[i].id+'" class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="40" style="width: 100%"></div></div>';
					}else{
						uptimeContent= '<div class="progress progress-striped active" style="width: 20px; margin-left: 25px;"><div id="progressBar'+dataList[i].id+'" class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="40" style="width: 100%"></div></div>';
					}
				}
				table.row.add( [ dataList[i].id, dataList[i].deviceName, dataList[i].model, dataList[i].currentUser, dataList[i].deviceOs,dataList[i].msisdn, 
				                 dataList[i].simPin, dataList[i].notes,dataList[i].manufacturer, dataList[i].osVersion, dataList[i].displayStatus,
				                 dataList[i].udid, uptimeContent] ).draw();
			}
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function populateDevices(selectRequired, displayStatus){
	$('#devices').empty().append("<option value='0'>-Select-</option>");
	if(selectRequired == false){
		$("#devices option[value='0']").remove();
	}
	var jsonObj = {
			displayStatus : displayStatus 
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "getAllDevicesDetails",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			$.each(jsonObj.deviceDataList,function(i,jsonObj){
				if(jsonObj.displayStatus == "Available"){
					var div_data_device="<option value='"+jsonObj.udid+"~"+jsonObj.id+"~"+jsonObj.deviceName+"~udid : "+jsonObj.udid+"<br/> Device Status : "+ jsonObj.displayStatus+"<br/> Notes : "+ jsonObj.notes+"<br/> Model : "+ jsonObj.model+"'>"+jsonObj.deviceName+"</option>";
					if(div_data_device){
						$(div_data_device).appendTo('#devices');
					}
				}	
			});  
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function populateUsers(){
	$('#user').empty().append("<option>-Select-</option>");
	var jsonObj = {
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "getAllUsers",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			$.each(jsonObj.userDataList,function(i,jsonObj)
					{
				var div_data_user="<option value='"+jsonObj.id+"~"+ jsonObj.firstName + " " + jsonObj.lastName+"'>"+jsonObj.firstName + ' ' + jsonObj.lastName+"</option>";
				if(div_data_user){
					$(div_data_user).appendTo('#user');
				}
					});  
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function deviceDescPopover(){
	$("#reserveAndOpenButton").prop('disabled', false);
	var e = document.getElementById("devices");
	document.getElementById('selectedDeviceName').value  = e.options[e.selectedIndex].text;
	var value = $('#devices').val();
	$('#messages').html("");
	var deviceDesc = value.split("~")[3];
	if(deviceDesc == '-Select-'){
		$('#popover').css("display","none");
	}else{
		$('#deviceDescription').html(deviceDesc);
		$('#popover').css("display","block");
	}
}

function generateTable(dataList){
	var html = '<thead>	<tr role="row"><th tabindex="0" aria-controls="dataTables-example" '
		+ ' style="width:22px">ID</th> '
		+ '<th  tabindex="0" aria-controls="dataTables-example"  '
		+ '>Device Name</th> <th  tabindex="0" aria-controls="dataTables-example"  '
		+ '>Model</th> <th  tabindex="0" '
		+ 'aria-controls="dataTables-example"   '
		+ '>Current User</th>	<th  tabindex="0" aria-controls="dataTables-example" 1" '
		+ 'style="width:35px">OS</th> <th  tabindex="0" '
		+ 'aria-controls="dataTables-example"   '
		+ 'style="width:100px">MSISDN</th><th  tabindex="0" '
		+ 'aria-controls="dataTables-example"   '
		+ 'style="width:35px">PIN</th><th  tabindex="0" '
		+ 'aria-controls="dataTables-example"   '
		+ '>Notes</th> <th  tabindex="0" aria-controls="dataTables-example" '
		+ '>Manufacturer</th> '
		+ '<th  tabindex="0" aria-controls="dataTables-example"  '
		+ 'style="width:60px">Version</th><th  tabindex="0" aria-controls="dataTables-example" '
		+ '>Status</th><th  tabindex="0" aria-controls="dataTables-example" '
		+ '>Device udid</th><th  tabindex="0" aria-controls="dataTables-example"  '
		+ 'style="width:60px">Uptime</th></tr></thead><tbody>';
	for(var i = 0; i< dataList.length ; i++){
		if(i%2 != 0){
			html += '<tr class="gradeA odd" role="row">';
		}else{
			html += '<tr class="gradeA even" role="row">';
		}
		html += '<td id="deviceId">'+dataList[i].id+'</td><td class="">'+dataList[i].deviceName+'</td><td>'+dataList[i].model
		+'</td><td class="center">'+dataList[i].currentUser+'</td>'
		+ '<td class="center">'+dataList[i].deviceOs+'</td><td class="center">'+dataList[i].msisdn
		+'</td><td class="center">'+dataList[i].simPin
		+'</td><td class="center">'+dataList[i].notes
		+'</td><td class="center">'+dataList[i].manufacturer+'</td><td class="center">'+dataList[i].osVersion
		+'</td><td class="center">'+dataList[i].displayStatus
		+'</td><td class="center">'+dataList[i].udid
		+'</td>';
		if(dataList[i].displayStatus != "Available" && dataList[i].displayStatus != "In Use"){
			html+= '<td><div class="progress progress-striped active" style="width: 20px; margin-left: 25px;"><div id="progressBar'+dataList[i].id+'" class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="40" style="width: 100%"></div></div></td>';
		}else {
			if(dataList[i].uptime.split(":")[0] > 160){
				html+= '<td><div class="progress progress-striped active" style="width: 20px; margin-left: 25px;"><div id="progressBar'+dataList[i].id+'" class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="40" style="width: 100%"></div></div></td>';
			}else{
				html+= '<td><div class="progress progress-striped active" style="width: 20px; margin-left: 25px;"><div id="progressBar'+dataList[i].id+'" class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="40" style="width: 100%"></div></div></td>';
			}
		}
		html += "</tr>";
	}
	html += "</tbody>";
	return html;
}

function changeUser(){
	$("#reserveAndOpenButton").prop('disabled', false);
	$('#messages').html("");
}

function releaseDevice(selectedRow){
	debugger;
	var deviceId = selectedRow.find("td").eq(0).html();
	var status = selectedRow.find("td").eq(10).html();
	if(status == "Available"){
		alertify.alert("Device is already available");
		return;
	}else if(status != "In Use"){
		alertify.alert("Incorrect device status! Device cannot be released.");
		return;
	}
	var jsonObj = {
			"id" : deviceId ,
	};
	var jsonString = JSON.stringify(jsonObj) ;
	$.ajax({
		type: 'POST',
		url: "releaseDevice", 
		data: jsonString,
		dataType: "json",
		contentType:'application/json',
		success: function (jsonObj) {
			var key = jsonObj.messages;
			if(key == "SUCCESS"){
				alertify.alert("Device released successfully!");
				setTimeout(function(){
					//wait for sometime before reload
				}, 5000);
				reloadTable();
			}else {
				alertify.alert("Error releasing device!");
			}
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function reserveAndOpenDevice(){
	$("#reserveAndOpenButton").prop('disabled', true);
	var selectedButton = $('input[name=reserveDeviceFor]:checked').val();
	var user = $('#user').val();
	var devicesSelected = $('#devices').val();
	var index = devicesSelected.indexOf("Device Status :");
	var statusString = devicesSelected.substr(index,35);
	var validFlag = true;
	if (selectedButton == "Other User" && (user == "-Select-")){
		$('#messages').css("color", "red").html("Select a user to reserve device.");
		validFlag = false;
	} else if($('#duration').val() != "" && $('#unit').val() == ""){
		$('#messages').css("color", "red").html("Select duration unit");
		validFlag = false;
	} else if(devicesSelected ==  "-Select-"){
		$('#messages').css("color", "red").html("Select device to reserve");
		validFlag = false;
	}else if(!statusString.includes("Available")){
		$('#messages').css("color", "red").html("Selected device is unavailable");
		validFlag = false;
	}

	var jsonObj = {
			"user" : user ,
			"selectedDeviceUdid" : $('#devices').val()
	};
	var jsonString = JSON.stringify(jsonObj) ;
	if (validFlag ==true){
		$.ajax({
			type: 'POST',
			url: "reserveDeviceForUser", 
			data: jsonString,
			dataType: "json",
			contentType:'application/json',
			success: function (jsonObj) {
				var key = jsonObj.messages;
				if(key == "SUCCESS"){
					$('#messages').text("Device reserved successfully").css("color", "green");
					alertify.success("Device reserved successfully");
					$('#deviceDescription').html($('#deviceDescription').html().replace("Available","In Use"));
					//$("#reserveAndOpenButton").prop('disabled', false);
				}else {
					$('#messages').text("Error reserving device : "+ key).css("color", "red");
					alertify.error("Error reserving device : "+ key);
					//$("#reserveAndOpenButton").prop('disabled', false);
				}
			},
			error: function (error) {
				alertify.alert("System encountered a problem, please try again later");
				$("#reserveAndOpenButton").prop('disabled', false);
			}
		});
		setTimeout(function(){
			//wait for sometime before reload
		}, 5000);
		reloadTable();
	}
}

function resetReserveDevice(){
	$('#reserveDeviceForm')[0].reset();
	$("#user").prop("disabled", true).css("background-color",
	"#eee").val("-Select-");
	$('#popover').css("display", "none");
	$('#messages').html("");
	$("#reserveAndOpenButton").prop('disabled', false);
}


function readLogFilePeriodically(){
	timerForLogFile = setInterval(function() {
		var jsonObj = {
				"filePos" : pos ,
		};
		var jsonString = JSON.stringify(jsonObj) ;
		$.ajax({
			type: 'POST',
			url: "readLogFile", 
			data: jsonString,
			dataType: 'json',
			contentType:'application/json',
			success: function (jsonObj) {
				var content = jsonObj.messages;
				pos = jsonObj.filePos;
				var htmlContent = generateLogHtml(content);
				$('#logMessage').append(htmlContent);
			},
			error: function (error) {
				alertify.alert("System encountered a problem, please try again later");
			}
		});
	}, 1000 * 60 * 0.01);
}

function generateLogHtml(content){
	var htmlContent = content;
	var sidePanelContent ="";
	if(content.indexOf('Device picked') > -1) {
		contentId++;
		htmlContent = '<br><b>&nbsp;' +content+ '</b>' + '<div class="progress progress-striped active" style="width:430px"><div id="progressBar'+contentId+'" class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="40" style="width: 20%"></div></div>';
		var index = htmlContent.indexOf("Device picked for maintenance");
		sidePanelContent = htmlContent.substring(index+32, htmlContent.indexOf("<br>", index));
		$('#logMessageSidePanel').append(generateSidePanelContent(sidePanelContent,contentId));
	}else if(content.indexOf('Clearing') > -1){
		$("#progressBar"+contentId).css({'width': '40%'});
	}else if(content.indexOf('cleared') > -1){
		$("#progressBar"+contentId).css({'width': '50%'});
	}else if(content.indexOf('Rebooting') > -1){
		$("#progressBar"+contentId).css({'width': '65%'});
	}else if(content.indexOf('required') > -1){
		$("#progressBar"+contentId).css({'width': '70%'});
	}else if(content.indexOf('entered') > -1){
		$("#progressBar"+contentId).css({'width': '80%'});
	}else if(content.indexOf('Error') > -1){
		$("#progressBar"+contentId).removeClass("progress-bar progress-bar-success").addClass("progress-bar progress-bar-danger");
		$("#progressBar"+contentId).css({'width': '100%'});
		$("#sign"+contentId).html('<button type="button" class="btn btn-danger btn-circle"><i class="fa fa-times"></i></button>');
	}else if(content.indexOf('released') > -1){
		$("#progressBar"+contentId).css({'width': '100%'});
		if($("#progressBar"+contentId).hasClass("progress-bar progress-bar-danger")){
			$("#sign"+contentId).html('<button type="button" class="btn btn-danger btn-circle"><i class="fa fa-times"></i></button>');
		}else{
			$("#sign"+contentId).html('<button type="button" class="btn btn-success btn-circle"><i class="fa fa-check"></i></button>');
		}
	}
	if(content.indexOf('(ms)') > -1) {
		var index = content.indexOf("(ms)")+7;
		$("#duration"+contentId).html(content.substring(index, content.indexOf("<br>",index)));
	}
	return htmlContent;
}

function generateSidePanelContent(sidePanelContent, contentId){
	var html = '<li class="left clearfix"><div class="chat-body clearfix" style="margin-left: 0px;"><div class="header">'
		+'<span id="sign'+contentId+'"><button type="button" class="btn btn-info btn-circle"><i class="fa fa-wrench"></i></button></span>&nbsp;<strong class="primary-font">'+sidePanelContent+'</strong> <small class="pull-right text-muted">'
		+ '<i class="fa fa-clock-o fa-fw"></i> <span id="duration'+contentId+'"> In progress.</span> </small></div></div></li>';
	return html;
}

function downloadLogPDF(){
	var jsonObj = {
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "downloadConsoleLogPDF",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			var key = jsonObj.messages;
			if(key == "SUCCESS"){
				alertify.success("Maintenance logs downloaded successfully!");
			}else {
				alertify.error("Error downloading maintenance logs!");
			}
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function getUSBChargingInfo(){
	var jsonObj = {
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "getUSBChargingInfo",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			alert(jsonObj);
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function startDeviceMaintenance(){
	/*
	 * Add validation to check for offline/In use devices
	 */
	var table = $('#dataTables-example').DataTable();
	var a= [];
	debugger;
	$.each(table.rows('.selected').data(), function() {
		debugger;
//		$('#progressBar'+this[0]).removeClass('progress-bar-danger').addClass('progress-bar-warning');
//		$('#progressBar'+this[0]).css({'width': '10%'});
		if(this[10] == "Available" ){
			a.push(this[11]);
		}
	});
	if(a.length > 0){
		//var flag = confirm("Please confirm to start device maintenance!");
		alertify.confirm('<div class="form-group"><label>Select maintenance activity to perform : </label><div class="checkbox" style="margin-left: -8px;"><label>'+
				'<input class="checkbox" type="checkbox" value="1">Clear Device cache<span style="color: red">*</span></label></div><div class="checkbox" style="margin-left: -41px;"><label><input type="checkbox" value="2">Reboot Device'+
				'</label></div><div class="checkbox"><label><input type="checkbox" value="3">Dim Brightness level<span style="color: red">*</span></label></div><span style="color: red">*[Android specific features]</span>'+
				'<div>Please confirm to start device maintenance!', function (e) {
			if(e){
				var selected = [];
				$('.checkbox input:checked').each(function() {
					selected.push($(this).attr('value'));
				});
				if(selected.length > 0){
					$("#parent-dialog-container").empty();
					$("div.ui-dialog").remove();
					var div = '<div id="dialog-container" class="panel-body maintenanceDialog"></div>';
					if (!$("#parent-dialog-container > div.panel-body").length > 0){
						$("#parent-dialog-container").append(div);
					}
					createDialogBox();
					loadJspPage("maintenanceConsole","Device Maintenance Console","panel-red","red-border",1435);
					$("#dialog-container").css({"height":"700px"});
					alertify.set({ delay: 10000 });
					alertify.log("Device maintenance initiated!");
					$('#deviceMaintenanceButton').prop("disabled",true);
					var jsonObj = {
							udidArray : a,
							selectedActivityId  : selected
					};
					var jsonString = JSON.stringify(jsonObj) ;
					jQuery.ajax({
						url : "triggerDeviceMaintenance",
						type : "POST",
						//async: false,
						data : jsonString,	
						contentType:'application/json',
						dataType:'json',
						success: function (jsonObj) {
							$('#deviceMaintenanceButton').prop("disabled",false);
							$('#downloadPdfLog').css("visibility","visible");
						},
						error: function (error) {
							alertify.alert("System encountered a problem, please try again later");
							$('#deviceMaintenanceButton').prop("disabled",false);
							if(timerForLogFile!= null  && timerForLogFile!= undefined){
								clearInterval(timerForLogFile);
							}
						}
					});
				}else{
					alertify.alert("Please select atleast one activity to proceed!");
				}

			}
		});
	}else {
		alertify.alert("Please select atleast one \"AVAILABLE\" device to start maintenance!");
	}
}

function exportToExcelDeviceInfo(){

	var jsonObj = {
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "exportToExcelDeviceInfo",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			var key = jsonObj.messages;
			if(key == "SUCCESS"){
				alertify.success("Download successful!");
			}else {
				alertify.error("Error downloading file!");
			}
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});

}

function getDeviceDetailsForStatistics(){
	var dataForGraph = [];
	var jsonObj = {
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "getDeviceDetailsForStatistics",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			debugger;
			dataForGraph.push(jsonObj.dataForDeviceStatistics[0]);
			dataForGraph.push(jsonObj.dataForDeviceStatistics[1]);
			dataForGraph.push(jsonObj.dataForDeviceStatistics[2]);
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
	return dataForGraph;
}


function sendBroadcastMail(){
	if($('#subject').val().length == 0){
		$('#messages').html("Please enter Subject!");
		return;
	}
	$('#messages').html("");
	alertify.confirm("Do you want to send the broadcast?", function (e) {
		if(e){
			//alertify.log("hello");
			var dataForModelGraph = [];
			var jsonObj = {
					id : "100",
					subject : $('#subject').val() ,
					messageContent : $('#messageContent').val() 
			};
			var jsonString = JSON.stringify(jsonObj) ;
			jQuery.ajax({
				url : "sendBroadcastMail",
				type : "POST",
				async: false,
				data : jsonString,	
				contentType:'application/json',
				dataType:'json',
				success: function (jsonObj) {
					alertify.alert(jsonObj.messages);
				},
				error: function (error) {
					alertify.alert("System encountered a problem, please try again later");
				}
			});
		}

	});
}

function isEmpty(value){
	if(value == "" || value == null || value == undefined || value.trim() == ""){
		return true;
	}else{
		return false;
	}
}

function saveMailConfigDetails(){
	debugger;
	$('#adminControlMessages').html("");
	var smtpHost = $('#smtpHost').val();
	var smtpPort = $('#smtpPort').val();
	var outlookEmail = $('#userMail').val();
	var outlookPassword = $('#mailPwd').val();
	if(isEmpty(smtpHost) || isEmpty(smtpPort) || isEmpty(outlookEmail) || isEmpty(outlookPassword)){
		$('#adminControlMessages').html("Please provide the mandatory input(s).");
		return;
	}
	var jsonObj = {
			id : "100",
			smtpHost : smtpHost,
			smtpPort : smtpPort,
			outlookEmail : outlookEmail,
			outlookPassword : outlookPassword
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "saveAdminConfigDetails",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			if(jsonObj.messages == "SUCCESS"){
				alertify.alert("Details saved successfully!");
			}else{
				alertify.alert("Data input incorrect! Please validate the inputs again.");
			}
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
}

function addData(){
	var options = $('#devices option:selected');

	var values = $.map(options ,function(option) {
		return option.value;
	});
	$('#deviceSelected').append(values);
}

function removeData(){
	$('#deviceSelected').remove();
}

