var isFinished = false;
var grid;
var table;
var myBaseArray; // a nested array of all the numbers (IN CORRECT ORDER)
var myCurrentArray; // the current nested array the user is working on (OUT OF ORDER)
const gridDim = 4;
const mixTimes = 20; // best scramble achieved when mixTimes > gridDim^2

function setup()
{
	document.getElementById("scrambleButton").addEventListener("click", scramble, false);

	var count = 1;
	var count2 = 1;
	isFinished = false;
	grid = document.getElementById("grid"); //a div element in the document

	// setup a 2D nested array to hold the solution space
	myBaseArray = new Array(gridDim);

	for(var i = 0; i < gridDim; i++){
		myBaseArray[i] = new Array(gridDim);
	}

	for (var i = 0; i < gridDim; i++){
		for (var j = 0; j < gridDim; j++){
			myBaseArray[i][j] = count;
			count++;
		}
	}
	myBaseArray[gridDim - 1][gridDim - 1] = "";

	//do the same thing but for an active working array that will be modified
	myCurrentArray = new Array(gridDim);

	for(var i = 0; i < gridDim; i++){
		myCurrentArray[i] = new Array(gridDim);
	}

	for (var i = 0; i < gridDim; i++){
		for (var j = 0; j < gridDim; j++){
			myCurrentArray[i][j] = count2;
			count2++;
		}
	}
	myCurrentArray[gridDim - 1][gridDim - 1] = "";

	//call method to scramble the working array
	scramble();
}

function assignEventListeners(){

	// setup event handlers for clicking on table data elements
	var tagNodeList = document.getElementsByTagName("td");

	for(var x = 0; x < tagNodeList.length; x++){
		var node = tagNodeList.item(x);
		node.addEventListener("click", moveTile, false);
	}
}

//function to call when user clicks on a tile
function moveTile(e)
{
	var clickedTileValue;
	var elem;
	if(e.target.innerHTML == ""){
		alert("You must click on a valid tile");
	} else {

		clickedTileValue = parseInt(e.target.innerHTML);

		for(var i=0; i < myCurrentArray.length; i++){
        	var index = myCurrentArray[i].indexOf(clickedTileValue);
        	if (index > -1){
           		elem = [i, index];
           		break;
        	}
   		}

		 if (myCurrentArray[Math.min(Math.max(elem[0]-1, 0), gridDim-1)][elem[1]] == ""){
	   		myCurrentArray[Math.min(Math.max(elem[0]-1, 0), gridDim-1)][elem[1]] = clickedTileValue;
	   		myCurrentArray[elem[0]][elem[1]] = "";
	   	} else if (myCurrentArray[Math.min(Math.max(elem[0]+1, 0), gridDim-1)][elem[1]] == ""){
	   		myCurrentArray[Math.min(Math.max(elem[0]+1, 0), gridDim-1)][elem[1]] = clickedTileValue;
	   		myCurrentArray[elem[0]][elem[1]] = "";
	   	} else if (myCurrentArray[elem[0]][Math.min(Math.max(elem[1]-1, 0), gridDim-1)] == ""){
	   		myCurrentArray[elem[0]][Math.min(Math.max(elem[1]-1, 0), gridDim-1)] = clickedTileValue;
	   		myCurrentArray[elem[0]][elem[1]] = "";
	   	} else if (myCurrentArray[elem[0]][Math.min(Math.max(elem[1]+1, 0), gridDim-1)] == ""){
	   		myCurrentArray[elem[0]][Math.min(Math.max(elem[1]+1, 0), gridDim-1)] = clickedTileValue;
	   		myCurrentArray[elem[0]][elem[1]] = "";
	   	} else {
	   		alert("You must click on a tile next to the empty space");
	   	}
	}

	updateGrid();
	checkFinished();
}

// update the innerHTML of the grid to reflect the contents of myCurrentArray
function updateGrid(){

	var tableContent = "<table align = 'center'>";

	for(var row = 0; row < gridDim; row++){

		tableContent += "<tr>";

		for(var col = 0; col < gridDim; col++){
			if(myCurrentArray[row][col] == ""){
				tableContent += "<td class = 'empty'>"+ myCurrentArray[row][col] + "</td>";
			} else {
				tableContent += "<td>"+ myCurrentArray[row][col] + "</td>";
			}

		}

		tableContent += "</tr>";
	}

	grid.innerHTML = tableContent;
	assignEventListeners();
}

//check to see if the arrangement of tiles is in the completed position
function checkFinished(){

	var winText = document.getElementById("winText");

	isFinished = true;

	for(var i = 0; i < gridDim; i++){
		for(var j = 0; j < gridDim; j++){
			if (myBaseArray[i][j] != myCurrentArray[i][j]){
				isFinished = false;
			}
		}
	}

	if(isFinished){
		var choice = confirm("Congratulations! You won! Play Again?")
	}

	if(choice){
		scramble();
	}

}

//scramble the tiles
function scramble(){
	var i = 0;
	while (i < mixTimes) {
		do {
			var j = Math.floor(Math.random()*gridDim);
			var k = Math.floor(Math.random()*gridDim);
			var l = Math.floor(Math.random()*gridDim);
			var m = Math.floor(Math.random()*gridDim);
		} while (j == l && k == m || ((Math.max(l, j) - Math.min(l,j)) + (Math.max(k, m) - Math.min(k,m))) <= 1); 
		// 'while' checks to make sure you aren't swapping a tile with itself, and also avoid swapping adjacent tiles to prevent an unsolvable puzzle

		var temp = myCurrentArray[l][m];
		myCurrentArray[l][m] = myCurrentArray[j][k];
		myCurrentArray[j][k] = temp;
		isFinished = false;

		i++;
	}

	console.log("After scramble, myBaseArray: "+ myBaseArray);
	console.log("After scramble, myCurrentArray: "+ myCurrentArray);

	updateGrid();
	checkFinished();
}

window.addEventListener("load", setup, false);