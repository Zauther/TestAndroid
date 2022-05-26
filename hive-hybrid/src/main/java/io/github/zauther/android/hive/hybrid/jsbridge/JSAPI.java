package io.github.zauther.android.hive.hybrid.jsbridge;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class JSAPI implements Serializable {
    public final String namespace;
    public final String token;
    public final String method;
    public final String params;

    public JSAPI(String namespace,  String token,String method, String params) {
        this.namespace = namespace;
        this.method = method;
        this.token = token;
        this.params = params;
    }

    @Override
    public String toString() {
        return "JSAPI{" +
                "namespace='" + namespace + '\'' +
                ", method='" + method + '\'' +
                ", token='" + token + '\'' +
                ", params='" + params + '\'' +
                '}';
    }

    @Nullable
    public static JSAPI parse(String jsapi) {

        /*
        // 协议
        String scheme = mUri.getScheme();
        // 域名+端口号+路径+参数
        String scheme_specific_part = mUri.getSchemeSpecificPart();
        // 域名+端口号
        String authority = mUri.getAuthority();
        // fragment
        String fragment = mUri.getFragment();
        // 域名
        String host = mUri.getHost();
        // 端口号
        int port = mUri.getPort();
        // 路径
        String path = mUri.getPath();
        // 参数
        String query = mUri.getQuery();
         */

        try {
            Uri uri = Uri.parse(jsapi);
            if ("hivejsapi".equals(uri.getScheme())){
                String[] host = uri.getAuthority().split(":");
                return new JSAPI(host[0],host[1],uri.getPath().substring(1),uri.getQuery());
            }
          return null;
        } catch (Throwable e) {
            return null;
        }
    }
}
