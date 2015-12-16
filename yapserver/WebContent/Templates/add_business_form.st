<div class="panel panel-primary">
  <div class="panel-heading">Login</div>
  <div class="panel-body">
    $if(has_error)$
    <div class="alert alert-danger">
      <strong>Error!</strong> $error_text$.
    </div>
    $endif$
      <form class="form-horizontal" role="form" action="addbusiness" method="post" name="addbusiness">
      <div class="form-group">
        <label class="col-sm-2 control-label">Business ID</label>
        <div class="col-sm-4">
          <input class="form-control" id="businessid" name="businessid" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Name</label>
        <div class="col-sm-4">
          <input class="form-control" id="businessname" name="businessname" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">City</label>
        <div class="col-sm-4">
          <input class="form-control" id="city" name="city" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">State</label>
        <div class="col-sm-4">
          <input class="form-control" id="state" name="state" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">latitude</label>
        <div class="col-sm-4">
          <input class="form-control" id="latitude" name="latitude" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">longitude</label>
        <div class="col-sm-4">
          <input class="form-control" id="longitude" name="longitude" type="text">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Neighborhood</label>
        <div class="col-sm-4">
          <input class="form-control" id="neighborhood" name="neighborhood" type="text">
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-primary">Add Business</button>
        </div>
      </div>
    </form>
  </div>
</div>
