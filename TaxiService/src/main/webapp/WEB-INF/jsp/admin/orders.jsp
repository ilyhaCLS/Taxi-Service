<%@page import="java.util.Arrays"%>
<%@page import="java.time.format.FormatStyle"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.taxi.web.model.entity.Ride"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>
    
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>

<table class="table table-hover table-dark">
		<thead>
			<tr>
				<th scope="col">id</th>
				<th scope="col"><fmt:message key="admin.rides.user_id"/></th>
				<th scope="col"><fmt:message key="account.rides.from"/></th>
				<th scope="col"><fmt:message key="account.rides.to"/></th>
				<th scope="col"><fmt:message key="account.rides.price"/></th>
				<th scope="col"><fmt:message key="account.rides.date"/></th>
				<th scope="col"><fmt:message key="account.rides.car"/></th>
			</tr>
		</thead>
		<tbody>
		<c:set var = "langTag" scope = "session" value = "${cookie.lang.value}"/>
		<c:forEach items="${rides}" var="ride">
			<tr>
			<th scope="row">${ride.getId()}</th>
			<td>${ride.getUserId()}</td>
    		<td>${ride.getPosFrom()}</td>
    		<td>${ride.getPosTo()}</td>
    		
    		<c:choose>
				<c:when test="${cookie.lang.value == 'en'}">
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${ride.getPrice() / 27}"/></td>
				</c:when>
				<c:when test="${cookie.lang.value == 'ru'}">
					<td>${ride.getPrice()}</td>
				</c:when>
			</c:choose>

			<fmt:parseDate value="${ride.getCreationTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
			<td>
			<fmt:formatDate type = "both" 
        	 dateStyle = "medium" timeStyle = "medium" value="${parsedDateTime}"/></td>
        	 
    		<td>${ride.getCar().getLicPlate()} ${ride.getCar().getName()} ${ride.getCar().getCarClass()}</td>
    		</tr>
		</c:forEach>
		</tbody>
		</tbody>
	</table>
	
	<c:forEach begin="1" end="${numOfPages}" varStatus="loop">
	<c:set var="str" value="${requestScope['javax.servlet.forward.query_string']}"/>
    	<a href="/controller?${fn:substringBefore(str, 'p=')}p=${loop.index}"
    	class="btn btn-primary"> <c:out value = "${loop.index}"/> </a>
	</c:forEach>
</body>
</html>