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
  id_article int primary key auto_increment,
  conference BIGINT(11) NOT NULL,
  name VARCHAR(150) not null,
  abstract text not null,
  number_pages TINYINT not null,
  insertion_date TIMESTAMP DEFAULT current_timestamp not null,
  CONSTRAINT pk_article PRIMARY KEY (id_article),
  CONSTRAINT uk_name UNIQUE KEY (name),
  CONSTRAINT fk_conference FOREIGN KEY (conference) REFERENCES conferences(id_conference) on DELETE  CASCADE
);

CREATE TABLE authors(
  id_author int PRIMARY KEY auto_increment,
  name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  institut VARCHAR(50) DEFAULT null,
  university VARCHAR(50) DEFAULT null,
  city VARCHAR(40) DEFAULT NULL,
  state VARCHAR(40) DEFAULT NULL,
  CONSTRAINT pk_author PRIMARY KEY (id_author),
  CONSTRAINT uk_name UNIQUE KEY (name, last_name)
);

CREATE TABLE authors_articles(
  id_author int NOT NULL,
  id_article int NOT NULL,
  CONSTRAINT pk_author_article PRIMARY KEY (id_author, id_article),
  CONSTRAINT fk_author FOREIGN KEY (id_author) REFERENCES authors(id_author) ON DELETE  CASCADE,
  CONSTRAINT fk_article FOREIGN KEY (id_article) REFERENCES articles(id_article) ON DELETE CASCADE
);

CREATE TABLE users(
  id_user int(5) PRIMARY KEY auto_increment,
  name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  email VARCHAR(80) NOT NULL,
  password VARCHAR(60) NOT NULL,
  registration_date TIMESTAMP DEFAULT current_timestamp NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id_user)
);