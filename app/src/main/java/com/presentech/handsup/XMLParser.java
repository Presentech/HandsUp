package com.presentech.handsup;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by Alex on 18/02/2016.
 */
public class XMLParser {
    PresentationFile presentationFile;

   // private XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
    //private XmlPullParser myparser = xmlFactoryObject.newPullParser();

    public PresentationFile getPresentation(String filename) throws XmlPullParserException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        }
        presentationFile = new PresentationFile();
        presentationFile.setTitle("title");
        presentationFile.setAuthor("author");
        presentationFile.setVersion("version");
        presentationFile.setComment("comment");
        return presentationFile;
    }
}
