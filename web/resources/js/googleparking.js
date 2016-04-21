/* 
 * Created by Sean Goodrich on 2016.04.07  * 
 * Copyright © 2016 Sean Goodrich. All rights reserved. * 
 */
      var map; 
      
      function processShapes(params) {
          initMap();
          for (i in params) {
              //process all shapes
              var shapeCoords = [];
             var coords = params[i]["coords"];
             for (x in coords) {
                  var coord = {lat: Number(coords[x]["coord"]["lat"]), lng: Number(coords[x]["coord"]["long"])};
                  shapeCoords.push(coord);
              }
              var shape = new google.maps.Polygon({
                  paths: shapeCoords,
                  strokeColor: '#FF0000',
                  strokeOpacity: 0.8,
                  strokeWeight: 2,
                  fillColor: '#FF0000',
                  fillOpacity: 0.35
              });
              shape.setMap(map);
          }
          
      }

      function initMap() {

            // Define the LatLng coordinates for the polygon's path.
           map = new google.maps.Map(document.getElementById('map'), {
              zoom: 16,
              center: {lat: 37.227612, lng: -80.422135},
              mapTypeId: google.maps.MapTypeId.TERRAIN
          });
        
      }
        
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

