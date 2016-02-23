package com.presentech.handsup;

import android.content.Context;
import android.util.Xml;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alex on 18/02/2016.
 */
public class XMLParser {
    PresentationFile presentationFile;

   // private XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
    //private XmlPullParser myparser = xmlFactoryObject.newPullParser();

    public PresentationFile getPresentation(String filename) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            File inputFile = new File(filename);
            InputStream inputStream = new FileInputStream(inputFile);

            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("Presentation")) {
                            presentationFile = new PresentationFile();
                        }
                        break;
                    case
                }

            }
        }




        presentationFile = new PresentationFile();
        presentationFile.setTitle("title");
        presentationFile.setAuthor("author");
        presentationFile.setVersion("version");
        presentationFile.setComment("comment");
        return presentationFile;
    }
}
