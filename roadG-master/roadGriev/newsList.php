<?php
  include('../includes/connectDB.php');

 ?>

 <div class="row">
   <div class="col-sm-8" style="font-size:20px;">NEWS</div>
   <div class="col-sm-4">
     <button type="button" class="btn btn-primary btn-sm" name="button">ADD NEW</button>
     <button type="button" class="btn btn-primary btn-sm" onclick="$('#news').load('newsList.php');">REfresh</button>
   </div>
 </div>

 <table class="table">
   <thead>
     <tr>
       <th scope="col">#</th>
       <th scope="col">Title</th>
       <th scope="col">EDIT</th>
     </tr>
   </thead>
   <tbody>
     <!-- <tr>
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
     </tr> -->
     <?php

     $q = "select id, title from news";
     $res = $conn->query($q);
     $count = 1;

     if ($res->num_rows > 0) {
       # code...
       while ($r = $res->fetch_assoc()) {
         $tab = '\'news\'';
         // $img = 'data:image/jpg;base64,' .  base64_encode($r['image']);
         // if($r['image'] == NULL) {
         //   $img = 'img/default.jpeg';
         // }
         // # code...
         // echo '
         //   <div class="slide">
         //     <div class="card">
         //     <img class="card-img-top" style="height:200px;" src="'.$img.'" alt="Card image cap">
         //       <div class="card-body">
         //         <h5 class="card-title" style="text-transform:uppercase;">'.$r['title'].'</h5>
         //         <p class="card-text" style="height:100px;overflow:hidden;">'.$r['summary'].'</p>
         //         <a href="news.php?id='.$r['id'].'" class="btn btn-sm btn-primary">Read more</a>
         //       </div>
         //     </div>
         //   </div>';
         //   }

         echo '
         <tr>
           <th scope="row">'.$count.'</th>
           <td>'.$r['title'].'</td>
           <td><button type="button" class="btn btn-primary btn-sm">VIEW / EDIT</button>&nbsp;
           <a href="#" onclick="deleteIt('.$r['id'].', '.$tab.');" class="btn btn-danger btn-sm">DELETE</a>
           </td>
         </tr>
         ';
         $count++;
         }

       }

         ?>
   </tbody>
 </table>
