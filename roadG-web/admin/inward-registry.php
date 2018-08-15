<?php include'admin-header.php';?>
<div class="container mt-5 w3-border w3-topbar w3-bottombar w3-leftbar w3-rightbar rounded">
	<div class="col">
		<h4 class="h4 text-center mt-5" style="font-weight:normal; font-variant:small-caps; font-size:25px; font-color:#001233">REGISTRATION</h4>
	</div>
	<form action="./php/inward-registry-throw.php" method="post">
		<div class="container col-sm-3 mt-5">
		</div>
		<div class="container col-sm-6">

			<div class="form-row">

				<div class="col-sm-1">

				</div>
				<div class="md-form col col-sm">
				</div>
			</div>
			<div class="form-row">


				<div class="md-form col col-sm">
          <i class="fa fa-send prefix my-colour float-left"  style="font-size: 25px;"></i>
					<input type="text" name="Name" id="Name" class="form-control" pattern="[a-zA-Z ]{0,50}">
					<label for="send_to_dept">Name<span style="color: red">*</span></label>
				</div>
				
			</div>
			<div class="form-row">

				
				<div class="md-form col col-sm">
          <i class="fa fa-tag prefix my-colour float-left" style="font-size: 25px;"></i>
					<input type="Email" name="Email" id="EmailId" class="form-control" maxlength="50">
					<label for="inward_sub">Email<span style="color: red">*</span></label>
				</div>
			
				
			</div>

			<div class="form-row">

				<div class="md-form col col-sm">

          <i class="fa fa-share-square prefix my-colour float-left"  style="font-size: 25px;"></i>
					<input type="Password" name="Password" id="Password" class="form-control" pattern="[0-9a-zA-Z@-_]{6,16}">
					<label for="send_out_no">Password<span style="color: red">*</span></label>
				</div>

				<div class="col-sm-1"></div>

				<div class="md-form col col-sm">
					<!-- <i class="fa fa-send prefix my-colour float-left"></i>
          <input type="text" id="send_to_dept" class="form-control" required="required" pattern="[-a-zA-Z ]{2,200}">
					<label for="send_to_dept">Send to Department<span style="color: red">*</span></label> -->
					<i class="fa fa-university prefix my-colour float-left" style="font-size: 25px;"></i>
					<select class="select-style ml-5 mt-2" name="Department" id="Department">
                        <option value="" disabled hidden selected>Department</option>
                        <option value="1">Road</option>
                        <option value="2">Civil</option>
                        <option value="3">Mechanical</option>
                        <option value="4">Builduing</option>
                    </select>

				</div>
			</div>
			
			<div class="form-row">
				<div class="col col-sm text-center">
					<span style="color: red">*</span><small style="color: red;text-align: center;"> Fields are mandatory</small>
				</div>
			</div><br>
			<div class="form-row">
				<div class="col-sm-5">

				</div>
				<div class="col-sm ml-3">

					<input type="submit" class="my-btn-box mb-5" name="AddInwardRegistry" value="ADD">
				</div>
				<div class="col-sm">

				</div>
			</div>
			<div class="container col-sm-3">
			</div>
		</div>
	</form>
</div>
<div class="container col-sm-1">
</div>
</div>
<div class="container-fluid float-left">
</div>
<?php include'footer.php';?>

