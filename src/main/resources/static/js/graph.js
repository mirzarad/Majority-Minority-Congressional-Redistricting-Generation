function draw(data){
	var responses = data["response"];
	var voteIndep=[];
	var voteDem=[];
	var voteRep=[];
	var voteGrn=[];
	
	
	var precincts=[];
	var asian = [];
	var afric = [];
	var nat = [];
	var hawaii = [];
	var hispanic = [];
	var white = [];
	
	responses.forEach((element,index,array)=>{
	var votes = element.votes;
	var demographics = element.demographics;
	precincts.push(votes.geomID);
	
	var dem = demographics.votingDemographics;
	asian.push(dem.ASIAN);
	afric.push(dem.AFRICAN_AMERICAN);
	nat.push(dem.NATIVE_AMERICAN);
	hawaii.push(dem.NATIVE_HAWAIIAN);
	hispanic.push(dem.HISPANIC);
	white.push(dem.WHITE);
	
	
	
	var vote = votes.presVotes;
	var voteResults = vote.PRESIDENTIAL2016;
	voteIndep.push(voteResults.INDEPENDENT);
	voteDem.push(voteResults.DEMOCRATIC);
	voteRep.push(voteResults.REPUBLICAN);
	voteGrn.push(voteResults.GREEN);
	}
	);
    console.log(precincts);
	console.log(voteIndep );
	console.log(voteDem);
    
	console.log(voteRep);
	

var binSize = Math.round(Math.sqrt(voteRep.length));
var max1 = Math.max(...voteDem);
var max2 = Math.max(...voteRep);
var max3=Math.max(max1,max2);

var min1 = Math.min(...voteDem) ;
var min2 = Math.min(...voteRep);

var min = Math.min(min1,min2) ;
var binWidth = Math.round((max3-min)/binSize);
var binR = Math.round((Math.max(...voteRep)-Math.min(...voteRep))/binSize);
var binGr = Math.round((Math.max(...voteGrn)-Math.min(...voteGrn))/binSize);
var binIndep = Math.round((Math.max(...voteIndep)-Math.min(...voteIndep))/binSize);
console.log(binSize);
console.log(binWidth);
var min1 = Math.min(...voteDem) ;
var min2 = Math.min(...voteRep);
var min3 = Math.min(...voteIndep);
var min4 = Math.min(...voteGrn);
console.log(Math.max(...voteRep));
console.log(Math.max(...voteDem));
console.log(binSize)
var binY= [];
var binYR = [];
var binYGR = [];
var binYIN = [];


var temp = [];
var tempR = [];
var tempGr = [];
var tempIN = [];


var binX = []; 
var binXR=[];
var binXGr = [];
var binXIN = [];    

for (let step = 0; step <binSize; step++) {
binX.push(min1);
binXR.push(min2);
binXGr.push(min4);
binXIN.push(min3);
temp = voteDem.filter(element => (element>min1 && element<min1+binWidth));
tempR = voteRep.filter(element => (element>min2 && element<min2+binWidth));
tempGr = voteGrn.filter(element => (element>min4 && element<min4+binGr));
tempIN = voteIndep.filter(element => (element>min3 && element<min3+binIndep));
min1 +=binWidth;
min2+=binR;
min4+=binGr;
min3+=binIndep;

binY.push(temp.length);
binYR.push(tempR.length);
binYGR.push(tempGr.length);
binYIN.push(tempIN.length);

}




var trace1 = {
  x: binX,
  y: binY,
  name: 'Democratic', 
  marker: {color: 'blue'}, 
  type: 'bar'
};

var trace2 = {
  x: binXR, 
  y: binYR, 
  name: 'Republican', 
  marker: {color: 'red'}, 
  type: 'bar'
};

var trace3 = {
  x:binXGr,
  y:binYGR, 
  name: 'Green', 
  marker: {color: 'green'}, 
  type: 'bar'
};

var trace4 = {
  x:binXIN,
  y:binYIN, 
  name: 'Independent', 
  marker: {color: 'grey'}, 
  type: 'bar'
};


var data = [trace1,trace2];

var layout = {
  title: 'Party Votes Distribution',
  xaxis: {
	  
      autorange: true,
	  tickfont: {
      size: 12, 
      color: 'rgb(107, 107, 107)'
    }}, 
  yaxis: {
	
    autorange: true,
    title: 'Precincts Count',
    titlefont: {
      size: 16, 
      color: 'rgb(107, 107, 107)'
    }, 
    tickfont: {
      size: 14, 
      color: "rgb(107, 107, 107)"
    }
  }, 
  legend: {
    x: -1, 
    y: 0, 
    bgcolor: 'rgba(255, 255, 255, 0)',
    bordercolor: 'rgba(255, 255, 255, 0)'
  }, 
  barmode: 'group', 
  bargap: 0.15, 
  bargroupgap: 0.1
};

Plotly.newPlot('data-analysis-vote', data, layout, {showSendToCloud:true});


/* Demographics graph */

var max1 = Math.max(...asian);
var max2 = Math.max(...afric);
var max3 = Math.max(...white);
var max4 = Math.max(...hispanic);
var max  = Math.max(max1,max2,max3,max4);


var min1 = Math.min(...asian) ;
var min2 = Math.min(...afric);
var min3 = Math.min(...white);
var min4 = Math.min(...hispanic);
var min = Math.min(min1,min2,min3,min4);

var binsize = Math.round(Math.sqrt(white.length));

var binwidth = Math.round((max-min)/binsize);
/*
var binR = Math.round((Math.max(...voteRep)-Math.min(...voteRep))/binSize);
var binGr = Math.round((Math.max(...voteGrn)-Math.min(...voteGrn))/binSize);
var binIndep = Math.round((Math.max(...voteIndep)-Math.min(...voteIndep))/binSize);
*/
console.log(binsize);
console.log(binwidth);

var binYA= [];
var binYB = [];
var binYW = [];
var binYH = [];


var tempA = [];
var tempW = [];
var tempB = [];
var tempH = [];


var binX= []; 
    

/*populate the data*/
for (let step = 0; step <binsize; step++) {
binX.push(min);
tempA = asian.filter(element => (element>min && element<min+binwidth));
tempW = afric.filter(element => (element>min && element<min+binwidth));
tempB = white.filter(element => (element>min && element<min+binwidth));
tempH = hispanic.filter(element => (element>min && element<min+binwidth));
min+=binwidth

binYA.push(tempA.length);
binYB.push(tempB.length);
binYW.push(tempW.length);
binYH.push(tempH.length);

}
var trace1 = {
  x: binX,
  y: binYW,
  name: 'White', 
  marker: {color: 'grey'}, 
  type: 'bar'
};

var trace2 = {
  x: binX, 
  y: binYB, 
  name: 'African American', 
  marker: {color: 'black'}, 
  type: 'bar'
};

var trace3 = {
  x:binX,
  y:binYH, 
  name: 'Hispanic', 
  marker: {color: 'brown'}, 
  type: 'bar'
};

var trace4 = {
  x:binX,
  y:binYA, 
  name: 'Asian', 
  marker: {color: 'yellow'}, 
  type: 'bar'
};


var data = [trace1,trace2,trace3,trace4];

var layout = {
  title: 'Demographics Distribution',
  xaxis: {
	  
      autorange: true,
	  tickfont: {
      size: 12, 
      color: 'rgb(107, 107, 107)'
    }}, 
  yaxis: {
	
    autorange: true,
    title: 'Precincts Count',
    titlefont: {
      size: 16, 
      color: 'rgb(107, 107, 107)'
    }, 
    tickfont: {
      size: 14, 
      color: "rgb(107, 107, 107)"
    }
  }, 
  legend: {
    x: -1, 
    y: 0, 
    bgcolor: 'rgba(255, 255, 255, 0)',
    bordercolor: 'rgba(255, 255, 255, 0)'
  }, 
  barmode: 'group', 
  bargap: 0.15, 
  bargroupgap: 0.1
};

Plotly.newPlot('data-analysis-dem', data, layout, {showSendToCloud:true});


}

