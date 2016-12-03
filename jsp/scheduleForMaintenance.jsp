<!-- /**
 * Description : Schedule for maintenance JSP
 * @author Sauveer Pandey
 * Date : 16 Aug 2016
 */ -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="scripts/flatpickr.js"></script>
<link href="css/flatpickr.min.css" rel="stylesheet">
<link href="css/multi-select.css" rel="stylesheet">
<script src="scripts/jquery.multi-select.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Schedule For Maintenance</title>
<%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
<script>
	$('#datetimepicker').datetimepicker({
		timeFormat : "hh:mm tt"
	});
</script>
</head>
<body>
	<div class="panel panel-default"
		style="width: 370px; border-color: white;">
		<div class="panel-body"
			style="height: 382px; width: 850px; margin: -6px; background-color: antiquewhite;">
			<div>
				<label>Schedule at :</label> <input type="text" value=""
					id="datetimepicker" style="padding-left: 5px; width: 135px;">&nbsp;<span
					class="fa fa-clock-o"></span>
				<div class="checkbox">
					<label><input type="checkbox" value="1">Select All</label>
				</div>
			</div>

			<br> <span class="fa fa-angle-double-right"
				style="font-size: 25px; position: absolute; margin-left: 393px; margin-top: 100px;"></span><span
				class="fa fa-angle-double-left"
				style="position: absolute; margin-left: 392px; margin-top: 122px; font-size: 25px;"></span>
			<div class="form-group" style="width: 700px;">
				<label style="position: absolute">Select device(s) for
					maintenance scheduling :</label><label style="margin-left: 440px">Selected
					device(s) :</label> <select id="devices" multiple="multiple"
					class="form-control" style="height: 175px;">
				</select>
			</div>
			<div>
				<button type="button" class="btn btn-danger"
					onclick="alertify.alert('Maintenance scheduled succesfully!')">Schedule</button>
			</div>

		</div>
	</div>
</body>
<script>
	populateDevices(false, "Available");
	$('#devices').multiSelect();
</script>
</html>