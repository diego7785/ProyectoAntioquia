var user = document.getElementById("user").value;
var password = document.getElementById("password").value;

function ingresar(){
	if(user.value == 0 || password.value == 0){
		alert("Datos incompletos");
	}else{
		//alert("Validando datos");
		var pg = require('pg');
		var conString = "postgres://postgres:"+password+"@localhost:5432/"+user;
		var client = new pg.Client(conString);
		client.connect();
		var query = client.query("SELECT * FROM usuarios;");
		query.on('row', function(row) {
    		console.log(row);
		});
	}
}