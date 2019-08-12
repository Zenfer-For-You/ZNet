package com.zenfer.network.download;


import com.zenfer.network.common.NetworkErrorCode;
import com.zenfer.network.framwork.NetwordException;
import com.zenfer.network.framwork.Network;
import com.zenfer.network.framwork.RxUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 下载工具类
 *
 * @author Zenfer
 * @date 2019/6/14 10:26
 */
public class DownloadUtil {

    public static void download(String url, String path, String filename, final NetworkDownloadCallBack callBack) {

        final File file = new File(path, filename);
        Network.getInstance().getApi(callBack.getListener()).download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        writeResponseToDisk(file, responseBody, callBack.getListener());
                        return file;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack.getNetWorkSubscriber());
        RxUtils.getInstance().addDisposable(callBack.getNetWorkSubscriber());
    }

    private static void writeResponseToDisk(File file, ResponseBody body, DownloadProgressListener downloadListener) {
        //从response获取输入流以及总大小
        writeFileFromIS(file, body.byteStream(), body.contentLength(), downloadListener);
    }

    //将输入流写入文件
    private static void writeFileFromIS(File file, InputStream is, final long totalLength, final DownloadProgressListener downloadListener) {

        //创建文件
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Observable.just(e).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<IOException>() {
                            @Override
                            public void accept(IOException e) {
                                downloadListener.onFail(new NetwordException(e, NetworkErrorCode.ERROR_CODE_DOWNLOAD));
                            }
                        });
            }
        }

        OutputStream os = null;
        long currentLength = 0;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            int sBufferSize = 8192;
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
                currentLength += len;
                //计算当前下载进度
                Observable.just(currentLength).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long currentLength) throws Exception {
                                downloadListener.onProgress(currentLength, totalLength, currentLength == totalLength);
                            }
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
            Observable.just(e).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    new Consumer<IOException>() {
                        @Override
                        public void accept(IOException e) {
                            downloadListener.onFail(new NetwordException(e, NetworkErrorCode.ERROR_CODE_DOWNLOAD));
                        }
                    });
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
