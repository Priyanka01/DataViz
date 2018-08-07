DataViz is based on using a real world reports and turning them into beautiful visualization. 
This is built upon Java with Spring FrameWork and MySQL database with d3js(JavaScript library) for visualization.

Tools used:
  - STS
  - MySQL Workbench
  
	Language: Java 1.8,
	FrameWork:  Spring, 
	Database: MySQL 8.0,
  Libraries: d3js
 
 
 Description:
 Data from a furniture store comes in .xsl format weekly to the manager. Manager is responsible for going thru big sheets and 
 calculate total weekly profit, compare individual brand profits and make decission based on those comparisions. This work 
 can be time-consuming and can sometimes compramise the accuracy. So, soultion for this was representing the data in graphical form. 
 
 Workflow:
  - Data comes in .xsl
  - Converted into csv
  - Parsed 
  - Stored in database
  - JSON is created based on required data in appropriate format
  - JSON is an input to d3js 
  - d3js makes the graphs based on data.
  
  Future enhancements:
  - Integrate graph within a zoomable and clickable graph to display yearly comparision of particular brands earnings.
  - Expand to all departments.
  - Modify SQL queries to fetch only brands with sales in it. 
  
  *Scope is restricted to furniture department.
 
 

  
