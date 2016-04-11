/*
 * Created by Michael Steele on 2016.03.15  * 
 * Copyright © 2016 Michael Steele. All rights reserved. * 
 */
package com.vtlocator.managers;

import java.util.*;
import javax.faces.bean.SessionScoped;
import javax.json.*;
/* 
Stores constants used by the program.
*/

/**
 *
 * @author Michael
 */
public class Constants {

    //-----------------------------------------------------------
    // Change /Users/Balci/FileStorageLocation/ below to /home/cs4984/Balci/FileStorageLocation/
    // for deployment to the server by replacing Balci with your last name.
    //-----------------------------------------------------------
    public static final String ROOT_DIRECTORY = "/home/cs4984/Steele/FileStorageLocation/";//TODO

    public static final String TEMP_FILE = "tmp_file";

    public static final Integer THUMBNAIL_SZ = 200;

    public static final Integer MAX_CAPTION_SIZE = 140;
    
    public static final String[] QUESTIONS = {"In what city were you born?",
    "What elementary school did you attend?",
    "What is the last name of your most favorite teacher?",
    "What is your father's middle name?",
    "What is your most favorite pet's name?"};

    
    public static final String ANY_LOT = "ANY";
    public static final String COMMUTER_LOT = "COMMUTER/GRADUATE";
    public static final String FACULTY_LOT = "FACULTY/STAFF/VISITOR";
    public static final String RESIDENT_LOT = "RESIDENT";
    public static final String METERED_LOT = "METERED";
    public static final String PARKING_OFFICE_LOT = "PARKING_OFFICE";
    
    
}
