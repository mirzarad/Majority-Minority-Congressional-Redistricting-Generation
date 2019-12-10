$( function() {
	var mode = "stateHover";
	var currentState = "full";
	var isInit = true;
	var election = "PRESIDENTIAL2016";
	var isPrecinctZoomed = false;
	var districtResponse = null;
	var precinctResponse = null;
	
	var map = L.map('map', {
		zoomSnap: .05
	});
	var clickStates = {};
	clickStates["42"] = "penn";
	clickStates["6"] = "california";
	
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
		this._div.innerHTML = '<h5>The Polsby-Popper ratio</h5>' +  (props ?
			'<b>' + props.name + '</b><br />' + props.score + ' <sup></sup>'
			: 'Hover over a state');
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
			color: 'black',
			dashArray: '1',
			fillOpacity: 0.7,
			//fillColor: getColor(feature.properties.score)
		};
	}
	
	function highlightFeature(e) {
		var layer = e.target;
	
		layer.setStyle({
			weight: 4,
			color: 'white',
			dashArray: '',
			fillOpacity: 0.7
		});
	
		if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
			layer.bringToFront();
		}
	
		info.update(layer.feature.properties);
	}
	
	function resetHighlight(e) {
		geojson.resetStyle(e.target);
		info.update();
	}
	
	function zoomToFeature(e) {
		map.fitBounds(e.target.getBounds());
	}
	
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
				url: "map/" + mode + "/" + currentState + "/" + feature.id + "/" + election,
				dataType: 'json',
				timeout: 600000,
				success: function(results) {
				},
				error: function(e) {
					alert("Failed To Retrieve State Data." + feature.id);
				}
			});
		});
		
		layer.on("click", function(e) {
			if (feature.id == "42" || feature.id == "06") {
				districtResponse = districtAjax(feature.id);
				precinctResponse = precinctAjax(feature.id);
			}
		});
	}
	
	$("#california").on("click",function(e) {
		e.preventDefault();
		districtAjax("6");
		precinctAjax(6);
	});
   
	$("#penn").on("click",function(e) {

		e.preventDefault();
		districtAjax("42");
		precinctAjax(42);
	});
   
	$("#full").on("click",function(e) {
		e.preventDefault();
		usaAjax();
	});
	
	map.on('zoomend', function() {
		if(mode == "stateHover"){
			return;
		}
		var zoomlevel = map.getZoom();
		    if (zoomlevel < 8 && isPrecinctZoomed == true){
		    	// display district mode
		    	isPrecinctZoomed = false;
		    	reloadMap(districtResponse["map"]);
		    }
		    if (zoomlevel >= 8 && isPrecinctZoomed == false){
		    	// display precinct mode
		    	isPrecinctZoomed = true;
		    	reloadMap(precinctResponse["map"]);
		    }
		console.log("Current Zoom Level =" + zoomlevel)
	});
	
    function colorizeFeatures(data) {
        var counter = 0;
        for (var i = 0; i < data.features.length; i++) {
            data.features[i].properties.color = 'hsl(' + 360 * Math.random() + ', 50%, 50%)';
            counter += data.features[i].geometry.coordinates[0].length;
        }
        return counter;
    }
	
	
	function usaAjax() {
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url: "selectState/full",
			dataType: 'json',
			timeout: 600000,
			success: function(results) {
				currentState = "full";
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
			url: "selectState/" + clickStates[state],
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
				currentState = clickStates[state];
				
				$("#phase-inputs").show();
				mode = "districtHover";

				var response = results["response"];
				var view = response["view"];
				var level = response["level"];
				var statesData = response["map"];
			
				map.setView(view, level)
				reloadMap(statesData);
				colorizeFeatures(statesData);
				districtResponse = response;
			},
			error: function(e) {
				alert("Failed To Load Requested Map");
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
		geojson = L.vectorGrid.slicer(statesData, {
			interactive: true,
			style: style,
			onEachFeature: onEachFeature
		}).addTo(map);
	}
});