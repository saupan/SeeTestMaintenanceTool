/**
 * Description : Global javascript for rendering panels, dialogs etc
 * @author Sauveer Pandey
 * Date : 23 June 2016
 */

//$(document).ready(function(){
//getDatePicker();
//});

//function getDatePicker() {
//$('.datepicker').datepicker({
//inline : true,
//showOtherMonths : true,
//dayNamesMin : [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ],
//dateFormat : 'dd-mm-yy',
//onSelect: function (date) {

//}
//});
//}

$(".popup-link").off('click');
$(document).off('click', '.popup-link');
$(document).on('click', '.popup-link', function () {
	var url = $(this).attr("data-href");
	var title = null;
	$("#parent-dialog-container").empty();
	$("div.ui-dialog").remove();
	var div = '<div id="dialog-container" class="panel-body"></div>';
	if (!$("#parent-dialog-container > div.panel-body").length > 0){
		$("#parent-dialog-container").append(div);
	}
	createDialogBox();

	if($(this).hasClass("scheduleForMaintenance")) {
		title = "Schedule For Maintenance";
		loadJspPage(url,title,"panel-red","red-border",875);
	} else if($(this).hasClass("reserveDevice")) {
		title = "Reserve Device";
		loadJspPage(url,title,"panel-primary","blue-border",800);
	}else if($(this).hasClass("broadcastMessage")) {
		var validatedFlag = validateMailConfigData();
		debugger;
		if(validatedFlag){
			title = "Broadcast Message";
			loadJspPage(url,title,"panel-yellow","yellow-border",800);
		}else{
			alertify.alert("Mailbox details not configured. Please configure it before using this feature!");
			return;
		}
	}else if($(this).hasClass("reloadDashboard")) {
		location.reload();
	}else if($(this).hasClass("showDeviceStatistics")) {
		$("#usableDivArea").html("");
		$("#usableDivArea").load('deviceStatistics');
	}else if($(this).hasClass("showUSBChargingControl")) {
		$("#usableDivArea").html("");
		$("#usableDivArea").load('usbChargingControl');
	}else if($(this).hasClass("adminControlPanel")) {
		$("#usableDivArea").html("");
		$("#usableDivArea").load('adminControlPanel', {"id":"100"});
	}
});

function createDialogBox() {
	$("#dialog-container").dialog({
		autoOpen:false,
		modal:true,
		closeOnEscape : true,
		resizable: false,
		cancel: function() {
			dialog.dialog('close');
		},
		close:function() {
			$("#parent-dialog-container").empty();
			closeDialog();
			//dialog.dialog('close');
		}
	});
}

//function loadPageInDialog(url){
//	$("#dialog-container").load(url).dialog('open');
//	//$("div.ui-dialog").addClass("panel");
//	//$("div.ui-dialog").addClass("dialog-position");
//	addCloseIcon();
//	
//}

function loadJspPage(url,title,className,borderClass,width) {
	$("#dialog-container").load(url).dialog('open');
	$("#dialog-container").dialog({
		title:title,
		width : width,
		height:450
	});
	$("div.ui-dialog").addClass("panel");
	//$("div.ui-dialog").addClass("dialog-position");
	$("div.ui-dialog").addClass(className);
	$("div.ui-dialog-titlebar").addClass("panel-heading");

	addCloseIcon();

}

function addCloseIcon() {
	$("div.ui-widget-header").find("button.ui-button").html("");
	var html = "<span><img src='images/cancelled-image.png' class='close-icon'><span>";
	$("div.ui-widget-header").find("button.ui-dialog-titlebar-close").append(html);
}

function closeDialog() {
	$("#parent-dialog-container").empty();
	$("#dialog-container" ).dialog( "destroy" );
	$("#dialog-container").parent("div.ui-dialog").empty();
	$("#dialog-container").remove();
	if(timerForLogFile!= null  && timerForLogFile!= undefined){
		clearInterval(timerForLogFile);
	}
	location.reload();
}



function generateHeadersHtml(columnHeaderList) {
	var html = "<thead><tr>";
	$.each(columnHeaderList,function(index,element){
		html = html + "<th>" + element + "</th>";
	});
	html = html + "</tr></thead>";
	return html;
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function bindRightClick(tableId) {
	var element = $("#"+tableId +" tbody tr");
	bindGlobalClickEvent(element);
	$(element).off(EventType.CONTEXTMENU);
	$(element).on(EventType.CONTEXTMENU,function(event) {
		event.preventDefault();
		$(element).each(function(){
			if($(this).hasClass(ClassName.TrSelected)) {
				$(this).removeClass(ClassName.TrSelected);
			}
		});
		$(this).addClass(ClassName.TrSelected);
		var row = this;
		selectedRow = this;
		if(tableId == TableID.ALL_TASK_TABLE_ID) {
			status = $(row).find("td").eq(getColumnIndex(columnHeaders.TaskStatus, allTasksColumnHeaders)).text();
			getViewAllTaskRightClickMenu(tableId);
			viewAllRightClickEventsActions(tableId);
		} 

		taskId = $(this).find("td").eq(0).text();

		var trIndex = $(row).index() + 1;
		var top = trIndex * $(row).height();

		$(row).contextMenu({
			menu: tableId + '-myMenu'
		});

		var xPos = event.target.offsetLeft + 90;

		$("#"+tableId+'-myMenu').css("display","block");
		$("#"+tableId+'-myMenu').css("top",top+45);
		$("#"+tableId+'-myMenu').css("left",event.pageX - xPos - 50);
	});
}

function bindGlobalClickEvent(element) {
	$(element).off(EventType.CLICK);
	$(element).on(EventType.CLICK,function(){
		$(element).each(function(){
			if($(this).hasClass(ClassName.TrSelected)) {
				$(this).removeClass(ClassName.TrSelected);
			}
		});
		if($(this).parent("tbody").parent("table").attr("id") == TableID.MY_TASK_TABLE_ID 
				|| $(this).parent("tbody").parent("table").hasClass(ClassName.MY_TASK_TABLE_CLASS)) {
			myTaskId = $(this).find("td").eq(getColumnIndex(columnHeaders.TaskID, myTasksColumnHeaders)).text();
		} else {
			taskId = $(this).find("td").eq(getColumnIndex(columnHeaders.TaskID, allTasksColumnHeaders)).text();
		}
		$(this).addClass(ClassName.TrSelected);
	});
}

function refreshAllTaskGrid() {
	var trCount = 0;
	$("#taskDetailsTable").find("tbody tr").each(function(){
		trCount++;
	});
	if(trCount == 1) {
		if(paginationMap.pageOffset > 0) {
			paginationMap.pageOffset = paginationMap.pageOffset - paginationMap.recordLimit;
			paginationMap.pageNoLabel = paginationMap.pageNoLabel - 1;
		}
	}
	getViewAllTaskDetails();
}

function refreshMyTaskScreen() {
	getMyTasksDetails(selectedDate,"c-event-grid","c-event-list",null,null);
}

function getColumnIndex(columnName,array) {
	var index = $.inArray(columnName,array);
	return index;
}

function removeErrorClass(divId, errorId){
	$('#'+divId).removeClass("has-error");
	$('#'+errorId).css('visibility', 'hidden');
}

function decimalNumbersOnly() {
	$(".decimalNumberOnly").off(EventType.KEYPRESS);
	$(".decimalNumberOnly").on(EventType.KEYPRESS,function(event){
		var specialKeys = new Array();
		specialKeys.push(8);//backspace is allowed only
		specialKeys.push(46);//Dot is allowed
		specialKeys.push(104);//l or L or h or H or t or T allowed
		specialKeys.push(72);
		specialKeys.push(116);
		specialKeys.push(84);
		specialKeys.push(108);
		specialKeys.push(76);//l or L or h or H or t or T allowed
		//specialKeys.push(110);
		var keyCode = event.which ? event.which : event.keyCode;
		if(keyCode >= 48 && keyCode <= 57 || specialKeys.indexOf(keyCode)!=-1) {
			return true;
		} else {
			return false;
		}
	});
}

function singleDecimalOnly() {
	$(".singleDecimalOnly").off(EventType.KEYUP);
	$(".singleDecimalOnly").on(EventType.KEYUP,function(event) {
		var curVal = $(this).val();
		if(curVal && curVal.indexOf(".") != -1) {
			var length = curVal.length;
			if(length == 1) {
				$(this).val('');
			} else if(length > 1) {
				var count = 0;
				for(var index=0;index < length; index++) {
					if(curVal.charAt(index)==".") {
						count++;
					}
					if(count > 1) {
						var str = setCharAt(curVal, length-1, '');
						$(this).val(str);
					}
				}
			}
		}
	});
}

function setCharAt(str, index, chr) {
	if (index > str.length - 1) return str;
	return str.substr(0, index) + chr + str.substr(index + 1);
}

function validateMailConfigData(){
	var flag = false;
	var jsonObj = {
			id : "100"
	};
	var jsonString = JSON.stringify(jsonObj) ;
	jQuery.ajax({
		url : "validateMailConfigData",
		type : "POST",
		async: false,
		data : jsonString,	
		contentType:'application/json',
		dataType:'json',
		success: function (jsonObj) {
			debugger;
			if(jsonObj.messages == "SUCCESS"){
				flag =  true;
			}else{
				flag =  false;
			}
		},
		error: function (error) {
			alertify.alert("System encountered a problem, please try again later");
		}
	});
	return flag;
}
