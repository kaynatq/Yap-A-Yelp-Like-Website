<div class="panel panel-primary">
  <div class="panel-heading">
    Yap Business List
  </div>
  <div class="panel-body">
    <table class="table table-striped">
      <thead>
        <tr>
          <th><a href=/business?sortby=name>Business Name</a></th>
          <th><a href=/business?sortby=city>City</a></th>
          <th><a href=/business?sortby=state>State</a></th>
          <th>Rating</th>
        </tr>
      </thead>
      <tbody>
        $businesses:{ b |
          <tr>
            <td><a href="reviews?businessID=$b.businessID$">$b.name$</a></td>
            <td>$b.city$</td>
            <td>$b.state$</td>
            <td>$b.rating$</td>
          </tr>
        }$
      </tbody>
    </table>
  </div>
  <div class="panel-footer" align="center">
    <a class="btn btn-primary" href="addbusiness">Add Business</a>
  </div>
</div>
