<!-- /**
 * Description : Dashboard JSP
 * @author Sauveer Pandey
 * Date : 19 Sep 2016
 */ -->
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  --%>
<html lang="en">
<%-- <%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%> --%>
<head>
<%-- <fmt:setBundle basename="AlertMessages" var="alertMsg"/>--%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Homepage</title>
<link href="css/global.css" rel="stylesheet" type="text/css">

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="css/metisMenu.min.css" rel="stylesheet">

<!-- Timeline CSS -->
<link href="css/timeline.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="css/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- DataTables CSS -->
<link href="css/dataTables.bootstrap.css" rel="stylesheet">

<link href="css/jquery.dataTables.min.css" rel="stylesheet">
<link href="css/select.dataTables.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/buttons/1.2.1/css/buttons.dataTables.min.css">

<!-- DataTables Responsive CSS -->
<link href="css/dataTables.responsive.css" rel="stylesheet">

<link href="css/jquery-ui.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="css/jquery.e-calendar.css" />

<link rel="stylesheet" href="css/jquery.contextMenu.css" />


<link rel="stylesheet" href="css/alertify.core.css" />
<link rel="stylesheet" href="css/alertify.default.css" />
<script type="text/javascript" src="scripts/alertify.js"></script>
<!-- jQuery -->

<script type="text/javascript" src="scripts/jquery-1.11.3.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<!-- Custom Theme JavaScript -->
<script type="text/javascript" src="scripts/sb-admin-2.js"></script>
<!-- DataTables JavaScript -->
<script type="text/javascript" src="scripts/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="scripts/dataTables.bootstrap.min.js"></script>
<!-- <!-- Metis Menu Plugin JavaScript -->
<script type="text/javascript" src="scripts/metisMenu.min.js"></script>
<script type="text/javascript" src="scripts/jquery.e-calendar.js"></script>
<script type="text/javascript" src="scripts/jquery.contextMenu.js"></script>
<script type="text/javascript" src="scripts/constants.js"></script>
<script type="text/javascript" src="scripts/common.js"></script>
<script type="text/javascript" src="scripts/global.js"></script>
<script type="text/javascript" language="javascript"
	src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript"
	src="https://cdn.datatables.net/select/1.2.0/js/dataTables.select.min.js"></script>
<script type="text/javascript" language="javascript"
	src="https://cdn.datatables.net/buttons/1.2.1/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" language="javascript"
	src="https://cdn.datatables.net/plug-ins/1.10.12/api/fnReloadAjax.js"></script>
<script
	src="//cdn.datatables.net/plug-ins/725b2a2115b/api/fnFilterClear.js"></script>
<script
	src="//cdn.jsdelivr.net/jquery.ui-contextmenu/1.7.0/jquery.ui-contextmenu.min.js"></script>
</head>

<body style="background-image:url(images/backgroundImage.jpg);background-repeat: no-repeat;background-size: cover;">

 <div id="wrapper" >
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0; background-color: #8181F7">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<b class="navbar-brand" style="color: white">TCS Integrated
					Portal Solution (TIPS) -v1.0</b>
				<!-- <a class="navbar-brand" href="javascript: void(0)"><b>SeeTest
					Maintenance Tool (v1.0)</b></a> -->
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					style="color: white" data-toggle="dropdown" href="#"> <i
						class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-messages">
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>Read
									All Messages</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-messages --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					style="color: white" data-toggle="dropdown" href="#"> <i
						class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-tasks">
						<li><a href="#">
								<div>
									<p>
										<strong>Task 1</strong> <span class="pull-right text-muted">40%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100" style="width: 40%">
											<span class="sr-only">40% Complete (success)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 2</strong> <span class="pull-right text-muted">20%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-info" role="progressbar"
											aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
											style="width: 20%">
											<span class="sr-only">20% Complete</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 3</strong> <span class="pull-right text-muted">60%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-warning"
											role="progressbar" aria-valuenow="60" aria-valuemin="0"
											aria-valuemax="100" style="width: 60%">
											<span class="sr-only">60% Complete (warning)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 4</strong> <span class="pull-right text-muted">80%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-danger"
											role="progressbar" aria-valuenow="80" aria-valuemin="0"
											aria-valuemax="100" style="width: 80%">
											<span class="sr-only">80% Complete (danger)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>See
									All Tasks</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-tasks --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					style="color: white" data-toggle="dropdown" href="#"> <i
						class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-alerts">
						<li><a href="#">
								<div>
									<i class="fa fa-comment fa-fw"></i> New Comment <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-twitter fa-fw"></i> 3 New Followers <span
										class="pull-right text-muted small">12 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-envelope fa-fw"></i> Message Sent <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-tasks fa-fw"></i> New Task <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-upload fa-fw"></i> Server Rebooted <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>See
									All Alerts</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-alerts --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					style="color: white" data-toggle="dropdown" href="#"> <i
						class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#"><i class="fa fa-user fa-fw"></i> User
								Profile</a></li>
						<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
						</li>
						<li class="divider"></li>
						<li><a href="/T3App"><i class="fa fa-sign-out fa-fw"></i>
								Logout</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-static-side -->
		</nav>

		<div id="tableIcon" style="margin-top: 5%; margin-left: 30%;">
			<table>
				<tbody>
					<tr>
						<td style="padding-left: 25px; width: 330px;"><div
								class="panel panel-primary" style="height: 180px;">
								<div class="panel-heading" style="height: 137px;">
									<div class="row">
										<div class="col-xs-3" style="margin-top: 20px;">
											<i class="fa fa-gears fa-5x"></i>
										</div>
										<div class="col-xs-9 text-right" style="font-size: 25px;">
											<div>SeeTest Management Tool</div>
										</div>
									</div>
								</div>
								<a href="dashboard">
									<div class="panel-footer">
										<span class="pull-left">SeeTest Management</span> <span
											class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</div>
								</a>
							</div></td>
						<td style="padding-left: 25px; width: 330px;"><div
								class="panel panel-green" style="height: 180px;">
								<div class="panel-heading" style="height: 137px;">
									<div class="row">
										<div class="col-xs-3" style="margin-top: 20px;">
											<i class="fa fa-mobile fa-5x"></i>
										</div>
										<div class="col-xs-9 text-right"
											style="margin-top: 30px; font-size: 25px; text-align: center;">
											<div>InvenApp</div>
										</div>
									</div>
								</div>
								<a href="invenDashboard">
									<div class="panel-footer">
										<span class="pull-left">Manage Device Inventory</span> <span
											class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</div>
								</a>
							</div></td>
					</tr>
					<tr>
						<td style="padding-left: 25px; width: 330px;"><div
								class="panel panel-yellow" style="height: 180px;">
								<div class="panel-heading" style="height: 137px;">
									<div class="row">
										<div class="col-xs-3" style="margin-top: 20px;">
											<i class="fa fa-tasks fa-5x"></i>
										</div>
										<div class="col-xs-9 text-right"
											style="margin-top: 30px; font-size: 25px; text-align: center;">
											<div>Repository</div>
										</div>
									</div>
								</div>
								<a href="#">
									<div class="panel-footer">
										<span class="pull-left">Access repository</span> <span
											class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</div>
								</a>
							</div></td>
						<td style="padding-left: 25px; width: 330px;"><div
								class="panel panel-red" style="height: 180px;">
								<div class="panel-heading" style="height: 137px;">
									<div class="row">
										<div class="col-xs-3" style="margin-top: 20px;">
											<i class="fa fa-group fa-5x"></i>
										</div>
										<div class="col-xs-9 text-right"
											style="margin-top: 30px; font-size: 25px; text-align: center;">
											<div>TCU Group</div>
										</div>
									</div>
								</div>
								<a href="#">
									<div class="panel-footer">
										<span class="pull-left">TCU Dashboard</span> <span
											class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</div>
								</a>
							</div></td>
					</tr>
				</tbody>

			</table>
		</div>
	</div>
	<!-- /.row -->
	</div>
	<nav class="navbar navbar-default navbar-fixed-bottom"
		role="navigation" style="margin-bottom: 0; background-color: darkgrey">
		<b class="navbar-brand" style="color: white;">&copy; 2016-2017</b>
	</nav>
</body>


</html>