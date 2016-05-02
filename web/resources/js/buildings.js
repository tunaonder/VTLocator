/* 
 * Created by VTLocator Group on 2016.04.24  * 
 * Copyright © 2016 VTLocator. All rights reserved. * 
 */

/* global i, google, infowindow */

var map;
var directionsService = new google.maps.DirectionsService;
var directionsDisplay;
var routeRefreshed = false;
var currentMarker = null;

/**
 * Initiliaze Map for buildings functionality
 * @returns {undefined}
 */
function initilaizeMap() {

    //Set the Map features
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {lat: 37.227264, lng: -80.420745},
        mapTypeControl: true,
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
            position: google.maps.ControlPosition.BOTTOM_LEFT
        },
        mapTypeId: google.maps.MapTypeId.HYBRID
    });
}

/**
 * Displays location of selected building
 * @returns {undefined}
 */
function displayBuilding() {

    refreshMap();

    //Only one marker will be displayed at a time
    if (currentMarker !== null) {
        currentMarker.setMap(null);
        currentMarker = null;
    }

    //Set the center of the map to the coordinates of selected building
    map.setCenter(new google.maps.LatLng(document.getElementById("dropDownForm:buildingLat").value, document.getElementById("dropDownForm:buildingLong").value));

    //Create Marker
    currentMarker = new google.maps.Marker({
        position: new google.maps.LatLng(document.getElementById("dropDownForm:buildingLat").value,
                document.getElementById("dropDownForm:buildingLong").value),
        map: map

    });

    //Add the marker to the map
    currentMarker.setMap(map);

}

/**
 * Display all buildings in a specific category
 * @returns {undefined}
 */
function displayBuildingLocations() {

    //First refresh map information
    refreshMap();
    
    //Get the json data which is already fetched from restful server
    var json = JSON.parse(document.getElementById("dropDownForm:jsonResult").value);
    var count = json.length;
    
    //Infovindow is used to display name of the building on the marker
    var infoWindow = new google.maps.InfoWindow();
    
    i = 0;

    //Iterate all buildings in a specific category
    while (i < count) {
        var marker = null;
        //Create Marker with buildings coordinates and name
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(json[i].latitude,
                    json[i].longitude),
            map: map,
            title: json[i].name

        });
        
        //Add marker to the map
        marker.setMap(map);
        
        //When user click on marker user can view its name on the map
        google.maps.event.addListener(marker, "click", function (evt) {
            infoWindow.setContent(this.get('title'));
            infoWindow.open(map, this);
            //Display selected building image and description
            buildingInfoFromTitle(this.get('title'));
        });  

        
        i++;
    }


}

/**
 * Get the building informations fo a selected building
 * @param {type} title the title information for the building info panel
 * @returns {undefined}
 */
function buildingInfoFromTitle(title) {
    //Update the value of selected building
    document.getElementById("dropDownForm:clickedBuildingMarker").value = title;
    //Trigger a hidden button click. Java bean will be triggered and selected building information will be updated
    $(".hidden-marker-button").click();
}

/**
 * Add starting point and draw route to destination
 * @returns {undefined}
 */
function drawRoute() {

    //Check If Starting Building Selected
    if (document.getElementById("dropDownForm:startBuildingLat").value === '0.0') {
        alert('Please Select Starting Building');

    } else {
        //If there is route drawed before, delete that data by setting the information null
        if (routeRefreshed === true) {
            directionsDisplay.setMap(null);
            directionsDisplay.setDirections(null);

        } else {
            //Set Destination Marker Null.It will be automatically created with route!
            currentMarker.setMap(null);

        }
        
        //Set the current marker to null
        currentMarker = null;
        directionsDisplay = new google.maps.DirectionsRenderer;
        directionsDisplay.setMap(map);
        
        //Coordinates of starting building and destination building
        var start = new google.maps.LatLng(document.getElementById("dropDownForm:startBuildingLat").value.toString(), document.getElementById("dropDownForm:startBuildingLong").value.toString());
        var end = new google.maps.LatLng(document.getElementById("dropDownForm:buildingLat").value.toString(), document.getElementById("dropDownForm:buildingLong").value.toString());
        var request = {
            origin: start,
            destination: end,
            travelMode: google.maps.TravelMode.WALKING
        };

        directionsService.route(request, function (response, status) {
            
            //If a direction is returned
            if (status === google.maps.DirectionsStatus.OK) {

                //Display the route
                directionsDisplay.setDirections(response);
                
                //Duration is returned as seconds from the API. Divide it by 60 to find minutes
                var minutes = response.routes[0].legs[0].duration.value/60;
                minutes = minutes.toFixed(2);
                
                //Find the seconds by using mod 60
                var seconds = response.routes[0].legs[0].duration.value%60;
                seconds = seconds.toFixed(2);
                //Generate final duration
                var durationValue = minutes + ' min ' + seconds + ' sec';
          
                //Api returns the distance as meters. One meter equals to 0.000621371192 miles
                var miles = 0.000621371192 * response.routes[0].legs[0].distance.value;
                miles = miles.toFixed(2);
                
                //Display distance and duration on the map
                document.getElementById("dropDownForm:distanceValue").innerHTML = miles  + ' miles';
                document.getElementById("dropDownForm:durationValue").innerHTML = durationValue;


            }
        });
        
        //Route is refreshed
        routeRefreshed = true;
    }


}

/**
 * Clear All Data On The Map
 * @returns {undefined}
 */
function refreshMap() {

    currentMarker = null;

    //This checks if Starting point is chosen ever. This means that route is drawed before. If route is not drawed before
    //There is no need to refresh this data
    if (document.getElementById("dropDownForm:startBuildingLat").value !== '0.0') {
        directionsDisplay.setMap(null);
        directionsDisplay.setDirections(null);
        routeRefreshed = false;

    }

    //Reset The Map
    initilaizeMap();
}

/**
 * Reset current marker
 * @returns {undefined}
 */
function resetMarker(){
     if (currentMarker !== null) {
        currentMarker.setMap(null);
        currentMarker = null;
    }
}


