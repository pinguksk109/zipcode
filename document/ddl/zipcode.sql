USE training;

CREATE TABLE IF NOT EXISTS zipcode (
	uuid varchar(36) NOT NULL PRIMARY KEY,
	zipcode varchar(8) NOT NULL,
	address varchar(50) NOT NULL,
	kana varchar(50) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);