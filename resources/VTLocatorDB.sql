/* 
 * Created by Sait Tuna Onder on 2016.04.03  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
/**
 * Author:  Onder
 * Created: Apr 3, 2016
 */

# ----------------------------------------------------------------
# SQL Script to Create All VTLocatorDB Tables
# ----------------------------------------------------------------
 
DROP TABLE IF EXISTS ItemPhoto, Subscription, Notification, Item, User, UserPhoto, Building, ParkingLot;

/* The UserPhoto table contains attributes of interest of a user's profile photo. */
CREATE TABLE UserPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now()
);

/* The User table contains attributes of interest of a user. */
CREATE TABLE User
(
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR (32) NOT NULL,
    last_name VARCHAR (32) NOT NULL,
    email VARCHAR(64) NOT NULL,
    phone_number VARCHAR(64) NOT NULL,
    security_question INT NOT NULL,
    security_answer VARCHAR (255) NOT NULL,
    password VARCHAR (256) NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now(),
    profile_photo INT UNSIGNED,
    FOREIGN KEY (profile_photo) REFERENCES UserPhoto(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

/* The Item table contains attributes of interest of an item. */
CREATE TABLE Item
(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(1024),
    latitude_found DECIMAL(13,10) NOT NULL,
    longitude_found DECIMAL(13,10) NOT NULL,
    date_found timestamp default now(),
    category ENUM('HOKIE_PASSPORT', 'PHONE', 'KEYS', 'ELECTRONICS', 'CLOTHING', 'OTHER') NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now(),
    PRIMARY KEY (id)
);

/* The ItemPhoto table contains attributes of interest of an item's photo(s). */
CREATE TABLE ItemPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now(),
    photo_for INT,
    FOREIGN KEY (photo_for) REFERENCES Item(id) ON DELETE CASCADE
);

/* The Subscription table contains attributes of interest of a subscription. */
CREATE TABLE Subscription
(
    id INT NOT NULL AUTO_INCREMENT,
    category ENUM('HOKIE_PASSPORT', 'PHONE', 'KEYS', 'ELECTRONICS', 'CLOTHING', 'OTHER') NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now(),
    subscriber INT,
    FOREIGN KEY (subscriber) REFERENCES User(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

/* The Notification table contains attributes of interest of a notification. */
CREATE TABLE Notification
(
    id INT NOT NULL AUTO_INCREMENT,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now(),
    sender INT,
    FOREIGN KEY (sender) REFERENCES User(id) ON DELETE CASCADE,
    recipient INT,
    FOREIGN KEY (recipient) REFERENCES User(id) ON DELETE CASCADE,
    item INT,
    FOREIGN KEY (item) REFERENCES User(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

/* The ParkingLot table contains attributes of interest of a parking lot. */
CREATE TABLE ParkingLot
(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    latitude VARCHAR(2000) NOT NULL,
    longitude VARCHAR(2000) NOT NULL,
    permission ENUM('ANY', 'COMMUTER/GRADUATE', 'FACULTY/STAFF/VISITOR', 'RESIDENT', 'METERED', 'PARKING_OFFICE') NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now(),
    PRIMARY KEY (id)
);

/* The Building table contains attributes of interest of a building. */
CREATE TABLE Building
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    abbreviation VARCHAR(32) NOT NULL,
    latitude DECIMAL(13,10) NOT NULL,
    longitude DECIMAL(13,10) NOT NULL,
    category VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image VARCHAR(255) NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now()
);

INSERT INTO Building (name, abbreviation, latitude, longitude, category, description, image) VALUES 
('Agnew Hall', 'AGNEW', '37.2247741885', '-80.4241237773', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/agnew/agnew.txt', 'http://manta.cs.vt.edu/vt/buildings/agnew/agnew.jpg'),
('Ambler Johnston Hall', 'AJ', '37.2231156422', '-80.420987521', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/aj/aj.txt', 'http://manta.cs.vt.edu/vt/buildings/aj/aj.jpg'),
('Architecture Annex', 'AA', '37.2285955676', '-80.4162869733', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/aa/aa.txt', 'http://manta.cs.vt.edu/vt/buildings/aa/aa.jpg'),
('Armory', 'ARMRY', '37.2293014374', '-80.415881076', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/armry/armry.txt', 'http://manta.cs.vt.edu/vt/buildings/armry/armry.jpg'),
('Art And Design Learning Center', 'ART C', '37.2317131084', '-80.4206949777', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/artc/artc.txt', 'http://manta.cs.vt.edu/vt/buildings/artc/artc.jpg'),
('Barringer Hall', 'BAR', '37.2262012788', '-80.4167921135', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/bar/bar.txt', 'http://manta.cs.vt.edu/vt/buildings/bar/bar.jpg'),
('Bishop-Favrao Hall', 'BFH', '37.2303157804', '-80.4255795833', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/bfh/bfh.txt', 'http://manta.cs.vt.edu/vt/buildings/bfh/bfh.jpg'),
('Burchard Hall', 'BURCH', '37.2296220437', '-80.4244101594', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/burch/burch.txt', 'http://manta.cs.vt.edu/vt/buildings/burch/burch.jpg'),
('Burrows-Burleson Tennis Center', 'TC', '37.214842347', '-80.4193116325', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/tc/tc.txt', 'http://manta.cs.vt.edu/vt/buildings/tc/tc.jpg'),
('Burruss Hall', 'BUR', '37.229031934', '-80.4237145305', 'Administration', 'http://manta.cs.vt.edu/vt/buildings/bur/bur.txt', 'http://manta.cs.vt.edu/vt/buildings/bur/bur.jpg'),
('Campbell Hall', 'CAM', '37.2260712095', '-80.4217950286', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/cam/cam.txt', 'http://manta.cs.vt.edu/vt/buildings/cam/cam.jpg'),
('Carol M. Newman Library', 'LIBR', '37.2287881028', '-80.4191603438', 'Support', 'http://manta.cs.vt.edu/vt/buildings/libr/libr.txt', 'http://manta.cs.vt.edu/vt/buildings/libr/libr.jpg'),
('Cassell Coliseum', 'COL', '37.2225472975', '-80.4189774813', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/col/col.txt', 'http://manta.cs.vt.edu/vt/buildings/col/col.jpg'),
('Julian Cheatham Hall', 'JCH', '37.223876354', '-80.422526603', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/jch/jch.txt', 'http://manta.cs.vt.edu/vt/buildings/jch/jch.jpg'),
('Cochrane Hall', 'CHRNE', '37.2232708161', '-80.4220193277', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/chrne/chrne.txt', 'http://manta.cs.vt.edu/vt/buildings/chrne/chrne.jpg'),
('Cowgill Hall', 'CO', '37.2299239144', '-80.4247422069', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/co/co.txt', 'http://manta.cs.vt.edu/vt/buildings/co/co.jpg'),
('Davidson Hall', 'DAV', '37.2270754616', '-80.4252168769', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/dav/dav.txt', 'http://manta.cs.vt.edu/vt/buildings/dav/dav.jpg'),
('Derring Hall', 'DER', '37.2290890049', '-80.4255941334', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/der/der.txt', 'http://manta.cs.vt.edu/vt/buildings/der/der.jpg'),
('Dietrick Hall', 'DTRIK', '37.2245312784', '-80.4211172582', 'Dining', 'http://manta.cs.vt.edu/vt/buildings/dtrik/dtrik.txt', 'http://manta.cs.vt.edu/vt/buildings/dtrik/dtrik.jpg'),
('Durham Hall', 'DURHM', '37.2316634686', '-80.423672831', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/durhm/durhm.txt', 'http://manta.cs.vt.edu/vt/buildings/durhm/durhm.jpg'),
('Eggleston Hall', 'EGG', '37.2272667619', '-80.4199667184', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/egg/egg.txt', 'http://manta.cs.vt.edu/vt/buildings/egg/egg.jpg'),
('Engel Hall', 'ENGEL', '37.2236380676', '-80.423558614', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/engel/engel.txt', 'http://manta.cs.vt.edu/vt/buildings/engel/engel.jpg'),
('Femoyer Hall', 'FEM', '37.2313274439', '-80.4212502249', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/fem/fem.txt', 'http://manta.cs.vt.edu/vt/buildings/fem/fem.jpg'),
('Biocomplexity Institute of Virginia Tech', 'BIOI1', '37.2208', '-80.42609', 'Research', 'http://manta.cs.vt.edu/vt/buildings/bioi1/bioi1.txt', 'http://manta.cs.vt.edu/vt/buildings/bioi1/bioi1.jpg'),
('Food Science and Technology Building', 'FST', '37.2198751369', '-80.4253400067', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/fst/fst.txt', 'http://manta.cs.vt.edu/vt/buildings/fst/fst.jpg'),
('Fralin Life Science Institute', 'FRALN', '37.2240931473', '-80.4234940341', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/fraln/fraln.txt', 'http://manta.cs.vt.edu/vt/buildings/fraln/fraln.jpg'),
('G. Burke Johnston Student Center', 'GBJ', '37.2292550782', '-80.424610578', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/gbj/gbj.txt', 'http://manta.cs.vt.edu/vt/buildings/gbj/gbj.jpg'),
('Goodwin Hall', 'GOODW', '37.23237', '-80.42542', 'cat', 'http://manta.cs.vt.edu/vt/buildings/goodw/goodw.txt', 'http://manta.cs.vt.edu/vt/buildings/goodw/goodw.jpg'),
('Graduate Life Center at Donaldson Brown', 'GLCDB', '37.2282893911', '-80.4175766028', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/glcdb/glcdb.txt', 'http://manta.cs.vt.edu/vt/buildings/glcdb/glcdb.jpg'),
('Hahn Garden Pavilion and Horticulture Garden', 'HGPAV', '37.2200791481', '-80.4243759420', 'Research', 'http://manta.cs.vt.edu/vt/buildings/hgpav/hgpav.txt', 'http://manta.cs.vt.edu/vt/buildings/hgpav/hgpav.jpg'),
('Hahn Hall-North Wing', 'HAHNN', '37.2283343173', '-80.4263007046', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/hahnn/hahnn.txt', 'http://manta.cs.vt.edu/vt/buildings/hahnn/hahnn.jpg'),
('Hahn Hall-South Wing', 'HAHNS', '37.2278901235', '-80.4257496289', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/hahns/hahns.txt', 'http://manta.cs.vt.edu/vt/buildings/hahns/hahns.jpg'),
('Hahn Hurst Basketball Practice Center', 'BBPF', '37.2235306695', '-80.4182276484', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/bbpf/bbpf.txt', 'http://manta.cs.vt.edu/vt/buildings/bbpf/bbpf.jpg'),
('Harper Hall', 'HARP', '37.2226895803', '-80.4230780507', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/harp/harp.txt', 'http://manta.cs.vt.edu/vt/buildings/harp/harp.jpg'),
('Health and Safety Building', 'HSBLD', '37.215071517', '-80.4180268472', 'Support', 'http://manta.cs.vt.edu/vt/buildings/hsbld/hsbld.txt', 'http://manta.cs.vt.edu/vt/buildings/hsbld/hsbld.jpg'),
('Henderson Hall', 'HOSP', '37.23056221', '-80.41669645', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/hosp/hosp.txt', 'http://manta.cs.vt.edu/vt/buildings/hosp/hosp.jpg'),
('Hillcrest Hall', 'HILL', '37.223851161', '-80.4249611504', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/hill/hill.txt', 'http://manta.cs.vt.edu/vt/buildings/hill/hill.jpg'),
('Holden Hall', 'HOLD', '37.2301940441', '-80.4223473045', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/hold/hold.txt', 'http://manta.cs.vt.edu/vt/buildings/hold/hold.jpg'),
('Holtzman Alumni Center', 'HOLTZ', '37.2297576133', '-80.4305317827', 'Support', 'http://manta.cs.vt.edu/vt/buildings/holtz/holtz.txt', 'http://manta.cs.vt.edu/vt/buildings/holtz/holtz.jpg'),
('Human and Agricultural Biosciences Building I', 'HABB1', '37.22023', '-80.42761', 'Research', 'http://manta.cs.vt.edu/vt/buildings/habb1/habb1.txt', 'http://manta.cs.vt.edu/vt/buildings/habb1/habb1.jpg'),
('Hutcheson Hall', 'HUTCH', '37.2256824991', '-80.4229589494', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/hutch/hutch.txt', 'http://manta.cs.vt.edu/vt/buildings/hutch/hutch.jpg'),
('Institute for Critical Technology and Applied Science (ICTAS II)', 'ICTAS II', '37.22075', '-80.42597', 'Research', 'http://manta.cs.vt.edu/vt/buildings/ictasii/ictasii.txt', 'http://manta.cs.vt.edu/vt/buildings/ictasii/ictasii.jpg'),
('Jamerson Athletic Center', 'JAMC', '37.2221614070', '-80.4187417030', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/jamc/jamc.txt', 'http://manta.cs.vt.edu/vt/buildings/jamc/jamc.jpg'),
('John W. Hancock Jr. Hall', 'HAN', '37.2302568526', '-80.4242615881', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/han/han.txt', 'http://manta.cs.vt.edu/vt/buildings/han/han.jpg'),
('Johnson Hall', 'JOHN', '37.2255399236', '-80.4177235209', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/john/john.txt', 'http://manta.cs.vt.edu/vt/buildings/john/john.jpg'),
('Kelly Hall', 'KELLY', '37.23108', '-80.42244', 'Research', 'http://manta.cs.vt.edu/vt/buildings/kelly/kelly.txt', 'http://manta.cs.vt.edu/vt/buildings/kelly/kelly.jpg'),
('Lane Hall', 'LANE', '37.2308976486', '-80.4197766686', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/lane/lane.txt', 'http://manta.cs.vt.edu/vt/buildings/lane/lane.jpg'),
('Lane Stadium / Worsham Field', 'STAD', '37.2200492457', '-80.4180559870', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/stad/stad.txt', 'http://manta.cs.vt.edu/vt/buildings/stad/stad.jpg'),
('Latham Hall', 'LATH', '37.2245707091', '-80.4226055726', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/lath/lath.txt', 'http://manta.cs.vt.edu/vt/buildings/lath/lath.jpg'),
('Lavery Hall', 'WLH', '37.2310374038', '-80.4226899147', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/wlh/wlh.txt', 'http://manta.cs.vt.edu/vt/buildings/wlh/wlh.jpg'),
('Lee Hall', 'LEE', '37.2244818531', '-80.4184691578', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/lee/lee.txt', 'http://manta.cs.vt.edu/vt/buildings/lee/lee.jpg'),
('Liberal Arts Building', 'LARTS', '37.2306122522', '-80.4208760104', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/larts/larts.txt', 'http://manta.cs.vt.edu/vt/buildings/larts/larts.jpg'),
('Life Sciences I Facility', 'LFSCI', '37.2209418794', '-80.4246484203', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/lfsci/lfsci.txt', 'http://manta.cs.vt.edu/vt/buildings/lfsci/lfsci.jpg'),
('Litton-Reaves Hall', 'LITRV', '37.2215083609', '-80.42394781', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/litrv/litrv.txt', 'http://manta.cs.vt.edu/vt/buildings/litrv/litrv.jpg'),
('Major Williams Hall', 'MAJWM', '37.231032007', '-80.4207640959', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/majwm/majwm.txt', 'http://manta.cs.vt.edu/vt/buildings/majwm/majwm.jpg'),
('McBryde Hall', 'MCB', '37.2305915726', '-80.4217767404', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/mcb/mcb.txt', 'http://manta.cs.vt.edu/vt/buildings/mcb/mcb.jpg'),
('McComas Hall', 'MCCOM', '37.2203073567', '-80.4224733516', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/mccom/mccom.txt', 'http://manta.cs.vt.edu/vt/buildings/mccom/mccom.jpg'),
('Media Annex', 'TVFLM', '37.2288052576', '-80.4159352148', 'Support', 'http://manta.cs.vt.edu/vt/buildings/tvflm/tvflm.txt', 'http://manta.cs.vt.edu/vt/buildings/tvflm/tvflm.jpg'),
('Media Building', 'MEDIA', '37.2288020247', '-80.4153331363', 'Support', 'http://manta.cs.vt.edu/vt/buildings/media/media.txt', 'http://manta.cs.vt.edu/vt/buildings/media/media.jpg'),
('Merryman Athletic Facility', 'MRYMN', '37.2215480711', '-80.4190727526', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/mrymn/mrymn.txt', 'http://manta.cs.vt.edu/vt/buildings/mrymn/mrymn.jpg'),
('Miles Hall', 'MILES', '37.2255087685', '-80.4169574999', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/miles/miles.txt', 'http://manta.cs.vt.edu/vt/buildings/miles/miles.jpg'),
('Military Building', 'MIL', '37.2318090515', '-80.4215258097', 'Support', 'http://manta.cs.vt.edu/vt/buildings/mil/mil.txt', 'http://manta.cs.vt.edu/vt/buildings/mil/mil.jpg'),
('Monteith Hall', 'MON', '37.2319382146', '-80.4193588706', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/mon/mon.txt', 'http://manta.cs.vt.edu/vt/buildings/mon/mon.jpg'),
('Moss Arts Center', 'MAC', '37.23234', '-80.41765', 'Support', 'http://manta.cs.vt.edu/vt/buildings/mac/mac.txt', 'http://manta.cs.vt.edu/vt/buildings/mac/mac.jpg'),
('New Hall West', 'NHW', '37.2219352148', '-80.4229134675', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/nhw/nhw.txt', 'http://manta.cs.vt.edu/vt/buildings/nhw/nhw.jpg'),
('New Residence Hall - East', 'NRHE', '37.2256625671', '-80.4191964801', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/nrhe/nrhe.txt', 'http://manta.cs.vt.edu/vt/buildings/nrhe/nrhe.jpg'),
('Newman Hall', 'NEW', '37.2261955545', '-80.4178530971', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/new/new.txt', 'http://manta.cs.vt.edu/vt/buildings/new/new.jpg'),
('Norris Hall', 'NOR', '37.2297380609', '-80.4231507814', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/nor/nor.txt', 'http://manta.cs.vt.edu/vt/buildings/nor/nor.jpg'),
('North End Center', 'NECTR', '37.2335', '-80.4205', 'Administration', 'http://manta.cs.vt.edu/vt/buildings/nectr/nectr.txt', 'http://manta.cs.vt.edu/vt/buildings/nectr/nectr.jpg'),
('OShaughnessy Hall', 'OSHA', '37.2253831361', '-80.418321493', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/osha/osha.txt', 'http://manta.cs.vt.edu/vt/buildings/osha/osha.jpg'),
('Old Security Building', 'SEC', '37.2314834934', '-80.4215852275', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/sec/sec.txt', 'http://manta.cs.vt.edu/vt/buildings/sec/sec.jpg'),
('Owens Hall', 'OWENS', '37.2267076757', '-80.4188925901', 'Dining', 'http://manta.cs.vt.edu/vt/buildings/owens/owens.txt', 'http://manta.cs.vt.edu/vt/buildings/owens/owens.jpg'),
('Pamplin Hall', 'PAM', '37.2286590085', '-80.4246658853', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/pam/pam.txt', 'http://manta.cs.vt.edu/vt/buildings/pam/pam.jpg'),
('Parking Services Building', 'PRKSV', '37.21592', '-80.41841', 'Support', 'http://manta.cs.vt.edu/vt/buildings/prksv/prksv.txt', 'http://manta.cs.vt.edu/vt/buildings/prksv/prksv.jpg'),
('Patton Hall', 'PAT', '37.2292277723', '-80.4222005878', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/pat/pat.txt', 'http://manta.cs.vt.edu/vt/buildings/pat/pat.jpg'),
('Payne Hall', 'PAYNE', '37.225814163', '-80.4200030439', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/payne/payne.txt', 'http://manta.cs.vt.edu/vt/buildings/payne/payne.jpg'),
('Pearson Hall', 'PHALL', '37.23076', '-80.41896', 'Resident', 'http://manta.cs.vt.edu/vt/buildings/phall/phall.txt', 'http://manta.cs.vt.edu/vt/buildings/phall/phall.jpg'),
('Peddrew-Yates Residence Hall', 'PY', '37.22503', '-80.41991', 'Resident', 'http://manta.cs.vt.edu/vt/buildings/py/py.txt', 'http://manta.cs.vt.edu/vt/buildings/py/py.jpg'),
('Perry Street Parking Deck', 'PARKP', '37.23086', '-80.42565', 'Support', 'http://manta.cs.vt.edu/vt/buildings/parkp/parkp.txt', 'http://manta.cs.vt.edu/vt/buildings/parkp/parkp.jpg'),
('Price Hall', 'PRICE', '37.2257366513', '-80.4240833891', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/price/price.txt', 'http://manta.cs.vt.edu/vt/buildings/price/price.jpg'),
('Pritchard Hall', 'PRIT', '37.2242495514', '-80.4196564388', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/prit/prit.txt', 'http://manta.cs.vt.edu/vt/buildings/prit/prit.jpg'),
('Public Safety Building', 'SGCTR', '37.21887', '-80.41612', 'Support', 'http://manta.cs.vt.edu/vt/buildings/sgctr/sgctr.txt', 'http://manta.cs.vt.edu/vt/buildings/sgctr/sgctr.jpg'),
('Randolph Hall', 'RAND', '37.2305751562', '-80.4235595611', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/rand/rand.txt', 'http://manta.cs.vt.edu/vt/buildings/rand/rand.jpg'),
('Rector Field House', 'RFH', '37.2189872178', '-80.421688069', 'Athletic', 'http://manta.cs.vt.edu/vt/buildings/rfh/rfh.txt', 'http://manta.cs.vt.edu/vt/buildings/rfh/rfh.jpg'),
('Robeson Hall', 'ROB', '37.2280257235', '-80.4252644554', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/rob/rob.txt', 'http://manta.cs.vt.edu/vt/buildings/rob/rob.jpg'),
('Sandy Hall', 'SANDY', '37.2258067806', '-80.4235231085', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/sandy/sandy.txt', 'http://manta.cs.vt.edu/vt/buildings/sandy/sandy.jpg'),
('Saunders Hall', 'SAUND', '37.2249239799', '-80.4243454457', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/saund/saund.txt', 'http://manta.cs.vt.edu/vt/buildings/saund/saund.jpg'),
('Seitz Hall', 'SEITZ', '37.2247316129', '-80.4236936728', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/seitz/seitz.txt', 'http://manta.cs.vt.edu/vt/buildings/seitz/seitz.jpg'),
('Shanks Hall', 'SHANK', '37.2316847346', '-80.4198269515', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/shank/shank.txt', 'http://manta.cs.vt.edu/vt/buildings/shank/shank.jpg'),
('Skelton Conference Center', 'SCC', '37.2294764616', '-80.4295019059', 'Support', 'http://manta.cs.vt.edu/vt/buildings/scc/scc.txt', 'http://manta.cs.vt.edu/vt/buildings/scc/scc.jpg'),
('Slusher Hall', 'SLUSH', '37.2252953086', '-80.421645363', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/slush/slush.txt', 'http://manta.cs.vt.edu/vt/buildings/slush/slush.jpg'),
('Smith Career Center (Career Services Building)', 'SMCC', '37.2215987738', '-80.4225605993', 'Support', 'http://manta.cs.vt.edu/vt/buildings/smcc/smcc.txt', 'http://manta.cs.vt.edu/vt/buildings/smcc/smcc.jpg'),
('Smyth Hall', 'SMYTH', '37.2250865297', '-80.4231740862', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/smyth/smyth.txt', 'http://manta.cs.vt.edu/vt/buildings/smyth/smyth.jpg'),
('Solitude', 'SOL', '37.2261807846', '-80.4268719595', 'Support', 'http://manta.cs.vt.edu/vt/buildings/sol/sol.txt', 'http://manta.cs.vt.edu/vt/buildings/sol/sol.jpg'),
('Squires Student Center', 'SQUIR', '37.2296066872', '-80.4179386309', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/squir/squir.txt', 'http://manta.cs.vt.edu/vt/buildings/squir/squir.jpg'),
('Sterrett Facility Complex', 'STCTR', '37.21965', '-80.41372', 'Support', 'http://manta.cs.vt.edu/vt/buildings/stctr/stctr.txt', 'http://manta.cs.vt.edu/vt/buildings/stctr/stctr.jpg'),
('Student Services Building', 'SSB', '37.2220842177', '-80.4218308177', 'Support', 'http://manta.cs.vt.edu/vt/buildings/ssb/ssb.txt', 'http://manta.cs.vt.edu/vt/buildings/ssb/ssb.jpg'),
('Surge Space Building', 'SURGE', '37.23306251', '-80.42308547', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/surge/surge.txt', 'http://manta.cs.vt.edu/vt/buildings/surge/surge.jpg'),
('The Grove', 'GROVE', '37.224299462', '-80.4273173019', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/grove/grove.txt', 'http://manta.cs.vt.edu/vt/buildings/grove/grove.jpg'),
('The Inn at Virginia Tech', 'VTINN', '37.22975761', '-80.43053178', 'Support', 'http://manta.cs.vt.edu/vt/buildings/vtinn/vtinn.txt', 'http://manta.cs.vt.edu/vt/buildings/vtinn/vtinn.jpg'),
('Thomas Hall', 'THOM', '37.2321448497', '-80.4200454707', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/thom/thom.txt', 'http://manta.cs.vt.edu/vt/buildings/thom/thom.jpg'),
('Torgersen Hall', 'TORG', '37.2297382184', '-80.4200649602', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/torg/torg.txt', 'http://manta.cs.vt.edu/vt/buildings/torg/torg.jpg'),
('University Bookstore', 'BOOK', '37.2281178735', '-80.418611472', 'Support', 'http://manta.cs.vt.edu/vt/buildings/book/book.txt', 'http://manta.cs.vt.edu/vt/buildings/book/book.jpg'),
('University Club', 'UCLUB', '37.227361084', '-80.4165894513', 'Support', 'http://manta.cs.vt.edu/vt/buildings/uclub/uclub.txt', 'http://manta.cs.vt.edu/vt/buildings/uclub/uclub.jpg'),
('Vawter Hall', 'VAW', '37.2270007259', '-80.417663702', 'Resident and Dining Halls', 'http://manta.cs.vt.edu/vt/buildings/vaw/vaw.txt', 'http://manta.cs.vt.edu/vt/buildings/vaw/vaw.jpg'),
('Virginia-Maryland Regional College of Veterinary Medicine', 'VM', '37.2181546855', '-80.427446400', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/vm/vm.txt', 'http://manta.cs.vt.edu/vt/buildings/vm/vm.jpg'),
('Visitor and Undergraduate Admissions Center', 'VUAC', '37.23117', '-80.43359', 'Support', 'http://manta.cs.vt.edu/vt/buildings/vuac/vuac.txt', 'http://manta.cs.vt.edu/vt/buildings/vuac/vuac.jpg'),
('Wallace Hall', 'WAL', '37.222950885', '-80.4242525239', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/wal/wal.txt', 'http://manta.cs.vt.edu/vt/buildings/wal/wal.jpg'),
('War Memorial Chapel', 'CHAP', '37.2288560699', '-80.4204236654', 'Support', 'http://manta.cs.vt.edu/vt/buildings/chap/chap.txt', 'http://manta.cs.vt.edu/vt/buildings/chap/chap.jpg'),
('War Memorial Hall', 'GYM', '37.2263361902', '-80.4206594839', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/gym/gym.txt', 'http://manta.cs.vt.edu/vt/buildings/gym/gym.jpg'),
('Whittemore Hall', 'WHIT', '37.2310217858', '-80.4244441784', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/whit/whit.txt', 'http://manta.cs.vt.edu/vt/buildings/whit/whit.jpg'),
('William E. Lavery Animal Health Research Center', 'VM4C2', '37.21713', '-80.42663', 'Support', 'http://manta.cs.vt.edu/vt/buildings/vm4c2/vm4c2.txt', 'http://manta.cs.vt.edu/vt/buildings/vm4c2/vm4c2.jpg'),
('Williams Hall', 'WMS', '37.22784', '-80.42435', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/wms/wms.txt', 'http://manta.cs.vt.edu/vt/buildings/wms/wms.png'),
('Wright House', 'WRGHT', '37.2268104329', '-80.4261888832', 'Academic', 'http://manta.cs.vt.edu/vt/buildings/wrght/wrght.txt', 'http://manta.cs.vt.edu/vt/buildings/wrght/wrght.jpg'),
('Alphin-Stuart Livestock Teaching Arena', 'LARNA', '37.21929', '-80.43991', 'Support', 'http://manta.cs.vt.edu/vt/buildings/larna/larna.txt', 'http://manta.cs.vt.edu/vt/buildings/larna/larna.jpg');

INSERT INTO Item (name, description, latitude_found, longitude_found, category) VALUES 
('Mike''s iPhone', 'iPhone 5. Lost in the cloud computing lab', '37.2305915726', '-80.4217767404','PHONE'),
('Seb''s Watch', 'Lost my fancy watch while waiting for the bus in front of Burruss', '37.229031934', '-80.4237145305','OTHER'),
('Tuna''s Keys', 'I lost my keys in the parking garage.  Keys to a sweet Mustang', '37.23086', '-80.42565','KEYS');

INSERT INTO ItemPhoto (extension, photo_for) VALUES
('jpeg', (SELECT id from Item WHERE name='Mike''s iPhone') ),
('png', (SELECT id from Item WHERE name='Seb''s Watch') );

INSERT INTO ParkingLot (name, latitude, longitude, permission)
VALUES ('Drillfield', '37.229204,37.227283,37.226195,37.228020', '-80.421507,-80.424192,-80.423054,-80.420323', 'any');