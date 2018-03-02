package com.example.youtubeapidemo;


import android.widget.RatingBar;
import android.widget.TextView;

public class VideoItem {
   // RatingBar rb;
    //TextView value;

    public String title;
    public String description;
    public String thumbnailURL;
    public String id;




    public String getTitle() {
        return title;
    }  // try put in DATABASE

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }  // try put in DATABASE

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
