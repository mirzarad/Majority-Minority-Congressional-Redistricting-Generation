var trace1 = {
  x: [], 
  y: [], 
  name: 'Rest of world', 
  marker: {color: 'rgb(55, 83, 109)'}, 
  type: 'bar'
};

var trace2 = {
  x: [], 
  y: [], 
  name: 'China', 
  marker: {color: 'rgb(26, 118, 255)'}, 
  type: 'bar'
};

var data = [trace1, trace2];

var layout = {
  title: 'US Export of Plastic Scrap',
  xaxis: {tickfont: {
      size: 14, 
      color: 'rgb(107, 107, 107)'
    }}, 
  yaxis: {
    title: 'USD (millions)',
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
    x: 0, 
    y: 1.0, 
    bgcolor: 'rgba(255, 255, 255, 0)',
    bordercolor: 'rgba(255, 255, 255, 0)'
  }, 
  barmode: 'group', 
  bargap: 0.15, 
  bargroupgap: 0.1
};

Plotly.newPlot('myDiv', data, layout, {showSendToCloud:true});