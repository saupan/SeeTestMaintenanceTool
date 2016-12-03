<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="scripts/common.js"></script>
<script src="scripts/global.js"></script>
<script src="scripts/raphael-min.js"></script>
<script src="scripts/morris.min.js"></script>

<script src="scripts/canvasjs.min.js"></script>

<!-- <script type="text/javascript" src="scripts/jquery.flot.js"></script>
<script type="text/javascript" src="scripts/jquery.flot.pie.js"></script> -->

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Device Statistics</title>
<%
	response.setHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
</head>
<body>
	<div class="panel-body" style="padding: 0px">

		<div class="col-lg-4" style="padding-right: 0px; width: 45%">
			<div class="panel-body" style="padding: 0px">
				<div class="panel panel-default" style="height: 485px;">
					<div class="panel-heading">
						<label><b>Devices based on OPCO</b></label>
					</div>
					<div class="panel-body">
						<div id="chartContainer1" style="height: 400px; width: 100%;"></div>
						<!-- <div id="flot-placeholder"
							style="width: 361px; height: 300px; position: relative;"></div>
						<div id="flot-memo"
							style="padding-left: 65px; width: 250px; height: 20px; text-align: center;"></div> -->
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-6" style="padding-right: 0px; width: 35%">
			<div class="panel-body" style="padding: 0px">
				<div class="panel panel-default" style="height: 485px;">
					<div class="panel-heading">
						<label><b>Device Models</b></label>
					</div>
					<div class="panel-body">
						<div id="bar-example"></div>
					</div>
				</div>

			</div>
		</div>
		<div class="col-lg-4" style="padding-right: 0px; width: 20%">

			<div class="panel-body" style="padding: 0px">
				<div class="panel panel-default" style="height: 485px;">
					<div class="panel-heading">
						<label><b>Device Availability chart</b></label>
					</div>
					<div class="panel-body">
						<div id="donut-example"></div>
					</div>
				</div>

			</div>
		</div>
		<div class="col-lg-8"
			style="padding-left: 0px; padding-right: 0px; width: 50%">
			<div class="panel-body" style="padding: 0px">
				<div class="panel panel-default" style="margin-left: 15px;">
					<div class="panel-heading">
						<label><b>Devices on Racks</b></label>
					</div>
					<div class="panel-body">
						<div id="area-example"></div>
					</div>
				</div>

			</div>
		</div>
		<div class="col-lg-4" style="padding-right: 0px; width: 27%">
			<div class="panel-body" style="padding: 0px">
				<div class="panel panel-default">
					<div class="panel-heading">
						<label><b>OS based data</b></label>
					</div>
					<div class="panel-body">
						<div id="chartContainer2" style="height: 342px; width: 100%;"></div>
						<!-- <div id="flot-placeholder1"
						style="width: 364px; height: 370px; position: relative;"></div> -->
					</div>

				</div>
			</div>

		</div>
</body>
<script type="text/javascript">
	var dataForModelGraph = [];
	var dataForOPCOBasedGraph = [];
	var dataForGraph = getDeviceDetailsForStatistics();
	debugger;

	$(document)
			.ready(
					function() {
						CanvasJS.addColorSet("colorSetCustomized", [ "#4661EE",
								"#EC5657", "#8FAABB", "#B08BEB", "#F5A52A",
								"#23BFAA", "#FAA586", "#DE000F", "#00A36A",
								"#7D0096", "#992B00", "#FF8000", "#1BCDD1",
								"#3EA0DD", "#BDBDBD", "#2EFEF7", "#F2F2F2",
								"#424242", "#80FF00", "#EB8CC6", "#AEB404",
								"#FA58D0", "#FE9A2E", "#2EFEF7", "#CED8F6",
								"#D8D8D8", "#088A85", "#886A08", "#0B3B17",
								"#380B61", "#F6CEEC", "#FF0000", "#FFFF00",
								"#D8F6CE", "#071019", "#A9F5A9", "#F6CECE",
								"#9FF781", "#3104B4", ]);
						var chart = new CanvasJS.Chart("chartContainer1", {
							colorSet : "colorSetCustomized",
							theme : "theme2",
							title : {
								text : ""
							},
							exportEnabled : true,
							data : [ {
								type : "pie",
								showInLegend : true,
								toolTipContent : "{y} device(s)",
								yValueFormatString : "",
								legendText : "{indexLabel}",
								dataPoints : dataForGraph[1]
							} ]
						});
						chart.render();
						var chart2 = new CanvasJS.Chart("chartContainer2", {
							title : {
								text : ""
							},
							exportEnabled : true,
							animationEnabled : true,
							data : [ {
								type : "doughnut",
								startAngle : 60,
								toolTipContent : "{legendText}: {y}",
								showInLegend : true,
								explodeOnClick : true, //**Change it to true
								dataPoints : dataForGraph[2]
							} ]
						});
						chart2.render();

						/* 		var options1 = {
									series : {
										pie : {
											show : true,
											innerRadius : 0.5,
											label : {
												show : true
											}
										}
									}
								};
								var options = {
									series : {
										pie : {
											show : true,
											label : {
												show : true,
												radius : 0.8,
												formatter : function(label, series) {
													return '<div style="border:1px solid grey;font-size:8pt;text-align:center;padding:5px;color:white;">'
															+ label
															+ ' : '
															+ Math
																	.round(series.percent)
															+ '%</div>';
												},
												background : {
													opacity : 0.8,
													color : '#000'
												}
											}
										}
									},
									grid : {
										hoverable : true
									}
								};
								$.fn.showMemo = function() {
									$(this)
											.bind(
													"plothover",
													function(event, pos, item) {
														if (!item) {
															return;
														}

														var html = [];
														var percent = parseFloat(
																item.series.percent)
																.toFixed(2);

														html
																.push(
																		"<div style=\"border:1px solid grey;background-color:",
						             item.series.color,
						             "\">",
																		"<span style=\"color:white\">",
																		item.series.label,
																		" : ",
																		item.series.data[0][1],
																		" (", percent,
																		"%)",
																		"</span>",
																		"</div>");
														$("#flot-memo").html(
																html.join(''));
													});
								} */
						/* $.plot($("#flot-placeholder1"), dataForGraph[2], options1);
						$.plot($("#flot-placeholder"), dataForGraph[1], options);
						$("#flot-placeholder").showMemo(); */

						var avlCount = $('#availableCount').text();
						var inUseCount = $('#reservedCount').text();
						var offlineCount = 78 - (parseInt(avlCount) + parseInt(inUseCount));
						Morris.Donut({
							element : 'donut-example',
							data : [ {
								label : "Available Devices",
								value : avlCount
							}, {
								label : "In Use Devices",
								value : inUseCount
							}, {
								label : "Offline/Disconnected",
								value : offlineCount
							} ]
						});

						if ($('#bar-example').length > 0) {
							Morris.Bar({
								element : 'bar-example',
								data : dataForGraph[0],
								gridTextSize : '9.5',
								xkey : 'y',
								ykeys : [ 'a', 'b' ],
								labels : [ 'Mobiles', 'Tablets' ],
								xLabelAngle : 60
							});
						}

						var months = [ "January", "February", "March", "April",
								"May", "June", "July", "August", "September",
								"October", "November", "December" ];
						Morris.Area({
							element : 'area-example',
							data : [ {
								m : '2016-01',
								a : 50,
								b : 20,
								c : 50
							}, {
								m : '2016-02',
								a : 45,
								b : 15,
								c : 50
							}, {
								m : '2016-03',
								a : 70,
								b : 25,
								c : 50
							}, {
								m : '2016-04',
								a : 45,
								b : 22,
								c : 50
							}, {
								m : '2016-05',
								a : 60,
								b : 12,
								c : 50
							}, {
								m : '2016-06',
								a : 55,
								b : 15,
								c : 35
							}, {
								m : '2016-07',
								a : 50,
								b : 20,
								c : 35
							} ],
							xkey : 'm',
							ykeys : [ 'a', 'b', 'c' ],
							labels : [ 'Android', 'Windows', 'IOS' ],
							xLabelFormat : function(x) {
								var month = months[x.getMonth()];
								return month;
							},
							dateFormat : function(x) {
								var month = months[new Date(x).getMonth()];
								return month;
							}
						});
					});
</script>
</html>