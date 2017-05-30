CREATE TABLE IF NOT EXISTS Tweet(
	ID SERIAL,
	anzahl_geteilt int,
	anzahl_like int,
	text VARCHAR,
	Datum date,
	Benutzer VARCHAR,
	Uhrzeit time,
	CONSTRAINT tweet_pkey PRIMARY KEY (ID)
	)

CREATE TABLE IF NOT EXISTS Hashtag(
	text VARCHAR,
	CONSTRAINT hashtag_pkey PRIMARY KEY (text)
	)

CREATE TABLE IF NOT EXISTS Tweet_Hashtag (
	tweet_id SERIAL REFERENCES  Tweet(ID) ON UPDATE CASCADE,
	Hashtag_text VARCHAR REFERENCES Hashtag(text) ON UPDATE CASCADE,
	CONSTRAINT tweet_hashtag_pkey PRIMARY KEY (tweet_id, Hashtag_text)
	)
