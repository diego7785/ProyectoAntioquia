const express = require('express');
const router = express.Router();

const Useri = require('../models/usuarios');

//Esta función hace consultas en la base de datos
router.get('/', async (req, res) => { //Atiende las peticiones del cliente
	const useris = await Useri.find(); //Async y await porque debe consultar en la base y esperar por el resultado
	console.log(useris);
	res.render('index', {
		useris
	});
});

router.post('/add', async (req, res) => {
	
	const useri = new Useri(req.body);
	await useri.save();
	res.redirect('/'); //Para redireccionar la página a ella misma (recargar)(/: Ruta inicial del servidor)
});

router.get('/delete/:id', async (req, res) => {
	const {id} = req.params;
	await Useri.remove({_id: id});
	res.redirect('/');
})

module.exports = router;