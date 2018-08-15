<?php include 'admin-header.php';?>


<div id="container2" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>

<script>

				Highcharts.chart('container2', {
			    chart: {
			        plotBackgroundColor: null,
			        plotBorderWidth: null,
			        plotShadow: false,
			        type: 'pie'
			    },
			    title: {
			        text: 'No. of grievences in Ahmedabad'
			    },
			    tooltip: {
			        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			    },
			    plotOptions: {
			        pie: {
			            allowPointSelect: true,
			            cursor: 'pointer',
			            dataLabels: {
			                enabled: true,
			                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
			                style: {
			                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                }
			            }
			        }
			    },
			    series: [{
			        name: 'Grievences',
			        colorByPoint: true,
			        data: [{
			            name: 'Potholes',
			            y: 61.41,
			            sliced: true,
			            selected: true
			        }, {
			            name: 'Tree fallen',
			            y: 11.84
			        }, {
			            name: 'Cracks',
			            y: 10.85
			        }, {
			            name: 'Huge Pits',
			            y: 4.67
			        }, {
			            name: 'Water Logging',
			            y: 4.18
			        }, {
			            name: 'Levelling',
			            y: 1.64
			        }, {
			            name: 'Degradations',
			            y: 1.6
			        }, {
			            name: 'Safety ',
			            y: 1.2
			        }]
			    }]
			});
	</script>


<?php include 'footer.php'; ?>