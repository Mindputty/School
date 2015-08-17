var images;
var isDragging;

function setup(){
	images =  document.getElementsByTagName("img");
	isDragging = false;

	images[0].addEventListener("mousemove", drag, false);
	images[0].addEventListener("mousedown", mouseDown, false);
	images[0].addEventListener("mouseup", mouseUp, false);

	images[1].addEventListener("mousemove", drag, false);
	images[1].addEventListener("mousedown", mouseDown, false);
	images[1].addEventListener("mouseup", mouseUp, false);
	
}

function mouseDown(e){
	e.target.setAttribute("class", "dragging");
}

function mouseUp(e){
	e.target.setAttribute("class", "still");
}

function drag(e){
	while(e.target.getAttribute("class") == "dragging"){
		e.target.setAttribute("style", "left:"+e.clientX);
		e.target.setAttribute("style", "left:"+e.clientY);
	}
}

window.addEventListener("load", setup, false);