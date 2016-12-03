<!-- /**
 * Description : Dashboard JSP
 * @author Sauveer Pandey
 * Date : 19 June 2016
 */ -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>Dashboard</title>

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

<script type="text/javascript"
	src="scripts/jquery.datetimepicker.full.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/jquery.datetimepicker.css">

<script>
	$(document)
			.ready(
					function() {
						var table = $('#dataTables-example').DataTable({
							dom : 'Bfrtip',
							//bDestroy: true,
							select : {
								style : 'multi'
							},
							//select : true,
							buttons : [ {
								text : 'Select All',
								action : function() {
									table.rows().select();
								}
							}, {
								text : 'Select None',
								action : function() {
									table.rows().deselect();
								}
							} ]

						});
						table.on('select', function(e, dt, type, indexes) {
							var rowData = table.rows(indexes).data().toArray();
						}).on('deselect', function(e, dt, type, indexes) {
							var rowData = table.rows(indexes).data().toArray();
						});
						$(document)
								.contextmenu(
										{
											delegate : ".dataTable tr",
											menu : [ {
												title : "<i class='fa fa-unlock'></i><b> Release Device</b>",
												cmd : "release",
											} /* ,
																																																																																																								{
																																																																																																									title : "<i class='fa 	fa-wrench'></i><b> Start device maintenance</b>",
																																																																																																									cmd : "maintain",
																																																																																																								} */],
											select : function(event, ui) {
												debugger;
												var selectedRow = ui.target
														.parent();
												var celltext = ui.target.text();
												var colvindex = ui.target
														.parent().children()
														.index(ui.target);
												var colindex = $(
														'table thead tr th:eq('
																+ colvindex
																+ ')').data(
														'column-index');
												table.rows().deselect();
												switch (ui.cmd) {
												case "release":
													//$(selectedRow).addClass('selected');

													releaseDevice(selectedRow);
													//alert("release::" +selectedRow);
													/* table.column(colindex).search(
															'^' + celltext + '$', true).draw(); */
													//$(selectedRow).removeClass('selected');
													break;
												case "maintain":
													//$(selectedRow).addClass('selected');
													alert("Call maintenance method");
													/* table.search('').columns().search('').draw(); */
													//$(selectedRow).removeClass('selected');
													break;
												}
											},
											beforeOpen : function(event, ui) {
												table.rows().deselect();
												var $menu = ui.menu, $target = ui.target, extraData = ui.extraData;
												ui.menu.zIndex(9999);
											}
										});
					});
</script>
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0;">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<b class="navbar-brand">SeeTest Maintenance Tool (v1.0)</b>
			<!-- <a class="navbar-brand" href="javascript: void(0)"><b>SeeTest
					Maintenance Tool (v1.0)</b></a> -->
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <i class="fa fa-envelope fa-fw"></i>
					<i class="fa fa-caret-down"></i>
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
				data-toggle="dropdown" href="#"> <i class="fa fa-tasks fa-fw"></i>
					<i class="fa fa-caret-down"></i>
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
				data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i>
					<i class="fa fa-caret-down"></i>
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
				data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
					<i class="fa fa-caret-down"></i>
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
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><a href="javascript: void(0)"
						class="popup-link reloadDashboard"><i
							class="fa fa-dashboard fa-fw"></i> Dashboard</a></li>
					<li><a href="#"><i class="fa fa-laptop fa-fw"></i> Devices<span
							class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a href="javascript: void(0)" data-href="reserveDevice"
								class="popup-link reserveDevice"><i class="fa fa-lock fa-fw"></i>
									Reserve a device</a></li>
							<li><a href="javascript: void(0)"
								data-href="showUSBChargingControl"
								class="popup-link showUSBChargingControl"><i
									class="fa fa-flash fa-fw"></i> USB Charging control</a></li>
						</ul> <!-- /.nav-second-level --></li>
					<li><a href="javascript: void(0)"
						data-href="showDeviceStatistics"
						class="popup-link showDeviceStatistics"><i
							class="fa fa-bar-chart-o fa-fw"></i> Device Statistics</a></li>
					<!-- <li><a href="#"><i class="fa fa-wrench fa-fw"></i>Admin
							Control Panel<span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<li><a href="#" data-href="addNewUser"
								class="popup-link addNewUser">Tab 1</a></li>
							<li><a href="buttons.html">Tab 2</a></li>
							<li><a href="#" data-href="controlSettings"
								class="popup-link controlSettings">Control Settings</a></li>
						</ul> /.nav-second-level</li> -->
					<li><a href="javascript: void(0)" data-href="broadcastMessage"
						class="popup-link broadcastMessage"><i class="fa fa-bullhorn"></i>
							Broadcast</a></li>
					<li><a href="javascript: void(0)"
						data-href="adminControlPanel" class="popup-link adminControlPanel"><i
							class="fa fa-wrench fa-fw"></i> Admin Control Panel</a></li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header-dashboard">Dashboard</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<img src="images/tasks.png"></img>
								</div>
								<div class="col-xs-9 text-right">
									<div id="availableCount" class="huge">0</div>
									<div>Devices Available</div>
								</div>
							</div>
						</div>
						<a href="javascript: void(0)" data-href="" class="popup-link">
							<div class="panel-footer">
								<span class="pull-left">View available devices</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<img src="images/assignTask.png" width="70px" height="70px"></img>
								</div>
								<div class="col-xs-9 text-right">
									<div id="reservedCount" class="huge">0</div>
									<div>Reserved</div>
								</div>
							</div>
						</div>
						<a href="javascript: void(0)" data-href="reserveDevice"
							class="popup-link reserveDevice">
							<div class="panel-footer">
								<span class="pull-left">Reserve a device</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-red">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<img src="images/reel.png"></img>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">0</div>
									<div>Scheduled</div>
								</div>
							</div>
						</div>
						<a href="javascript: void(0)" data-href="scheduleForMaintenance"
							class="popup-link scheduleForMaintenance">
							<div class="panel-footer">
								<span class="pull-left">Schedule for maintenance</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-yellow">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<img src="images/controlPanel.png" height="80px" width="100px"></img>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge">Admin</div>
									<div>Control Panel</div>
								</div>
							</div>
						</div>
						<a href="javascript: void(0)" data-href="adminControlPanel"
							class="popup-link adminControlPanel">
							<div class="panel-footer">
								<span class="pull-left">Go To Control Panel</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
			</div>
			<!-- /.row -->
			<div id="usableDivArea">
				<div class="panel panel-default">
					<div class="panel-heading">
						<label><b>Cloud Devices</b></label>
						<div class="form-group"
							style="margin-left: 115px; margin-top: -30px; position: absolute;">
							<select id="deviceStatus" name="deviceStatus"
								class="form-control-try" style="width: 105px"
								onChange="reloadTable();">
								<option value="">All</option>
								<option>Available</option>
								<option>In Use</option>
								<option>Offline</option>
							</select>
						</div>
						<div class="form-group" style="float: right; margin-top: -2px;">
							<button id="deviceMaintenanceButton" type="button"
								class="btn btn-info" onclick="startDeviceMaintenance();"
								style="margin-top: -16px; padding-top: 3px;">Start
								device maintenance</button>
							<button id="reloadDeviceTable" onclick="reloadTable();"
								style="width: 30px; height: 30px; margin-top: -2px;">
								<img src='images/refreshButton.png'
									style="width: 31px; height: 31px; margin-top: -3px; float: right; margin-right: -8px;">
							</button>
							<button id="exportToExcelDeviceInfo"
								onclick="exportToExcelDeviceInfo();"
								style="width: 30px; height: 30px; margin-top: -3px; margin-right: 7px;">
								<img src='images/exportToExcel.png'
									style="width: 30px; height: 31px; margin-top: -3px; float: right; margin-right: -8px;">
							</button>
						</div>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="dataTable_wrapper">
							<div id="dataTables-example_wrapper"
								class="dataTables_wrapper form-inline dt-bootstrap no-footer">
								<div class="row">
									<div class="col-sm-12">
										<table
											class="table table-striped table-bordered table-hover dataTable no-footer"
											id="dataTables-example" role="grid"
											aria-describedby="dataTables-example_info">
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- /.panel-body -->
				</div>
			</div>
			<!-- /.col-lg-4 -->
		</div>
		<!-- /.row -->
	</div>

	<div id="parent-dialog-container"></div>
	<script>
		getAllDevices();
	</script>
</body>
</html>
