<?php include 'admin-header.php';?>


<div id="container3" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>

<script>
Highcharts.chart('container3', {
			    chart: {
			        type: 'spline'
			    },
			    title: {
			        text: 'No. of requests per date(grievence wise)'
			    },
			    
			    xAxis: {
			        type: 'datetime',
			        dateTimeLabelFormats: { // don't display the dummy year
			            month: '%e. %b',
			            year: '%b'
			        },
			        title: {
			            text: 'Date'
			        }
			    },
			    yAxis: {
			        title: {
			            text: 'No. of requests'
			        },
			        min: 0
			    },
			    tooltip: {
			        headerFormat: '<b>{series.name}</b><br>',
			        pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
			    },

			    plotOptions: {
			        spline: {
			            marker: {
			                enabled: true
			            }
			        }
			    },

			    colors: ['#6CF', '#F00', '#0F0', '#036', '#000'],

			    // Define the data points. All series have a dummy year
			    // of 2017/71 in order to be compared on the same x axis. Note
			    // that in JavaScript, months start at 0 for January, 1 for February etc.
			    series: [{
			        name: "Potholes",
			        data: [
			            [Date.UTC(2017, 10, 25), 2],
			            [Date.UTC(2017, 11,  6), 4],
			            [Date.UTC(2017, 11, 20), 6],
			            [Date.UTC(2017, 11, 25), 12],
			            [Date.UTC(2018, 1,  4), 31],
			            [Date.UTC(2018, 1, 17), 24],
			            [Date.UTC(2018, 1, 24), 14],
			            [Date.UTC(2018, 1,  4), 12],
			            [Date.UTC(2018, 1, 14), 36],
			            [Date.UTC(2018, 2,  6), 45],
			            [Date.UTC(2018, 2, 14), 6],
			            [Date.UTC(2018, 2, 24), 23],
			            [Date.UTC(2018, 3,  1), 21]
			            
			        ]
			    }, {
			        name: "Road Cracks",
			        data: [
			            [Date.UTC(2017, 10,  9), 7],
			            [Date.UTC(2017, 10, 15), 23],
			            [Date.UTC(2017, 10, 20), 25],
			            [Date.UTC(2017, 10, 25), 23],
			            [Date.UTC(2017, 10, 30), 39],
			            [Date.UTC(2017, 11,  5), 41],
			            [Date.UTC(2017, 11, 10), 9],
			            [Date.UTC(2017, 11, 15), 73],
			            [Date.UTC(2017, 11, 20), 41],
			            [Date.UTC(2017, 11, 25), 07],
			            [Date.UTC(2017, 11, 30), 8],
			            [Date.UTC(2018, 1,  5), 15],
			            [Date.UTC(2018, 1, 11), 9],
			            [Date.UTC(2018, 1, 17), 24],
			            [Date.UTC(2018, 1, 20), 42],
			            [Date.UTC(2018, 1, 25), 03],
			            [Date.UTC(2018, 1, 30), 39],
			            [Date.UTC(2018, 1,  5), 77],
			            [Date.UTC(2018, 1, 26), 12],
			            [Date.UTC(2018, 3, 19), 1]
			        ]
			    }, {
			        name: "Leveling",
			        data: [
			            [Date.UTC(2017, 9, 15), 2],
			            [Date.UTC(2017, 9, 31), 9],
			            [Date.UTC(2017, 10,  7), 17],
			            [Date.UTC(2017, 10, 10), 12],
			            [Date.UTC(2017, 11, 10), 11],
			            [Date.UTC(2017, 11, 13), 31],
			            [Date.UTC(2017, 11, 16), 41],
			            [Date.UTC(2017, 11, 19), 11],
			            [Date.UTC(2017, 11, 22), 8],
			            [Date.UTC(2017, 11, 25), 23],
			            [Date.UTC(2017, 11, 28), 37],
			            [Date.UTC(2018, 1, 16), 68],
			            [Date.UTC(2018, 1, 19), 55],
			            [Date.UTC(2018, 1, 22), 4],
			            [Date.UTC(2018, 1, 25), 24],
			            [Date.UTC(2018, 1, 22), 45],
			            [Date.UTC(2018, 1, 25), 62],
			            [Date.UTC(2018, 1, 28), 21],
			            [Date.UTC(2018, 2,  4), 68],
			            [Date.UTC(2018, 2,  7), 32],
			            [Date.UTC(2018, 2, 10), 5],
			            [Date.UTC(2018, 2, 13), 75],
			            [Date.UTC(2018, 2, 16), 86],
			            [Date.UTC(2018, 2, 19), 14],
			            [Date.UTC(2018, 2, 22), 2],
			            [Date.UTC(2018, 2, 25), 27],
			            [Date.UTC(2018, 2, 27), 12],
			            [Date.UTC(2018, 2, 30), 8],
			            [Date.UTC(2018, 3,  3), 5],
			            [Date.UTC(2018, 3,  6), 4]
			            
			        ]
			    }]
			});


	var categories = [
	    '0-2', '3-5', '6-8', '9-11',
	    '12-14', '15-17', 
	  '18-20','21-23','24-26','27+'
	];
	</script>


<?php include 'footer.php'; ?>