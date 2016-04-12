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

