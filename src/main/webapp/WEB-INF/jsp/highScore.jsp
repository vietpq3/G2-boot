<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>HIGH SCORE</title>
<%@ include file="taglib.jsp" %>
<link href="../css/highScore/style.css?t=<%=t%>" rel="stylesheet" type="text/css" />
<script src="../js/highScore/script.js?t=<%=t%>" type="text/javascript"></script>
</head>
<body>
	<div class="wrapper">
		<div class="content">
			<%@ include file="common.jsp" %>
			<div class="content-header">
                <div class="content-left">
                    Welcome: ${loginUser}
                </div>
                <div class="content-mid">
                    BestScore: ${highScore}
                </div>
                <div class="content-right">
                    <button id="btnLogout" class="btnSubmit btnLogout">Logout</button>
                </div>
                <div class="clear"></div>
            </div>
			<div class="content-body">
				<table>
					<tr>
						<th>Name</th>
						<th>Score</th>
					</tr>
					<tr>
						<td>A</td>
						<td>1</td>
					</tr>
					<tr>
						<td>B</td>
						<td>2</td>
					</tr>
				</table>
				<c:if test="${not empty pageInfo && pageInfo.totalRecord > 0}">
					<c:set var="msg~" value="～"></c:set>
					<c:set var="msg/" value="/"></c:set>
				</c:if>
			</div>
			<div class="content-footer">
			</div>
		</div>
	</div>
</body>
</html>