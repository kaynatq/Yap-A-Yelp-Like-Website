<script>
function validateReviewForm() {
    var rating = document.forms["add_review_form"]["rating"].value;
    if (rating == null || rating == "") {
        document.getElementById("rating_input").className = "form-group has-error"
        var rating_error = document.getElementById("rating_error");

        rating_error.style.display = "block";
        rating_error.innerHTML = "<strong>Erorr!</strong> Please provide a rating for the business.";

        return false;
    }
}
</script>

$business_header$

<div class="panel panel-primary">
  <div class="panel-heading"><h4>Add Review</h4></div>
  <div class="panel-body">
    $if(has_error)$
    <div class="alert alert-danger">
      <strong>Error!</strong> $error_text$.
    </div>
    $endif$

    <form class="form-horizontal" role="form"
          action="addreview" method="post" name="add_review_form"
          onsubmit="return validateReviewForm()">
      <div class="form-group" id="reviewtext_input">
        <label class="col-sm-2 control-label" align="right">Comment</label>
        <div class="col-sm-6">
          <textarea class="form-control" id="reviewtext" name="reviewtext" rows="6"></textarea>
        </div>
      </div>
      <div class="form-group" id="rating_input">
        <label class="col-sm-2 control-label" align="right">Rating</label>
        <div class="col-sm-6">
          <input class="form-control" id="rating" name="rating" type="number"
                min="1" max="5">
          <span class="help-block" style="display:none" id="rating_error"></span>
        </div>
      </div>
      <input id="businessid" name="businessid" type="hidden" value="$businessid$">
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-4">
          <button type="submit" class="btn btn-primary">Add</button>
        </div>
      </div>
    </form>
  </div>
</div>
