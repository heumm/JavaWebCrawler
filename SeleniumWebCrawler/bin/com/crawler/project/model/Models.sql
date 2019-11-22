CREATE DATABASE crawler_db default CHARACTER SET UTF8MB3;

CREATE USER 'crawleruser'@'localhost' IDENTIFIED BY 'crawler';


create table com_hotels(
    hotel_name varchar(50) NOT NULL,  
	hotel_grade varchar(16) NOT NULL,    
	hotel_location varchar(50) ,  
	hotel_price varchar(50) ,  
    hotel_price_discounted varchar(50) ,  
	PRIMARY KEY (hotel_name)   
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    
create table com_hotels_review(
	hotel_name varchar(50) NOT NULL, 
    review_num int unsigned NOT NULL, 
    review_rate double, 
    review_content varchar(3000), 
    review_date date, 
    PRIMARY KEY (hotel_name, review_num) , 
    FOREIGN KEY (hotel_name) REFERENCES com_hotels (hotel_name)    
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


alter database crawler_db default character set = utf8mb4 COLLATE = utf8mb4_general_ci;


ALTER TABLE com_hotels CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE com_hotels_review CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


SHOW FULL COLUMNS FROM com_hotels_review;