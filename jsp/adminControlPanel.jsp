<!-- /**
 * Description : Admin Control Panel JSP
 * @author Sauveer Pandey
 * Date : 12 Oct 2016
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
<title>Admin Control Panel</title>
<%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
</head>
<body>
	<div class="panel panel-default">
		<div class="panel-heading"
			style="background-color: #f0ad4e; border-color: #f0ad4e;">
			<b>Admin Control Panel</b>
		</div>
		<div class="panel-body">
			<div class="panel-group" id="accordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseThree" class="" aria-expanded="true">Broadcast
								mail configuration</a>
						</h4>
					</div>
					<div id="collapseThree" class="panel-collapse collapse in"
						aria-expanded="true">
						<div class="panel-body">
						Note: Following details are mandatory to allow MS Outlook enabled mail broadcasts.
						<br>
						<br>
							<table>
								<tr>
									<td><label>Mail SMTP Host <span class="mandatory">*</span> :</label></td>
									<td><input id="smtpHost" name="smtpHost" value="${smtpHost}"
										class="form-control-try"
										style="width: 300px; margin-left: 20px;"></td>
								</tr>
								<tr>
									<td><label>Mail SMTP Port <span class="mandatory">*</span> :</label></td>
									<td><input id="smtpPort" name="smtpPort" value="${smtpPort}"
										class="form-control-try"
										style="width: 300px; margin-left: 20px;"></td>
								</tr>
								<tr>
									<td><label>User Mail id <span class="mandatory">*</span> :</label></td>
									<td><input id="userMail" name="userMail" value="${userMail}"
										class="form-control-try"
										style="width: 300px; margin-left: 20px;"></td>
								</tr>
								<tr>
									<td><label>Password <span class="mandatory">*</span> :</label></td>
									<td><input type="password" id="mailPwd" name="mailPwd"
										class="form-control-try"
										style="width: 300px; margin-left: 20px;"></td>
								</tr>
							</table>
							<button type="button" class="btn btn-success" style="float: right"
								id="saveMailConfigDetails" onClick="saveMailConfigDetails()">Save details</button>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseOne" aria-expanded="false" class="collapsed">Scheduler
								configuration</a>
						</h4>
					</div>
					<div id="collapseOne" class="panel-collapse collapse"
						aria-expanded="false" style="height: 0px;">
						<div class="panel-body">Content</div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseTwo" class="collapsed" aria-expanded="false">User
								data configuration</a>
						</h4>
					</div>
					<div id="collapseTwo" class="panel-collapse collapse"
						aria-expanded="false" style="height: 0px;">
						<div class="panel-body">Content</div>
					</div>
				</div>
			</div>
			<span id="adminControlMessages" class="mandatory"></span>
		</div>
	</div>

</body>
</html>