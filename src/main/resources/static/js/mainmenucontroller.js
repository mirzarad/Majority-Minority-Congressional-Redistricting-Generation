$(function () {
	var $sysCtrl = $("#sys-controls-content");
	var $mapData = $("#map-data-content");
	var $dataAnalysis = $("#data-analysis-content");
	
    
	$("#system-controls-tab").click(function () {
		$sysCtrl.show();
		$mapData.hide();
		$dataAnalysis.hide();

    });

    $("#map-data-tab").click(function () {
		$sysCtrl.hide();
		$mapData.show();
		$dataAnalysis.hide();
		
    });

    $("#data-analysis-tab").click(function () {
		$sysCtrl.hide();
		$mapData.hide();
		$dataAnalysis.show();
    });
});