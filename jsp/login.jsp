<!-- /**
 * Description : Login jsp
 * @author Sauveer Pandey
 * Date : 19 Sep 2016
 */ -->
 <!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  --%>
<html lang="en">
<%
	response.setHeader(
			"Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.setHeader("Pragma", "No-cache");
	response.setDateHeader("Expires", -1);
%>
<head>
	<%-- <fmt:setBundle basename="AlertMessages" var="alertMsg"/>--%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content=""> 

    <title>Login</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="css/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">
    
    <link href="css/global.css" rel="stylesheet" type="text/css">
    
    <script src="scripts/jquery-1.11.3.js"></script>
<!--     <script src="scripts/login.js"></script>
 -->	
	<script>
		/* var alertMessage = {
				provideUserName : "<fmt:message key="provideUserName" bundle="${alertMsg}" />",
				providePassword : "<fmt:message key="providePassword" bundle="${alertMsg}" />"
		}; */
	</script>
	
 </head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                <%-- <c:choose>
	                <c:when test="${empty userDetails || userDetails.firstLoginFlag==false}"> --%>
	                    <div class="panel-heading">
	                        <h3 class="panel-title">Sign In</h3>
	                    </div>
	                    <div class="panel-body">
	                        <form action="userHome" method="post">
	                            <fieldset>
	                                <div class="form-group">
	                                    <input class="form-control" placeholder="User Name" name="userName" id="userName" type="text" autofocus>
	                                </div>
	                                <div class="form-group">
	                                    <input class="form-control" placeholder="Password" name="password" id="password" type="password" value="">
	                                </div>
	                                <div class="checkbox">
	                                    <label>
	                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
	                                    </label>
	                                </div>
	                                <input type="submit" class="btn btn-lg btn-success btn-block" value="Login" onclick="return validateLogin(this)"/>
	                            </fieldset>
	                        </form>
	                        <c:if test="${not empty error}">
	                        	<span class="red-color">${error}</span>
	                        </c:if>
	                    </div>
                    <%-- </c:when>
                    <c:otherwise> --%>
	                   <%--  <div class="panel-heading">
	                        <h3 class="panel-title">Please Change Password</h3>
	                    </div>
	                    <div class="panel-body">
	                        <form action="changePassword" method="post">
	                            <fieldset>
	                                <div class="form-group">
	                                    <input class="form-control" placeholder="User Name" name="userName" id="userName" type="text" autofocus>
	                                </div>
	                                <div class="form-group">
	                                    <input class="form-control" placeholder="Old Password" name="password" id="password" type="password" value="">
	                                </div>
	                                <div class="form-group">
	                                    <input class="form-control" placeholder="New Password" name="newpassword" id="newpassword" type="password" value="">
	                                </div>
	                                <div class="form-group">
	                                    <input class="form-control" placeholder="Re-Password" name="repassword" id="repassword" type="password" value="">
	                                </div>
	                                <input type="submit" class="btn btn-lg btn-success btn-block" value="Change Password"/>
	                            </fieldset>
	                        </form>
	                        <c:if test="${not empty error}">
	                        	<span class="red-color">${error}</span>
	                        </c:if>
	                    </div> --%>
                   <%--  </c:otherwise>
                   </c:choose> --%>
                </div>
            </div>
        </div>
    </div>
</body>

</html>