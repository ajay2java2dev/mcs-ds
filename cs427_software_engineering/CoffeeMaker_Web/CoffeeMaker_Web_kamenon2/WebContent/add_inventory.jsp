<%@page
	import="edu.ncsu.csc326.coffeemaker.exceptions.InventoryException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.ncsu.csc326.coffeemaker.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CoffeeMaker - Add Inventory</title>
<%@include file="head.jsp"%>
</head>

<body>
	<div align=center class="font1">
		<h1>CoffeeMaker</h1>
		<h3>Add Inventory</h3>

		<%
			CoffeeMaker cm = (CoffeeMaker) session.getAttribute("cm");

			String coffeeContent = request.getParameter("coffeeContent");
			String milkContent = request.getParameter("milkContent");
			String sugarContent = request.getParameter("sugarContent");
			String chocolateContent = request.getParameter("chocolateContent");
			if (coffeeContent != null && milkContent != null && sugarContent != null && chocolateContent != null) {
				cm.addInventory(coffeeContent, milkContent, sugarContent, chocolateContent);
			}

			String currentInventoryDetails = cm.checkInventory();
		%>
		Current Inventory : <span class="font1"><%=currentInventoryDetails%></span>
		<br> <br>
		<hr>
		<form method="post" action="add_inventory.jsp">
			<table border="2" bordercolor="brown">
				<tr>
					<td>Coffee :</td>
					<td><input type="text" name="coffeeContent" value="0"></td>
				</tr>
				<tr>
					<td>Milk :</td>
					<td><input type="text" name="milkContent" value="0"></td>
				</tr>
				<tr>
					<td>Sugar :</td>
					<td><input type="text" name="sugarContent" value="0"></td>
				</tr>
				<tr>
					<td>Chocolate :</td>
					<td><input type="text" name="chocolateContent" value="0"></td>
				</tr>
				<tr>
					<td>Click to Submit</td>
					<td align="center"><input type="submit" name="submitContent"></td>
				</tr>
			</table>
		</form>
		<br> <span class="font1"><a href="index.jsp"> <span
				class="font1">Back to CoffeeMaker Menu</span></a> </span>
	</div>
</body>
</html>