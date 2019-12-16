$(function () {
	var $sysCtrl = $("#sys-controls-content");
	var $mapData = $("#map-data-content");
	var $dataAnalysis = $("#data-analysis-content");
	var $phaseTabs = $("#phase-tabs");
	var $menuTitle = $("#controls-text");
	
    
	$("#system-controls-tab").click(function () {
		$phaseTabs.show();
		$sysCtrl.show();
		$mapData.hide();
		$dataAnalysis.hide();
		$("#controls-text").html("System Controls");
    });

    $("#map-data-tab").click(function () {
		$phaseTabs.hide();
		$sysCtrl.hide();
		$mapData.show();
		$dataAnalysis.hide();
		$("#controls-text").html("Map Data");
    });

    $("#data-analysis-tab").click(function () {
		$phaseTabs.hide();
		$sysCtrl.hide();
		$mapData.hide();
		$dataAnalysis.show();
		$("#controls-text").html("Data Analysis");
    });
});