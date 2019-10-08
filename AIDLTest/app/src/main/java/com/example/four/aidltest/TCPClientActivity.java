package com.example.four.aidltest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.four.aidltest.NetWorkUtil.NetworkUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 没有网络，服务端肯定不能回复
 *
 * BUG:
 *
 * 当断开网络再重连后，服务端会无法回复
 *
 * 当网络ip在交互过程中改变，服务端也无法回复
 *
 * 解决：
 *
 * 监听网络变化广播？没试过
 */
public class TCPClientActivity extends AppCompatActivity {
    private static final String TAG = "TCPClientActivity";
    public static final int MESSAGE_SCOKET_CONNECT = 0;
    public static final int MESSAGE_RECEIVE_NEW_MAG = 1;

    private TextView displayMsgTv;
    private EditText sendMsgEdt;
    private Button sendMsgBtn;

    private Socket mClientSocket = null;

    private PrintWriter mPrinter;

//    /**
//     * 切回主线程更新UI
//     */
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MESSAGE_SCOKET_CONNECT:
//                    displayMsgTv.append((String) msg.obj + "\n");
//                    break;
//                case MESSAGE_RECEIVE_NEW_MAG:
//                    displayMsgTv.append((String) msg.obj + "\n");
//                    sendMsgBtn.setEnabled(true);
//                    break;
//                default:
//                    super.handleMessage(msg);
//            }
//        }
//    };

    /**
     * 这里需要格外注意的是，调用mPrinter.println(msgSend2Server);不能在主线程进行
     * 会抛出 主线程访问网络异常
     */
    public void sendMsg(View view) {
        final String msgSend2Server = sendMsgEdt.getText().toString();
        if (!TextUtils.isEmpty(msgSend2Server) && mPrinter != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mPrinter.println(msgSend2Server);
                }
            }).start();
            sendMsgEdt.setText("");
            String time = formatDataTime(System.currentTimeMillis());
            final String displaySendMsg = "Four " + time + ": " + msgSend2Server + "\n";
            displayMsgTv.append(displaySendMsg);
        }
    }

    /**
     * @return 返回格式化的时间格式 时:分:秒
     */
    @SuppressLint("SimpleDateFormat")
    private String formatDataTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpclient);
        displayMsgTv = (TextView) findViewById(R.id.show_tv);
        sendMsgEdt = (EditText) findViewById(R.id.send_edt);
        sendMsgBtn = (Button) findViewById(R.id.send_btn);

        init();
    }

    private void init() {
        // 启动服务
        Intent startService = new Intent(this, TCPServerService.class);
        startService(startService);

        // 客户端启动即尝试和服务端建立 Socket连接
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectToServer();
            }
        }).start();
    }

    private void connectToServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                socket = new Socket(NetworkUtils.getIPAddress(this), 8888);
                mClientSocket = socket;

                // 用于向服务端发消息，最后一个参数设置自动flush输出缓冲区
                mPrinter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            } catch (IOException e) {
                // 失败重连
                SystemClock.sleep(1000);
                Log.i(TAG, "-->>连接服务端失败，正尝试重新连接...");
            }

        }

        Log.i(TAG, "-->>Connect server success.");

        // 连接成功后，服务端会自动回复一条 提示连接成功 消息！
        try {
            // 用于读取服务端数据
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String connectSuccess = br.readLine();
            displayMsgTv.post(new Runnable() {
                @Override
                public void run() {
                    displayMsgTv.append(connectSuccess+'\n');
                }
            });
//            mHandler.obtainMessage(MESSAGE_SCOKET_CONNECT, connectSuccess).sendToTarget();

            while (!TCPClientActivity.this.isFinishing()) {
                String msgFromServer = null;
                try {
                    msgFromServer = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "Receive: " + msgFromServer);
                if (msgFromServer != null) {
                    String time = formatDataTime(System.currentTimeMillis());
                    final String showedMsg = "Server " + time + ":" + msgFromServer + "\n";
                    displayMsgTv.post(new Runnable() {
                        @Override
                        public void run() {
                            displayMsgTv.append(showedMsg + '\n');
                        }
                    });
//                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MAG, showedMsg).sendToTarget();
                }
            }
            Log.i(TAG, "-->>quit...");

            if (br != null) {
                br.close();
            }

            if (mPrinter != null) {
                mPrinter.close();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
