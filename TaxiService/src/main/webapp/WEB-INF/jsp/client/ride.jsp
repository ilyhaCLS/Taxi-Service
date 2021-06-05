<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
<%@include file="/WEB-INF/jspf/header.jspf" %>

	<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1><fmt:message key="ride.new"/></h1>
            
  <form action="/controller" method="post">
  <input type="hidden" name="command" value="rideDetails"/>
   <div class="form-group">
    <input type="text" class="form-control" id="search_input1" placeholder="<fmt:message key="account.rides.from"/> ?" name="posFrom" />
    <br>
    <input type="text" class="form-control" id="search_input2" placeholder="<fmt:message key="account.rides.to"/> ?" name="posTo" />
    <br>
    </div>
    <br>
    <input type="text" class="form-control" placeholder="<fmt:message key="ride.passNum"/> ?" name="numOfPass" />
   <label for="sel1">Select list:</label>
  <select class="form-control" id="sel1" name="carClass">
    <option>ECONOM</option>
    <option>ECONOMXL</option>
    <option>COMFORT</option>
    <option>VAN</option>
    <option>BUSINESS</option>
    <option>ELITE</option>
  </select>
    <button type="submit" value="submit"><fmt:message key="ride.submit"/></button>
   </form>
     </div>
   </div>
</div>
<br>
<br>
<br>
<script>
var searchInput1 = 'search_input1';
var searchInput2 = 'search_input2';
$(document).ready(function () {
 var autocomplete;
 autocomplete = new google.maps.places.Autocomplete((document.getElementById(searchInput1)), {
  types: ['geocode'],
  componentRestrictions: {
   country: "UA"
  }
 });
  
 google.maps.event.addListener(autocomplete, 'place_changed', function () {
  var near_place = autocomplete.getPlace();
 });
});

$(document).ready(function () {
	 var autocomplete;
	 autocomplete = new google.maps.places.Autocomplete((document.getElementById(searchInput2)), {
	  types: ['geocode'],
	  componentRestrictions: {
	   country: "UA"
	  }
	 });
	  
	 google.maps.event.addListener(autocomplete, 'place_changed', function () {
	  var near_place = autocomplete.getPlace();
	 });
	});
</script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&key=AIzaSyB9nfRnIbVxpTifteh8wiI6-8OvHb7o72k"></script>
</body>
</html>