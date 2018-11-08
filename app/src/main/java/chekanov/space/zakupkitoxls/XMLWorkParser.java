package chekanov.space.zakupkitoxls;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLWorkParser {
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        }
        finally {
            in.close();
        }
    }



    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
        List entries = new ArrayList();
        int event = parser.getEventType();
        String tagName = "";
        String text = "";
        while (event != XmlPullParser.END_DOCUMENT){
                if (event == XmlPullParser.START_DOCUMENT) {
                    Log.d(MainActivity.TAG, "Start Document");
                } else if (event == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    Log.d(MainActivity.TAG, "<" + tagName + ">");
                } else if (event == XmlPullParser.END_TAG) {
                    Log.d(MainActivity.TAG, "</" + tagName + ">");
                } else if (event == XmlPullParser.TEXT) {
                    text = parser.getText();
                    Log.d(MainActivity.TAG, text);
                }
                event = parser.next();
        }
        return entries;
    }
}