<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload CSV file</title>
</head>
<style>
	.main_container{
		border:1px solid black;
		margin-top:200px;
		width:300px;
		height:300px;
		padding:50px;
	}
</style>
<body>
	
	<div class="main_container">
	Please upload your CSV File here....
		<form action="upload" method="post" enctype="multipart/form-data">
		    <input type="text" name="description" />
		    <input type="file" name="file" />
		    <input type="submit" />
	    </form>
    </div>
    

</body>
</html>