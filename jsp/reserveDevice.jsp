<!-- /**
 * Description : Reserve device JSP
 * @author Sauveer Pandey
 * Date : 09 July 2016
 */ -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	$("form input:radio").change(
			function() {
				var selectedButton = $('input[name=reserveDeviceFor]:checked')
						.val();
				if (selectedButton == "Own User") {
					$("#user").prop("disabled", true).css("background-color",
							"#eee").val("-Select-");
					$("#reserveAndOpenButton").prop('disabled', false);
					$("#messages").html("");
				} else {
					$("#user").prop("disabled", false).css("background-color",
							"#fff");
					$("#reserveAndOpenButton").prop('disabled', false);
					$("#messages").html("");
				}

			});
</script>
<script src="scripts/global.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reserve Device</title>
<%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
</head>
<body>
	<div>
		<form id="reserveDeviceForm" action="#" method="post">
			<div class="form-group">
				<label>Reserve For :</label> &nbsp;<label class="radio-inline">
					<input type="radio" name="reserveDeviceFor" id="assignTask"
					value="Own User" checked>Own
				</label> <label class="radio-inline"> <input type="radio"
					name="reserveDeviceFor" id="assignTask" value="Other User">Other
					User
				</label>
			</div>

			<div class="row width-103Per alert alert-info alert-dismissable"
				style="width: 750px; height: 83px; margin-left: 5px">
				<div class="form-group">
					<span class="fa fa-user"></span> <label><b>User</b></label><span
						class="mandatory">*</span> <select id="user" name="user"
						class="form-control-try" onChange="changeUser();"
						style="background-color: #eee" disabled>
						<option>-Select-</option>
					</select>
				</div>
				<div class="alert alert-info alert-dismissable"
					style="width: 750px; height: 250px; margin-left: -17px">
					<div class="form-group">
						<label><b>Cloud device</b></label> <span class="mandatory">*</span>
						<select id="devices" name="selectedDevice"
							class="form-control-try" onChange="deviceDescPopover();"
							style="margin-top: 15px">
							<option>-Select-</option>
						</select> <input type="hidden" id="selectedDeviceName"
							name="selectedDeviceName" value="">
					</div>

					<div class="row width-103Per" style="width: 140px;">
						<div class="form-group " style="margin-left: 15px">
							<label style="width: 150px">Est. Reservation Time</label><input
								id="duration" name="estimatedDuration" class="form-control-try"
								style="width: 120px">
						</div>
						<br>
						<div class="form-group"
							style="margin-left: 155px; margin-top: -70px;">
							<select id="unit" name="effortUnit" class="form-control-try"
								style="width: 100px">
								<option value="">-Unit-</option>
								<option>Hrs.</option>
								<option>Mins</option>
							</select>
						</div>
					</div>
					<br>
					<button type="button" class="btn btn-default"
						id="reserveAndOpenButton" onClick="reserveAndOpenDevice()">Reserve
						Device</button>
					<button type="button" class="btn btn-default" id="reserveReset"
						onClick="resetReserveDevice()">Reset</button>
					<br> <span id="messages" class="mandatory"></span>

				</div>
			</div>
		</form>
	</div>

	<div class="popover fade right in" role="tooltip" id="popover"
		style="top: 148px; left: 287px; display: none;">
		<div class="arrow" style="top: 42px;"></div>
		<h3 class="popover-title">Device Description</h3>
		<div class="popover-content">
			<span id="deviceDescription" style="font-size: 12px;"></span>
		</div>
	</div>
</body>
<script>
	populateUsers();
	populateDevices(true, "Available");
</script>

</html>