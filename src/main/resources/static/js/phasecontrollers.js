var stompClient = null;
var layers = {};
connect(stompClient);

$(function () {
	var stompClient = null;
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
		
		$("#phase1-run").attr("disabled", false); // enable phase 1 button
		
		if (is_running == "0") {
			var data = {};
			data["demographicBlocPercentage"] = $("#phase0-demographic-bloc-measure").val();
			data["voteBlocPercentage"] = $("#phase0-vote-bloc-measure").val();
			data["election"] = selectedElection;
			data["state"] = selectedState;

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
			

	        var numberOfDesiredDistricts = document.getElementById("numDistrictsInput").value;
			
			var data = {};
			data["maxDemographicBlocPercentage"] = $("#phase1-demographic-bloc-measure").val();
			data["minDemographicBlocPercentage"] = $("#phase1-vote-bloc-measure").val();
			data["demographics"] = {AFRICAN_AMERICAN: $("#phase1-african").is(":checked"), 
									 NATIVE_AMERICAN: $("#phase1-native").is(":checked"), 
									 ASIAN: $("#phase1-asian").is(":checked"), 
									 NATIVE_HAWAIIAN: $("#phase1-hawaiin").is(":checked"),
									 HISPANIC: $("#phase1-hispanic").is(":checked"),
									 WHITE: $("#phase1-white").is(":checked")};
			
			data["election"] = selectedElection;
			data["state"] = selectedState;
			data["numberOfDistricts"] = numberOfDesiredDistricts;
			sendName(data);
			showGreeting();
		

			//phasePost("phase1", data, "1", "Couldn't Start Graph Partitioning.", phase1);
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

// Web Socket functions:
function connect() {
	var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/phase/results', function (response) {
            showGreeting(JSON.parse(response.body));
        });
    });
}

function sendName(data) {
	//waitForSocketConnection(stompClient, function(){
	stompClient.send("/home/run.phase1", {}, JSON.stringify(data));
	//});
}

function waitForSocketConnection(socket, callback){
    setTimeout(
        function () {
            if (socket.status === "CONNECTED") {
                console.log("Connection is made")
                if (callback != null){
                    callback();
                }
            } else {
                console.log("wait for connection...")
                waitForSocketConnection(socket, callback);
            }

        }, 5); 
}

function precinctStyle(newDistrictID){
	return {
		weight:2,
		opacity: 1,
		color: '#0000b3',
		fillColor: precinctColor(newDistrictID),
		dashArray: '1',
		fillOpacity: 0.7
	}
}

function precinctColor(newDistrictID){
	var id = parseInt(newDistrictID);
	desiredDistricts = 10000;
	return uniqueNewDistrictColoring(id, desiredDistricts); //colorNum == newDistrictID | colors = numberOfDesiredDistricts
}

function uniqueNewDistrictColoring(newDistrictID, desiredDistricts){
		if(desiredDistricts < 1) desiredDistricts = 1; // default to a single color and avoids divide by zero
			return "hsl(" + (newDistrictID * (360/desiredDistricts) % 360) + ",100%,50%)";
}

function eachFeature(feature, layer){
	layers[feature.id] = layer;
}

function showGreeting(message) {
	console.log(message);
	var id = message["uniqueID"];
	var p = message["newPrecinct"];

	var match = layers[p];
	match.setStyle({fillColor: precinctStyle(id)});
}
// END WEBSOCKET FUNCTIONS


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
			
            drawTable(results);
            draw(results);
		},
		error: function(e) {
			phase.val(0);
			alert(err);
		}
	});
};

