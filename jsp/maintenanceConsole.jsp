<!-- /**
 * Description : Maintenance console JSP
 * @author Sauveer Pandey
 * Date : 07 August 2016
 */ -->
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="scripts/global.js"></script>
<script src="scripts/common.js"></script>

<script>
	$(document).ready(function() {
		readLogFilePeriodically();
	});
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Maintenance Console</title>
<%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
<script>
	$('#line').css({
		'width' : 2,
		'height' : 673,
		'background-color' : 'black',
		'left' : 380,
		'top' : 10,
		'position' : 'absolute'
	});
</script>
</head>
<body>

	<div class="row">
		<div class="col-lg-2" style="width: 28%;">
			<div class="panel panel-default"
				style="width: 370px; border-color: white;">
				<div class="panel-body" style="height: 647px; overflow: auto;">
					<ul class="chat">
						<div id="logMessageSidePanel"></div>
					</ul>
				</div>
			</div>
		</div>

		<div id="line"></div>

		<div class="col-lg-9" style="padding-left: 0px; width: 72%;">
			<div class="panel panel-default" style="border-color: white;">
				<div class="panel-heading"
					style="background-color: grey; border-color: grey;">Console</div>
				<div class="panel-body"
					style="padding: 15px; overflow: auto; height: 606px;">
					Initializing execution environment.<br> Triggering device
					maintenance...
					<button id="downloadPdfLog" onclick="downloadLogPDF();" 
						style="visibility: hidden; width: 175px; height: 41px; margin-top: -10px; float: right; ">
						<img src='images/pdfImage.png'
							style="width: 178px;height: 41px;margin-top: -3px;float: right;margin-right: -11px;">
					</button>
					<div id="logMessage"></div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>