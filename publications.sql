create table conferences(
  id_conference BIGINT(11) not null,
  name varchar(150) not null,
  theme varchar(150) not null,
  month TINYINT(2) not null,
  year YEAR(4) not null,
  building VARCHAR(30) not null,
  city VARCHAR(30) NOT NULL,
  state VARCHAR(30) NOT NULL,
  CONSTRAINT pk_conference PRIMARY KEY(id_conference),
  CONSTRAINT uk_name UNIQUE KEY (name, month, year)
);


CREATE TABLE articles(
  id_article int auto_increment,
  conference BIGINT(11) NOT NULL,
  name VARCHAR(150) not null,
  abstract text not null,
  number_pages TINYINT not null,
  insertion_date TIMESTAMP DEFAULT current_timestamp not null,
  CONSTRAINT pk_article PRIMARY KEY (id_article, conference),
  CONSTRAINT uk_name UNIQUE KEY (conference, name),
  CONSTRAINT fk_conference FOREIGN KEY (conference) REFERENCES conferences(id_conference) on DELETE  CASCADE
);

CREATE table article_authors(
  id_article int not null,
  name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  institut VARCHAR(50) DEFAULT null,
  university VARCHAR(50) DEFAULT null,
  city VARCHAR(40) DEFAULT NULL,
  state VARCHAR(40) DEFAULT NULL,
  CONSTRAINT pk_article_authors PRIMARY KEY (id_article, name, last_name),
  CONSTRAINT fk_article FOREIGN KEY (id_article) REFERENCES articles(id_article) on DELETE CASCADE
);

CREATE TABLE users(
  id_user int(5) auto_increment,
  name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  email VARCHAR(80) NOT NULL,
  password VARCHAR(60) NOT NULL,
  registration_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id_user)
);

ALTER TABLE conferences ADD FULLTEXT conference
(name, theme, building, city, state);

ALTER TABLE articles ADD FULLTEXT search_articles
(name, abstract);

ALTER TABLE article_authors ADD FULLTEXT authors
(name, last_name, institut, university, city, state);

create VIEW conference_articles as
  SELECT c.name as conference, a.name as article, CONCAT_WS(' ', aa.name, aa.last_name) as author
  from article_authors as aa
  join articles as a ON a.id_article = aa.id_article
  join conferences as c on a.conference = c.id_conference;