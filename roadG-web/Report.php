<?php include 'header.php';?>

<div class="row">
  <div class="col-sm-3">
    <ul class="list-group">
	<li class="list-group-item"><a href="admin/city_wise.php">City wise</a></li>
	<li class="list-group-item"><a href="admin/grievances.php">Grievances (City wise)</a></li>
	<li class="list-group-item"><a href="admin/complains.php">Complains (Date wise)</a></li>
	<li class="list-group-item"><a href="admin/comparisons.php">Comparison of two Grievences</a></li>
	<li class="list-group-item"><a href="admin/average.php">Average of Grievences in Month</a></li>
	<li class="list-group-item"><a href="admin/status.php">Status of Grievences</a></li>
    </ul>
  </div>
  <div class="col-sm-9" id="data">
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">City</th>
          <th scope="col">Requests</th>
          <th scope="col">Handle</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">1</th>
          <td>Mark</td>
          <td>Otto</td>
          <td>@mdo</td>
        </tr>
        <tr>
          <th scope="row">2</th>
          <td>Jacob</td>
          <td>Thornton</td>
          <td>@fat</td>
        </tr>
        <tr>
          <th scope="row">3</th>
          <td>Larry</td>
          <td>the Bird</td>
          <td>@twitter</td>
        </tr>
      </tbody>
    </table>

  </div>
</div>

<script type="text/javascript">
	window.onload = function() {
		function login() {
			var name = document.getElementById('orangeForm-name').value;
			var pass = document.getElementById('orangeForm-pass').value;

			alert(name+" "+pass);
		}

	}
</script>
<?php include 'footer.php';?>
