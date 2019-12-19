function drawTable(data) { 
    
    var responses = data["response"];
	var precincts=[];
	var recordDem= [];
	var recordVot = [];
	
	
	responses.forEach((element,index,array)=>{
	var votes = element.votes;
	
	var demographics = element.demographics;
	var dem = demographics.votingDemographics;
	precincts.push(votes.geomID);
	
	let max = Math.max(dem.ASIAN,dem.AFRICAN_AMERICAN,dem.HISPANIC,dem.WHITE);
	var key = Object.keys(dem).find(key => dem[key] == max);
	var precentage = Math.ceil(max/dem.TOTAL *100);
	var record = precentage + "% " + key;
	recordDem.push(record);
	
	var vote = votes.presVotes;
	var voteResults = vote.PRESIDENTIAL2016;
	
	
	 arrSum = voteResults.INDEPENDENT+ voteResults.DEMOCRATIC+ voteResults.REPUBLICAN + voteResults.GREEN;
	
	if(voteResults.DEMOCRATIC>voteResults.REPUBLICAN){
		var avg = Math.ceil(voteResults.DEMOCRATIC/arrSum *100);
		recordVot.push(avg + "% Democratic");
		
	}
	else{
		var avg = Math.ceil(voteResults.REPUBLICAN/arrSum *100);
		recordVot.push(avg + "% Republican");
	}
	
	}
	);
    console.log(recordVot);
	console.log(recordDem);

var values = [
      precincts,
      recordDem,
      recordVot]

var data = [{
  type: 'table',
  columnorder: [1,2,3],
  columnwidth: [400,400,400],
  header: {
    values: [["<b>PrecinctID</b>"],["<b>Demographic</b>"],
				                      ["<b>VotingParty</b>"]],
    align: ["justify"],
    line: {width: 3, color: '#506784'},
    fill: {color: '#119DFF'},
    font: {family: "Arial", size: 14, color: "white"}
  },
  cells: {
    values: values,
    align: ["left", "center"],
    line: {color: "#506784", width: 1},
	 fill: {color: ['#25FEFD', 'white']},
    font: {family: "Arial", size: 14, color: "black"}
  }
}]

Plotly.plot('data-analysis-content', data);

}