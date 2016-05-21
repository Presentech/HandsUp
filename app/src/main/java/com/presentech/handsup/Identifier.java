package com.presentech.handsup;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;
/**
 * Created by epren on 17/05/2016.   This was all stolen from googles website
 */
public class Identifier {
    private static String sID = null;
    private static final String INSTALLATION = "HandsUP";

    public synchronized static String id(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}

//
//
///**Old stuff - to be paid attention to at your peril
//
//
//
// /**
// * Returns a uniquie device-specific ID based on available UID
// */
//public int GetIdentifier(Context context) {
//
//
//    /**
//     *Get persistant hash value
//     */
//
//    String FILENAME = "hash";
//    String string = "1972";
//    FileInputStream fisTestID;
//    FileOutputStream fosID;
//    StringBuffer datax = new StringBuffer("");
//    int hash;
//
//    boolean fileDoesNotExist = false;
//
//
//    try {
//        fisID = context.openFileInput("IDHashFile.txt");
//    } catch (FileNotFoundException e) {
//        fileDoesNotExist = true;
//    }
//
//
//    if (!fileDoesNotExist) //If file does exist
//    { /**Read int from file*/
//        try {
//            fisTestID = context.openFileInput("IDHashFile.txt");
//
//            InputStreamReader isr = new InputStreamReader ( fisTestID ) ;
//            BufferedReader buffreader = new BufferedReader ( isr ) ;
//            String readString = buffreader.readLine ( ) ;
//            while ( readString != null ) {
//                datax.append(readString);
//                readString = buffreader.readLine ( ) ;
//            }
//            hash = Integer.parseInt(buffreader.readLine());
//            isr.close ( ) ;
//        } catch ( IOException ioe ) {
//            ioe.printStackTrace();
//        }
//
//
//
//
//
//    } else {/**Generate random int and write to file*/
//
//    }
//
//
//    /**
//     *Get UID
//     */
//    //Try to get
//
//}
//
//    public String GetDeviceID(Context context) {
//        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//
//        final String tmDevice, tmSerial, androidId;
//        tmDevice = "" + tm.getDeviceId();
//        tmSerial = "" + tm.getSimSerialNumber();
//        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        String deviceId = deviceUuid.toString();
//
//        return deviceId;
//    }
//
///*


