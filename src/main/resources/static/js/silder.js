/* *************** */
/*     PHASE 0     */
/* *************** */
var phase0DemSlider = Document.getElementById("phase0-demographic-bloc-measure"); // Slider
var phase0DemOutput = Document.getElementById("phase0-dem-bloc-value"); // Output value for phase0 demographic Slider

phase0DemOutput.innerHTML = $("#phase0-demographic-bloc-measure").val();

phase0DemSlider.addEventListener('input', function(){
	phas0DemOutput.innerHTML = $("#phase0-demographic-bloc-measure").val();
}, false);

/*
var phase0VoteSlider = $("#phase0-vote-bloc-measure").val()
phase0DemOutput.innerHTML = phase0DemSlider.value;

var phase1DemSlider = $("#phase1-demographic-bloc-measure").val()


var phase1VoteSlider = $("#phase1-vote-bloc-measure").val()
phase0DemOutput.innerHTML = phase0DemSlider.value;

$("#phase2-polsby-popper-measure").val()

phase0DemSlider.oninput = function(){
	phase0DemOutput.innerHtml = this.value;
}

var phase0VoteSlider = document.getElementById("phase0-vote-bloc-measure");;
var phase0VoteOutput = document.getElementById("phase0-voting-bloc-value");

phase0VoteSlider.oninput = function(){
	phase0VoteOutput.innerHtml = this.value;
}




var phase1DemSlider = document.getElementById("phase1-demographic-bloc-measure");
var phase1DemOutput = document.getElementById("phase1-dem-bloc-value");

phase1DemSlider.oninput = function(){
	phase1DemOutput.innerHtml = this.value;
}

var phase1VoteSlider = ;
var phase1VoteOutput = ;

phase1VoteSlider.oninput = function(){
	phase1VoteOutput.innerHtml = this.value;
}


phase0-dem-bloc-value

phase0-voting-bloc-value

phase1-dem-bloc-value

phase1-voting-bloc-value

phase2-political-fairness-value

phase2-lopsided-margins-value

phase2-mean-median-difference-value

phase2-graph-compactness-value

phase2-polsby-popper-value

phase2-schwartzberg-value

phase2-population-difference-value

output.innerHTML = slider.value;

slider.oninput = function(){
	output.innerHtml = this.value;
}

*/