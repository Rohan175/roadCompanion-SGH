<?php include 'admin-header.php';?>


<div id="container1" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>

<script>

Highcharts.chart('container1', {
		    chart: {
		        type: 'bar'
		    },
		    title: {
		        text: 'No. of requests (city wise)'
		    },
		    
		    xAxis: {
		        categories: ['Ahmedabad' , 'Surat' , 'Baroda'],
		        title: {
		            text: 'Name of cities'
		        }
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text: 'No. of Complains',
		            align: 'high'
		        },
		        labels: {
		            overflow: 'justify'
		        }
		    },
		    tooltip: {
		        valueSuffix: ' millions'
		    },
		    plotOptions: {
		        bar: {
		            dataLabels: {
		                enabled: true
		            }
		        }
		    },
		    legend: {
		        layout: 'vertical',
		        align: 'right',
		        verticalAlign: 'top',
		        x: -40,
		        y: 80,
		        floating: true,
		        borderWidth: 1,
		        backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
		        shadow: true
		    },
		    credits: {
		        enabled: false
		    },


		    series: [{
		         name: 'Potholes',
			        data: [31, 23, 12]
			    }, {
			        name: 'Leveling',
			        data: [12, 3, 5]
			    }, {
			        name: 'Road Cracks',
			        data: [34, 53, 12]
			    }, {
			        name: 'Huge Pits',
			        data: [32, 12, 5]
			    }]
		});
	</script>


<?php include 'footer.php'; ?>
