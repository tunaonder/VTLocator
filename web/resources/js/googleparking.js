/*
 * Created by Sean Goodrich on 2016.04.07  *
 * Copyright © 2016 Sean Goodrich. All rights reserved. *
 */
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
            lat: 37.227612,
            lng: -80.422135
        },
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });


}