package com.sande.soundload.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sandeep on 17-08-2016.
 */
public class Track implements Parcelable {
    long id;
    long duration;
    String title;
    String artwork_url;
    String stream_url;
    User user;


    public String getDuration() {
        long s = (duration/1000) % 60;
        long m = (duration /(1000* 60)) % 60;
        long h = (duration / (1000 * 60 * 60)) % 24;
        if(h!=0) {
            return String.format("%d:%02d:%02d", h, m, s);
        }else{
            return String.format("%02d:%02d",m,s);
        }
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    public String getArtist() {
        return user.getUsername();
    }

    public String getDownloadLink() {
        return stream_url;
    }

    public String getTrackArtist(){
        int pos;
        if((pos=title.indexOf('-'))!=-1){
            String artist=title.substring(0,pos);
            artist=artist.trim();
            return artist;
        }
        return getArtist();
    }

    public String getTrackTitle(){
        int pos;
        if((pos=title.indexOf('-'))!=-1){
            String tle=title.substring(pos);
            tle=tle.trim();
            return tle;
        }
        return getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.duration);
        dest.writeString(this.title);
        dest.writeString(this.artwork_url);
        dest.writeString(this.stream_url);
        dest.writeParcelable(this.user, flags);
    }

    public Track() {
    }

    protected Track(Parcel in) {
        this.id = in.readLong();
        this.duration = in.readLong();
        this.title = in.readString();
        this.artwork_url = in.readString();
        this.stream_url = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
