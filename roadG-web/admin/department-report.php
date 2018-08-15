<?php include 'admin-header.php';?>
<?php include './php/db-connect-dept.php';?>


<div class="container table-responsive" style="margin-top:50px;">

	<div>
		<center>
			<h2>Department Report</h2>
		</center>
	<br>
	</div>

	<table style="border: 1px;" class="table table-hover">
		<thead style="background-color: #e6e6e6; font-weight: bolder;">
			<tr>
				<td>ID</th>
				<td>DEPARTMENT NAME</td>
				<td>ENTERED BY</td>
				<td>ENTERED ON</td>
			</tr>
		</thead>
			
		<tbody>
			<?php
			$sqlSelect = "SELECT * FROM `department-registry` WHERE 1";
			$resultSelect = mysqli_query($conn, $sqlSelect);
			while ($rowSelect = mysqli_fetch_assoc($resultSelect)) {
				echo "
					<tr>
						<td>{$rowSelect['Id']}</td>
			 			<td>{$rowSelect['DepartmentName']}</td>
			 			<td>{$rowSelect['EnteredBy']}</td>
			 			<td>{$rowSelect['EnteredOn']}</td>
					</tr>
				";
			}
		?>
		</tbody>

	</table>
</div >




		<center>
			<button type="reset" class="btn btn-primary">Download</button>
			<button type="submit" class="btn btn-success">Cancel</button>
		</center>
	</form>


<?php include 'footer.php'; ?>
