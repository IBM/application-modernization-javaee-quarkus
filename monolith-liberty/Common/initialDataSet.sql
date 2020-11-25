INSERT INTO CUSTOMER (CUSTOMER_ID, NAME, USERNAME, TYPE, RESIDENTIAL_HOUSEHOLD_SIZE, RESIDENTIAL_FREQUENT_CUSTOMER, ADDRESSLINE1, ADDRESSLINE2, CITY, COUNTRY, STATE, ZIP)
	VALUES ('2', 'Roland Barcia', 'rbarcia', 'RESIDENTIAL', '6', 'Y', '222 2nd street', 'Apt 2', 'Leonia', 'USA', 'NJ', '07605');

INSERT INTO CUSTOMER (CUSTOMER_ID, NAME, USERNAME, TYPE, BUSINESS_VOLUME_DISCOUNT, BUSINESS_PARTNER, BUSINESS_DESCRIPTION, ADDRESSLINE1, CITY, COUNTRY, STATE, ZIP)
	VALUES ('3', 'Kyle Brown', 'kbrown', 'BUSINESS', 'Y', 'Y', 'Bulk buyer', '500 Miami Bld', 'Raleigh', 'USA', 'NC', '21233');


INSERT INTO CATEGORY (CAT_NAME, CAT_ID)
	VALUES ('Entertainment', '1');

INSERT INTO CATEGORY (PARENT_CAT, CAT_NAME, CAT_ID)
	VALUES ('1', 'Movies', '2');

INSERT INTO CATEGORY (PARENT_CAT, CAT_NAME, CAT_ID)
	VALUES ('1', 'Music', '3');

INSERT INTO CATEGORY (PARENT_CAT, CAT_NAME, CAT_ID)
	VALUES ('1', 'Games', '4');

INSERT INTO CATEGORY (CAT_NAME, CAT_ID)
	VALUES ('Electronics','10');

INSERT INTO CATEGORY (CAT_NAME, CAT_ID, PARENT_CAT)
	VALUES ('TV','12','10');

INSERT INTO CATEGORY (CAT_NAME, CAT_ID, PARENT_CAT)
	VALUES ('Cellphones','13','10');

INSERT INTO CATEGORY (CAT_NAME, CAT_ID, PARENT_CAT)
	VALUES ('DVD Players','14','10');


INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('1', '29.99', 'Return of the Jedi', 'Episode 6, Luke has the final confrontation with his father!', 'images/Return.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('2', '29.99', 'Empire Strikes Back', 'Episode 5, Luke finds out a secret that will change his destiny', 'images/Empire.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('3', '29.99', 'New Hope', 'Episode 4, after years of oppression, a band of rebels fight for freedom', 'images/NewHope.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('10', '100.00', 'DVD Player', 'This Sony Player has crystal clear picture', 'images/Player.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('20', '200.00', 'BlackBerry Curve', 'This BlackBerry offers rich PDA functions and works with Notes.', 'images/BlackBerry.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('21', '150.00', 'Sony Ericsson', 'This Sony phone takes pictures and plays Music.', 'images/SonyPhone.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('30', '1800.00', 'Sony Bravia', 'This is a 40 inch 1080p LCD HDTV', 'images/SonyTV.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('31', '1150.00', 'Sharp Aquos', 'This is 32 inch 1080p LCD HDTV', 'images/SamTV.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('40', '20.00', 'Go Fish: Superstar', 'Go Fish release their great new hit, Superstar', 'images/Superstar.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('41', '20.00', 'Ludwig van Beethoven', 'This is a classic, the 9 Symphonies Box Set', 'images/Bet.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('51', '399.99', 'PlayStation 3', 'Brace yourself for the marvels of the PlayStation 3 complete with 80GB hard disk storage', 'images/PS3.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('52', '169.99', 'Nintendo Wii', 'Next-generation gaming with a motion-sensitive controller', 'images/wii.jpg');
INSERT INTO PRODUCT (PRODUCT_ID, PRICE, NAME, DESCRIPTION, IMAGE)
	VALUES ('53', '299.99', 'XBOX 360', 'Expand your horizons with the gaming and multimedia capabilities of this incredible system', 'images/xbox360.jpg');


INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
	VALUES (1,2,1);
INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
	VALUES (2,2,2);
INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
	VALUES (3,2,3);
INSERT INTO PROD_CAT (PRODUCT_ID, CAT_ID, PC_ID)
	VALUES (10,14,4);
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
