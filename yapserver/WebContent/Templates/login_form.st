<script>
function validateLoginForm() {
    var userid = document.forms["login_form"]["userid"].value;
    if (userid == null || userid == "") {
    	  document.getElementById("userid_input").className = "form-group has-error"
        alert("Name must be filled out");
        return false;
    }
    
    var password = document.forms["login_form"]["password"].value;
    if (userid == null || password == "") {
        document.getElementById("password_input").className = "form-group has-error"
        alert("Empty password is not allowed");
        return false;
    }
}
</script>

<div class="panel panel-primary">
	<div class="panel-heading">Login</div>
	<div class="panel-body">
		$if(has_error)$
		<div class="alert alert-danger">
			<strong>Error!</strong> $error_text$.
		</div>
		$endif$

		<form class="form-horizontal" role="form"
		      action="login" method="post" name="login_form"
		      onsubmit="return validateLoginForm()">
			<div class="form-group" id="userid_input">
				<label class="col-sm-2 control-label">User ID</label>
				<div class="col-sm-4">
					<input class="form-control" id="userid" name="userid" type="text">
				</div>
			</div>
			<div class="form-group" id="password_input">
				<label class="col-sm-2 control-label">Password</label>
				<div class="col-sm-4">
					<input class="form-control" id="password" name="password"	type="password">
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
