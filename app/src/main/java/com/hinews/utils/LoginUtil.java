package com.hinews.utils;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
/**
 * OkHttp
 */
public class LoginUtil {

    private OkHttpClient client;

    public LoginUtil() {
        client = new OkHttpClient();
    }

    /**
     * RxAndroid
     *   implementation 'io.reactivex:rxjava:1.1.0'
     *   implementation 'io.reactivex:rxandroid:1.1.0' 当用compile竟然引入不对。   4-1
     */
    public Observable<String> login(final String url, final Map<String,String> params){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()){
                    FormBody.Builder builder = new FormBody.Builder();
                    if (params!=null&&!params.isEmpty()){
                        for(Map.Entry<String,String> entry:params.entrySet()){
                            builder.add(entry.getKey(),entry.getValue());
                        }
                    }
                    RequestBody requestBody = builder.build();
                    //post
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()){
                                subscriber.onNext(response.body().string());
                            }
                            subscriber.onCompleted();
                        }
                    });
                }
            }
        });
    }
}

