/*
 * Created by Sean Goodrich on 2016.04.07  *
 * Copyright © 2016 Sean Goodrich. All rights reserved. *
 */
var map;





function initLostAndFound() {

  // Define the LatLng coordinates for the polygon's path.
  map = new google.maps.Map(document.getElementById('map'), {
         zoom: 16,
         center: {lat: 37.227264, lng: -80.420745},
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

function initEditMap(longitude, latitude) {

    // Define the LatLng coordinates for the polygon's path.
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {
            lat: latitude,
            lng: longitude
        },
        mapTypeControl: true,
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
            position: google.maps.ControlPosition.BOTTOM_LEFT
        },
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });

    var latLng = new google.maps.LatLng(latitude, longitude);
    var originalMarker = new google.maps.Marker({
        position: latLng,
        map: map
    });

    // marker = placeMarkerAndPanTo(e.latLng, map, marker);
    // marker.setMap(map);

    x = 0;
    var marker;
    var geocoder = new google.maps.Geocoder;
    var infowindow = new google.maps.InfoWindow;
    map.addListener('click', function(e) {
        if (originalMarker) {
            originalMarker.setMap(null);
        }
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

function setLocationSearchFields(longitude, latitude) {
     document.getElementById(PF('latitudeSearch').id).value = latitude;
     document.getElementById(PF('longitudeSearch').id).value = longitude;

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
    var markerBounds = new google.maps.LatLngBounds();

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

            shapeCoords.push(coord);
            markerBounds.extend(new google.maps.LatLng(Number(coordArr[index]["coord"]["long"]), Number(coordArr[index]["coord"]["lat"])));
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

    map.fitBounds(markerBounds);
} // end function

function initMap() {

    // Define the LatLng coordinates for the polygon's path.
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {
            lat: 37.227264,
            lng: -80.420745
        },
        mapTypeControl: true,
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
            position: google.maps.ControlPosition.BOTTOM_LEFT
        },
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });


}

function initItem(longitude, latitude) {

    // Define the LatLng coordinates for the polygon's path.
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {
            lat: latitude,
            lng: longitude
        },
        mapTypeControl: true,
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
            position: google.maps.ControlPosition.BOTTOM_LEFT
        },
        mapTypeId: google.maps.MapTypeId.TERRAIN,
        scrollwheel: false,
        scaleControl: false,
        draggable: false
    });
    var latLng = new google.maps.LatLng(latitude, longitude);
     marker = new google.maps.Marker({
        position: latLng,
        map: map
      });


}



function initLostAndFoundSearch() {

  // Define the LatLng coordinates for the polygon's path.
  lfmap = new google.maps.Map(document.getElementById('lfmap'), {
         zoom: 15,
         center: {lat: 37.227612, lng: -80.422135},
         mapTypeId: google.maps.MapTypeId.TERRAIN
  })
  x = 0;
  var marker;
  var geocoder = new google.maps.Geocoder;
  var infowindow = new google.maps.InfoWindow;
  lfmap.addListener('click', function(e) {
      if (x>0) {
          marker.setMap(null);
      }
      marker = placeMarkerAndPanTo(e.latLng, lfmap, marker);
      marker.setMap(lfmap);
      geocodeLatLng(geocoder,lfmap, infowindow, e.latLng, marker);
      setLocationSearchFields(e.latLng.lng(), e.latLng.lat());

      setGeocodedAddress(geocoder, e.latLng);
      x++;
  });
}

function setGeocodedAddress(geocoder, latlng) {

    geocoder.geocode({'location': latlng}, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
          if (results[1]) {
            address = results[1].formatted_address;
            document.getElementById(PF('geocodedAddress').id).value = address;
          } else {
            address = 'No address found';
            document.getElementById(PF('geocodedAddress').id).value = address;
          }
        } else {
          address = "No address found.";
          document.getElementById(PF('geocodedAddress').id).value = address;
        }
     });

}