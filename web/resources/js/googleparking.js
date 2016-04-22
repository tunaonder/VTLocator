/*
 * Created by Sean Goodrich on 2016.04.07  *
 * Copyright © 2016 Sean Goodrich. All rights reserved. *
 */
var map; 





function initLostAndFound() {

  // Define the LatLng coordinates for the polygon's path.
  map = new google.maps.Map(document.getElementById('map'), {
         zoom: 16,
         center: {lat: 37.227612, lng: -80.422135},
         mapTypeId: google.maps.MapTypeId.TERRAIN
  })
  x = 0;
  var marker;
  var geocoder = new google.maps.Geocoder;
  var infowindow = new google.maps.InfoWindow;
  map.addListener('click', function(e) {
      if (x>0) {
          marker.setMap(null);
      }
      marker = placeMarkerAndPanTo(e.latLng, map, marker);
      marker.setMap(map);
      geocodeLatLng(geocoder,map, infowindow, e.latLng, marker);
      setLostAndFoundFields(e.latLng.lng(), e.latLng.lat());
      x++;
  });
}  

function setLostAndFoundFields(longitude, latitude) {
     document.getElementById(PF('latitude').id).value = latitude;
     document.getElementById(PF('longitude').id).value = longitude;

}
function placeMarkerAndPanTo(latLng, map, marker) {
  marker = new google.maps.Marker({
    position: latLng,
    map: map
  });
  map.panTo(latLng);
  return marker;
}

function geocodeLatLng(geocoder, map, infowindow, latlng, marker) {
  geocoder.geocode({'location': latlng}, function(results, status) {
    if (status === google.maps.GeocoderStatus.OK) {
      if (results[1]) {

        infowindow.setContent(results[1].formatted_address);
        infowindow.open(map, marker);
      } else {
        window.alert('No results found');
      }
    } else {
      window.alert('Geocoder failed due to: ' + status);
    }
  });
}
      
var map;
var bounds;

function processShapes(input) {
    initMap();
    var shapes = [];
    var i = 0;
    var inputLen = input.length;

    for (shapeNum in input) {
        var shapeCoords = [];
        var coordArr = input[shapeNum]["coords"];
        // console.log(coordArr);

        var index = 0;
        for (index; index < coordArr.length; index++) {
            /***
             * Lat and long are changed because mySQL returns opposite values.
             * @type type
             */
            var coord = {
                lng: Number(coordArr[index]["coord"]["lat"]),
                lat: Number(coordArr[index]["coord"]["long"])
            };
            // console.log(coord);
            shapeCoords.push(coord);
        } // end for

        shapes.push(new google.maps.Polygon({
            paths: shapeCoords,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35
        }));
        // shapes[i].setMap(map);
//        lotPolygons.push(polygons);
    }
    for(var i=0; i<shapes.length; i++) {
      shapes[i].setMap(map);
    }

} // end function

function initMap() {

    // Define the LatLng coordinates for the polygon's path.
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {
            lat: 37.2217518,
            lng: -80.419138315
        },
        mapTypeControl: true,
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
            position: google.maps.ControlPosition.BOTTOM_LEFT
        },
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });


}