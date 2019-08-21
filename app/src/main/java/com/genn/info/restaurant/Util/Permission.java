package com.genn.info.restaurant.Util;

import android.os.Parcel;
import android.os.Parcelable;

public class Permission implements Parcelable {


    private String name,id;
    private boolean menuright,FA,FS,FD,FV,FX;

    /**
     * Create parcelable of friend
     */
    public static final Creator<Permission> CREATOR = new Creator<Permission>() {
        public Permission createFromParcel(Parcel in) {
            return new Permission(in);
        }

        public Permission[] newArray(int size) {
            return new Permission[size];
        }
    };


    public Permission(Parcel in) {
        this.name = in.readString();
        this.menuright = in.readByte() != 0;
        this.FA = in.readByte() != 0;
        this.FS = in.readByte() != 0;
        this.FD = in.readByte() != 0;
        this.FV = in.readByte() != 0;
        this.FX = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte((byte) (this.menuright ? 1 : 0));
        dest.writeByte((byte) (this.FA ? 1 : 0));
        dest.writeByte((byte) (this.FS ? 1 : 0));
        dest.writeByte((byte) (this.FD ? 1 : 0));
        dest.writeByte((byte) (this.FV ? 1 : 0));
        dest.writeByte((byte) (this.FX ? 1 : 0));
    }

    public Permission(String name, boolean menuright,boolean FA,boolean FS,boolean FD,boolean FV,boolean FX) {

        this.name = name;

        if (menuright){
            this.menuright = true;
        }else{
            this.menuright = false;
        }

        if (FA){
            this.FA = true;
        }else{
            this.FA = false;
        }


        if (FS){
            this.FS = true;
        }else{
            this.FS = false;
        }


        if (FD){
            this.FD = true;
        }else{
            this.FD = false;
        }
      if (FV){
            this.FV = true;
        }else{
            this.FV = false;
        }


        if (FX){
            this.FX = true;
        }else{
            this.FX = false;
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMenuright() {
        return menuright;
    }

    public void setMenuright(boolean menuright) {
        this.menuright = menuright;
    }

    public boolean isFA() {
        return FA;
    }

    public void setFA(boolean FA) {
        this.FA = FA;
    }

    public boolean isFS() {
        return FS;
    }

    public boolean isFD() {
        return FD;
    }

    public void setFD(boolean FD) {
        this.FD = FD;
    }

    public boolean isFV() {
        return FV;
    }

    public void setFV(boolean FV) {
        this.FV = FV;
    }

    public void setFS(boolean FS) {
        this.FS = FS;
    }

    public boolean isFX() {
        return FX;
    }

    public void setFX(boolean FX) {
        this.FX = FX;
    }
}