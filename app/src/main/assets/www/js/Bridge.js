var Bridge = {};

Bridge.login = function(state){
    // build json string

    var message = {method: 'login', state:state}

    prompt("bridgeKey", JSON.stringify(message));
}

Bridge.callBack = function(result){
    if (result.success){
        alert(result.message);
    }
}