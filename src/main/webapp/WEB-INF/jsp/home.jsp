<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>HOME</title>
<%@ include file="taglib.jsp" %>
<link href="../css/home/style.css?t=<%=t%>" rel="stylesheet" type="text/css" />
<script src="../js/home/script.js?t=<%=t%>" type="text/javascript"></script>
</head>
<body>
<div class="wrapper">
    <div class="content">
    	<%@ include file="common.jsp" %>
        <form:form id="form" action="" method="POST" modelAttribute="form">
            <div class="content-header">
                <div class="content-left">
                    Welcome: ${loginUser}
                </div>
                <div class="content-mid">
                    BestScore: ${highScore}
                </div>
                <div class="content-right">
                    <input type="button" id="btnLogout" value="Logout" class="btnSubmit btnLogout" />
                </div>
                <div class="clear"></div>
            </div>
            
            <div class="content-body">
                <input type="button" id="btnFight" value="Fight" class="btnSubmit btnFight" />
            </div>
            
            <div class="content-footer">
                <div class="content-left footer-fix">
                    <input type="button" id="btnHighScore" value="HighScore" class="btnSubmit btnLogout" />
                </div>
                <div class="content-right">
                    <input type="button" id="btnTraining" value="Training" class="btnSubmit btnLogout" />
                </div>
                <div class="clear"></div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>