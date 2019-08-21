package com.genn.info.restaurant.Util;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {


    private String name,id;
    private boolean isSelected;

    /**
     * Create parcelable of friend
     */
    public static final Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>() {
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };


    public Friend(Parcel in) {
        this.name = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte((byte) (this.isSelected ? 1 : 0));
    }

    public Friend(String name,boolean isSelect) {

        this.name = name;

        if (isSelect){
            this.isSelected = true;
        }else{
            this.isSelected = false;
        }

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

}