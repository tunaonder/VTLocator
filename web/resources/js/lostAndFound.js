/* 
 * Created by VTLocator Group on 2016.05.01  * 
 * Copyright © 2016 VTLocator. All rights reserved. * 
 */

var map;

/**
 * Initialize lost and found map
 */
function initLostAndFound() {

    // Define the LatLng coordinates for the polygon's path.
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {
            lat: 37.227264,
            lng: -80.420745
        },
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });
    x = 0;
    var marker;
    var geocoder = new google.maps.Geocoder;
    var infowindow = new google.maps.InfoWindow;
    map.addListener('click', function(e) {
        if (x > 0) {
            marker.setMap(null);
        }
        marker = placeMarkerAndPanTo(e.latLng, map, marker);
        marker.setMap(map);
        geocodeLatLng(geocoder, map, infowindow, e.latLng, marker);
        setLostAndFoundFields(e.latLng.lng(), e.latLng.lat());
        x++;
    });
}

/**
 * Initializes the map on the edit item page
 * @param  {Decimal} longitude longitude to center
 * @param  {Decimal} latitude  latitude to center
 */
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

    // Set latlng to a marker
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
      // If first marker exists, erase from map onClick
        if (originalMarker) {
            originalMarker.setMap(null);
        }
        // more than 1 clicks resolve previous markers
        if (x > 0) {
            marker.setMap(null);
        }
        marker = placeMarkerAndPanTo(e.latLng, map, marker);
        marker.setMap(map);
        geocodeLatLng(geocoder, map, infowindow, e.latLng, marker);
        setLostAndFoundFields(e.latLng.lng(), e.latLng.lat());
        x++;
    });
}

/**
 * Sets lost and found fields (hidden) for the front-end
 * @param {Decimal} longitude longtitude to change
 * @param {Decimal} latitude  latitude to change
 */
function setLostAndFoundFields(longitude, latitude) {
    document.getElementById(PF('latitude').id).value = latitude;
    document.getElementById(PF('longitude').id).value = longitude;

}

/**
 * Sets lost and found fields (hidden) for the front-end search
 * @param {Decimal} longitude longtitude to change
 * @param {Decimal} latitude  latitude to change
 */
function setLocationSearchFields(longitude, latitude) {
    document.getElementById(PF('latitudeSearch').id).value = latitude;
    document.getElementById(PF('longitudeSearch').id).value = longitude;

}

/**
 * helper method to refocus (re-center) map
 * @param  {latLng point} latLng center point
 * @param  {google.maps.Map} map    map to operate on
 * @param  {google.maps.Marker} marker marker
 * @return {google.maps.Marker}        marker
 */
function placeMarkerAndPanTo(latLng, map, marker) {
    marker = new google.maps.Marker({
        position: latLng,
        map: map
    });
    map.panTo(latLng);
    return marker;
}

/**
 * geocode the given location
 * @param  geocoder   geocoder to use
 * @param  map        map to draw
 * @param  infowindow infowindow to pop up
 * @param  latlng     point
 * @param  marker     marker
 */
function geocodeLatLng(geocoder, map, infowindow, latlng, marker) {
    geocoder.geocode({
        'location': latlng
    }, function(results, status) {
        if (status === google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                // set content window to the geocoded address
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

/**
 * initializes map for detailed item view
 * @param {type} longitude
 * @param {type} latitude
 * @returns {undefined}
 */
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

/**
 * Initializes map for lost and found search
 * @returns {undefined}
 */
function initLostAndFoundSearch() {

    // Define the LatLng coordinates for the polygon's path.
    lfmap = new google.maps.Map(document.getElementById('lfmap'), {
        zoom: 15,
        center: {
            lat: 37.227612,
            lng: -80.422135
        },
        mapTypeId: google.maps.MapTypeId.TERRAIN
    })
    x = 0;
    var marker;
    var geocoder = new google.maps.Geocoder;
    var infowindow = new google.maps.InfoWindow;
    lfmap.addListener('click', function(e) {
        if (x > 0) {
            marker.setMap(null);
        }
        marker = placeMarkerAndPanTo(e.latLng, lfmap, marker);
        marker.setMap(lfmap);
        geocodeLatLng(geocoder, lfmap, infowindow, e.latLng, marker);
        setLocationSearchFields(e.latLng.lng(), e.latLng.lat());

        setGeocodedAddress(geocoder, e.latLng);
        x++;
    });
}

/**
 * Gets the geocoded address from given latlng
 * @param {type} geocoder
 * @param {type} latlng
 * @returns {undefined}
 */
function setGeocodedAddress(geocoder, latlng) {

    geocoder.geocode({
        'location': latlng
    }, function(results, status) {
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

