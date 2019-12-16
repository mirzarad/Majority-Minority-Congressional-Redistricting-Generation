
var selectedElection = "PRESIDENTIAL2016"; // PRESEDENTIAL 2016 IS THE DEFAULT

$(document).ready(function() {
	$("#election-select-menu").attr("disabled", true);
	$("#phase0-run").attr("disabled", true);
	$("#phase1-run").attr("disabled", true);
	$("#phase2-run").attr("disabled", true);
	
	$("#congress-2016").click(function () {
		$("#election-select-menu").text('2016 Congressional Election');
		selectedElection = "CONGRESSIONAL2016";
		$("#phase0-run").attr("disabled", false);
	});
	
	$("#pres-2016").click(function () {
		$("#election-select-menu").text("2016 Presidential Election");
		selectedElection = "PRESIDENTIAL2016";
		$("#phase0-run").attr("disabled", false);
	});
	
	$("#congress-2018").click(function () {
		$("#election-select-menu").text("2018 Congressional Election");
		selectedElection = "CONGRESSIONAL2018";
		$("#phase0-run").attr("disabled", false);
	});
});
