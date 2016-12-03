var paginationMap = {};
var INITIAL_PAGE = "initialPage";
var FIRST_PAGE = "firstPage";
var PREVIOUS_PAGE = "previousPage";
var NEXT_PAGE = "nextPage";
var LAST_PAGE = "lastPage";
var GOTO_PAGE = "goToPage";
var userId = null;
var userRole = null;
var taskId = null;
var status = null;
var statusIndex = null;
var myTaskStatus = null;
var taskStatusMap = {
		ACTIVE : "ACTIVE",
		ONHOLD : "ON HOLD",
		CLOSED : "CLOSED"	
};

var allTasksColumnHeaders = new Array();
var myTasksColumnHeaders = new Array();
var selectedRow = null;
var columnHeaders = {
		TaskID : "Task ID",
		TaskName : "Task Name",
		TaskDescription : "Task Description",
		TaskStatus : "Task Status",
		ActualEfforts : "Actual Efforts"
};
var assignReq = false;
var myTaskStatusMap = {
		OPEN : "OPEN",
		WIP : "WIP",
		COMPLETED : "COMPLETED"	
};
var myTaskId = null;
var dataRequest = {
		MY_TASKS : "myTasks",
		ALL_TASKS : "allTasks" 
}; 
var requestType = {
	DELETE : "delete",
	SAVE : "save",
	SUBMIT : "submit"
};
var selectedDate = getCurrentSystemDate();
var EventType = {
		CLICK : "click",
		KEYPRESS:"keypress",
		KEYUP : "keyup",
		CONTEXTMENU : "contextmenu"
};
var TableID = {
		 MY_TASK_TABLE_ID : "myTaskDetailsTable",
		 ALL_TASK_TABLE_ID : "taskDetailsTable"
};
var ClassName = {
	MY_TASK_TABLE_CLASS : "myTaskTable",
	TrSelected : "trSelected"
};

function getCurrentSystemDate() {
	var date = new Date();
 	var day = date.getDate();
 	if(day < 10) {
 		day = "0" + day;
 	}
 	var month = date.getMonth() + 1;
 	if(month < 10) {
 		month = "0" + month;
 	}
 	var currDate = day + "-" + month + "-" + date.getFullYear();
 	return currDate;
}
