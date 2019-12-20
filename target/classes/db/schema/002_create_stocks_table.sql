--liquibase formatted sql

--changeset scholanova:1

CREATE TABLE IF NOT EXISTS STOCKS (
  ID                  SERIAL          NOT NULL,
  NAME                VARCHAR(255)    NOT NULL,
  TYPE				  VARCHAR(255),
  VALUE				  INT,
  IDSTORE			  INT,
  PRIMARY KEY (ID),
  FOREIGN KEY (IDSTORE) REFERENCES STORES(ID)
);
