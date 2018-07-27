<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css" />
<meta charset="UTF-8">
<title>Upload CSV file</title>
</head>
<body>
	<h1 class="header">Welcome to the Crate & Barrel Data Analyst</h1>
	<div class="main_container">
	<p>Please upload your CSV File here....</p>
		<form action="upload" method="post" enctype="multipart/form-data">
		    <input class="desc" placeholder="Description..." type="text" name="description" />
		    <input id="fileName" type="file" name="file" /><br />
		    <input class="btn" type="submit" />
	    </form>
    </div>
</body>
</html>