$( function() {
	var mode = "stateHover";
	var currentState = "full";
	var isInit = true;
	
	var map = L.map('map');
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

    stateAjax("full", false);

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
			fillColor: getColor(feature.properties.score)
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
				url: "map/" + mode + "/" + currentState + "/" + feature.id,
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
				stateAjax(clickStates[feature.id], true);
			}
		});
	}
	
	$("#california").on("click",function(e) {
		e.preventDefault();
		stateAjax("california", true);
	});
   
	$("#penn").on("click",function(e) {
		e.preventDefault();
		stateAjax("penn", true);
	});
   
	$("#full").on("click",function(e) {
		e.preventDefault();
		stateAjax("full", false);
	});
	
	function stateAjax(state, showInputs) {
		$.ajax({
			type: "GET",
			contentType: "application/json",
			url: "selectState/" + state,
			dataType: 'json',
			timeout: 600000,
			success: function(results) {
				currentState = state;
				
				if (showInputs) {
					$("#phase-inputs").show();
				}
				else {
					$("#phase-inputs").hide();
				}
				if (state =="penn" || state == "california") {
					mode = "precinctHover";
				}
				else {
					mode = "stateHover";
				}

				var response = results["response"];
				var view = response["view"];
				var level = response["level"];
				var statesData = response["map"];
			
				map.setView(view, level)
				
				//alert(JSON.stringify(statesData));
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
				
			},
			error: function(e) {
				alert("Failed To Load Requested Map");
			}
		});
	}
	
});

