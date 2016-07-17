package edu.bilkent.findatutor.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;

import edu.bilkent.findatutor.R;
import edu.bilkent.findatutor.model.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    //public ImageView starView;
    //public TextView numStarsView;
    public TextView bodyView;
    public RatingBar ratingBar;
    public TextView priceView;
    public TextView languageView;
    public TextView schoolView;
    public ImageView imageView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
        ratingBar = (RatingBar) itemView.findViewById(R.id.rating);
        priceView = (TextView) itemView.findViewById(R.id.post_price);
        languageView = (TextView) itemView.findViewById(R.id.post_language);
        schoolView = (TextView) itemView.findViewById(R.id.course_title);
        imageView = (ImageView) itemView.findViewById(R.id.post_author_photo);


    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        //numStarsView.setText(String.valueOf(post.viewsCount));
        //numStarsView.setText("23 Sessions");
        bodyView.setText(post.body);
        ratingBar.setRating((float) 3.2);
        priceView.setText(post.price);
        languageView.setText(post.language);
        schoolView.setText(post.school);


        String url = post.authorPhotoUrl;
        if (url != null)
            new DownloadImageTask(imageView).execute(url);
        else
            imageView = null;

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            Bitmap resized = Bitmap.createScaledBitmap(result, 100, 100, true);
            bmImage.setImageBitmap(resized);
        }
    }
}


