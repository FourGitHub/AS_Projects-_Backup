package com.example.four.webviewtest;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {

    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    private static StringBuilder parseResult = new StringBuilder();
    private Handler mHandler;

    public ContentHandler(Handler handler) {
        mHandler = handler;
    }


    /**
     * 开始XML解析的时候调用
     */
    @Override
    public void startDocument() throws SAXException {
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
        Log.d("ContentHandler", "哈SAX解析结果:");
    }

    /**
     * 开始解析某个节点的时候调用
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 记录当前结点名
        nodeName = localName;
    }

    /**
     * 获取某个节点中的信息时调用
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 根据当前的结点名判断将内容添加到哪一个StringBuilder对象中
        if ("id".equals(nodeName)) {
            id.append(ch, start, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, start, length);
        }
    }

    /**
     * 完成某个节点的解析时调用
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)) {
            Log.d("ContentHandler", "哈id is " + id.toString().trim());
            Log.d("ContentHandler", "哈name is " + name.toString().trim());
            Log.d("ContentHandler", "哈version is " + version.toString().trim());
            parseResult.append("\n\n名字: " + name.toString().trim() + " ID: " + id.toString().trim() + " 版本: " + version.toString().trim());
            // 最后要将StringBuilder清空掉
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    /**
     * 结束XML解析的时候调用
     * parseResult.setLength(0); 这一步很重要，一不小心就踩坑
     * 如果长度不清零，多次发送请求就会造成parseResult.append() 的时候，接着上一次的解析结果append()
     */
    @Override
    public void endDocument() {
        try {
            super.endDocument();
            Message message = new Message();
            message.obj = parseResult.toString();
            mHandler.sendMessage(message);
            parseResult.setLength(0);
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

}
