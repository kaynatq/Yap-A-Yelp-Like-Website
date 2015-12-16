$business_header$

<div class="panel panel-primary">
  <div class="panel-heading">
    <div align="left"><h3>Reviews</h3></div>
	</div>
  <div class="panel-body">
    <div class="container-fluid">
      $reviews:{ r |
      <div class="row">
        <div class="col-md-12">
          <div class="panel panel-success">
            <div class="panel-heading">
              <div class="row">
                <div class="col-sm-8">$r.userName$</div>
                <div class="col-sm-4" align="right">
                  <button type="button" class="btn btn-danger btn-sm">
                    <span class="glyphicon glyphicon-star" aria-hidden="true"></span> &nbsp; $r.rating$ &nbsp;
                  </button>
                </div>
              </div>
            </div>
            <div class="panel-body"> $r.text$ </div>
            <div class="panel-footer" align="center">
              <small>$r.reviewDate$</small>
            </div>
          </div>
        </div>
      </div>
      }$
    </div>
  </div>
  <div class="panel-footer" align="center">
    <nav>
      <ul class="pager">
        $if(has_prev)$
        <li class="previous">
          <a href="$page_info.prevPageUrl$"><span aria-hidden="true">&larr;</span> Previous </a>
			  $else$
			  <li class="previous disabled">
          <span aria-hidden="true">&larr; Previous </span>
			  $endif$
			  </li>
			  $if(has_next)$
        <li class="next">
          <a href="$page_info.nextPageUrl$"> Next <span aria-hidden="true">&rarr;</span></a>  
        $else$
        <li class="next disabled">
          <span aria-hidden="true"> Next &rarr;</span>
        $endif$
				</li>
			</ul>
		</nav>
  </div>  
</div>

<div align="center">
  <a class="btn btn-primary" href="addreview">Add Review</a>
</div>