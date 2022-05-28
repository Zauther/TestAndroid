package io.github.zauther.test.android.func;

import android.content.Context;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WX {
    public static final String WECHAT_APP_ID = "wx88888888";
    public static void req(Context context, BaseReq req){
        IWXAPI api = WXAPIFactory.createWXAPI(context, WECHAT_APP_ID, true);
        api.registerApp(WECHAT_APP_ID);
        api.sendReq(req);
    }
}
