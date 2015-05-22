package hevs.ch.valaistory.controller;

import android.os.Handler;
import android.os.Message;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre-Alain Wyssen on 22.05.2015.
 * Project: Valaistory
 * Package: hevs.ch.valaistory.controller
 * Description:
 *
 * Based on this tutorial:
 * http://www.tutorialspoint.com/android/android_xml_parsers.htm
 */
public class PictureFetcher {
    private static final int STEP_ONE_COMPLETE = 0;
    private static final int STEP_TWO_COMPLETE = 1;

    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myParser;
    private XmlPullParser myParser2;

    private List<String> identifiers;
    private String id;

    public PictureFetcher() {

        identifiers = new ArrayList<>();
        doThread1();

    }

    private void doThread1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://xml.memovs.ch/oai/oai2.php?verb=ListIdentifiers&metadataPrefix=oai_dc&set=k");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    myParser = xmlFactoryObject.newPullParser();

                    myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myParser.setInput(stream, null);
                    parseXMLAndStoreIt(myParser);
                    stream.close();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }

                Message msg = Message.obtain();
                msg.what = STEP_ONE_COMPLETE;
                handler.sendMessage(msg);
            }
        };

        thread.start();
    }

    private void doThread2() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://xml.memovs.ch/oai/oai2.php?verb=GetRecord&metadataPrefix=qdc&identifier="+id);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    myParser2 = xmlFactoryObject.newPullParser();

                    myParser2.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myParser2.setInput(stream, null);
                    parseXmlAndCount(myParser2);
                    stream.close();
                } catch(XmlPullParserException e) {
                    e.printStackTrace();
                } catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    private void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text=null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("identifier")){
                            id = text;
                            //identifiers.add(text);
                        }
                        break;
                }
                event = myParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXmlAndCount(XmlPullParser myParser) {
        int event;
        int count = 0;
        String text=null;
        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("spatial")){

                        } else if(name.equals("hasPart")){
                            count++;
                        }
                        identifiers.add(id + " (" + count + " pics)");
                        break;
                }
                event = myParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case STEP_ONE_COMPLETE:
                    doThread2();
                    break;
                case STEP_TWO_COMPLETE:
                    // do final steps;
                    break;
            }
        }
    };
}
