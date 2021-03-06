/*
 *   Copyright 2015 Ruben Gees
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.rubengees.introduction.entity;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * A bean which contains the data of an option.
 *
 * @author Ruben Gees
 */
public class Option implements Parcelable {
    public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>() {
        public Option createFromParcel(Parcel source) {
            return new Option(source);
        }

        public Option[] newArray(int size) {
            return new Option[size];
        }
    };
    private String title;
    private Integer titleResource;
    private boolean isActivated;
    private int position;

    /**
     * @param title The title of this option.
     */
    public Option(@NonNull String title) {
        this.title = title;
        this.isActivated = false;
    }

    /**
     * @param title       The title of this option.
     * @param isActivated makes this option activated or not. If true is passed,
     *                    the checkbox will be checked.
     */
    public Option(@NonNull String title, boolean isActivated) {
        this.title = title;
        this.isActivated = isActivated;
    }

    /**
     * @param titleResource The title as a resource
     */
    public Option(@StringRes int titleResource) {
        this.titleResource = titleResource;
        this.isActivated = false;
    }

    /**
     * @param titleResource       The title as a resource
     * @param isActivated makes this option activated or not. If true is passed,
     *                    the checkbox will be checked.
     */
    public Option(@StringRes int titleResource, boolean isActivated) {
        this.titleResource = titleResource;
        this.isActivated = isActivated;
    }

    protected Option(Parcel in) {
        this.title = in.readString();
        this.titleResource = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isActivated = in.readByte() != 0;
        this.position = in.readInt();
    }

    public String getTitle() {
        return title;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Creates and finds all the needed data. Don't call this Method yourself.
     *
     * @param context  The context.
     * @param position The position of the slide, this option is assigned to.
     */
    public void init(@NonNull Context context, @IntRange(from = 0) int position) {
        this.position = position;

        if (titleResource != null) {
            this.title = context.getString(titleResource);
            this.titleResource = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.titleResource);
        dest.writeByte(isActivated ? (byte) 1 : (byte) 0);
        dest.writeInt(this.position);
    }
}
