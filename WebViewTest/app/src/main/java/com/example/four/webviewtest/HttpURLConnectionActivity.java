package com.example.four.webviewtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * 使用HttpURLConnection访问网络，获取本机的get_data.xml文件，并将该文件解析出来（PULL解析 和 SAX解析）
 * 注意下面方法中的ipAddress可能会变  用cmd ipconfig查看
 */
public class HttpURLConnectionActivity extends AppCompatActivity {

    EditText responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_urlconnection);
        Button sendRequestPull = (Button) findViewById(R.id.send_request);// 发送请求
        final Button postData = (Button) findViewById(R.id.post_data); // 提交数据
        responseText = (EditText) findViewById(R.id.respond_text);

        // 点击按钮，发送请求， GET
        sendRequestPull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestWithHttpUrlConnection();
            }
        });

        // 点击按钮，发送请求，POST
        postData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataWithHttpUrlConnection();
            }
        });
    }

    /**
     *
     */
    public void sendRequestWithHttpUrlConnection() {

        String address = "https://api.berryapi.net/get/bing?AppKey=k1m0Iirueu";
        HttpURLConnectionUtils.sendHttpUrlConnectionRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                showOnScreen("\n服务器返回数据：" + "\n" + response);
                showOnScreen("\nPull解析结果：");
                parseXMLWithPull(response);
                showOnScreen("\nSAX解析结果：");
                parseXMLWithSAX(response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }


    public void postDataWithHttpUrlConnection() {
//        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=e70302717c5d493499c49dd2ee6b8de2";
//        String address = "https://wx.idsbllp.cn/cyxbsMobile/index.php/Home/Person/search";
//        String post = "stuNum=2016211743&idNum=284415";

        String address = "118.24.175.82/data/describe/getamount";
        String post = "index=学生食堂";
        HttpURLConnectionUtils.postHttpUrlConnectionRequest(address, post, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                showOnScreen("\n服务器返回数据：" + "\n" + response);
//                textViewAppend("\nPull解析结果：");
//                parseXMLWithPull(response);
//                textViewAppend("\nSAX解析结果：");
//                parseXMLWithSAX(response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void parseXMLWithPull(String xmlData) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    // 开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    // 每完成解析某个结点，就调用showOnScreen()方法将解析内容显示出来
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            Log.d("MainActivity", "哈id is " + id);
                            Log.d("MainActivity", "哈name is " + name);
                            Log.d("MainActivity", "哈version is " + version);
                            showOnScreen("\n" + "名字: " + name + " " + "ID: " + id + " " + "版本号: " + version + "\n");
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 这个Handler用于SAX解析结束时，将解析结果返回主线程，显示到TextView中
     * 是一个异步消息处理机制
     */
    private final Handler handler  = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String parseResult = (String) msg.obj;
            showOnScreen(parseResult);
        }
    };

    // SAX解析XML文件是通过新建一个类ContentHandler extends DefaultHandler{} 实现的，需要@Override 5个方法，
    // 解析结果在ContentHandler中完成，所以我用了一个Handler的异步机制，用于将解析结果返回给调用方（回到主线程，用于UI操作）。
    public void parseXMLWithSAX(String XMLdata) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            // 创建ContentHandler实例
            ContentHandler contentHandler = new ContentHandler(handler);
            // 将ContentHandler实例添加到XMLReader中
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(new StringReader(XMLdata)));

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOnScreen(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.append(response);
                responseText.setSelection(responseText.getText().length());
            }
        });
    }
}
