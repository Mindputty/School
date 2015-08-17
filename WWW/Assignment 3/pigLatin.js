var myText;
var myArray;
var startButton;
var clearButton;
var myOutput;
var translatedText;

function start(){
	startButton = document.getElementById("startButton");
	clearButton = document.getElementById("clearButton");
	startButton.addEventListener("click", printLatinWord, false);
	clearButton.addEventListener("click", clear, false);
	translatedText = document.getElementById("output");
}

function printLatinWord(){
	myOutput = "";
	myText = document.getElementById("textfield").value;
	myArray = myText.split(" ");

	for(var x in myArray){
		var letter = myArray[x].charAt(0);
		var halfWord = myArray[x].substr(1);
		var newWord = halfWord + letter + "ay";
		myOutput += newWord + " ";
	}

	translatedText.innerHTML += myOutput + "&#13;&#10;";
}

function clear(){

	translatedText.innerHTML = "";
}

window.addEventListener("load", start, false);