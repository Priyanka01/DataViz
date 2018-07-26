<!DOCTYPE html>
<head>
	<meta charset="utf-8">

	<!-- D3.js -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.6/d3.min.js" charset="utf-8"></script>
	<script src="http://d3js.org/queue.v1.min.js"></script>

	<!-- jQuery -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

	<!-- Stylesheet -->
	<style>
		body { text-align: center; }
	</style>
	
</head>
<body>

	<div id="chart"></div>
	<script>
		queue() 
			.defer(d3.json, "click.json")
			.await(drawAll);
			
		function drawAll(error, dataset) {
			////////////////////////////////////////////////////////////// 
			////////////////// Create Set-up variables  ////////////////// 
			////////////////////////////////////////////////////////////// 

			var width = Math.max($("#chart").width(),350) - 20,
				height = (window.innerWidth < 768 ? width : window.innerHeight - 20);

			var mobileSize = (window.innerWidth < 768 ? true : false);

			var centerX = width/2,
				centerY = height/2;
			////////////////////////////////////////////////////////////// 
			/////////////////////// Create SVG  /////////////////////// 
			////////////////////////////////////////////////////////////// 
			
			//Create the visible canvas and context
			var canvas  = d3.select("#chart").append("canvas")
				.attr("id", "canvas")
				.attr("width", width)
				.attr("height", height);
			var context = canvas.node().getContext("2d");
				context.clearRect(0, 0, width, height);
			
			//Create a hidden canvas in which each circle will have a different color
			//We can use this to capture the clicked on circle
			var hiddenCanvas  = d3.select("#chart").append("canvas")
				.attr("id", "hiddenCanvas")
				.attr("width", width)
				.attr("height", height)
				.style("display","none");	
			var hiddenContext = hiddenCanvas.node().getContext("2d");
				hiddenContext.clearRect(0, 0, width, height);

			//Create a custom element, that will not be attached to the DOM, to which we can bind the data
			var detachedContainer = document.createElement("custom");
			var dataContainer = d3.select(detachedContainer);

			////////////////////////////////////////////////////////////// 
			/////////////////////// Create Scales  /////////////////////// 
			////////////////////////////////////////////////////////////// 

			var colorCircle = d3.scale.ordinal()
					.domain([0,1,2,3])
					.range(['#bfbfbf','#838383','#4c4c4c','#1c1c1c']);

			var diameter = Math.min(width*0.9, height*0.9);
			var pack = d3.layout.pack()
				.padding(1)
				.size([diameter, diameter])
				.value(function(d) { return d.sales; })
				.sort(function(d) { return d.sku; });

			////////////////////////////////////////////////////////////// 
			////////////////// Create Circle Packing /////////////////////
			////////////////////////////////////////////////////////////// 

			var nodes = pack.nodes(dataset),
				root = dataset,
				focus = dataset;		
			
			//Dataset to swtich between color of a circle (in the hidden canvas) and the node data	
			var colToCircle = {};
			
			//Create the circle packing as if it was a normal D3 thing
			var dataBinding = dataContainer.selectAll(".node")
				.data(nodes)
				.enter().append("circle")
				.attr("id", function(d,i) { return "nodeCircle_"+i; })
				.attr("class", function(d,i) { return d.parent ? d.children ? "node" : "node node--leaf" : "node node--root"; })
				.attr("r", function(d) { return d.r; })
				.attr("cx", 0)
				.attr("cy", 0)
				.attr("fill", function(d) { return d.children ? colorCircle(d.depth) : "white"; });
				//.attr("transform", function(d) { return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")"; });;
			
			//First zoom to get the circles to the right location
			zoomToCanvas(root);
			
			////////////////////////////////////////////////////////////// 
			///////////////// Canvas draw function ///////////////////////
			////////////////////////////////////////////////////////////// 
			
			//The draw function of the canvas that gets called on each frame
			function drawCanvas(chosenContext, hidden) {

			  //Clear canvas
			  chosenContext.fillStyle = "#fff";
			  chosenContext.rect(0,0,canvas.attr("width"),canvas.attr("height"));
			  chosenContext.fill();
			  
			  //Select our dummy nodes and draw the data to canvas.
			  var elements = dataContainer.selectAll(".node");
			  elements.each(function(d) {
				  
				var node = d3.select(this);

				//If the hidden canvas was send into this function
				//and it does not yet have a color, generate a unique one
				if(hidden) {
					if(node.attr("color") === null) {
					  // If we have never drawn the node to the hidden canvas get a new color for it and put it in the dictionary.
					  node.attr("color", genColor());
					  colToCircle[node.attr("color")] = node;
					}//if
					// On the hidden canvas each rectangle gets a unique color.
					chosenContext.fillStyle = node.attr("color");
				} else {
					chosenContext.fillStyle = node.attr("fill");
				}//else
				
				//Draw each circle
				chosenContext.beginPath();
				chosenContext.arc((centerX + +node.attr("cx")), (centerY + +node.attr("cy")), node.attr("r"), 0,  2 * Math.PI, true);
				chosenContext.fill(); 
				chosenContext.fillStyle ="red";
				/* chosenContext.fillText("brand name",(centerX + +node.attr("cx")) -  node.attr("r")/4,(centerY + +node.attr("cy")) - node.attr("r")/2); */
 
				chosenContext.closePath();
				
			  })
			}//function drawCanvas

			////////////////////////////////////////////////////////////// 
			/////////////////// Click functionality ////////////////////// 
			////////////////////////////////////////////////////////////// 
			
			// Listen for clicks on the main canvas
			document.getElementById("canvas").addEventListener("click", function(e){
			  // We actually only need to draw the hidden canvas when there is an interaction. 
			  // This sketch can draw it on each loop, but that is only for demonstration.
			  drawCanvas(hiddenContext, true);

			  //Figure out where the mouse click occurred.
			  var mouseX = e.layerX;
			  var mouseY = e.layerY;

			  // Get the corresponding pixel color on the hidden canvas and look up the node in our map.
			  // This will return that pixel's color
			  var col = hiddenContext.getImageData(mouseX, mouseY, 1, 1).data;
			  //Our map uses these rgb strings as keys to nodes.
			  var colString = "rgb(" + col[0] + "," + col[1] + ","+ col[2] + ")";
			  var node = colToCircle[colString];

			  if(node) {
				chosen = dataContainer.selectAll("#"+node.attr("id"))[0][0].__data__;
				//Zoom to the clicked on node
				if (focus !== chosen) zoomToCanvas(chosen); else zoomToCanvas(root);
			  }//if
			});

			////////////////////////////////////////////////////////////// 
			///////////////////// Zoom Function //////////////////////////
			////////////////////////////////////////////////////////////// 

			//Zoom into the clicked on circle
			//Use the dataContainer to do the transition on
			//The canvas will continuously redraw whatever happens to these circles
			function zoomToCanvas(d) {
				focus = d;
				var v = [focus.x, focus.y, focus.r * 2.05],
					k = diameter / v[2]; 
					
				dataContainer.selectAll(".node")
					.transition().duration(2000)
					.attr("cx", function(d) { return (d.x - v[0]) * k; })
					.attr("cy", function(d) { return (d.y - v[1]) * k; })
					.attr("r", function(d) { return d.r * k; });

			}//function zoomToCanvas
			
			////////////////////////////////////////////////////////////// 
			//////////////////// Other Functions /////////////////////////
			////////////////////////////////////////////////////////////// 
			
			//Generates the next color in the sequence, going from 0,0,0 to 255,255,255.
			//From: https://bocoup.com/weblog/2d-picking-in-canvas
			var nextCol = 1;
			function genColor(){
				var ret = [];
				// via http://stackoverflow.com/a/15804183
				if(nextCol < 16777215){
				  ret.push(nextCol & 0xff); // R
				  ret.push((nextCol & 0xff00) >> 8); // G 
				  ret.push((nextCol & 0xff0000) >> 16); // B

				  nextCol += 100; // This is exagerated for this example and would ordinarily be 1.
				}
				var col = "rgb(" + ret.join(',') + ")";
				return col;
			}//function genColor

			////////////////////////////////////////////////////////////// 
			/////////////////////// Initiate ///////////////////////////// 
			////////////////////////////////////////////////////////////// 
			
			//d3.timer(function() { drawCanvas(context) });
			function animate() {
				drawCanvas(context);
				window.requestAnimationFrame(animate);
			}
			window.requestAnimationFrame(animate);

		}//drawAll
	</script>
	
</body>
</html>
