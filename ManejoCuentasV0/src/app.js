const path = require('path');
const express = require('express');
const morgan = require('morgan');
const mongoose = require('mongoose');

const app = express();

//Connecting to the database
mongoose.connect('mongodb://localhost/Proyecto-Antioquia')
	.then(db => console.log('Database connected'))
	.catch(err => console.log(err)); 
//Importing routes
const indexRoutes = require('./routes/index');

//Settings
app.set('port', process.env.PORT || 3000); //Para que escoja el puerto del sistema o si no el 3000
app.set('views', path.join(__dirname, 'views')); //Especificar al servidor donde esta la carpeta view
app.set('view engine', 'ejs');

//middleweares (funciones que se ejecutan antes de llegar a las rutas)
app.use(morgan('dev')); //Imprime las peticiones y el tiempo de respuesta
app.use(express.urlencoded({extended: false}));

//Routes
app.use('/', indexRoutes);
//Starting the server

app.listen(app.get('port'), () => {
	console.log(`Server on port: ${app.get('port')}`);
});