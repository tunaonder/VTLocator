/*
 * Created by VTLocator Group on 2016.04.07  *
 * Copyright © 2016 VTLocator Group. All rights reserved. *
 */

var map;
var bounds;

/**
 * Draws polygons on the map (parking)
 * @param  {JSON} input object of lat/lngs for polygon arrays
 */
function processShapes(input) {
    initMap();
    var shapes = [];
    var i = 0;
    var inputLen = input.length;
    var markerBounds = new google.maps.LatLngBounds();

    for (shapeNum in input) {
        var shapeCoords = [];
        var coordArr = input[shapeNum]["coords"];

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

        // draw polygons (shapeCoords) on map
        shapes.push(new google.maps.Polygon({
            paths: shapeCoords,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#FF0000',
            fillOpacity: 0.35
        }));
    }
    for (var i = 0; i < shapes.length; i++) {
        shapes[i].setMap(map);
    }
    map.fitBounds(markerBounds);
} // end function

// Initializes map for parking
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