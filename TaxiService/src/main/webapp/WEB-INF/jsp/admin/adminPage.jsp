<%@page import="java.time.format.FormatStyle"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.taxi.web.model.entity.Ride"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<fmt:setBundle basename="resources" />

<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/jspf/head.jspf"%>
</head>
<body>
	<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>


	<c:choose>
		<c:when test="${cookie.lang.value != 'en'}">
			<a href="/controller?command=lang&newLang=en"> EN </a>
		</c:when>
		<c:when test="${cookie.lang.value != 'ru'}">
			<a href="/controller?command=lang&newLang=ru"> RU </a>
		</c:when>

	</c:choose>
	
	<a href="controller?command=logout">
		<fmt:message key="header.logout" />
	</a>

	<header id="header" class="header">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<img src="img/logo.png" class="log-adj animated fadeInLeft" />
				</div>
			</div>
		</div>
		<h1>
		<fmt:message key="adminPage.rides24h" />
		:
		<c:out value="${requestScope.ridesInADay}" />
		<br />
	</h1>

	<h1>

		<fmt:message key="adminPage.income24h" />
		:
		<c:out value="${requestScope.sumInADay}" />
		<br />
	</h1>

	<h3>
		<fmt:message key="adminPage.allRides" />
		:
	</h3>

	<h3>
		<a href="controller?command=showRides&q=all&order=desc&p=1"
			target="_blank"><fmt:message key="adminPage.newFirst" /></a>
	</h3>
	<h3>
		<a href="controller?command=showRides&q=all&order=asc&p=1"
			target="_blank"> <fmt:message key="adminPage.oldFirst" /></a>
	</h3>
	<h3>
		<a href="controller?command=showRides&q=all&order=exp&p=1"
			target="_blank"> <fmt:message key="adminPage.expFirst" /></a>
	</h3>
	<h3>
		<a href="controller?command=showRides&q=all&order=cheap&p=1"
			target="_blank"><fmt:message key="adminPage.cheapFirst" /></a>
	</h3>


	<div class="col s3">
		<form action="controller?command=showRides&q=date&p=1" method="post">
			<p>
				<b><fmt:message key="adminPage.madeFrom" />:</b>
			</p>
			<input type="datetime-local" name="from" required="required">

			<p>
				<b><fmt:message key="adminPage.madeUntil" /></b>
			</p>
			<input type="datetime-local" name="until" required="required">

			<button type="submit" name="show">OK</button>
		</form>
	</div>

	<form action="controller?command=showRides&q=us&p=1" method="post">
		<p>
			<b><fmt:message key="adminPage.byId" /></b>
		</p>
		<input type="number" name="user_id" required="required">


		<button type="submit" name="show">OK</button>
	</form>
	</header>
</body>
</html>