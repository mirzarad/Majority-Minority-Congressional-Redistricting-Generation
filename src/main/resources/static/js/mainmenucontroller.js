$(function () {
	var $sysCtrl = $("#sys-controls-content");
	var $voteData = $("#voting-data-content");
	var $demographicData = $("#demographic-data-content");
	var $dataAnalysis = $("#data-analysis-content");
	var $phaseTabs = $("#phase-tabs");
	var $menuTitle = $("#controls-text");
    
	$("#system-controls-tab").click(function (){
		$phaseTabs.show();
		$sysCtrl.show();
		$voteData.hide();
		$demographicData.hide();
		$dataAnalysis.hide();
		$("#controls-text").html("System Controls");
    });

    $("#voting-data-tab").click(function () {
		$phaseTabs.hide();
		$sysCtrl.hide();
		$voteData.show();
		$demographicData.hide();
		$dataAnalysis.hide();
		$("#controls-text").html("Voting Data");
    });
    
    $("#demographic-data-tab").click(function () {
		$phaseTabs.hide();
		$sysCtrl.hide();
		$voteData.hide();
		$demographicData.show();
		$dataAnalysis.hide();
		$("#controls-text").html("Demographic Data");
    });

    $("#data-analysis-tab").click(function () {
		$phaseTabs.hide();
		$sysCtrl.hide();
		$voteData.hide();
		$demographicData.hide();
		$dataAnalysis.show();
		$("#controls-text").html("Data Analysis");
    });
});