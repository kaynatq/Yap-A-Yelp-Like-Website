<div class="panel panel-primary">
	<div class="panel-heading">Welcome! $username$</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-md-3">
				<div class="well" style="min-height: 150px" align="center">
					<a href="business"><h4>View</h4></a> the list of available business
				</div>
			</div>
			<div class="col-md-6" align="center">
				<div class="well" style="min-height: 150px">
					<p>Search for phrases in review text</p>
					<form class="form-inline" role="form" action="reviews">
						<div class="form-group">
							<!--  label class="sr-only" for="query"></label -->
							<input type="text" class="form-control" id="query" name="query">
						</div>
						<button type="submit" class="btn btn-success">
							<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							Search
						</button>
					</form>
				</div>
			</div>
			<div class="col-md-3">
				<div class="well"  style="min-height: 150px" align="center">
					<a href="logout"><h4>Logout</h4></a> of your account
				</div>
			</div>
		</div>
	</div>
</div>
