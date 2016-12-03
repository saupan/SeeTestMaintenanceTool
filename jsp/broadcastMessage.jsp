<!-- /**
 * Description : Boradcast message JSP
 * @author Sauveer Pandey
 * Date : 20 Sep 2016
 */ -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="scripts/global.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Broadcast Message</title>
<%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
</head>
<body>

	<div class="form-group">
		<label>Subject : <span class="mandatory">*</span></label><input
			id="subject" name="subject" class="form-control-try"
			style="width: 100%">
	</div>
	<div class="form-group">
		<label>Message Content : </label>
		<textarea class="form-control" rows="10" id="messageContent"
			name="messageContent">


Regards,
SeeTest Admin.
		</textarea>
	</div>
	<br>
	<button type="button" class="btn btn-warning" id="sendBroadcastMail"
		onclick="sendBroadcastMail();" style="float: right">Send
		broadcast</button>
	<span id="messages" class="mandatory"></span>



</body>
</html>