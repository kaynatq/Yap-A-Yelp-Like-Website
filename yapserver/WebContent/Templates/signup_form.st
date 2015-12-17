<div class="panel panel-primary">
  <div class="panel-heading">
    Sign Up
  </div>
  <div class="panel-body">
    $if(has_error)$
      <div class="alert alert-danger">
        <strong>Error!</strong> $error_text$.
      </div>
    $endif$

    <form class="form-horizontal" role="form" action="signup" method="post">
      <div class="form-group">
        <label class="col-sm-2 control-label">User ID</label>
        <div class="col-sm-4">
          <input class="form-control" id="userid" name="userid" type="text">
        </div>
      </div>
        <div class="form-group">
        <label class="col-sm-2 control-label">Name</label>
        <div class="col-sm-4">
          <input class="form-control" id="username" name="username" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Password</label>
        <div class="col-sm-4">
          <input class="form-control" id="password" name="password" type="password">
        </div>
      </div>
      <div class="form-group">        
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </div>
    </form>
  </div>
</div>