package chekanov.space.zakupkitoxls;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public class XMLWorkParser {
    private static final String ns = null;

    public ZakupkiItem parse(InputStream in) throws XmlPullParserException, IOException {
        ZakupkiItem zakupkiItem = null;
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            //Log.d(MainActivity.TAG, "new zakupki item");
            zakupkiItem = new ZakupkiItem(readFeed(parser));
        }
        catch (Exception e){
            Log.e(MainActivity.TAG, "Parser error", e);
        }
        finally {
            in.close();
        }
        return zakupkiItem;
    }



    private Map<String, String> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
        int event = parser.getEventType();
        String tagName = null;
        String tagText = null;
        Map<String, String> map = new TreeMap<>();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case (XmlPullParser.START_TAG):
                    tagName = parser.getName();
                    break;
                case (XmlPullParser.TEXT):
                    if (!parser.isWhitespace()) {
                        tagText = parser.getText();
                    }
                    break;
                default:
                    break;
            }
            if (tagName != null && tagText != null) {
                map.put(tagName, tagText);
            }
            event = parser.next();
        }

        return map;
    }
}