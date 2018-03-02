package com.example.youtubeapidemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    DatabaseHelper myDb2;
    ListView favListView;
    ArrayList<Videos> videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb2 = new DatabaseHelper(this);
        setContentView(R.layout.activity_favorites);

        favListView= (ListView) findViewById(R.id.favListView);
        videos=new ArrayList<>();

        Cursor res = myDb2.getAllData();
        if(res.getCount() == 0) {
            // show message
            Toast.makeText(this,"Error",Toast.LENGTH_LONG);
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            Videos video=new Videos();
            video.setRating(res.getString(0));
            video.setTitle(res.getString(1));
            video.setVideoid(res.getString(2));
            video.setThumbnail(res.getString(3));
            videos.add(video);
        }
        updateVideosFound();
        addClickListener();
    }

    private void updateVideosFound() {
        ArrayAdapter<Videos> adapter = new ArrayAdapter<Videos>(getApplicationContext(), R.layout.fav_view, videos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.fav_view, parent, false);
                }

                ImageView thumbnail = (ImageView) convertView.findViewById(R.id.fav_video_thumbnail);
                TextView title = (TextView) convertView.findViewById(R.id.fav_video_title);
                RatingBar ratingBar= (RatingBar) convertView.findViewById(R.id.favRatingBar);

                Videos searchResult = videos.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnail()).into(thumbnail);
                title.setText(searchResult.getTitle());
                ratingBar.setRating(Float.parseFloat(searchResult.getRating()));

                return convertView;
            }
        };
        favListView.setAdapter(adapter);
    }

    private void addClickListener() {
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), FavPlayerActivity.class);
                intent.putExtra("FAV_VIDEO_ID", videos.get(position).getVideoid());
                startActivity(intent);
            }
        });
    }
}
