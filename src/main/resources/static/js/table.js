function drawTable(data) { 
    
    var responses = data["response"];
	var table = {};
	
	responses.forEach((element,index,array)=>{
		var maxVote = element.maxVote;
		var maxDemographic = element.maxDemographic;
		
		if (!(maxDemographic in table)) {
			table[maxDemographic] = {}
		}
		if (!(maxVote in table[maxDemographic])) {
			table[maxDemographic][maxVote] = 0
		}
		table[maxDemographic][maxVote] += 1 
		
	});
    console.log(table);
    
    var demographics = [];
    var republican = [];
    var democratic = [];
    
    for (var key in table) {
    	demographics.push(key);
    	republican.push(isNaN(table[key]["REPUBLICAN"])? 0 : table[key]["REPUBLICAN"]);
    	democratic.push(isNaN(table[key]["DEMOCRATIC"])? 0 : table[key]["DEMOCRATIC"]);
    }
var data = [{
	  type: 'table',
	  columnorder: [1,2,3],
	  columnwidth: [400,400,400],
	  header: {
	    values: [["<b>Race</b>"],["<b>Democratic</b>"],
					                      ["<b>Republican</b>"]],
	    align: ["justify"],
	    line: {width: 3, color: '#506784'},
	    fill: {color: '#119DFF'},
	    font: {family: "Arial", size: 14, color: "white"}
	  },
	  cells: {
	    values: [demographics, democratic, republican],
	    align: ["left", "center"],
	    line: {color: "#0073e6", width: 1},
		 fill: {color: ['#ffffff', 'white']},
	    font: {family: "Arial", size: 14, color: "black"}
	  }
	}]

Plotly.plot('data-analysis-content', data);

}