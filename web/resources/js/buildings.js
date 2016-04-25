/* 
 * Created by Sait Tuna Onder on 2016.04.24  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */

/* global i, google, infowindow */

var map;
var directionsService = new google.maps.DirectionsService;
var directionsDisplay;
var routeRefreshed = false;
var currentMarker = null;



//Initiliaze Map
function initilaizeMap() {

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



//Displays location of selected building
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

    currentMarker.setMap(map);


}

function displayBuildingLocations() {

    refreshMap();
    
    var json = JSON.parse(document.getElementById("dropDownForm:jsonResult").value);
    var count = json.length;
    
    
    var infoWindow = new google.maps.InfoWindow();
    
    i = 0;

    while (i < count) {
        var marker = null;
        //Create Marker
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(json[i].latitude,
                    json[i].longitude),
            map: map,
            title: json[i].name

        });
        
        marker.setMap(map);
        
        google.maps.event.addListener(marker, "click", function (evt) {
            infoWindow.setContent(this.get('title'));
            infoWindow.open(map, this);
            buildingInfoFromTitle(this.get('title'));
        });  

        
        i++;
    }


}

function buildingInfoFromTitle(title){
    document.getElementById(PF('clickedBuildingMarker').id).value = title;
    var value = document.getElementById(PF('clickedBuildingMarker').id).value;
    alert(value);
   



}



//Add starting point and draw route to destination
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

            if (status === google.maps.DirectionsStatus.OK) {

                directionsDisplay.setDirections(response);
                
                var minutes = response.routes[0].legs[0].duration.value/60;
                minutes = minutes.toFixed(2);
                var seconds = response.routes[0].legs[0].duration.value%60;
                seconds = seconds.toFixed(2);
                var durationValue = minutes + ' min ' + seconds + ' sec';
          
                
                document.getElementById("dropDownForm:distanceValue").innerHTML = response.routes[0].legs[0].distance.value  + ' meters';
                document.getElementById("dropDownForm:durationValue").innerHTML = durationValue;
                
              


            }
        });

        routeRefreshed = true;
    }


}

//Clear All Data On The Map
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

function resetMarker(){
     if (currentMarker !== null) {
        currentMarker.setMap(null);
        currentMarker = null;
    }
}


