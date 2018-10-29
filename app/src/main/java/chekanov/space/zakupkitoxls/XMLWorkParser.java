package chekanov.space.zakupkitoxls;

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

    /*
    *
    * Дописываем тут потом ололо класс readFeed
    *
    * */


    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
        List entries = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, "ns2:export");
        while (parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            }
            return entries;
        }
    }
