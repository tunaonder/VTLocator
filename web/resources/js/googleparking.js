/* 
 * Created by Sean Goodrich on 2016.04.07  * 
 * Copyright © 2016 Sean Goodrich. All rights reserved. * 
 */
      var map; 
      
      function processShapes(jsonparams) {
          var params = JSON.parse(jsonparams);
          for (i=0; i<params.length; i++) {
              //process all shapes
              var shapeCoords = [];
              for (x = 0; x<params[i].length; x++) {
                  var coord = {lat: Number(params[i][x][0]), lng: Number(params[i][x][1])};
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
      

        var coords = [];
        var exampleParams = [];
        var coord = ['37.229204', '-80.421507'];
        var coord2 = ['37.227283', '-80.424192'];
        var coord3 = ['37.226195', '-80.423054'];
        var coord4 = ['37.228020', '-80.420323'];
        coords.push(coord);
        coords.push(coord2);
        coords.push(coord3);
        coords.push(coord4);
        exampleParams.push(coords);
       // processShapes(exampleParams);
        // Construct the polygon.
        
      }

