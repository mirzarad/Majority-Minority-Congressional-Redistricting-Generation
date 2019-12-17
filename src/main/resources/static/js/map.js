$( function() {
	var stateId = {};
	stateId["06"] = "california";
	stateId["42"] = "pennsylvania";
	var mode = "stateHover";
	var currentState = "usa";
	var isInit = true;
	var isPrecinctZoomed = false;
	var districtResponse = null;
	var precinctResponse = null;
	
	
	var map = L.map('map', {
		zoomSnap: .05,
		minZoom: 4,
		maxZoom: 10,
	});

	
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 18,
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		id: 'mapbox.light'
	}).addTo(map);
	
	// control that shows state info on hover
	var info = L.control();

	info.onAdd = function (map) {
		this._div = L.DomUtil.create('div', 'info');
		this.update();
		return this._div;
	};
	
	info.update = function (props) {
		this._div.innerHTML = '<h5></h5>' +  (props ?
			'<b>' + props.name + '</b><br />' + props.score + ' <sup></sup>'
			: '');
	};

	info.addTo(map);
	
	var geojson;
	
	geojson = L.geoJson(null, {
		style: style,
		onEachFeature: onEachFeature
	}).addTo(map);
	
	map.attributionControl.addAttribution('Population data &copy; <a href="http://census.gov/">US Census Bureau</a>');
	
	
	var legend = L.control({position: 'bottomright'});
	
	legend.onAdd = function (map) {
	
		var div = L.DomUtil.create('div', 'info legend'),
			grades = [0, 10, 20, 30, 40, 50],
			labels = [],
			from, to;
	
		for (var i = 0; i < grades.length; i++) {
			from = grades[i];
			to = grades[i + 1];
	
			labels.push(
				'<i style="background:' + getColor(from + 1) + '"></i> ' +
				from + (to ? '&ndash;' + to : '+'));
		}
	
		div.innerHTML = labels.join('<br>');
		return div;
	};
	
	legend.addTo(map);

    usaAjax();

	//get color depending on population density value
	function getColor(d) {
		return  d > 50  ? '#edf8fb' :
				d > 40  ? '#bfd3e6' :
				d > 30  ? '#9ebcda' :
				d > 20  ? '#8c96c6' :
				d > 10  ? '#8856a7' :
				d > 0   ? '#810f7c' :
						  '#d9d9d9';
	}
	
	function style(feature) {
		return {
			weight: 2,
			opacity: 1,
			color: '#00004d',
			fillColor: '#0000b3',
			dashArray: '1',
			fillOpacity: 0.7,
			//fillColor: getColor(feature.properties.score)
		};
	}
	
	// HIGHLIGHT FEATURE HANDLER --> on mouseover
	function highlightFeature(e) {
		var layer = e.target;
	
		layer.setStyle({
			weight: 4,
			color: 'white',
			fillColor:'#8080ff',
			dashArray: '',
			fillOpacity: 0.7
		});
	
		if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
			layer.bringToFront();
		}
	
		info.update(layer.feature.properties);
	}
	
	// RESET HIGHLIGHT BACK TO NORMAL
	function resetHighlight(e) {
		geojson.resetStyle(e.target);
		info.update();
	}
	
	// ZOOM HANDLER
	function zoomToFeature(e) {
		//map.fitBounds(e.target.getBounds());
	}
	
	
	// ON EACH FEATURE HANDLER FUNCTION
	function onEachFeature(feature, layer) {
		layer.on({
			mouseover: highlightFeature,
			mouseout: resetHighlight,
			click: zoomToFeature
		});
		
		layer.on("mouseover", function(e) {
			$.ajax({
				type: "GET",
				contentType: "application/json",
				url: "map/" + mode + "/" + currentState + "/" + feature.id + "/" + selectedElection,
				dataType: 'json',
				timeout: 600000,
				success: function(results) {
					// ============//
					// VOTING DATA //
					// ============//
					
					// DECLARATIONS --------------------------------------------------------------------------------------------------

					// STATE //
					var stateRepublicanVotes;
					var stateDemocraticVotes;
					var stateLibertarianVotes;
					var stateGreenVotes;
					var stateMajorityParty;
					var stateTotalPopulation;
					
					// DISTRICT //
					var districtRepublicanVotes;
					var districtDemocraticVotes;
					var districtLibertarianVotes;
					var districtGreenVotes;
					var districtMajorityParty;
					var districtTotalPopulation;
					
					// PRECINCT //
					var precinctRepublicanVotes;
					var precinctDemocraticVotes;
					var precinctLibertarianVotes;
					var precinctGreenVotes;
					var precinctMajorityParty;
					var precinctTotalPopulation;
					// -----------------------------------------------------------------------------------------------------------------
					
					/*

					
					if(mode == "stateHover" && (feature.id == "42" || feature.id == "06")){
						// STATE VOTING DATA 
						$("#state-republican-votes").text("Republican Votes: " + );
						$("#state-democratic-votes").text("Democratic Votes: " + );
						$("#state-libertarian-votes").text("Libertarian Votes: " + );
						$("#state-green-votes").text("Green Votes: " + );
						$("#state-majority-party").text("Majority Party: " + );
						$("#state-total-population").text("Total Population: " + );
						
						// STATE DEMOGRAPHIC DATA 
						$("#state-white").text("White: " +);
						$("#state-african-american").text("African American: " +);
						$("#state-hispanic").text("Hispanic: " +);
						$("#state-native-american").text("Native American: " + );
						$("#state-asian").text("Asian: " + );
						$("#state-native-hawaiian").text("Native Hawaiian: " + );
						$("#state-native-alaskan").text("Native Alaskan: " + );

					}
				
					*/
					
					// ================ //
					// PRESEDENTIAL2016 //
					// ================ //

					if(selectedElection == "PRESIDENTIAL2016" && mode == "districtHover"){
						districtRepublicanVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["REPUBLICAN"];
						districtDemocraticVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["DEMOCRATIC"];
						districtLibertarianVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["INDEPENDENT"];
						districtGreenVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["GREEN"];
						districtTotalPopulation = response.votes.presVotes.PRESIDENTIAL2016.totalPopulation;

						// District Voting Data Table Update
						$("#district-republican-votes").text("Republican Votes: " + districtRepublicanVotes);
						$("#district-democratic-votes").text("Democratic Votes: " + districtDemocraticVotes);
						$("#district-libertarian-votes").text("Liberatarian Votes: " + districtLibertarianVotes);
						$("#district-green-votes").text("Green Votes: " + districtGreenVotes);
						$("#district-total-population").text("Total Population: " + districtTotalPopulation);
						
					}else if(selectedElection == "PRESIDENTIAL2016" && mode == "precinctHover"){
						precinctRepublicanVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["REPUBLICAN"];
						precinctDemocraticVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["DEMOCRATIC"];
						precinctLibertarianVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["INDEPENDENT"];
						precinctGreenVotes = results["response"]["votes"]["presVotes"]["PRESIDENTIAL2016"]["GREEN"];
						
						$("#precinct-republican-votes").text("Republican Votes: " + precinctRepublicanVotes);
						$("#precinct-democratic-votes").text("Democratic Votes: " + precinctDemocraticVotes);
						$("#precinct-libertarian-votes").text("Liberatarian Votes: " + precinctLibertarianVotes);
						$("#precinct-green-votes").text("Green Votes: " + precinctGreenVotes);
						$("#precinct-total-population").text("Total Population: " + precinctTotalPopulation);
					}
					// ================= //
					// CONGRESSIONAL2016 //
					// ================= //
					if(selectedElection == "CONGRESSIONAL2016"  && mode == "districtHover"){
						districtRepublicanVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["REPUBLICAN"];
						districtDemocraticVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["DEMOCRATIC"];
						districtLibertarianVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["INDEPENDENT"];
						districtGreenVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["GREEN"];
						
						$("#district-republican-votes").text("Republican Votes: " + districtRepublicanVotes);
						$("#district-democratic-votes").text("Democratic Votes: " + districtDemocraticVotes);
						$("#district-libertarian-votes").text("Liberatarian Votes: " + districtLibertarianVotes);
						$("#district-green-votes").text("Green Votes: " + districtGreenVotes);
						$("#district-total-population").text("Total Population: " + districtTotalPopulation);

					}else if(selectedElection == "CONGRESSIONAL2016"  && mode == "precinctHover"){
						precinctRepublicanVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["REPUBLICAN"];
						precinctDemocraticVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["DEMOCRATIC"];
						precinctLibertarianVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["INDEPENDENT"];
						precinctGreenVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2016"]["GREEN"];
						
						$("#precinct-republican-votes").text("Republican Votes: " + precinctRepublicanVotes);
						$("#precinct-democratic-votes").text("Democratic Votes: " + precinctDemocraticVotes);
						$("#precinct-libertarian-votes").text("Liberatarian Votes: " + precinctLibertarianVotes);
						$("#precinct-green-votes").text("Green Votes: " + precinctGreenVotes);
						$("#precinct-total-population").text("Total Population: " + precinctTotalPopulation);
					}
					// ================= //
					// CONGRESSIONAL2018 //
					// ================= //
					if(selectedElection == "CONGRESSIONAL2018" && mode == "districtHover"){
						/*
						districtRepublicanVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["REPUBLICAN"];
						districtDemocraticVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["DEMOCRATIC"];
						districtLibertarianVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["INDEPENDENT"];
						districtGreenVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["GREEN"];
						$("#district-republican-votes").text("Republican Votes: " + districtRepublicanVotes);
						$("#district-democratic-votes").text("Democratic Votes: " + districtDemocraticVotes);
						$("#district-libertarian-votes").text("Liberatarian Votes: " + districtLibertarianVotes);
						$("#district-green-votes").text("Green Votes: " + districtGreenVotes);
						$("#district-total-population").text("Total Population: " + districtTotalPopulation);
						*/
					}else if(selectedElection == "CONGRESSIONAL2018" && mode == "precinctHover"){
						/*
						precinctRepublicanVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["REPUBLICAN"];
						precinctDemocraticVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["DEMOCRATIC"];
						precinctLibertarianVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["INDEPENDENT"];
						precinctGreenVotes = results["response"]["votes"]["presVotes"]["CONGRESSIONAL2018"]["GREEN"];
						$("#precinct-republican-votes").text("Republican Votes: " + precinctRepublicanVotes);
						$("#precinct-democratic-votes").text("Democratic Votes: " + precinctDemocraticVotes);
						$("#precinct-libertarian-votes").text("Liberatarian Votes: " + precinctLibertarianVotes);
						$("#precinct-green-votes").text("Green Votes: " + precinctGreenVotes);
						$("#precinct-total-population").text("Total Population: " + precinctTotalPopulation);
						*/
					}
					
					
					// ======================= //
					// DEMOGRAPHIC POPULATIONS //
					// ======================= //
					// STATE
					var stateWhitePopulation;
					var stateAfricanAmericanPopulation;
					var stateHispanicPopulation;
					var stateNativeAmericanPopulation;
					var stateAsianPopulation;
					var stateNativeHawaiianPopulation;
					var stateNativeAlaskanPopulation = null;
					var stateTotalPopulation;

					// DISTRICT
					var districtWhitePopulation;
					var districtAfricanAmericanPopulation;
					var districtHispanicPopulation;
					var districtNativeAmericanPopulation;
					var districtAsianPopulation;
					var districtNativeHawaiianPopulation;
					var districtNativeAlaskanPopulation = null;
					var districtTotalPopulation;
					
					if(selectedElection == "PRESIDENTIAL2016" && mode == "districtHover"){
						districtWhitePopulation = results["response"]["demographics"]["totalDemographics"]["WHITE"];
						districtAfricanAmericanPopulation = results["response"]["demographics"]["totalDemographics"]["AFRICAN_AMERICAN"];
						districtHispanicPopulation = results["response"]["demographics"]["totalDemographics"]["HISPANIC"];
						districtNativeAmericanPopulation = results["response"]["demographics"]["totalDemographics"]["NATIVE_AMERICAN"];
						districtAsianPopulation = results["response"]["demographics"]["totalDemographics"]["ASIAN"];
						districtNativeHawaiianPopulation = results["response"]["demographics"]["totalDemographics"]["NATIVE_HAWAIIAN"];
						districtNativeAlaskanPopulation = null;
						districtTotalPopulation = results["response"]["demographics"]["totalDemographics"]["TOTAL"];
					}
					
					if(selectedElection == "CONGRESSIONAL2016"  && mode == "districtHover"){
						districtWhitePopulation = results["response"]["demographics"]["totalDemographics"]["WHITE"];
						districtAfricanAmericanPopulation = results["response"]["demographics"]["totalDemographics"]["AFRICAN_AMERICAN"];
						districtHispanicPopulation = results["response"]["demographics"]["totalDemographics"]["HISPANIC"];
						districtNativeAmericanPopulation = results["response"]["demographics"]["totalDemographics"]["NATIVE_AMERICAN"];
						districtAsianPopulation = results["response"]["demographics"]["totalDemographics"]["ASIAN"];
						districtNativeHawaiianPopulation = results["response"]["demographics"]["totalDemographics"]["NATIVE_HAWAIIAN"];
						districtNativeAlaskanPopulation = null;
						districtTotalPopulation = results["response"]["demographics"]["totalDemographics"]["TOTAL"];
					}
					
					if(selectedElection == "CONGRESSIONAL2018" && mode == "precinctHover"){
						/*
						districtWhitePopulation = results["response"]["demographics"]["totalDemographics"]["WHITE"];
						districtAfricanAmericanPopulation = results["response"]["demographics"]["totalDemographics"]["AFRICAN_AMERICAN"];
						districtHispanicPopulation = results["response"]["demographics"]["totalDemographics"]["HISPANIC"];
						districtNativeAmericanPopulation = results["response"]["demographics"]["totalDemographics"]["NATIVE_AMERICAN"];
						districtAsianPopulation = results["response"]["demographics"]["totalDemographics"]["ASIAN"];
						districtNativeHawaiianPopulation = results["response"]["demographics"]["totalDemographics"]["NATIVE_HAWAIIAN"];
						districtNativeAlaskanPopulation = null;
						districtTotalPopulation = results["response"]["demographics"]["totalDemographics"]["TOTAL"];
						*/
					}

				}, // end success
				error: function(e) {
					//alert("Failed To Retrieve State Data." + feature.id);
				}
			});
		});
		
		layer.on("click", function(e) {
			if (feature.id == "42" || feature.id == "06") {
				if(feature.id=="42"){
					$("#state-select-menu").text("PENNSYLVANIA");
					$("#current-map-view-text").text("Pennsylvania");
					$("#district-view-toggle").attr("disabled", false);
					$("#election-select-menu").css("background-color", "#3902cc");
					$("#election-select-menu").attr("disabled", false);
				}
				if(feature.id=="06"){
					$("#state-select-menu").text("CALIFORNIA");
					$("#current-map-view-text").text("California");
					$("#district-view-toggle").attr("disabled", false);
					$("#election-select-menu").css("background-color", "#3902cc");
					$("#election-select-menu").attr("disabled", false);

				}
				districtResponse = districtAjax(stateId[feature.id]);
				precinctResponse = precinctAjax(stateId[feature.id]);
			}
		});
	}
	
	$("#california").on("click",function(e) {
		e.preventDefault();
		districtAjax(this.id);
		precinctAjax(this.id);

	});
   
	$("#pennsylvania").on("click",function(e) {

		e.preventDefault();
		districtAjax(this.id);
		precinctAjax(this.id);
		
	});
   
	$("#full").on("click",function(e) {
		e.preventDefault();
		usaAjax();
	});
	
	$("#district-view-toggle").on("click", function(e){
		reloadMap(districtResponse["map"]);
	});
	
	map.on('zoomend', function() {
		if(mode == "stateHover"){
			return;
		}
		var zoomlevel = map.getZoom();
		    if (zoomlevel < 7 && isPrecinctZoomed == true){
		    	// display district mode
				mode = "districtHover";
		    	isPrecinctZoomed = false;
		    	reloadMap(districtResponse["map"]);
				$("#district-view-toggle").attr("disabled", false);
				console.log(mode);
		    }
		    if (zoomlevel >= 7 && isPrecinctZoomed == false){
		    	// display precinct mode
				mode = "precinctHover";
		    	isPrecinctZoomed = true;
		    	reloadMap(precinctResponse["map"]);
				$("#district-view-toggle").attr("disabled", true);
				console.log(mode);
		    }
		console.log("Current Zoom Level =" + zoomlevel)
	});
	
	
	function usaAjax() {
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url: "selectState/usa",
			dataType: 'json',
			timeout: 600000,
			success: function(results) {
				currentState = "usa";
				$("#phase-inputs").hide();
	
				mode = "stateHover";

				var response = results["response"];
				var view = response["view"];
				var level = response["level"];
				var statesData = response["map"];
	
				map.setView(view, level)
				reloadMap(statesData);
				
				precinctResponse = null;
				districtResponse = null;
			},
			error: function(e) {
				alert("Failed To Load Requested Map");
			}
		});
	}
	
	function precinctAjax(state) {
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url: "selectState/precincts/" + selectedElection + "/" + state,
			dataType: 'json',
			timeout: 600000,
			success: function(results) {
				currentState = state;
				mode = "precinctHover";

				var response = results["response"];
				var statesData = response["map"];
			
				//reloadMap(statesData);
				precinctResponse = response;
			},
			error: function(e) {
				alert("Failed To Load Requested Map");
			}
		});
	}
	
	function districtAjax(state) {
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url: "selectState/districts/" + state,
			dataType: 'json',
			timeout: 600000,
			success: function(results) {
				currentState = state;
				
				$("#phase-inputs").show();
				mode = "districtHover";

				var response = results["response"];
				var view = response["view"];
				var level = response["level"];
				var statesData = response["map"];
			
				map.setView(view, level)
				reloadMap(statesData);
				districtResponse = response;
			},
			error: function(e) {
				alert("Failed To Load Requested Map");
			}
		});
	}
	
	function electionAjax(){
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url: "selectState/precincts/" + selectedElection + "/" + state,
			dataType: 'json',
			timeout: 600000,
			success: function(results) {
				
			var results = response["response"];
				
			},
			error: function(e){
				alert("Failed to retrieve the voting data");
			}
		});
	}
	
	function reloadMap(statesData) {
		if (isInit) {
			map.removeLayer(geojson);
		}
		else {
			isInit = false;
		}
		geojson = L.geoJson(statesData, {
			style: style,
			onEachFeature: onEachFeature
		}).addTo(map);
	}
	
});