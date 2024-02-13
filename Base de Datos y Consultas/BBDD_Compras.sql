DROP DATABASE IF EXISTS Compras;

CREATE DATABASE Compras;

USE Compras;

DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS tarjetas;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS pedido_producto;

CREATE TABLE productos (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) UNIQUE,
    alias VARCHAR(30),
    descripcion VARCHAR(15),
    stock INTEGER,
    precio FLOAT
);

CREATE TABLE clientes (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30) UNIQUE,
    mail VARCHAR(30),
    pasword VARCHAR(20),
    telefono VARCHAR(10)
);

CREATE TABLE viviendas (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    idCliente INTEGER NOT NULL,
	codigoPostal INTEGER(3),
    municipio VARCHAR(30),
    via VARCHAR(15),
    numero INTEGER(4),
    portal CHARACTER,
    piso INTEGER(2),
    letra CHARACTER,
	FOREIGN KEY (idCliente)
		REFERENCES clientes (id)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);

CREATE TABLE tarjetas(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    idCliente INTEGER NOT NULL,
    CVV INTEGER(3),
    numero INTEGER(16),
    propietario VARCHAR(30),
	FOREIGN KEY (idCliente)
		REFERENCES clientes (id)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);

CREATE TABLE pedidos (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    idTarjeta INTEGER NOT NULL,
    idVivienda INTEGER NOT NULL,
    idCliente INTEGER NOT NULL,
    incidencia VARCHAR(200),
    fechaRealizacion VARCHAR(10),
    fechaEntrega VARCHAR(10),
    estado VARCHAR(20),
    CHECK ( estado = 'Entregado' 
		or  estado = 'En reparto' 
		or  estado = 'Preparandose' 
		or estado = 'Incidencia'),
	FOREIGN KEY (idTarjeta)
		REFERENCES tarjetas (id)
		ON UPDATE CASCADE
    	ON DELETE CASCADE,
	FOREIGN KEY (idVivienda)
		REFERENCES viviendas (id)
		ON UPDATE CASCADE
    	ON DELETE CASCADE,
	FOREIGN KEY (idCliente)
		REFERENCES clientes (id)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);

CREATE TABLE pedido_producto(
	idProducto INTEGER,
    idPedido INTEGER,
    cantidad INTEGER,
	FOREIGN KEY (idPedido)
		REFERENCES pedidos (id)
		ON UPDATE CASCADE
    	ON DELETE CASCADE,
	FOREIGN KEY (idProducto)
		REFERENCES productos (id)
		ON UPDATE CASCADE
    	ON DELETE CASCADE
);