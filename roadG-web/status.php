<?php include 'admin-header.php';?>


<div id="container6" style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>

<script>
 Highcharts.chart('container6', {

    chart: {
        type: 'column'
    },

    title: {
        text: 'Status of request in differeent cities'
    },

    subtitle: {
        text: ''
    },

    legend: {
        align: 'right',
        verticalAlign: 'middle',
        layout: 'vertical'
    },

    xAxis: {
        categories: ['Ahmedabad', 'Surat', 'Vadodara', 'Bhavnagar', 'Gandhinagar', 'Jamnagar'],
        labels: {
            x: -10
        }
    },

    yAxis: {
        allowDecimals: false,
        title: {
            text: 'Count of request'
        }
    },

    series: [{
        name: 'Completed',
        data: [11, 10, 3,5,20,11]
    }, {
        name: 'Pending',
        data: [6, 4, 2,11,15,6]
    }, {
        name: 'In progress',
        data: [8, 4, 3,7,11,10]
    }],

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    align: 'center',
                    verticalAlign: 'bottom',
                    layout: 'horizontal'
                },
                yAxis: {
                    labels: {
                        align: 'left',
                        x: 0,
                        y: -5
                    },
                    title: {
                        text: null
                    }
                },
                subtitle: {
                    text: null
                },
                credits: {
                    enabled: false
                }
            }
        }]
    }
});

$('#small').click(function () {
    chart.setSize(400, 300);
});

$('#large').click(function () {
    chart.setSize(600, 300);
});
	</script>


<?php include 'footer.php'; ?>