<?php include 'admin-header.php';?>


<div id="container5" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>

<script>
Highcharts.chart('container5', {
    chart: {
        type: 'areaspline'
    },
    title: {
        text: 'Count grievances in months'
    },
    legend: {
        layout: 'vertical',
        align: 'left',
        verticalAlign: 'top',
        x: 150,
        y: 100,
        floating: true,
        borderWidth: 1,
        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
    },
    xAxis: {
        categories: [
            '0-4',
            '5-9',
            '10-14',
            '15-19',
            '20-24',
            '25+'
        ],
        plotBands: [{ // visualize the weekend
            from: 4.5,
            to: 6.5,
            color: 'rgba(68, 170, 213, .2)'
        }]
    },
    yAxis: {
        title: {
            text: 'Number of request'
        }
    },
    tooltip: {
        shared: true,
        valueSuffix: ' units'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        areaspline: {
            fillOpacity: 0.5
        }
    },
    series: [{
        name: 'Potholes',
        data: [3, 4, 3, 5, 4, 10]
    }, {
        name: 'Cracks',
        data: [1, 3, 4, 3, 3, 5]
    }]
});
	</script>


<?php include 'footer.php'; ?>