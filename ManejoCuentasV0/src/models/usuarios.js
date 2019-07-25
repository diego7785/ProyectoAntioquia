const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const usersSchema = new Schema({ //Similar a crear una tabla en SQL
	nombre: String,
	cedula: String,
	celular: String,
	saldo: Number,
	fecha: Date
});

module.exports = mongoose.model('Usuarios', usersSchema); //Permite exportar "la tabla" para su uso en otro código
