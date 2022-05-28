(function (window) {
    //console.log("hivejsapi://{'method':'hello','params':{'test':'world'}}");
    //console.log("hivejsapi://hive:12345534434/method?params={'test':'world'}");
    //const token_map = new Map();
    //function call(namespace,method,params){
    //    var timestamp=new Date().getTime();
    //    var token = namespace+"_"+method+"_"+timestamp;
    //    token_map.set(token,callback);
    //    console.log("hivejsapi://"+namespace+":"+token+"/"+method+"?"+params);
    //}
    //
    //function callback(token,success,failed){
    //    console.log(token);
    //}
    //console.log("hivejsapi://SystemInfo:token_123455dsdssss34434/getOS?params={'test':'world'}")
    //call('SystemInfo','getOS','paramstest');

    window.hivejsapi = {
        callbackMap: {},
        call: function ({ namespace, method, params, success, failed, timeout = 15000 }) {
            window.hivejsapi.innerCall(namespace, method, params, success, failed, timeout)
        },
        innerCall: function (namespace, method, params,success, failed, timeout) {
            var timestamp = new Date().getTime();
            var token = namespace + "_" + method + "_" + timestamp;
            if (timeout > 0) {
                window.hivejsapi.callbackMap[token] = function (_success, _failed) {
                    console.log(`${JSON.stringify(_success)} ${JSON.stringify(_failed)}`);
                    success(_success);
                    failed(_failed);
                };
//                console.log(` timeout > 0 innerCall:namespace=${namespace},method=${method},params=${params},timeout=${timeout},token=${token}`)
            } else {
                window.hivejsapi.callbackMap[token] = function (_success, _failed) {
                    console.log(`${JSON.stringify(_success)} ${JSON.stringify(_failed)}`);
                    success(_success);
                    failed(_failed);
//                console.log(` timeout <= 0 innerCall:namespace=${namespace},method=${method},params=${params},timeout=${timeout},token=${token}`)

                };
            }
            console.log("hivejsapi://" + namespace + ":" + token + "/" + method + "?" + params);
//            console.log(`return: token=${token},${window.hivejsapi.callback[token]}`);
            // window.hivejsapi.callback(token,{"success":"I'm success"},{"failed":"I'm failed"});
            return token;
        },

        callback: function (token, success, failed) {
            console.log(`==callback===: token=${token},${window.hivejsapi.callback[token]}`);
            if (window.hivejsapi.callbackMap[token]) {
//                console.log(`==callback===: token=${token},${window.hivejsapi.callbackMap[token]}`);
                (window.hivejsapi.callbackMap[token](success,failed));
            } else {
//                console.log("==callback=== function null");
            }
        }
    }
    //hivejsapi.call('SystemInfo','getOS','paramstest');
})(window);