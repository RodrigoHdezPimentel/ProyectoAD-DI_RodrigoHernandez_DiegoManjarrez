DROP DATABASE IF EXISTS FORO;
CREATE DATABASE FORO;
USE FORO;
DROP TABLE IF EXISTS Usuarios;
DROP TABLE IF EXISTS Likes;
DROP TABLE IF EXISTS Publicaciones;
DROP TABLE IF EXISTS Temas;


CREATE TABLE Usuarios(
	idUsuario INTEGER PRIMARY KEY AUTO_INCREMENT,
    anioNacimiento INTEGER,
	Us_Nombre VARCHAR(20),
    Us_Genero boolean,
    Us_Descripcion VARCHAR(200),
    Us_Mail VARCHAR(40),
    Us_Contrasena VARCHAR(20),
    foto VARCHAR(100),
    fechabaja datetime
);
CREATE TABLE Grupos(
	idGrupo INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    foto varchar(100),
    codigo varchar(10)
);
CREATE TABLE Grupo_Usuario(
	idGrupoUsuario INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	idGrupo INTEGER NOT NULL,
    idUsuario INTEGER NOT NULL,
	nombre varchar(15),
    fechabaja datetime,
    
	FOREIGN KEY (idUsuario)
		REFERENCES Usuarios (idUsuario)
		ON UPDATE CASCADE
    	ON DELETE CASCADE,
	FOREIGN KEY (idGrupo)
		REFERENCES Grupos (idGrupo)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);

CREATE TABLE Conversaciones(
	idConversacion INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	idGrupoUsuario INTEGER NOT NULL,
    fecha DATETIME,
    contenido VARCHAR(300),
   
	FOREIGN KEY (idGrupoUsuario)
		REFERENCES Grupo_Usuario (idGrupoUsuario)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);


CREATE TABLE Temas(
	idTema INTEGER PRIMARY KEY AUTO_INCREMENT,
    Titulo VARCHAR(35),
    edadMinima INTEGER
);



CREATE TABLE Publicaciones(
	idPublicacion INTEGER PRIMARY KEY AUTO_INCREMENT,
    idUsuario INTEGER NOT NULL,
    idTema INTEGER NOT NULL,
    idPubliRefer INTEGER,
    fecha DATE,
    numLikes INTEGER,
    titulo VARCHAR(30),
    contenido VARCHAR(700),
	FOREIGN KEY (idPubliRefer)
		REFERENCES Publicaciones (idPublicacion)
		ON UPDATE CASCADE
    	ON DELETE CASCADE,
	FOREIGN KEY (idTema)
		REFERENCES Temas (idTema)
		ON UPDATE CASCADE
    	ON DELETE CASCADE,
	FOREIGN KEY (idUsuario)
		REFERENCES Usuarios (idUsuario)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);


CREATE TABLE Likes(
	idLike INTEGER PRIMARY KEY AUTO_INCREMENT,
	idPublicacion INTEGER NOT NULL,
	idUsuario INTEGER NOT NULL,
    
	FOREIGN KEY (idPublicacion)
		REFERENCES Publicaciones (idPublicacion)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	FOREIGN KEY (idUsuario)
		REFERENCES Usuarios (idUsuario)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);


CREATE TABLE Usuario_Tema(
    idUsuario INTEGER NOT NULL,
	idTema INTEGER NOT NULL,
	FOREIGN KEY (idTema)
		REFERENCES Temas (idTema)
		ON UPDATE CASCADE
    	ON DELETE CASCADE,
	FOREIGN KEY (idUsuario)
		REFERENCES Usuarios (idUsuario)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);
