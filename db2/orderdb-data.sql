INSERT INTO CUSTOMER (CUSTOMER_ID,NAME,USERNAME,TYPE,BUSINESS_VOLUME_DISCOUNT,BUSINESS_PARTNER,BUSINESS_DESCRIPTION,addressLine1,addressLine2,city,country,state,zip)
VALUES (3,'Kyle Brown','kbrown','BUSINESS','Y','Y','Bulk buyer','500 Miami Bld','','Raleigh','USA','NC','21233');       

INSERT INTO CUSTOMER (CUSTOMER_ID,NAME,USERNAME,TYPE,RESIDENTIAL_HOUSEHOLD_SIZE,RESIDENTIAL_FREQUENT_CUSTOMER,addressLine1,addressLine2,city,country,state,zip)
VALUES (2,'Roland Barcia','rbarcia','RESIDENTIAL',6,'Y','222 2rd Street','Apt 2','Leonia','USA','NJ','07605');   

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (1,'Entertainment',null);

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (2,'Movies',1);

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (3,'Music',1);

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (4,'Games',1);

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (10,'Electronics',null);

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (12,'TV',10);

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (13,'Cellphones',10);

INSERT INTO CATEGORY (CAT_ID, CAT_NAME, PARENT_CAT)
VALUES (14,'DVD Players',10);

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (1,29.99,'Return of the Jedi','Episode 6, Luke has the final confrontation with his father!','images/Return.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (2,29.99,'Empire Strikes Back','Episode 5, Luke finds out a secret that will change his destiny','images/Empire.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (3,29.99,'New Hope','Episode 4, after years of oppression, a band of rebels fight for freedom','images/NewHope.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (10,100.00,'DVD Player','This Sony Player has crystal clear picture','images/Player.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (20,200.00,'BlackBerry Curve','This BlackBerry offers rich PDA functions and works with Notes.','images/BlackBerry.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (21,150.00,'Sony Ericsson','This Sony phone takes pictures and plays Music.','images/SonyPhone.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (30,1800.00,'Sony Bravia','This is a 40 inch 1080p LCD HDTV','images/SonyTV.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (31,1150.00,'Sharp Aquos','This is 32 inch 1080p LCD HDTV','images/SamTV.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (40,20.00,'Go Fish: Superstar','Go Fish release their great new hit, Superstar','images/Superstar.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (41,20.00,'Ludwig van Beethoven','This is a classic, the 9 Symphonies Box Set','images/Bet.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (51,399.99,'PlayStation 3','race yourself for the marvels of the PlayStation 3 complete with 80GB hard disk storage','images/PS3.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (52,169.99,'Nintendo Wii','Next-generation gaming with a motion-sensitive controller','images/wii.jpg');

INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
VALUES (53,299.99,'Return of the Jedi','Episode 6, Luke has the final confrontation with his father!','images/xbox360.jpg');

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (1,2,1);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (2,2,2);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (3,2,3);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (10,2,5);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (20,13,6);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (21,13,7);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (30,12,8);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (31,12,9);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (40,3,10);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (41,3,11);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (51,4,21);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (52,4,22);

INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
VALUES (53,4,23);

INSERT INTO CUSTOMER 
            (CUSTOMER_ID,
             NAME,
             USERNAME,
             TYPE,
             BUSINESS_VOLUME_DISCOUNT,
             BUSINESS_PARTNER,
             BUSINESS_DESCRIPTION, 
             addressLine1,
             addressLine2,
             city,
             country,
             state,
             zip)
VALUES  (1001,
             'David VandePol',
             'dvandepol',
             'BUSINESS',
             'Y',
             'Y',
             'description',
             'street',
             'streetcont',
             'city',
             'country',
             'state',
             '90210');

INSERT INTO CUSTOMER 
            (CUSTOMER_ID,
             NAME,
             USERNAME,
             TYPE,
             RESIDENTIAL_HOUSEHOLD_SIZE,
             RESIDENTIAL_FREQUENT_CUSTOMER,
             addressLine1,
             addressLine2,
             city,
             country,
             state,
             zip)
VALUES 
            (1002,
             'Dave Mulley',
             'dmulley',
             'RESIDENTIAL',
             '5',
             'Y',
             'street',
             'streetcont',
             'city',
             'country',
             'state',
             '90210');
