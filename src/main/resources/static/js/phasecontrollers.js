$(function () {
	var $phase0 = $("#phase0-content");
	var $phase1 = $("#phase1-content");
	var $phase2 = $("#phase2-content");
    
	$("#phase0-tab").click(function () {
		$phase0.show();
		$phase1.hide();
		$phase2.hide();
    });

    $("#phase1-tab").click(function () {
		$phase0.hide();
		$phase1.show();
		$phase2.hide();
    });

    $("#phase2-tab").click(function () {
		$phase0.hide();
		$phase1.hide();
		$phase2.show();
    });
});

$(function (){
	$('#phase0-is-running').val("0");
	$('#phase1-is-running').val("0");
	$('#phase2-is-running').val("0");
	
	var phase0 = $('#phase0-is-running');
	var phase1 = $('#phase1-is-running');
	var phase2 = $('#phase2-is-running'); 
	
	$('#phase0-run').on("click",function(e) {
		e.preventDefault();
		var is_running = phase0.val();
		
		if (is_running == "0") {
			var data = {};
			data["demographicBlocPercentage"] = $("#phase0-demographic-bloc-measure").val();
			data["voteBlocPercentage"] = $("#phase0-vote-bloc-measure").val();
			data["election"] = "CONGRESSIONAL2016";
			data["state"] = "pennsylvania";

			phasePost("phase0", data, "1", "Couldn't Start Demographic Bloc Analysis.", phase0);
		}
		else if (is_running == "1") {
			//STOP THE CLICKING UNTILL SUCCESS
		}
	});
	
	$('#phase1-run').on("click",function(e) {
		e.preventDefault();
		var is_running = phase1.val();
		
		if (is_running == "0") {
			var data = {};
			data["demographicBlocPercentage"] = $("#phase1-demographic-bloc-measure").val();
			data["voteBlocPercentage"] = $("#phase1-vote-bloc-measure").val();
			data["demographics"] = {AFRICAN_AMERICAN: $("#phase1-african").is(":checked"), 
									 NATIVE_AMERICAN: $("#phase1-native").is(":checked"), 
									 ASIAN: $("#phase1-asian").is(":checked"), 
									 NATIVE_HAWAIIAN: $("#phase1-hawaiin").is(":checked"),
									 HISPANIC: $("#phase1-hispanic").is(":checked"),
									 WHITE: $("#phase1-white").is(":checked")};

			phasePost("phase1", data, "1", "Couldn't Start Graph Partitioning.", phase1);
		}
		else if (is_running == "1") {
			//STOP THE CLICKING UNTILL SUCCESS
		}
	});
	
	$('#phase2-run').on("click",function(e) {
		e.preventDefault();
		var is_running = phase2.val();
		
		if (is_running == "0") {
			var data = {};
			data["polsbyPopper"] = $("#phase2-polsby-popper-measure").val();
			data["graphCompactness"] = $("phase2-graph-compactness-measure").val();
			data["schwartzberg"] = $("phase2-schwartzberg-measure").val();

			data["populationDifference"] = $("#phase2-pop-measure").val();
			
			data["efficiencyGap"] = $("#phase2-efficiency-gap-measure").val();
			data["lopsidedMargins"] = $("#phase2-lopsided-margins-measure").val();
			data["meanMedianDifference"] = $("#phase2-mean-median-difference-measure").val();

			phasePost("phase2", data, "1", "Couldn't Start Simmulated Annealing.", phase2);
		}
		else if (is_running == "1") {
			//STOP THE CLICKING UNTILL SUCCESS
		}
	});
});

function phasePost(path, data, setVal, err, phase) {
	phase.val(setVal);
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: "phaseController/" + path,
		data: JSON.stringify(data),
		dataType: 'json',
		timeout: 600000,
		success: function(results) {
			//Set BTN To PAUSE
			phase.val(0);
		},
		error: function(e) {
			phase.val(1);
			alert(err);
		}
	});
};

