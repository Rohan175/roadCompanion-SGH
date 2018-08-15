<?php include 'admin-header.php';?>


<div id="container4" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>

<script>
Highcharts.chart('container4', {
	    chart: {
	        type: 'bar'
	    },
	    title: {
	        text: 'Comparision for two types of road grivance'
	    },
	    subtitle: {
	        text: ''
	    },
	    xAxis: [{
	        categories: categories,
	        reversed: false,
	        labels: {
	            step: 1
	        }
	    }, { // mirror axis on right side
	        opposite: true,
	        reversed: false,
	        categories: categories,
	        linkedTo: 0,
	        labels: {
	            step: 1
	        }
	    }],
	    yAxis: {
	        title: {
	            text: null
	        },
	        labels: {
	            formatter: function () {
	                return Math.abs(this.value) ;
	            }
	        }
	    },

	    plotOptions: {
	        series: {
	            stacking: 'normal'
	        }
	    },

	    tooltip: {
	        formatter: function () {
	            return '<b>' + this.series.name + ', age ' + this.point.category + '</b><br/>' +
	                'Population: ' + Highcharts.numberFormat(Math.abs(this.point.y), 0);
	        }
	    },

	    series: [{
	        name: 'Potholes',
	        data: [
	            -1, -2, -0, -1,
	            -4, -3.0, -3, -3,
	            -2, -3
	        ]
	    }, {
	        name: 'Fallen tree',
	        data: [
	            2, 0, 1, 2, 0,
	            2, 3, 0, 2, 3
	        ]
	    }]
	});
	</script>


<?php include 'footer.php'; ?>