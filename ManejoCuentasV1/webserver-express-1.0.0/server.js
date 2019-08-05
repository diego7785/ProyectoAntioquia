const express = require('express');
const mongoose = require('mongoose');
const path = require('path');

const app = express();
const Useri = require('.../../models/usuarios.js');

const hbs = require('hbs');
require('./hbs/helpers');

//Connecting to the database
mongoose.connect('mongodb://localhost/Proyecto-Antioquia')
	.then(db => console.log('Database connected'))
	.catch(err => console.log(err)); 

const port = process.env.PORT || 4000;

app.set('views', path.join(__dirname, 'views')); //Especificar al servidor donde esta la carpeta view
app.use(express.static(__dirname + '/public'));

// Express HBS engine
hbs.registerPartials(__dirname + '/views/parciales');
app.set('view engine', 'ejs');


app.get('/', (req, res) => {

    res.render('home');
});

app.get('/about', async(req, res) => {
	const useris = await Useri.find(); //Async y await porque debe consultar en la base y esperar por el resultado

	res.render('about', {
		useris
	});
});


app.post('/add', async (req, res) => {
	
	const useri = new Useri(req.body);
	await useri.save();
	res.redirect('/'); //Para redireccionar la página a ella misma (recargar)(/: Ruta inicial del servidor)
});

app.get('/delete/:id', async (req, res) => {
	const {id} = req.params;
	await Useri.remove({_id: id});
	res.redirect('/');
});


app.listen(port, () => {
    console.log(`Escuchando peticiones en el puerto ${ port }`);
});