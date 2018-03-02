package com.example.youtubeapidemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;
    RatingBar rb;  // Added myself
    TextView value;   // CONTAINS THE RATING VALUE
    TextView Vid_Title;
    TextView Vid_Id;
    DatabaseHelper myDb2;
    VideoItem vid2;    // Reference to video item class created
    Button btnAddData;
    Button btnviewAll;  // tRY THIS THING LATER
    String thumbnailURL, rating, videoId, videoTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        myDb2 = new DatabaseHelper(this);
        vid2 = new VideoItem();
        playerView = (YouTubePlayerView) findViewById(R.id.player_view);
        playerView.initialize(YoutubeConnector.KEY, this);
        rb = (RatingBar) findViewById(R.id.textView_ratingBar);    // Code added myself
        //value=(TextView)findViewById(R.id.textView_value);    // Code Added myself
        Vid_Title = (TextView) findViewById(R.id.textView_Vid_Title);
        Vid_Id = (TextView) findViewById(R.id.textView_Vid_Id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        value=(TextView)findViewById(R.id.textView_value);

        thumbnailURL = getIntent().getStringExtra("THUMBNAIL_URL");
        videoId = getIntent().getStringExtra("VIDEO_ID");
        videoTitle = getIntent().getStringExtra("VIDEO_TITLE");

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() { // code added myself

            @Override
            public void onRatingChanged(RatingBar rb, float v, boolean b) {
                rating = String.valueOf(v);// Code added by me
                //value.setText(v);   // This code added by me 2/12/18
                Toast.makeText(PlayerActivity.this, "You rated this " + String.valueOf(v), Toast.LENGTH_LONG).show();
            }
        });

        AddData();
        viewAll();  // added to attach sqlite code

    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb2.insertData(rating, videoTitle, videoId, thumbnailURL);
                        if (isInserted == true)
                            Toast.makeText(PlayerActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(PlayerActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Cursor res = myDb2.getAllData();
                        if (res.getCount() == 0) {
                            return;
                        }
                        Intent intent = new Intent(PlayerActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override   // THIS METHOD IS VERY IMPORTANT AND NEED TO BE FURTHER STUDIED.
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
            Vid_Title.setText("You are playing: " + getIntent().getStringExtra("VIDEO_TITLE"));
            Vid_Id.setText("URL: https://www.youtube.com/watch?v=" + getIntent().getStringExtra("VIDEO_ID"));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Initialization Failed", Toast.LENGTH_LONG).show();
    }
}
