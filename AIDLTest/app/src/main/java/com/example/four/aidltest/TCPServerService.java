package com.example.four.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/*
Socket 方式实现 IPC
 */
public class TCPServerService extends Service {
    private static final String TAG = "TCPServerService";
    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMsg = {"你好啊，哈哈", "请问你叫什么名字呀?", "今天重庆天气不错哦，Shy(✿◡‿◡)", "听说爱笑的人运气不会太差，不知道是不是真的。", "必须的",
            "不想和傻子聊天！", "你是小哥哥吗？", "em...", "打你哦", "我喜欢小姐姐，嘻", "少他妈跟我来这套", "嫌弃", "你咬我？", "哼哼"};


    public TCPServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TCPServer()).start();
    }

    /**
     * @param client 有客户端连接成功时，新生成的用于和客户端通信的 Socket
     * @throws IOException
     */
    private void responseClient(Socket client) throws IOException {
        // 用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // 用于向客户端回复消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

        // 客户端首次连接提示
        out.println("欢迎来到YY聊天室!");

        while (!mIsServiceDestoryed) {
            String str = in.readLine();
            Log.i(TAG, "Msg from client: " + str);

            if (str == null) {
                // 客户端断开连接
                break;
            }

            // 回复客户端
            String msg = mDefinedMsg[new Random().nextInt(mDefinedMsg.length)];
            out.println(msg);

            Log.i(TAG, "Send to client: " + msg);
        }

        // 客户端退出
        Log.i(TAG, "-->>Client quit.");

        // 关闭流
        if (in != null) {
            in.close();
        }

        if (out != null) {
            out.close();
        }

        // 别忘了关闭客户端scoket连接
        client.close();

    }

    private class TCPServer implements Runnable {

        @SuppressWarnings("resource")
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8888);
            } catch (IOException e) {
                Log.i(TAG, "-->>服务端建立 TCP 监听失败，port:8888 ,尝试重连...");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestoryed) {
                try {
                    final Socket socket = serverSocket.accept();
                    Log.i(TAG, "-->>新连接一个客户端！");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(socket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
