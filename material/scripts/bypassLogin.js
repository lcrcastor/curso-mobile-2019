Java.perform(function (){
	console.log('');
	console.log('[*] Iniciando el bypass del login');
	console.log('[*] Presionen el boton "Sign in"');
	var Activity = Java.use('com.mwr.example.sieve.MainLoginActivity');
	Activity.checkKeyResult.implementation = function(bypass) {
	send('checkKeyResult');
	console.log('[*] Cambiamos el valor dentro de checkKeyResult')
	this.checkKeyResult(true);
	console.log('[*] ¡Funciono el bypass!');
	}
});
