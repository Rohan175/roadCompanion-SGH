<?php include 'header.php';?>
<!-- <div class="container mt-5">
    <div class="col">
        <h4 class="h4 text-center">Login</h4>
    </div>
    <form>
        <div class="container col-sm-3 mt-5">
        </div>
        <div class="container col-sm-6">
            <div class="form-row">
                <div class="md-form">
                    <i class="fa fa-user prefix my-colour float-left"></i>
                    <input type="text" id="orangeForm-name" class="form-control" required="required" pattern="[0-9a-z_]{2,10}">
                    <label for="orangeForm-name">Username</label>
                </div>
            </div>
            <div class="form-row">
                <div class="md-form">
                    <i class="fa fa-lock prefix my-colour"></i>
                    <input type="password" id="orangeForm-pass" class="form-control" required="" pattern="[a-zA-Z0-9@$%]{6,15}" maxlength="15">
                    <label for="orangeForm-pass">Password</label>
                </div>
            </div>
            <div class="form-row">
                <div class="text-center">
                    <button class="my-btn-box">Login</button>
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
   <span style="color: red">*</span><small style="color: red;text-align: center;">All fields are mandatory</small>
</div>

<br> -->
<div id="info">
</div>
<div class="container mt-3">
	<div class="container col-sm-4">
	</div>
<div class="container col-sm-4  mt-5 " >
<div>
    <p class="h4 text-center mb-4" style="font-weight:normal; font-variant:small-caps; font-size:25px; font-color:#001233">Login</p>

    <div class="md-form">
        <i class="fa fa-user prefix my-colour float-left"></i>
        <input type="text" id="orangeForm-name" class="form-control" required="required" pattern="[0-9a-z_]{2,10}" >
        <label for="orangeForm-name">Email</label>
    </div>

    <div class="md-form">
        <i class="fa fa-lock prefix my-colour"></i>
        <input type="password" id="orangeForm-pass" class="form-control" required="" pattern="[a-zA-Z0-9@$%]{6,15}">
        <label for="orangeForm-pass">Password</label>
    </div>

    <div class="text-center">
        <a href="dash.php" class="my-btn-box" id="btnn" onclick="login();">Login</a>
	<a href="admin/inward-registry.php" class="my-btn-box" id="btnn">Sign up</a>
    </div>
</div>
<div class="container col-sm-4">
</div>
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
