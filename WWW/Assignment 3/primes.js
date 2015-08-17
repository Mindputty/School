var size;
var primes;
var ARRAY_SIZE = 1000;
var index;
var result;
var resultArray;

function start(){
	//initialize variables
	size = ARRAY_SIZE;
	primes = new Array(size);
	resultArray = new Array();
	counter = 2;
	result = document.getElementById("result");

	//fill the primes array with 1's
	while(size--){
		primes[size] = 1;
	}

	//set zero and one, which are not primes, to 0
	primes[0] = 0;
	primes[1] = 0;

	while(counter < ARRAY_SIZE){
		if(primes[counter] == 1){
			removeFactor(primes, counter);
			resultArray.push(counter);
		}
		counter++;
	}

	result.innerHTML = "<h1>Primes between 1 and "+ARRAY_SIZE+":</h1><p>"+resultArray.join(" - ")+"</p>";
}

function removeFactor(targetArray, factor){
	var x;
	for(x in targetArray){
		if(targetArray[x] == 1 && x % factor == 0){
			targetArray[x] = 0;
		}
	}
}

window.addEventListener("load", start, false);