<?php include 'admin-header.php';?>
<?php include './php/connect.php';?>


<div class="container-fluid table-responsive" style="margin-top:50px;">

	<div>
		<center>
			<h2>Inward Entry Report</h2>
		</center>
	<br>
	</div>

	<table style="border: 1px;" class="table table-hover">
		<thead style="background-color: #e6e6e6; font-weight: bolder;">
			<tr>
				<td>ID</td>
				<td>INWARD NO</td>
				<td>RECEIVED FROM</td>
				<td>SENDER OUTWARD NO</td>
				<td>SUBJECT</td>
				<td>SEND TO DEPARTMENT</td>
				<td>SEND TO PERSON</td>
				<td>DATE OF RECEIPT</td>
				<td>ENTERED BY</td>
				<td>ENTERED ON</td>
			</tr>
		</thead>

		<tbody>
			<?php
			$sqlSelect = "SELECT * FROM `inward-registry` WHERE 1";
			$resultSelect = mysqli_query($conn, $sqlSelect);
			while ($rowSelect = mysqli_fetch_assoc($resultSelect)) {
				echo "
					<tr>
						<td>{$rowSelect['Id']}</td>
						<td>{$rowSelect['InwardNo']}</td>
						<td>{$rowSelect['RecievedFromDepartment']}</td>
						<td>{$rowSelect['SenderOutwardNo']}</td>
						<td>{$rowSelect['Subject']}</td>
						<td>{$rowSelect['SendToDepartment']}</td>
						<td>{$rowSelect['SendToPerson']}</td>
						<td>{$rowSelect['DateOfReceipt']}</td>
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
