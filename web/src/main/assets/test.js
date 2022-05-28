//window.hivejsapi.innerCall('SystemInfo','getOS','{}');
var token = window.hivejsapi.call({
    namespace:'SystemInfo',
    method:'getOS',
    params:'{}',
    success:function(res){
        console.log(`window.hivejsapi.call: ${JSON.stringify(res)}`);
    },
    failed:function(err){
        console.log(`window.hivejsapi.call: ${JSON.stringify(err)}`);
    }
});
//console.log(token);
//window.hivejsapi.callback(token,{"success":"I'm success"},{"failed":"I'm failed"});
// window.hivejsapi.callback("SystemInfo_getOS_1653748814048",{"success":"I'm success"},{"failed":"I'm failed"});