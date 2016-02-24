package com.presentech.handsup;

import android.content.Context;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.util.Xml;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Alex on 18/02/2016.
 */
public class XMLParser {
    PresentationFile presentationFile;

    String curText = "";

   // private XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
    //private XmlPullParser myparser = xmlFactoryObject.newPullParser();

    public PresentationFile getPresentation(InputStream in) throws XmlPullParserException, IOException {
        //presentationFile = new PresentationFile();
        try {
            //XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //XmlPullParser parser = factory.newPullParser();
            XmlPullParser parser = Xml.newPullParser();

            //File inputFile = new File(filename);

            //FileInputStream stream = new FileInputStream(inputFile);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            //InputStream in = this.getClass().getClassLoader().getResourceAsStream("/src/main/res/raw/test.xml");

            parser.setInput(in, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();

                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("presentation")) {
                            presentationFile = new PresentationFile();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        curText = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equals("Title")) {
                            presentationFile.setTitle(curText);
                        }
                        if (tagname.equals("Author")){
                            presentationFile.setAuthor(curText);
                        }
                        if (tagname.equals("Version")){
                            presentationFile.setVersion(curText);
                        }
                        if (tagname.equals("Comment")){
                            presentationFile.setComment(curText);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();

            }


        } catch (Exception e){

        }

        return presentationFile;
    }
}
