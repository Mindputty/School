var tortoiseTrack;
var hareTrack;
var TRACK_LENGTH = 70;
var finished;
var outputArray;
var startButton;
var count;

function setup(){

	finished = false;
	count = 0;

	startButton = document.getElementById("startButton");

	startButton.addEventListener("click", pressButton, false);

	hareTrack = [TRACK_LENGTH];
	tortoiseTrack = [TRACK_LENGTH];
	outputArray = [TRACK_LENGTH];

	hareTrack[0] = 'T';
	tortoiseTrack[0] = 'H';

}

function pressButton(){

	document.writeln("<p><b>ON YOUR MARK, GET SET<br>BANG!!!<br>AND THEY'RE OFF!!!</b></p>")

	while(!finished){
		moveHare();
		moveTortoise();
		checkGameOver();
		update()
		count++;
	}
}

function update(){

	//array for output
	outputArray = new Array(TRACK_LENGTH); 	//clear the array
	outputArray[hareTrack.indexOf('H')] = 'H';
	if(outputArray[tortoiseTrack.indexOf('T')]){
		outputArray[tortoiseTrack.indexOf('T')] = 'OUCH!!!';
	} else {
		outputArray[tortoiseTrack.indexOf('T')] = 'T';
	}

	//write the outputArray to a line
	document.writeln("<p>"+outputArray.join(".")+"</p>");

	//written notices regarding winners
	if(finished){
		if(outputArray[TRACK_LENGTH-1] == 'T'){
			document.writeln("<p><b>TORTOISE WINS!!! YAY!!!</b></p>");
		} else if (outputArray[TRACK_LENGTH-1] == 'H'){
			document.writeln("<p><b>HARE WINS. YUCK!</b></p>");
		} else if (outputArray[TRACK_LENGTH-1] == 'OUCH!!!'){
			document.writeln("<p><b>IT'S A TIE.</b></p>");
		}
		document.writeln("<p><b>The race took "+count+" turns.</b></p>")
	}
}

function checkGameOver(){
	if(hareTrack[TRACK_LENGTH-1]||tortoiseTrack[TRACK_LENGTH-1]){
		finished = true;
	}
}

function moveHare(){
	var amt = roll('H');
	var start = hareTrack.indexOf('H');
	var newSpace = start+amt;
	if(newSpace <= 0){
		newSpace = 0;
	} else if (newSpace > (TRACK_LENGTH-1)){
		newSpace = TRACK_LENGTH-1;
	}
	hareTrack[start] = null;
	hareTrack[newSpace] = 'H';
}

function moveTortoise(){
	var amt = roll('T');
	var start = tortoiseTrack.indexOf('T');
	var newSpace = start+amt;
	if(newSpace <= 0){
		newSpace = 0;
	} else if (newSpace > (TRACK_LENGTH-1)){
		newSpace = TRACK_LENGTH-1;
	}
	tortoiseTrack[start] = null;
	tortoiseTrack[newSpace] = 'T';
}

function roll(characterType){
	
	var myRoll = Math.floor((Math.random()*10) + 1);

	if(characterType == 'T'){

		switch(myRoll){
			case 1: case 2: case 3: case 4: case 5:
				amt = 3;
				break;
			case 6: case 7:
				amt = -6;
				break;
			case 8: case 9: case 10:
				amt = 1;
		}
	} else if (characterType == 'H'){
		switch(myRoll){
			case 1: case 2:
				amt = 0;
				break;
			case 3: case 4:
				amt = 9;
				break;
			case 5:
				amt = -12;
				break;
			case 6: case 7: case 8:
				amt = 1;
				break;
			case 9: case 10:
				amt = -2;
		}
	}
	return amt;
}

window.addEventListener("load", setup, false);