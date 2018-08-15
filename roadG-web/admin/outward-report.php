<?php include 'admin-header.php';?>
<?php include './php/connect.php';?>

<div class="container-fluid table-responsive" style="margin-top:50px;">

	<div>
		<center>
			<h2>Outward Entry Report</h2>
		</center>
	<br>
	</div>

	<table style="border: 1px;" class="table table-hover">
		<thead style="background-color: #e6e6e6; font-weight: bolder;">
			<tr>
				<td>ID</td>
				<td>OUTWARD NO</td>
				<td>RECIEVED FROM DEPARTMENT</td>
				<td>RECIEVED FROM PERSON</td>
				<td>SEND TO</td>
				<td>SUBJECT</td>
				<td>SENT MODE</td>
				<td>SENT BY AGENCY</td>
				<td>DISPATCHED ITEM ID</td>
				<td>TICKET MONEY</td>
				<td>SENT WITH</td>
				<td>SENT ON DATE</td>
				<td>ENTERED BY</td>
				<td>ENTERED ON</td>
			</tr>
		</thead>

		<tbody>
		<?php
			$sqlSelect = "SELECT * FROM `outward-registry` WHERE 1";
			$resultSelect = mysqli_query($conn, $sqlSelect);
			while ($rowSelect = mysqli_fetch_assoc($resultSelect)) {
				echo "
					<tr>
						<td>{$rowSelect['Id']}</td>
						<td>{$rowSelect['OutwardNo']}</td>
						<td>{$rowSelect['RecievedFromDepartment']}</td>
						<td>{$rowSelect['RecievedFromPerson']}</td>
						<td>{$rowSelect['SentTo']}</td>
						<td>{$rowSelect['Subject']}</td>
						<td>{$rowSelect['SentMode']}</td>
						<td>{$rowSelect['SentByAgency']}</td>
						<td>{$rowSelect['DispatchltemId']}</td>
						<td>{$rowSelect['TicketMoney']}</td>
						<td>{$rowSelect['SentWith']}</td>
						<td>{$rowSelect['SentOnDate']}</td>
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