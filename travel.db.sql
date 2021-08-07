
CREATE TABLE IF NOT EXISTS `users` (
	`userId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	TEXT NOT NULL,
	`username`	TEXT NOT NULL,
	`password`	TEXT NOT NULL,
	`mobile`	TEXT NOT NULL,
	`email`	TEXT NOT NULL,
	`userDate`	TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS `package` (
	`packId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`place`	TEXT NOT NULL UNIQUE,
	`pDetails`	TEXT NOT NULL,
	`noAdult`	INTEGER NOT NULL,
	`noChild`	INTEGER NOT NULL,
	`stayFee`	INTEGER NOT NULL,
	`foodFee`	INTEGER NOT NULL,
	`busFee`	INTEGER NOT NULL,
	`trainFee`	INTEGER NOT NULL,
	`airlinesFee`	INTEGER NOT NULL,
	`noDays`	INTEGER NOT NULL,
	`noNights`	INTEGER NOT NULL,
	`pImage`	BLOB NOT NULL
);
CREATE TABLE IF NOT EXISTS `booked` (
	`packId`	INTEGER NOT NULL,
	`userId`	INTEGER NOT NULL,
	`foodFee`	INTEGER NOT NULL,
	`travelMode`	TEXT NOT NULL,
	`travelFee`	INTEGER NOT NULL,
	`travelDate`	text NOT NULL,
	`paid`	TEXT NOT NULL DEFAULT No,
	FOREIGN KEY(`userId`) REFERENCES `users`(`userId`) on delete cascade on update cascade
);



