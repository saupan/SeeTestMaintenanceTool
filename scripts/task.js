/**
 * All validation etc related to task 
 * 1) Create New Task
 * 2) assign New Task
 */

function validateCreateNewTask(formNum){
	var taskName = $('#taskName'+formNum).val();
	var taskDesc = $('#taskDesc'+formNum).val();
	var validFlag = true;

	if(!taskName){
		$('#taskNameDiv'+formNum).addClass("has-error");
		$('#errorTaskName'+formNum).css('visibility', 'visible');
		validFlag= false;
	}
	if(!taskDesc){
		$("#taskDescDiv"+formNum).addClass("has-error");
		$('#errorTaskDesc'+formNum).css('visibility', 'visible');
		validFlag= false;
	}
	if (validFlag){
		removeErrorClass();
		var jsonString = JSON.stringify($("#addNewTask"+formNum).serializeObject()) ;
		ajaxCall("addNewTask", jsonString,"createNewTask");
	}

}

function resetForm(formNum){
	$('#addNewTask'+formNum)[0].reset();
	$('#messages').html("");
	removeErrorClass('taskNameDiv'+formNum, 'errorTaskName'+formNum);
	removeErrorClass('taskDescDiv'+formNum, 'errorTaskDesc'+formNum);
}

function taskDescPopover(){
	var e = document.getElementById("taskToAssign");
	document.getElementById('selectedTaskName').value  = e.options[e.selectedIndex].text;
	var value = $('#taskToAssign').val();
	$('#messages').css("color", "red").html("");
	var taskDesc = value.substring(value.indexOf('^')+1, value.length);
	if(taskDesc == '-Select-'){
		$('#popover').css("display","none");
	}else{
		$('#taskDescription').html(taskDesc);
		$('#popover').css("display","block");
	}
}

function resetAssignTask(){
	$('#assignNewTask')[0].reset();
	$("#user").prop("disabled", true).css("background-color",
	"#eee").val("-Select-");
	$("#subordinate").prop("disabled", true).css(
			"background-color", "#eee").val("-Select-");
	$('#popover').css("display", "none");
	$('#messages').css("color", "red").html("");

}

function getOnLoadDataAssignTask(userId){
	$.ajax({
		type: 'POST',
		url: "getOnLoadDataAssignTask", 
		data: JSON.stringify(userId),
		dataType: "json",
		contentType:'application/json',
		success: function(data) {
			var flag= false;
			$.each(data.dataList,function(i,data)
					{
				if(data[0] == "listSeparator"){
					flag =true;
					return;
				}
				if(flag== true){
					var div_data_task="<option value='"+data[0]+"^"+data[2]+"'>"+data[1]+"</option>";
				} else{
					var div_data_user="<option value="+data[0]+">"+data[1]+"</option>";
				}
				$(div_data_user).appendTo('#user');
				$(div_data_task).appendTo('#taskToAssign');
					});  
		}
	});
}

function populateSubordinateUsers(){
	$("#subordinate").prop("disabled", true).css(
			"background-color", "#eee");
	$('#subordinate').empty().append("<option>-Select-</option>");
	$('#subordinateAddNewUser').empty();
	debugger;
	var userId= $('#user').val();
	$.ajax({
		type: 'POST',
		url: "getSubordinateUsers", 
		data: userId,
		dataType: "json",
		contentType:'application/json',
		success: function(data) {
			$.each(data.dataList,function(i,data)
					{
				var div_data_subordinate="<option value="+data[0]+">"+data[1]+"</option>";
				if(div_data_subordinate){
					$("#subordinate").prop("disabled", false).css(
							"background-color", "#fff");
					$(div_data_subordinate).appendTo('#subordinate');
					$(div_data_subordinate).appendTo('#subordinateAddNewUser');

				}
					});  
		}
	});
}

function adhocTaskDivControl(activeTab){
	if(activeTab == 'task'){
		$('#taskToAssign').show();
		$('#adhocTaskToAssign').hide();
		$('#adhocTaskName').val("");
		$('#adhocTaskDesc').val("");
		$('#messages').css("color", "red").html("");
	}else if(activeTab == 'adhoc'){
		$('#taskToAssign').hide();
		$('#popover').css("display", "none");
		$('#adhocTaskToAssign').show();
		$('#taskToAssign').val("-Select-");
		$('#messages').css("color", "red").html("");
	}	
}

function validateAssignNewtask(){
	var selectedButton = $('input[name=assignTaskTo]:checked').val();
	var adhocTaskName = $('#adhocTaskName').val();
	var user = $('#user').val();
	var validFlag = true;
	if (selectedButton == "Subordinate Task" && (user == "-Select-")){
		$('#messages').css("color", "red").html("Select a user to assign task.");
		validFlag = false;
	}
	else if($('#taskToAssign').val() == "-Select-" && 
			(adhocTaskName == null || adhocTaskName == undefined 
					|| adhocTaskName =="")){
		$('#messages').css("color", "red").html("Please select or add a task to assign!");
		validFlag = false;
	}else if($('#efforts').val() != "" && $('#unit').val() == ""){
		$('#messages').css("color", "red").html("Select effort unit");
		validFlag = false;

	}
	if (validFlag ==true){
		var jsonString = JSON.stringify($("#assignNewTask").serializeObject()) ;
		ajaxCall("assignNewTask", jsonString);

	}
}

function moveToRightBox(divCode){
	var projectCode = $('#projectCode').val();
	var roleCode = $('#roleCode').val();
	if (divCode == 'pc' && !(projectCode == null || projectCode == undefined 
			|| projectCode =="" )){
		var append_data="<option value="+projectCode+">"+projectCode+"</option>";
		$(append_data).appendTo('#addedProjectCodes');
		$('#projectCode').val('');
	} else if(divCode ='rc' && !(roleCode == null || roleCode == undefined 
			|| roleCode =="" )){
		var append_data="<option value="+roleCode+">"+roleCode+"</option>";
		$(append_data).appendTo('#addedRoleCodes');
		$('#roleCode').val('');

	}
}

function removeFromList(divCode){
	var addedProjectCodes = $('select#addedProjectCodes').val()
	var addedRoleCodes = $('select#addedRoleCodes').val()
	if (divCode == 'pc' && !(addedProjectCodes == null || addedProjectCodes == undefined 
			|| addedProjectCodes =="" )){
		addedProjectCodes.forEach(function(item) {
			$("#addedProjectCodes option[value="+item+"]").remove();
		});
	} else if(divCode ='rc' && !(addedRoleCodes == null || addedRoleCodes == undefined 
			|| addedRoleCodes =="" )){
		addedRoleCodes.forEach(function(item) {
			$("#addedRoleCodes option[value="+item+"]").remove();
		});
	}
	
}