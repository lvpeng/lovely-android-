var Bridge = {};

Bridge.login = function(state){
    // build json string

    var message = {method: 'Login', state:state}

    prompt("bridgeKey", JSON.stringify(message));
}