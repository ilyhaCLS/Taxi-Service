<%@page import="com.taxi.web.model.entity.Ride"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
	<%@include file="/WEB-INF/jspf/header.jspf" %>


	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1><fmt:message key="rideDetails"/></h1>
				
				<c:if test="${empty sessionScope.option1}">
				<fmt:message key="account.rides.from"/>: <c:out value="${param.posFrom }"/><br/>
				<fmt:message key="account.rides.to"/>: <c:out value="${param.posTo }"/><br/>
				<fmt:message key="rideDetails.dist"/>: <c:out value="${distance }"/><br/>
				<fmt:message key="ride.passNum"/>: <c:out value="${param.numOfPass }"/><br/>
				
				<fmt:parseNumber var = "i" integerOnly = "true" 
         		type = "number" value = "${price}" />
				<c:choose>
					<c:when test="${cookie.lang.value == 'en'}">
						<fmt:message key="account.rides.price"/>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${i / 27}"/> <fmt:message key="rideDetails.comp6"/><br/>
						
					</c:when>
					<c:when test="${cookie.lang.value == 'ru'}">
						<fmt:message key="account.rides.price"/>: <c:out value="${i}"/> <fmt:message key="rideDetails.comp6"/><br/>
					</c:when>

				</c:choose>
				</c:if>

				<c:if test="${empty sessionScope.option1}">
				<fmt:message key="rideDetails.time"/>: <c:out value="${time }"/><br/>
				<fmt:message key="rideDetails.disc"/>: <c:out value="${disc }"/><br/>
				
					<form action="/controller" method="post">
						<input type="hidden" name="command" value="rideConfirm"/>
						<input type="hidden" name="opt" value="0"/>
						<button type="submit" value="Войти">Подтвердить заказ</button>
					</form>
				</c:if> 
				
				<c:if test="${not empty sessionScope.option1}">
				<fmt:message key="account.rides.from"/>: <c:out value="${param.posFrom }"/><br/>
				<fmt:message key="account.rides.to"/>: <c:out value="${param.posTo }"/><br/>
				<fmt:message key="rideDetails.dist"/>: <c:out value="${sessionScope.dist }"/><br/>
				<fmt:message key="ride.passNum"/>: <c:out value="${param.numOfPass }"/><br/>
				<fmt:message key="rideDetails.time"/>: <c:out value="${sessionScope.time }"/><br/>
				<fmt:message key="rideDetails.disc"/>: <c:out value="${sessionScope.disc }"/><br/>
				
				<fmt:message key="rideDetails.comp1"/>
				<fmt:message key="rideDetails.comp2"/> <a href="/controller?command=rideConfirm&opt=1"><fmt:message key="rideDetails.comp3"/><c:out value="${sessionScope.option1.numOfCars }"/> <fmt:message key="rideDetails.comp4"/><c:out value="${sessionScope.option1.carClass }"/> 
				<fmt:message key="rideDetails.comp5"/>
				
				<c:if test="${cookie.lang.value == 'ru'}">
					<c:out value="${sessionScope.option1.price }"/>
				</c:if>
				<c:if test="${cookie.lang.value == 'en'}">
					<fmt:parseNumber var = "i" integerOnly = "true" 
         			type = "number" value = "${sessionScope.option1.price}" />
					<fmt:formatNumber type="number" maxFractionDigits="2" value="${i / 27}"/>
				</c:if>
				<fmt:message key="rideDetails.comp6"/></a>
				
				<fmt:message key="header.or"/> <a href="/controller?command=rideConfirm&opt=2">
				<fmt:message key="rideDetails.comp3"/><c:out value="${sessionScope.option2.numOfCars }"/> 
				<fmt:message key="rideDetails.comp4"/><c:out value="${sessionScope.option2.carClass }"/> 
				<fmt:message key="rideDetails.comp5"/>
				
				<c:if test="${cookie.lang.value == 'ru'}">
					<c:out value="${sessionScope.option2.price }"/>
				</c:if>
				<c:if test="${cookie.lang.value == 'en'}">
					<fmt:parseNumber var = "i" integerOnly = "true" 
         			type = "number" value = "${sessionScope.option2.price}" />
					<fmt:formatNumber type="number" maxFractionDigits="2" value="${i / 27}"/>
				</c:if>
				<fmt:message key="rideDetails.comp6"/></a>
				
				<p><fmt:message key="rideDetails.comp8"/></p>
				</c:if> 
			</div>
		</div>
	</div>
</body>
</html>