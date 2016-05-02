/*
 * Created by VTLocator Group on 2016.04.10  *
 * Copyright © 2016 VTLocator Group. All rights reserved. *
 */
package com.vtlocator.managers;

import com.vtlocator.jpaentityclasses.ParkingLot;
import com.vtlocator.sessionbeans.ParkingLotFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

@Named(value = "parkingLotManager") // what to use to refer to this class
@SessionScoped // this class will leave scope when the browser ends the session

/**
 * The class ParkingLotManager contains information for parking lot data.
 * Constants.java provides basic polygons, which is converted to json for javascript parameter passing.
 * @author VTLocator Group
 */
public class ParkingLotManager implements Serializable {


     /**
     * The instance variable 'parkingLotFacade' is annotated with the @EJB annotation.
     * This means that the GlassFish application server, at runtime, will inject in
     * this instance variable a reference to the @Stateless session bean UserFacade.
     */
    @EJB
    private ParkingLotFacade parkingLotFacade;

    public String getANY() {
        List<ParkingLot> lots = parkingLotFacade.getLotsByPermission(Constants.ANY_LOT);
        return ParkingLotManager.getJsonFromLots(lots);
    }

    public String getCOMMUTER() {
        List<ParkingLot> lots = parkingLotFacade.getLotsByPermission(Constants.COMMUTER_LOT);
        return ParkingLotManager.getJsonFromLots(lots);
    }

    public String getFACULTY() {
        List<ParkingLot> lots = parkingLotFacade.getLotsByPermission(Constants.FACULTY_LOT);
        return ParkingLotManager.getJsonFromLots(lots);
    }

    public String getRESIDENT() {
        List<ParkingLot> lots = parkingLotFacade.getLotsByPermission(Constants.RESIDENT_LOT);
        return ParkingLotManager.getJsonFromLots(lots);
    }

    public String getMETERED() {
        List<ParkingLot> lots = parkingLotFacade.getLotsByPermission(Constants.METERED_LOT);
        return ParkingLotManager.getJsonFromLots(lots);
    }

    public String getPARKING() {
        List<ParkingLot> lots = parkingLotFacade.getLotsByPermission(Constants.PARKING_OFFICE_LOT);
        return ParkingLotManager.getJsonFromLots(lots);
    }

    /**
     * Converts a list to json
     * @param  lots List of parking lot polygons (long/lat)
     * @return      A json array of long/lat points
     */
    public static String getJsonFromLots(List<ParkingLot> lots) {
        ArrayList<JsonObject> jsonLots = new ArrayList();
        for (int p = 0; p < lots.size(); p++) {
            ArrayList<String> longs = ParkingLotManager.parseCoordinates(lots.get(p).getLongitude());
            ArrayList<String> lats = ParkingLotManager.parseCoordinates(lots.get(p).getLatitude());
            jsonLots.add(ParkingLotManager.createJsonShapeFromCoords(lats,longs));
        }
        return ParkingLotManager.createJsonStringFromShapes(jsonLots);
    }

    /**
     * Parses coordinates to an arraylist
     * @param  coords string representation of long/lat
     * @return        list of coordinates
     */
    public static ArrayList<String> parseCoordinates(String coords) {
        ArrayList<String> listOfCoords = new ArrayList(Arrays.asList(coords.split(",")));
        return listOfCoords;
    }

    public ParkingLotManager() {

    }

    /**
     * Creates a json shape from lat long arraylists
     * @param  lats  array list of latitude points
     * @param  longs array list of longtitude points
     * @return       A jsonobject that contains json shapes
     */
    public static JsonObject createJsonShapeFromCoords(ArrayList<String> lats, ArrayList<String> longs) {
        if (lats.size() != longs.size()) {
            return null;
        }
        JsonArrayBuilder b = Json.createArrayBuilder();
        for (int x = 0; x<lats.size(); x++) {
            JsonObject jcoord = Json.createObjectBuilder()
                .add("coord", Json.createObjectBuilder().add("lat", lats.get(x)).add("long", longs.get(x))).build();
            b.add(jcoord);
        }
        JsonObject coords = Json.createObjectBuilder().add("coords", b).build();
        return coords;

    }

    /**
     * Creates a json string from shape arraylist
     * @param  shapes a shape arraylist that already contains lat/long data
     * @return        a json string
     */
    public static String createJsonStringFromShapes(ArrayList<JsonObject> shapes) {
        JsonObjectBuilder b = Json.createObjectBuilder();
        for (int x = 0; x < shapes.size(); x++) {
            b.add("shape" + Integer.toString(x), shapes.get(x));
        }
        JsonObject shapesJson = b.build();
        return shapesJson.toString();
    }
}
