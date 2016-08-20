package com.sande.soundload.DataLayer;

import android.net.Uri;
import android.os.Environment;

import com.sande.soundload.Pojo.Track;

import java.io.File;

/**
 * Created by Sandeep on 19-08-2016.
 */
public class StorageManager {

    public static boolean isWritable(){
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getRoot(){
        return new File(Environment.getExternalStorageDirectory()+"/SoundLoad");
    }

    public static File getContainingFolder(String artist){
        File mFolder=new File(getRoot(),artist);
        try {
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }
        }catch (Exception e){
            //do something?
        }
        return mFolder;
    }

    public static Uri getDestnationUri(Track object){
        File mFile=new File(getContainingFolder(object.getArtist()),object.getTitle()+".mp3");
        return Uri.fromFile(mFile);
    }


}
