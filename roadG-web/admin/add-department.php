<?php include 'admin-header.php';?>

<div class="container mt-3">
  <div class="container col-sm-4">
  </div>
<div class="container col-sm-4  mt-5 " >
<form action="./php/dept-throw.php" method="post">
    <p class="h4 text-center mb-4" style="font-weight:normal; font-variant:small-caps; font-size:25px; font-color:#001233">Add Department</p>

    <div class="md-form">
        <i class="fa fa-plus prefix my-colour float-left"></i>
        <input type="text" id="add_dept" name="DepartmentName" class="form-control" required="required">
        <label for="orangeForm-name">Add New Department</label>
    </div>

        <div class="text-center">
            <!-- <button class="my-btn-box" type="submit" name="submit">Add</button> -->
            <input type="submit" class="my-btn-box mb-5" name="submit" value="Add">
        </div>
    </form>
    <div class="container col-sm-4">
    </div>
    </div>
    </div>


<?php include 'footer.php';?>
