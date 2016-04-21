DROP TABLE IF EXISTS ItemPhoto, Subscription, Notification, Item, User, UserPhoto, Building, ParkingLot;

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
    PRIMARY KEY (id)
);

/* The UserPhoto table contains attributes of interest of a user's profile photo. */
CREATE TABLE UserPhoto
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
    created_at timestamp default now(), 
    updated_at timestamp default now() on update now(),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
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

INSERT INTO ParkingLot (name, latitude, longitude, permission) VALUES
('Inn at VT North', '-80.432845,-80.432888,-80.432904,-80.432914,-80.432825,-80.432617,-80.432255,-80.432386,-80.432507,-80.432147,-80.431616,-80.431243,-80.430742,-80.430299,-80.429814,-80.429374,-80.429149,-80.429095,-80.429106,-80.429873,-80.429905,-80.429991,-80.429994,-80.430082,-80.430222,-80.430613,-80.430946,-80.431638,-80.432807,-80.433024,-80.433113,-80.433059,-80.433070,-80.432936', '37.231520,37.231315,37.231123,37.230798,37.230651,37.230563,37.230474,37.230687,37.230909,37.231020,37.230956,37.230935,37.230931,37.231031,37.231054,37.230918,37.230662,37.230563,37.230448,37.230064,37.230136,37.230089,37.229982,37.229910,37.229854,37.229884,37.230089,37.230209,37.230486,37.230659,37.230922,37.231482,37.231529,37.231516', 'ANY'),
('Chicken Hill Lot', '-80.418742,-80.418828,-80.418795,-80.418838,-80.418747,-80.418704,-80.417942,-80.417921,-80.417148,-80.417025,-80.416639,-80.415797,-80.415158,-80.414649,-80.414686,-80.414590,-80.414670,-80.416542,-80.416816,-80.417052,-80.418012,-80.418731,-80.418736,-80.418742', '37.216591,37.216886,37.217027,37.217193,37.217283,37.217347,37.217433,37.217390,37.217112,37.217108,37.216993,37.216809,37.217010,37.216253,37.216224,37.216074,37.215989,37.216339,37.216343,37.216313,37.216672,37.216578,37.216595,37.216591', 'ANY'),
('Parking & Transportation Lot', '-80.418656,-80.418318,-80.418270,-80.418221,-80.418119,-80.418152,-80.418136,-80.418109,-80.418060,-80.418484,-80.418656', '37.215822,37.215886,37.215912,37.215882,37.215583,37.215570,37.215540,37.215540,37.215365,37.215262,37.215822', 'ANY'),
('Health & Safety Lot', '-80.418291,-80.418232,-80.418243,-80.417840,-80.417824,-80.417776,-80.417621,-80.418178,-80.418291', '37.214784,37.214810,37.214874,37.214959,37.214908,37.214891,37.214604,37.214502,37.214784', 'ANY'),
('Rec Fields Lot', '-80.417926,-80.417883,-80.417792,-80.417486,-80.417470,-80.417503,-80.417422,-80.417481,-80.417465,-80.417744,-80.417771,-80.417830,-80.417948,-80.417926', '37.213951,37.213964,37.213959,37.214023,37.213955,37.213938,37.213729,37.213690,37.213652,37.213588,37.213647,37.213639,37.213929,37.213951', 'ANY'),
('Duck Pond Road Lot', '-80.428956,-80.429835,-80.429653,-80.430404,-80.428677,-80.427926,-80.429342,-80.428430,-80.428945,-80.428956', '37.219248,37.220530,37.220598,37.221674,37.222426,37.221401,37.220718,37.219436,37.219180,37.219248', 'RESIDENT'),
('Stadium Lot', '-80.419225,-80.419270,-80.419230,-80.419182,-80.418785,-80.418034,-80.417449,-80.417132,-80.416945,-80.416902,-80.416762,-80.416666,-80.416510,-80.416494,-80.416499,-80.416548,-80.416816,-80.416923,-80.416977,-80.417401,-80.417637,-80.417969,-80.418195,-80.418527,-80.418849,-80.419123,-80.419214', '37.217787,37.217934,37.218116,37.218616,37.218603,37.218594,37.218565,37.218466,37.218295,37.218197,37.218120,37.218031,37.217937,37.217877,37.217830,37.217766,37.217544,37.217424,37.217441,37.217480,37.217556,37.217601,37.217603,37.217571,37.217497,37.217420,37.217761', 'RESIDENT'),
('North End Center Garage', '-80.422915,-80.421177,-80.421059,-80.421628,-80.421574,-80.421510,-80.421306,-80.421488,-80.421596,-80.421596,-80.422111,-80.422915', '37.233685,37.234360,37.234318,37.233942,37.233908,37.233967,37.233908,37.233344,37.233361,37.233318,37.233480,37.233685', 'FACULTY/STAFF/VISITOR'),
('Lower Stranger', '-80.423881,-80.422583,-80.423033,-80.424020,-80.423881', '37.233053,37.232643,37.232310,37.232609,37.233053', 'FACULTY/STAFF/VISITOR'),
('Lower Stranger', '-80.422711,-80.421960,-80.422153,-80.422561,-80.422894,-80.422711', '37.232379,37.231815,37.231678,37.232020,37.232233,37.232379', 'FACULTY/STAFF/VISITOR'),
('Substation Lot', '-80.422561,-80.422422,-80.422239,-80.422143,-80.421993,-80.422261,-80.422561', '37.232490,37.232618,37.232473,37.232549,37.232455,37.232225,37.232490', 'FACULTY/STAFF/VISITOR'),
('Perry St Lot', '-80.424449,-80.424042,-80.423924,-80.423913,-80.425200,-80.425705,-80.425683,-80.424449', '37.232549,37.232379,37.232259,37.232080,37.231097,37.231550,37.231635,37.232549', 'FACULTY/STAFF/VISITOR'),
('Perry St Garage F1-F2', '-80.427024,-80.426327,-80.425533,-80.425662,-80.425758,-80.426263,-80.427089,-80.427024', '37.231268,37.231815,37.231183,37.231020,37.231012,37.230568,37.231242,37.231268', 'FACULTY/STAFF/VISITOR'),
('Perry St Garage F3-F6', '-80.427024,-80.426327,-80.425533,-80.425662,-80.425758,-80.426263,-80.427089,-80.427024', '37.231268,37.231815,37.231183,37.231020,37.231012,37.230568,37.231242,37.231268', 'COMMUTER/GRADUATE'),
('Perry St Lot', '-80.425705,-80.425576,-80.426027,-80.426177,-80.425705', '37.230961,37.230867,37.230533,37.230644,37.230961', 'FACULTY/STAFF/VISITOR'),
('Perry St Lot', '-80.426241,-80.426327,-80.426252,-80.427561,-80.428001,-80.427260,-80.427443,-80.426842,-80.426713,-80.426241', '37.230345,37.230303,37.230269,37.229320,37.229799,37.230363,37.230457,37.230764,37.230730,37.230345', 'FACULTY/STAFF/VISITOR'),
('Prices Fork Lot', '-80.426821,-80.427293,-80.427400,-80.428097,-80.428355,-80.428483,-80.427969,-80.427808,-80.426821', '37.230764,37.230405,37.230508,37.230004,37.230294,37.231251,37.231670,37.231644,37.230764', 'COMMUTER/GRADUATE'),
('Prices Fork Lot', '-80.427625,-80.427539,-80.426960,-80.426917,-80.426788,-80.426520,-80.426574,-80.426466,-80.427067,-80.427153,-80.427239,-80.427625', '37.231627,37.231755,37.232225,37.232182,37.232242,37.232020,37.231951,37.231866,37.231379,37.231396,37.231302,37.231627', 'COMMUTER/GRADUATE'),
('Derring Lot', '-80.426584,-80.426509,-80.426177,-80.425705,-80.425276,-80.425791,-80.426134,-80.426295,-80.425984,-80.425855,-80.425619,-80.425544,-80.425436,-80.425479,-80.425286,-80.425211,-80.425158,-80.424922,-80.425158,-80.425297,-80.425436,-80.425394,-80.425608,-80.425748,-80.425898,-80.426059,-80.426198,-80.426370,-80.426552,-80.426295,-80.426016,-80.426584', '37.229756,37.229833,37.229577,37.229953,37.229619,37.229184,37.228910,37.228799,37.228534,37.228552,37.228774,37.228722,37.228816,37.228902,37.229038,37.228996,37.229021,37.228885,37.228722,37.228765,37.228697,37.228654,37.228432,37.228449,37.228389,37.228423,37.228492,37.228637,37.228808,37.229056,37.229295,37.229756', 'FACULTY/STAFF/VISITOR'),
('Inn at VT East', '-80.428333,-80.428612,-80.428913,-80.429127,-80.429031,-80.429127,-80.429342,-80.429084,-80.428838,-80.428913,-80.428913,-80.428870,-80.428741,-80.428591,-80.428773,-80.428537,-80.428473,-80.428441,-80.428258,-80.428247,-80.428194,-80.428376,-80.428451,-80.428591,-80.428258,-80.428333', '37.229833,37.229636,37.230166,37.230098,37.229935,37.229995,37.229910,37.229483,37.229585,37.229705,37.229782,37.229799,37.229594,37.229346,37.229243,37.228868,37.228868,37.228808,37.228910,37.228962,37.228996,37.229261,37.229320,37.229551,37.229748,37.229833', 'FACULTY/STAFF/VISITOR'),
('Hahn Lot', '-80.426440,-80.426252,-80.426166,-80.426091,-80.425640,-80.425678,-80.425662,-80.425898,-80.425968,-80.425914,-80.426440', '37.227825,37.227966,37.227941,37.227898,37.227518,37.227492,37.227445,37.227227,37.227287,37.227351,37.227825', 'FACULTY/STAFF/VISITOR'),
('Davidson Lot', '-80.425179,-80.425479,-80.425844,-80.425758,-80.425179', '37.226600,37.226459,37.227014,37.227082,37.226600', 'FACULTY/STAFF/VISITOR'),
('Wright House Lot', '-80.426466,-80.426332,-80.426241,-80.426000,-80.426102,-80.425925,-80.425764,-80.425860,-80.425855,-80.426268,-80.426466', '37.226531,37.226582,37.226416,37.226476,37.226668,37.226762,37.226441,37.226399,37.226343,37.226181,37.226531', 'FACULTY/STAFF/VISITOR'),
('Price Hall Lot', '-80.425029,-80.424739,-80.424707,-80.424535,-80.424315,-80.424364,-80.424407,-80.424476,-80.424739,-80.425013,-80.425029', '37.225613,37.225698,37.225660,37.225707,37.225263,37.225186,37.225092,37.225028,37.224917,37.225489,37.225613', 'FACULTY/STAFF/VISITOR'),
('Ag Quad Lot', '-80.423017,-80.422813,-80.422813,-80.422626,-80.422583,-80.422550,-80.422309,-80.422556,-80.422620,-80.422776,-80.423028,-80.423055,-80.423017', '37.225673,37.225690,37.225634,37.225609,37.225117,37.225130,37.224917,37.224810,37.224891,37.224929,37.225211,37.225596,37.225673', 'FACULTY/STAFF/VISITOR'),
('Smythe Hall Lot', '-80.423237,-80.423366,-80.423178,-80.423044,-80.423237', '37.224622,37.224899,37.224964,37.224669,37.224622', 'FACULTY/STAFF/VISITOR'),
('Dietrick Lot', '-80.421842,-80.421140,-80.420936,-80.421467,-80.421558,-80.421708,-80.421842', '37.224618,37.225126,37.224959,37.224596,37.224596,37.224494,37.224618', 'FACULTY/STAFF/VISITOR'),
('Owens Lot', '-80.418935,-80.418747,-80.418672,-80.418640,-80.418093,-80.418264,-80.418221,-80.418152,-80.418136,-80.418173,-80.418141,-80.418028,-80.417958,-80.418012,-80.418168,-80.418216,-80.418227,-80.418211,-80.418211,-80.418425,-80.418388,-80.418656,-80.418731,-80.418237,-80.418264,-80.418291,-80.418339,-80.418935', '37.226181,37.226053,37.226083,37.226053,37.226591,37.226706,37.226758,37.226881,37.226971,37.227099,37.227304,37.227501,37.227625,37.227646,37.227330,37.227236,37.227138,37.227005,37.226894,37.227031,37.227082,37.227253,37.227219,37.226873,37.226758,37.226723,37.226753,37.226181', 'FACULTY/STAFF/VISITOR'),
('Bookstore Lot', '-80.418699,-80.418280,-80.418248,-80.418071,-80.417926,-80.417942,-80.418060,-80.418205,-80.418457,-80.418500,-80.418645,-80.418699', '37.227796,37.228069,37.228026,37.228065,37.227898,37.227834,37.227753,37.227894,37.227727,37.227770,37.227761,37.227796', 'METERED'),
('Inner Squires Lot', '-80.418264,-80.417980,-80.417926,-80.418071,-80.418082,-80.418077,-80.418034,-80.417572,-80.417604,-80.417583,-80.417663,-80.418264', '37.228329,37.228436,37.228398,37.228278,37.228244,37.228223,37.228257,37.227774,37.227727,37.227697,37.227659,37.228329', 'FACULTY/STAFF/VISITOR'),
('Outer Squires Lot', '-80.417385,-80.417325,-80.417352,-80.416741,-80.416698,-80.416623,-80.416344,-80.417079,-80.417181,-80.417245,-80.417385', '37.229150,37.229184,37.229214,37.229636,37.229589,37.229619,37.229355,37.228910,37.229013,37.228979,37.229150', 'FACULTY/STAFF/VISITOR'),
('Outer Squires Lot', '-80.417095,-80.417041,-80.416349,-80.416151,-80.416886,-80.416945,-80.416998,-80.417122,-80.417079,-80.417132,-80.417218,-80.417293,-80.417331,-80.417304,-80.417218,-80.417079,-80.416923,-80.416773,-80.416612,-80.416542,-80.416671,-80.416907,-80.417063,-80.417213,-80.417309,-80.417390,-80.417395,-80.417363,-80.417347,-80.417095', '37.229004,37.228936,37.229359,37.229137,37.228697,37.228774,37.228752,37.228872,37.228919,37.228957,37.228906,37.228816,37.228654,37.228581,37.228449,37.228325,37.228201,37.228129,37.228082,37.228026,37.228022,37.228116,37.228218,37.228351,37.228458,37.228594,37.228718,37.228825,37.228868,37.229004', 'COMMUTER/GRADUATE'),
('Agricultural Annex', '-80.416414,-80.416296,-80.416322,-80.415797,-80.415759,-80.415496,-80.415448,-80.415389,-80.415432,-80.415389,-80.416172,-80.416220,-80.416274,-80.416381,-80.416349,-80.416414', '37.228248,37.228295,37.228325,37.228658,37.228628,37.228628,37.228646,37.228581,37.228552,37.228505,37.227954,37.228013,37.227996,37.228090,37.228137,37.228248', 'FACULTY/STAFF/VISITOR'),
('Duck Pond Lot', '-80.431445,-80.430560,-80.430050,-80.430946,-80.431445', '37.223263,37.224242,37.223951,37.222977,37.223263', 'COMMUTER/GRADUATE'),
('Smithfield Rd Lot', '-80.429530,-80.429358,-80.429460,-80.429406,-80.429406,-80.429406,-80.429283,-80.429310,-80.429213,-80.429165,-80.429111,-80.429095,-80.429149,-80.428843,-80.428784,-80.428768,-80.428811,-80.428843,-80.429181,-80.429213,-80.429347,-80.429379,-80.429412,-80.429347,-80.429245,-80.429181,-80.429111,-80.429165,-80.429138,-80.430195,-80.430248,-80.430307,-80.429889,-80.429830,-80.429760,-80.429712,-80.429642,-80.429589,-80.429433,-80.429390,-80.429530', '37.223152,37.223212,37.223511,37.223524,37.223601,37.223652,37.223686,37.223746,37.223750,37.223729,37.223682,37.223661,37.223656,37.222849,37.222858,37.222785,37.222738,37.222695,37.222606,37.222665,37.222772,37.222768,37.222648,37.222623,37.222554,37.222473,37.222384,37.222341,37.222285,37.221794,37.221918,37.221939,37.222721,37.222700,37.222764,37.222781,37.222755,37.222725,37.222648,37.222777,37.223152', 'COMMUTER/GRADUATE'),
('Litton Reavers Lot', '-80.427765,-80.427657,-80.426204,-80.426096,-80.425823,-80.426279,-80.426354,-80.426928,-80.427255,-80.427582,-80.427765', '37.222358,37.222413,37.222606,37.222409,37.222213,37.221743,37.221764,37.221418,37.221696,37.222063,37.222358', 'COMMUTER/GRADUATE'),
('Litton Reavers Ext Lot', '-80.426204,-80.425651,-80.425501,-80.425833,-80.426010,-80.426118,-80.426204', '37.222597,37.222674,37.222503,37.222225,37.222337,37.222435,37.222597', 'FACULTY/STAFF/VISITOR'),
('Litton Reavers Ext Lot', '-80.427094,-80.427019,-80.426831,-80.426332,-80.426241,-80.426145,-80.425774,-80.425694,-80.425651,-80.425576,-80.427099,-80.427094', '37.222648,37.222794,37.222990,37.223046,37.223165,37.223182,37.223229,37.223191,37.223208,37.222815,37.222631,37.222648', 'FACULTY/STAFF/VISITOR'),
('Litton Reavers Ext Lot', '-80.426306,-80.426177,-80.425292,-80.425614,-80.425748,-80.426150,-80.426204,-80.426214,-80.426198,-80.426236,-80.426134,-80.426295,-80.426268,-80.426306', '37.221739,37.221875,37.221478,37.221000,37.221029,37.221196,37.221264,37.221337,37.221405,37.221435,37.221580,37.221679,37.221726,37.221739', 'FACULTY/STAFF/VISITOR'),
('Hillcrest Lot', '-80.425281,-80.425302,-80.425892,-80.425882,-80.425490,-80.425453,-80.425281', '37.223716,37.223580,37.223614,37.223763,37.223759,37.223729,37.223716', 'FACULTY/STAFF/VISITOR'),
('Hillcrest Extension Lot', '-80.425383,-80.425217,-80.425200,-80.425050,-80.424728,-80.424723,-80.424787,-80.424750,-80.424744,-80.424771,-80.425152,-80.425270,-80.425351', '37.223447,37.223486,37.223520,37.223563,37.223550,37.223225,37.223187,37.223105,37.223088,37.223003,37.222879,37.222917,37.223242', 'FACULTY/STAFF/VISITOR'),
('Wallace Lot', '-80.424964,-80.424090,-80.423875,-80.424852,-80.424964', '37.222341,37.222606,37.222114,37.221815,37.222341', 'FACULTY/STAFF/VISITOR'),
('Duck Pond Rd Lot', '-80.428693,-80.428467,-80.428355,-80.426885,-80.426289,-80.426976,-80.428736,-80.428693', '37.219329,37.219453,37.219346,37.219996,37.219201,37.218868,37.219299,37.219329', 'COMMUTER/GRADUATE'),
('Food Science Lot', '-80.425941,-80.425817,-80.425538,-80.425592,-80.425522,-80.425265,-80.425281,-80.424981,-80.425029,-80.425131,-80.425200,-80.425002,-80.424830,-80.424868,-80.425254,-80.425512,-80.425941', '37.219778,37.219825,37.219453,37.219410,37.219287,37.219381,37.219474,37.219547,37.219761,37.219765,37.220017,37.220077,37.219530,37.219419,37.219346,37.219231,37.219778', 'FACULTY/STAFF/VISITOR'),
('Engel Lot', '-80.423661,-80.423409,-80.423366,-80.423393,-80.423441,-80.423532,-80.423500,-80.423049,-80.422717,-80.423489,-80.423661', '37.223191,37.223285,37.223310,37.223349,37.223370,37.223545,37.223618,37.223768,37.223080,37.222841,37.223191', 'FACULTY/STAFF/VISITOR'),
('McComas West', '-80.423114,-80.422942,-80.422851,-80.423039,-80.423114', '37.220530,37.220551,37.219556,37.219547,37.220530', 'FACULTY/STAFF/VISITOR'),
('Greenhouse Lot', '-80.423918,-80.423790,-80.423634,-80.423495,-80.423478,-80.423806,-80.423816,-80.423918', '37.219662,37.219701,37.219594,37.219590,37.219295,37.219278,37.219560,37.219662', 'FACULTY/STAFF/VISITOR'),
('Coliseum Lot', '-80.421590,-80.421563,-80.421515,-80.420517,-80.420528,-80.421440,-80.421494,-80.421370,-80.420898,-80.420587,-80.419761,-80.419815,-80.419868,-80.420056,-80.420249,-80.420190,-80.419965,-80.419815,-80.419739,-80.419670,-80.419627,-80.419616,-80.419621,-80.419632,-80.419734,-80.419756,-80.421569,-80.421590', '37.221525,37.220880,37.220752,37.220790,37.220888,37.220867,37.221499,37.221521,37.221845,37.221546,37.221563,37.221815,37.221939,37.222191,37.222392,37.222431,37.222183,37.221995,37.221811,37.221602,37.221418,37.221307,37.221093,37.221000,37.221025,37.221559,37.221516,37.221525', 'FACULTY/STAFF/VISITOR'),
('Coliseum Lot', '-80.421494,-80.421311,-80.421343,-80.420292,-80.420147,-80.420045,-80.419927,-80.419858,-80.419782,-80.419761,-80.419729,-80.419632,-80.419739,-80.419739,-80.420512,-80.420528,-80.421472,-80.421499,-80.420566,-80.420898,-80.421381,-80.421494', '37.221521,37.221657,37.221692,37.222431,37.222277,37.222166,37.222003,37.221901,37.221721,37.221563,37.221029,37.221008,37.220871,37.220803,37.220777,37.220884,37.220876,37.221499,37.221533,37.221858,37.221529,37.221521', 'COMMUTER/GRADUATE'),
('Vet Med Lot', '-80.426247,-80.426193,-80.427196,-80.428242,-80.429031,-80.429267,-80.429256,-80.429336,-80.429873,-80.430222,-80.430125,-80.429808,-80.429567,-80.429218,-80.428886,-80.428269,-80.427952,-80.427459,-80.427384,-80.427475,-80.426568,-80.426831,-80.427743,-80.427979,-80.428087,-80.428162,-80.427952,-80.428328,-80.428548,-80.428419,-80.428457,-80.428365,-80.428623,-80.428886,-80.429492,-80.429626,-80.429658,-80.429186,-80.429052,-80.428403,-80.427368,-80.426499,-80.426247,-80.426241', '37.216830,37.216685,37.216758,37.216779,37.216779,37.217232,37.217411,37.217488,37.217309,37.218090,37.218295,37.218560,37.218714,37.218885,37.219047,37.219184,37.219064,37.218744,37.218688,37.218454,37.218133,37.217697,37.217954,37.217949,37.218052,37.218223,37.218706,37.218753,37.218684,37.218505,37.218488,37.218330,37.218189,37.218535,37.218236,37.218176,37.218099,37.217488,37.217377,37.217168,37.216856,37.216775,37.216830,37.230345', 'FACULTY/STAFF/VISITOR'),
('Track/Field House Lot', '-80.420051,-80.420051,-80.419890,-80.419858,-80.419836,-80.419536,-80.419536,-80.419493,-80.419632,-80.419621,-80.419761,-80.419621,-80.419525,-80.419364,-80.419364,-80.419589,-80.419772,-80.419793,-80.420051', '37.218300,37.218035,37.217386,37.217411,37.217377,37.217411,37.217471,37.217462,37.218060,37.218266,37.218317,37.218445,37.218505,37.218556,37.218624,37.218539,37.218385,37.218334,37.218300', 'COMMUTER/GRADUATE'),
('Southgate Center Lot', '-80.416714,-80.416489,-80.416231,-80.416006,-80.416349,-80.416714', '37.218206,37.218351,37.218300,37.218206,37.217941,37.218206', 'FACULTY/STAFF/VISITOR'),
('Stadium Lot West', '-80.416478,-80.415448,-80.415319,-80.415201,-80.415287,-80.416467,-80.416478', '37.217642,37.218436,37.218240,37.217625,37.217411,37.217292,37.217642', 'FACULTY/STAFF/VISITOR'),
('Police Lot', '-80.415362,-80.415276,-80.413656,-80.413806,-80.414010,-80.414075,-80.413892,-80.413892,-80.414504,-80.414482,-80.414761,-80.415362', '37.218505,37.218377,37.218821,37.218906,37.218872,37.219009,37.219103,37.219146,37.219017,37.218941,37.218889,37.218505', 'FACULTY/STAFF/VISITOR'),
('Hahn-Hurst Lot', '-80.418506,-80.418339,-80.418366,-80.418565,-80.418656,-80.418538,-80.418490,-80.418296,-80.418441,-80.418828,-80.418763,-80.418908,-80.418849,-80.418720,-80.418516,-80.418495,-80.418618,-80.418506', '37.223746,37.223537,37.223464,37.223375,37.223302,37.223199,37.223221,37.223046,37.222952,37.223281,37.223332,37.223464,37.223507,37.223387,37.223520,37.223584,37.223678,37.223746', 'FACULTY/STAFF/VISITOR'),
('Basketball Ext Lot', '-80.418296,-80.418103,-80.417980,-80.417604,-80.417454,-80.417910,-80.417776,-80.417921,-80.418296', '37.223853,37.223712,37.223802,37.223349,37.223456,37.223883,37.223998,37.224126,37.223853', 'FACULTY/STAFF/VISITOR'),
('Cranwell Center Lot', '-80.416167,-80.415979,-80.415904,-80.415829,-80.415791,-80.415781,-80.415904,-80.415963,-80.416049,-80.416167', '37.223554,37.223584,37.223289,37.223298,37.223131,37.223114,37.223076,37.223199,37.223187,37.223554', 'FACULTY/STAFF/VISITOR');

