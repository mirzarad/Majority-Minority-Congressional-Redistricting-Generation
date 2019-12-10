
var selectedElection = "PRESIDENTIAL2016"; // PRESEDENTIAL 2016 IS THE DEFAULT

$(document).ready(function() {
	$("#congress-2016").click(function () {
		$("#election-select-menu").text('2016 Congressional Election');
		selectedElection = "CONGRESSIONAL2016";
	});
	
	$("#pres-2016").click(function () {
		$("#election-select-menu").text("2016 Presidential Election");
		selectedElection = "PRESIDENTIAL2016";
	});
	
	$("#congress-2018").click(function () {
		$("#election-select-menu").text("2018 Congressional Election");
		selectedElection = "CONGRESSIONAL2018";
	});
});
