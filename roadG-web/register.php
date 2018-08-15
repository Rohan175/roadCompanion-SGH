<?php include 'header.php';?>
<div class="container mt-5 w3-border w3-topbar w3-bottombar w3-leftbar w3-rightbar rounded">
    <div class="col">
        <h4 class="h4 text-center mt-5" style="font-weight:normal; font-variant:small-caps; font-size:25px; font-color:#001233">Registration</h4>
    </div>
    <form>
        <div class="container col-sm-3 mt-5">
        </div>
        <div class="container col-sm-6">
            <div class="form-row">
                <div class="md-form col col-sm">
                  <i class="fa fa-user-circle prefix my-colour float-left" style="font-size: 25px;"></i>
                    <!-- <input type="text" id="orangeForm-name" class="form-control" required="required" pattern="[a-zA-Z]{1,15}[ ]+[a-zA-Z]{1,15}[ ]+[a-zA-Z]{1,15}"> -->
                    <input type="text" id="orangeForm-name" class="form-control" required="required" pattern="[a-zA-Z ]{1,40}" maxlength="40">
                    <label for="orangeForm-name">Full Name<span style="color: red">*</span></label>
                </div>
                <div class="col-sm-1">

                </div>
                <div class="md-form col col-sm">
                  <i class="fa fa-user prefix my-colour float-left" style="font-size: 25px;"></i>
                    <input type="text" id="orangeForm-name" class="form-control" required="required" pattern="[0-9a-z_]{2,10}" maxlength="10" minlength="2">
                    <label for="orangeForm-name">Username<span style="color: red">*</span></label>
                </div>
            </div>

            <div class="form-row">
                <div class="md-form col col-sm">
                  <i class="fa fa-lock prefix my-colour float-left" style="font-size: 25px;"></i>
                    <input type="password" id="orangeForm-pass" class="form-control" required="required" pattern="[a-zA-Z0-9@$%]{6,15}" maxlength="15" minlength="6">
                    <label for="orangeForm-pass">Password<span style="color: red">*</span></label>
                </div>
                <div class="col-sm-1">

                </div>
                <div class="md-form col col-sm">
                  <i class="fa fa-unlock-alt prefix my-colour float-left" style="font-size: 25px;"></i>
                    <input type="password" id="orangeForm-pass" class="form-control" required="required" pattern="[a-zA-Z0-9@$%]{6,15}" maxlength="15" minlength="6">
                    <label for="orangeForm-pass">Re-enter Password<span style="color: red">*</span></label>
                </div>
            </div>
            <div class="form-row">
                <div class="md-form col col-sm">
                   <i class="fa fa-mobile prefix my-colour float-left" style="font-size: 25px;"></i>
                    <input type="text" id="orangeForm-name" class="form-control" required="required" pattern="[0-9]{10}" maxlength="10" minlength="10">
                    <label for="orangeForm-name">Contact Number<span style="color: red">*</span></label>
                </div>
                <div class="col-sm-1">

                </div>
                <div class="md-form col col-sm">
                  <i class="fa fa-envelope-square prefix my-colour float-left" style="font-size: 25px;"></i>
                    <input type="text" id="orangeForm-name" class="form-control" required="required" pattern="[a-z0-9]{1,30}[@]+[a-z]{1,10}[.]+[a-z.]{1,10}" maxlength="35">
                    <label for="orangeForm-name">Email-ID<span style="color: red">*</span></label>
                </div>
            </div>
            <div class="form-row">
              <div class="col-sm-4">

              </div>
              <i class="fa fa-user-secret prefix my-colour float-left" style="font-size: 25px;"></i>
                <div class="md-form col col-sm text-center">
                    <select class="select-style" required="">
                        <option value="" disabled hidden selected="">Role</option>
                        <option value="1">Staff</option>
                        <option value="2">Admin</option>
                    </select>
                </div>
                <div class="col-sm-1">

                </div>
                <div class="col col-sm">

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
                <div class="col-sm">

                  <button type="submit" class="my-btn-box mb-5">REGISTER</button>
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

<br>
<?php include 'footer.php';?>
