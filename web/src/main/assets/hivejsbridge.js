(function(){
//console.log("hivejsapi://{'method':'hello','params':{'test':'world'}}");
//console.log("hivejsapi://hive:12345534434/method?params={'test':'world'}");
const token_map = new Map();
function call(namespace,method,params){
    var timestamp=new Date().getTime();
    var token = namespace+"_"+method+"_"+timestamp;
    token_map.set(token,callback);
    console.log("hivejsapi://"+namespace+":"+token+"/"+method+"?"+params);
}

function callback(token,success,failed){
    console.log(token);
}
//console.log("hivejsapi://SystemInfo:token_123455dsdssss34434/getOS?params={'test':'world'}")
call('SystemInfo','getOS','paramstest');
})();