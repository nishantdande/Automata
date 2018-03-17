package com.automata.error;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.automata.R;
import com.automata.util.AppUtils;


import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class APIErrorHandler extends Handler {

    private Activity activity;

    public APIErrorHandler(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case APIErrorCodes.ERR_NETWORK_NOT_AVAILABLE:
                AppUtils.displayShortSnackbar(activity, R.string.no_internet_connection);
                break;

            case APIErrorCodes.ERR_HTTP_500:
//                AppUtils.displayShortSnackbar(activity, R.string.err_http_500);
                break;

            case APIErrorCodes.ERR_UNKNOWN:
                Bundle bundle = msg.getData();
                AppUtils.displayShortSnackbar(activity, bundle.getString("msg"));
                break;

        }
    }

    public void handleError(Throwable throwable) {
        if (throwable instanceof IOException) {
            sendEmptyMessage(APIErrorCodes.ERR_NETWORK_NOT_AVAILABLE);
        } else if (throwable instanceof HttpException) {
            HttpException ex = (HttpException) throwable;
                switch (ex.code()) {
                    // HTTP code 400 bad request
                    case 400:
//                        APIGenericRespBean bean = LoganSquare.parse(ex.response().errorBody().string(), APIGenericRespBean.class);
//                        sendEmptyMessage(bean.getAppCode());
                        break;

                    // HTTP code 500 internal server error
                    case 500:
                        sendEmptyMessage(APIErrorCodes.ERR_HTTP_500);
                        break;

                    default:
                        sendEmptyMessage(APIErrorCodes.ERR_UNKNOWN);
                        break;
                }

        } else {
            Message message = obtainMessage();
            message.what = APIErrorCodes.ERR_UNKNOWN;

            Bundle bundle= new Bundle();
            bundle.putString("msg", throwable.getMessage());
            message.setData(bundle);
            sendMessage(message);
        }
    }
}