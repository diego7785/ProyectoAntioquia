CREATE TABLE tarjeta{
	numero INT NOT NULL,
	idUsuario INT NOT NULL,
	nombreUsuario VARCHAR(50) NOT NULL,
	direccion VARCHAR(60) NOT NULL,
	saldo INT NOT NULL,
}

CREATE TABLE bus 
{
	placa VARCHAR(6) NOT NULL,
	modelo INT NOT NULL,
	marca VARCHAR(30)NOT NULL,
	tipo VARCHAR(20) NOT NULL,
	capacidad INT NOT NULL,

}

CREATE TABLE ruta
{
	
	codigo INT NOT NULL,
	origenDestino VARCHAR(50) NOT NULL,
	tipo INT NOT NULL,
}

INSERT INTO tarjeta (numero, idUsuario, nombreUsuario, direccion) VALUES (1,1122334455,'Jejeje','Carrera 0');