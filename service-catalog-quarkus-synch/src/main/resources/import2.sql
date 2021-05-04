INSERT INTO category (id, name) VALUES (1, 'Entertainment');
INSERT INTO category (id, name, parent) VALUES (2, 'Movies', '1');
INSERT INTO category (id, name, parent) VALUES (3, 'Music', '1');
INSERT INTO category (id, name, parent) VALUES (4, 'Games', '1');
INSERT INTO category (id, name) VALUES (10, 'Electronics');
INSERT INTO category (id, name, parent) VALUES (12, 'TV', '10');
INSERT INTO category (id, name, parent) VALUES (13, 'Cellphones', '10');
INSERT INTO category (id, name, parent) VALUES (14, 'DVD Players', '10');

INSERT INTO product (id, price, name, description, image) VALUES (1, 29.99, 'Return of the Jedi', 'Episode 6, Luke has the final confrontation with his father!', 'images/Return.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (2, '29.99', 'Empire Strikes Back', 'Episode 5, Luke finds out a secret that will change his destiny', 'images/Empire.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (3, '29.99', 'New Hope', 'Episode 4, after years of oppression, a band of rebels fight for freedom', 'images/NewHope.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (10, '100.00', 'DVD Player', 'This Sony Player has crystal clear picture', 'images/Player.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (20, '200.00', 'BlackBerry Curve', 'This BlackBerry offers rich PDA functions and works with Notes.', 'images/BlackBerry.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (21, '150.00', 'Sony Ericsson', 'This Sony phone takes pictures and plays Music.', 'images/SonyPhone.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (30, '1800.00', 'Sony Bravia', 'This is a 40 inch 1080p LCD HDTV', 'images/SonyTV.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (31, '1150.00', 'Sharp Aquos', 'This is 32 inch 1080p LCD HDTV', 'images/SamTV.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (40, '20.00', 'Go Fish: Superstar', 'Go Fish release their great new hit, Superstar', 'images/Superstar.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (41, '20.00', 'Ludwig van Beethoven', 'This is a classic, the 9 Symphonies Box Set', 'images/Bet.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (51, '399.99', 'PlayStation 3', 'Brace yourself for the marvels of the PlayStation 3 complete with 80GB hard disk storage', 'images/PS3.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (52, '169.99', 'Nintendo Wii', 'Next-generation gaming with a motion-sensitive controller', 'images/wii.jpg');
INSERT INTO product (id, price, name, description, image)
	VALUES (53, '299.99', 'XBOX 360', 'Expand your horizons with the gaming and multimedia capabilities of this incredible system', 'images/xbox360.jpg');

INSERT INTO productcategory (id, productid, categoryid)
	VALUES (1,1,2);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (2,2,2);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (3,3,2);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (4,10,14);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (5,10,2);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (6,20,13);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (7,21,13);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (8,30,12);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (9,31,12);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (10,40,3);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (11,41,3);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (21,51,4);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (22,52,4);
INSERT INTO productcategory (id, productid, categoryid)
	VALUES (23,53,4);

