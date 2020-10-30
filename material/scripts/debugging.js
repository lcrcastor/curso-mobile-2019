Java.perform(function () {
    console.log("Controlo el modo debug dentro del java");
    var debugging = Java.use("android.os.Debug");
    debugging.isDebuggerConnected.implementation = function() {
        console.log("[*] Se llama al debbuger connected");
        return false;
    }
});