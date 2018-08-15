<!DOCTYPE html>
<html>
<head>
	<title>
		<?php
			if (basename($_SERVER['PHP_SELF']) == "inward-registry.php") {
				echo "Inward Registry | Inward Outward Registry";
			} elseif (basename($_SERVER['PHP_SELF']) == "outward-report.php") {
				echo "Outward Report | Inward Outward Registry";
			} elseif (basename($_SERVER['PHP_SELF']) == "department-report.php") {
				echo "Department Report | Inward Outward Registry";
			} elseif (basename($_SERVER['PHP_SELF']) == "add-department.php") {
				echo "Add Department | Inward Outward Registry";
			}
		?>
	</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- custom css links -->
	<link rel="stylesheet" type="text/css" href="./css/style4.css">
	<link rel="stylesheet" type="text/css" href="./css/nav-style1.css">
	<link rel="stylesheet" type="text/css" href="./css/nav-bootstrap.css">
	<!-- mdb css -->
	<link href="./css/mdb.min.css" rel="stylesheet">
	<!-- bootstrap links -->
	<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> -->
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>
    <!-- Font awesome links -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://code.highcharts.com/highcharts.js"></script>
  <script src="https://code.highcharts.com/modules/exporting.js"></script>
  <script src="https://highcharts.github.io/export-csv/export-csv.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style type="text/css">
    	.navbar{
    		margin-top: 15px;
    		background: #031017 !important;
    		padding: 0 50px !important;
    		height: 50px;
    		border-bottom: 3px solid #D3B53D;
    		box-shadow: none;
    	}
		.active{
			background: #D3B53D !important;
		}
		.nav-item{
			min-width: 180px;
			text-align: center;
		}
		.nav-item a.nav-link{
			color: #fff !important;
			padding: 10px 0;
			font-size: 18px;
			font-weight: normal !important;
		}
		.nav-item a.nav-link:hover{
			background: #D3B53D !important;
		}
		.dropdown-menu a.dropdown-item{
			font-size: 18px;
		}
		.dropdown-menu a.dropdown-item:hover{
			color: black !important;
			background: green;
		}
		.form-row label{
			background: transparent !important;
		}
		.md-form label{
			background: transparent !important;
		}
		.md-form i{
			background: transparent !important;
		}
		.form-control{
			color: black !important;
		}
		.navbar-collapse{
			background: #031017;
			width: 100%;
		}
    </style>
</head>
<body>
	<header>						<!-- header STARTS here -->
		<div class="container-90">
			<div class="row">
				<div class="col-sm-2">
					<div class="image-fluid text-center">
						<img src="./images/r_and_b-logo.jpg" class="my-logo">
					</div>
				</div>
				<div class="col-sm-9">
					<div class="text-center">
						<h1 class="my-heading">ROADS AND BUILDINGS DEPARTMENT</h1>
					</div>
				</div>
			</div>
		</div>
	</header>						<!-- header ENDS here -->

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link <?php if(basename($_SERVER['PHP_SELF']) == 'inward-registry.php') echo 'active';?>" href="inward-registry.php"><span class="fa fa-home"></span>Registry <span class="sr-only">(current)</span></a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle <?php if(basename($_SERVER['PHP_SELF']) == 'inward-report.php' OR basename($_SERVER['PHP_SELF']) == 'outward-report.php' OR basename($_SERVER['PHP_SELF']) == 'department-report.php') echo 'active';?>" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Reports
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="city_wise.php">City Wise</a>
						<a class="dropdown-item" href="grievances.php">Grievances(City wise)</a>
						<a class="dropdown-item" href="complains.php">Complains(Date wise)</a>
						<a class="dropdown-item" href="comparisons.php">Comparison of two grievances</a>
						<a class="dropdown-item" href="average.php">Average of grievances in month</a>
						<a class="dropdown-item" href="status.php">Status of greivences</a>
						<!-- <div class="dropdown-divider"></div> -->
					</div>
				</li>
				<li class="nav-item">
					<a class="nav-link <?php if(basename($_SERVER['PHP_SELF']) == 'add-department.php') echo 'active';?>" href="add-department.php">Add Department</a>
				</li>
			</ul>
		</div>

	</nav>
</body>
