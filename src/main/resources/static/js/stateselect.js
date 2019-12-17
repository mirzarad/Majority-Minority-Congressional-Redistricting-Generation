var selectedState = "usa";

$(document).ready(function() {
	$("#election-select-menu").attr("disabled", true);
	$("#election-select-menu").css("background-color", "#999999");
	$("#district-view-toggle").attr("disabled", true);
	
	$("#full").click(function () {
		$("#state-select-menu").text('FULL USA MAP');
		$("#election-select-menu").text("SELECT ELECTION DATA");
		$("#current-map-view-text").text('Full USA Map');
		$("#election-select-menu").attr("disabled", true); 
		$("#election-select-menu").css("background-color", "#999999");
		$("#district-view-toggle").attr("disabled", true);
		$("#phase0-run").attr("disabled", true);
		$("#phase1-run").attr("disabled", true);
		$("#phase2-run").attr("disabled", true);
		selectedState = "usa";
	});
	
	$("#pennsylvania").click(function () {
		$("#state-select-menu").text('PENNSYLVANIA');
		$("#election-select-menu").text("SELECT ELECTION DATA");
		$("#current-map-view-text").text('Pennsylvania');
		$("#district-view-toggle").show();
		$("#election-select-menu").attr("disabled", false);
		$("#election-select-menu").css("background-color", "#3902cc");
		$("#district-view-toggle").attr("disabled", false);
		$("#phase0-run").attr("disabled", true);
		$("#phase1-run").attr("disabled", true);
		$("#phase2-run").attr("disabled", true);
		selectedState = "pennsylvania";
	});
	
	$("#california").click(function () {
		$("#state-select-menu").text('CALIFORNIA');
		$("#election-select-menu").text("SELECT ELECTION DATA");
		$("#current-map-view-text").text('California');
		$("#district-view-toggle").show();
		$("#election-select-menu").attr("disabled", false);
		$("#election-select-menu").css("background-color","#3902cc");
		$("#district-view-toggle").attr("disabled", false);
		$("#phase0-run").attr("disabled", true);
		$("#phase1-run").attr("disabled", true);
		$("#phase2-run").attr("disabled", true);
		selectedState = "california";
	});
});