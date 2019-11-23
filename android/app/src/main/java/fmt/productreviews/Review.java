package fmt.productreviews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Review extends AppCompatActivity {


    Spinner TYPE;

    EditText NAME;

    RatingBar QUALITY, SPEED, TOUCH_SENSITIVITY, WEIGHT, DURABILITY, OVERALL_RATING;

    Button POST_REVIEW;


    String type, name;

    Float quality, speed, touch_sensitivity, weight, durability, overall_rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        TYPE = (Spinner) findViewById(R.id.type);

        NAME = (EditText) findViewById(R.id.name);

        QUALITY = (RatingBar) findViewById(R.id.quality);

        SPEED = (RatingBar) findViewById(R.id.speed);

        TOUCH_SENSITIVITY = (RatingBar) findViewById(R.id.touch_sensitivity);

        WEIGHT = (RatingBar) findViewById(R.id.weight);

        DURABILITY = (RatingBar) findViewById(R.id.durability);

        OVERALL_RATING = (RatingBar) findViewById(R.id.overall_rating);

        POST_REVIEW = (Button) findViewById(R.id.post_review);


        quality = speed = touch_sensitivity = weight = durability = overall_rating = 0.0f;


        POST_REVIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = TYPE.getSelectedItem().toString();

                name = NAME.getText().toString();

                quality = QUALITY.getRating();

                speed = SPEED.getRating();

                touch_sensitivity = TOUCH_SENSITIVITY.getRating();

                weight = WEIGHT.getRating();

                durability = DURABILITY.getRating();

                overall_rating = OVERALL_RATING.getRating();

                if(TextUtils.isEmpty(name))
                    Toast.makeText(Review.this, "Kindly enter a Product Name !", Toast.LENGTH_LONG).show();

                else if(quality == 0.0)
                    Toast.makeText(Review.this, "Kindly rate the Quality of the Product !", Toast.LENGTH_LONG).show();

                else if(speed == 0.0)
                    Toast.makeText(Review.this, "Kindly rate the Speed of the Product !", Toast.LENGTH_LONG).show();

                else if(touch_sensitivity == 0.0)
                    Toast.makeText(Review.this, "Kindly rate the Touch Sensitivity of the Product !", Toast.LENGTH_LONG).show();

                else if(weight == 0.0)
                    Toast.makeText(Review.this, "Kindly rate the Weight of the Product !", Toast.LENGTH_LONG).show();

                else if(durability == 0.0)
                    Toast.makeText(Review.this, "Kindly rate the Durability of the Product !", Toast.LENGTH_LONG).show();

                else if(overall_rating == 0.0)
                    Toast.makeText(Review.this, "Kindly enter the Overall Rating of the Product !", Toast.LENGTH_LONG).show();

                else {

                    PostReviewBackgroundTask backgroundTask = new PostReviewBackgroundTask(Review.this);
                    backgroundTask.execute(type, name, quality.toString(), speed.toString(),
                            touch_sensitivity.toString(), weight.toString(), durability.toString(), overall_rating.toString());

                }

            }
        });

    }


    private class PostReviewBackgroundTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        Context ctx;
        String data;

        PostReviewBackgroundTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Review.this);
            progressDialog.setMessage("Posting your Review ... ");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            String name = params[1];
            String quality = params[2];
            String speed = params[3];
            String touch_sensitivity = params[4];
            String weight = params[5];
            String durability = params[6];
            String overall_rating = params[7];

                try {

                    URL url = new URL("http://10.0.3.2/dm_assignment/post_review.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                    data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                            + "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                            + "&" + URLEncoder.encode("quality", "UTF-8") + "=" + URLEncoder.encode(quality, "UTF-8")
                            + "&" + URLEncoder.encode("speed", "UTF-8") + "=" + URLEncoder.encode(speed, "UTF-8")
                            + "&" + URLEncoder.encode("touch_sensitivity", "UTF-8") + "=" + URLEncoder.encode(touch_sensitivity, "UTF-8")
                            + "&" + URLEncoder.encode("weight", "UTF-8") + "=" + URLEncoder.encode(weight, "UTF-8")
                            + "&" + URLEncoder.encode("durability", "UTF-8") + "=" + URLEncoder.encode(durability, "UTF-8")
                            + "&" + URLEncoder.encode("overall_rating", "UTF-8") + "=" + URLEncoder.encode(overall_rating, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));

                    String response = "";
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }

                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    IS.close();

                    return response;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            switch (result) {

                case "Your Review has been posted !":

                    Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                    Intent intent = new Intent(Review.this, MainActivity.class);
                    startActivity(intent);

                    break;

                case "Posting Failed !":

                    Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                    break;

                default:

                    Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                    break;
            }
        }
    }
}