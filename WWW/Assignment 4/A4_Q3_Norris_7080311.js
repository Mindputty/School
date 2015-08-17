function setup(){

	document.addEventListener("click", processMouseClick, false);

}

function processMouseClick(e){
	if (e.ctrlKey){
		alert("Event type: " + e.type);
	}

	if (e.shiftKey){
		alert("Element type: " + e.target);
	}
}

window.addEventListener("load", setup, false);