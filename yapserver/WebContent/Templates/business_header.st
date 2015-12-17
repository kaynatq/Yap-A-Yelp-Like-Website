<script src="https://maps.googleapis.com/maps/api/js"></script>
<script>
  function initializeMap() {
    var mapCenter = {lat: $business.latitude$, lng: $business.longitude$};
    var mapOptions = {
      scrollwheel: false,
      center: mapCenter,
      zoom: 8,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    }
    var mapCanvas = document.getElementById('businessMap');
    var map = new google.maps.Map(mapCanvas, mapOptions)
    var marker = new google.maps.Marker({
      map: map,
      position: mapCenter,
      title: '$business.name$'
    });
  }
  google.maps.event.addDomListener(window, 'load', initializeMap);
</script>

<div class="panel panel-danger">
  <div class="panel-heading">
    <div class="container-fluid">
      <div class="row" style="margin-top:5px">
        <div class="col-md-8" >
          <h3 style="margin-top:0px">$business.name$</h3>
        </div>
        <div class="col-md-4" align="right">
          <button type="button" class="btn btn-success btn-sm">
            <span class="glyphicon glyphicon-star" aria-hidden="true"></span>
            &nbsp; $business.rating$ &nbsp;
          </button>
        </div>
      </div>
    </div>
  </div>
  <div class="panel-body" align="center">
    $business.city$, $business.state$
  </div>
</div>

<div id="businessMap" style="height:300px;width:100%;margin-top:2%;margin-bottom:2%">
</div>
