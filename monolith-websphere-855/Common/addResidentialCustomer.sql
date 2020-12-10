---------------------------------------------------------------------
-- SQL file to insert a new residential customer into the ORDER DB --
---------------------------------------------------------------------

---------------------------------------
-- Description of the CUSTOMER table --
---------------------------------------

-- CUSTOMER_ID INTEGER NOT NULL ,
-- USERNAME VARCHAR(30) NOT NULL,
-- OPEN_ORDER INTEGER , 
-- NAME VARCHAR(30) NOT NULL , 
-- BUSINESS_VOLUME_DISCOUNT CHAR(1) DEFAULT 'N', 
-- BUSINESS_PARTNER CHAR(1) DEFAULT 'N', 
-- BUSINESS_DESCRIPTION CLOB(12582912) , 
-- RESIDENTIAL_HOUSEHOLD_SIZE SMALLINT , 
-- RESIDENTIAL_FREQUENT_CUSTOMER CHAR(1) DEFAULT 'Y',
-- TYPE VARCHAR(11) NOT NULL,
-- ADDRESSLINE1 VARCHAR(50),
-- ADDRESSLINE2 VARCHAR(50),
-- CITY VARCHAR(50),
-- COUNTRY VARCHAR(50),
-- STATE VARCHAR(30),
-- ZIP VARCHAR (10)

-- Example

-- INSERT INTO CUSTOMER (CUSTOMER_ID,NAME,USERNAME,TYPE,RESIDENTIAL_HOUSEHOLD_SIZE,RESIDENTIAL_FREQUENT_CUSTOMER,addressLine1,addressLine2,city,country,state,zip)
-- VALUES (2,'Test User','tuser','RESIDENTIAL',6,'Y','222 3rd Street','Apt 3 West','New York','USA','NJ','22344');   

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
            ('An integer',
             'A name',
             'A unique username',
             'RESIDENTIAL',
             'An integer',
             'Y or N',
             'some street',
             'some street cont',
             'some city',
             'some country',
             'some state',
             'A zip code');