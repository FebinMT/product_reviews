package fmt.productreviews;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {


    private ProgressDialog pDialog;


    Spinner P_TYPE;

    RatingBar P_QUALITY, P_SPEED, P_TOUCH_SENSITIVITY, P_WEIGHT, P_DURABILITY, P_OVERALL_RATING;

    Button SEARCH_PRODUCTS;

    LinearLayout SEARCH_RESULTS;


    String type;

    Float quality, speed, touch_sensitivity, weight, durability, overall_rating;


    JSONArray NewsArray = null;

    private List<Products> productsList = new ArrayList<>();

    RecyclerView productsRecyclerView;

    LinearLayoutManager productsLayoutManager;

    private NewsAdapter productsAdapter;

    public final String JSON_ARRAY = "result";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        P_TYPE = (Spinner) findViewById(R.id.s_type);

        P_QUALITY = (RatingBar) findViewById(R.id.s_quality);

        P_SPEED = (RatingBar) findViewById(R.id.s_speed);

        P_TOUCH_SENSITIVITY = (RatingBar) findViewById(R.id.s_touch_sensitivity);

        P_WEIGHT = (RatingBar) findViewById(R.id.s_weight);

        P_DURABILITY = (RatingBar) findViewById(R.id.s_durability);

        P_OVERALL_RATING = (RatingBar) findViewById(R.id.s_overall_rating);

        SEARCH_PRODUCTS = (Button) findViewById(R.id.search_products);

        SEARCH_RESULTS = (LinearLayout) findViewById(R.id.search_results);

        productsRecyclerView = (RecyclerView) findViewById(R.id.search_list);


        productsLayoutManager = new LinearLayoutManager(this);
        productsLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productsRecyclerView.setLayoutManager(productsLayoutManager);
        productsAdapter = new NewsAdapter(this);
        productsRecyclerView.setAdapter(productsAdapter);


        SEARCH_PRODUCTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productsList.clear();

                productsAdapter.notifyDataSetChanged();

                type = P_TYPE.getSelectedItem().toString();

                switch (type) {

                    case "All Products":
                        type = "All";
                        break;

                    case "Washing Machine":
                        type = "WM";
                        break;

                    case "Rice Cooker":
                        type = "RC";
                        break;

                }

                quality = P_QUALITY.getRating();

                speed = P_SPEED.getRating();

                touch_sensitivity = P_TOUCH_SENSITIVITY.getRating();

                weight = P_WEIGHT.getRating();

                durability = P_DURABILITY.getRating();

                overall_rating = P_OVERALL_RATING.getRating();

                if(quality == 0.0)
                    Toast.makeText(Search.this, "Kindly enter the Quality of the Product !", Toast.LENGTH_LONG).show();

                else if(speed == 0.0)
                    Toast.makeText(Search.this, "Kindly enter the Speed of the Product !", Toast.LENGTH_LONG).show();

                else if(touch_sensitivity == 0.0)
                    Toast.makeText(Search.this, "Kindly enter the Touch Sensitivity of the Product !", Toast.LENGTH_LONG).show();

                else if(weight == 0.0)
                    Toast.makeText(Search.this, "Kindly enter the Weight of the Product !", Toast.LENGTH_LONG).show();

                else if(durability == 0.0)
                    Toast.makeText(Search.this, "Kindly enter the Durability of the Product !", Toast.LENGTH_LONG).show();

                else if(overall_rating == 0.0)
                    Toast.makeText(Search.this, "Kindly enter the Overall Rating of the Product !", Toast.LENGTH_LONG).show();

                searchForProducts();

            }
        });

    }


    private void searchForProducts() {

        pDialog = ProgressDialog.show(this, "", "Fetching Products ... ", false, false);

        StringRequest stringRequest = new StringRequest("http://10.0.3.2/dm_assignment/search_products.php?type="
                + type + "&quality=" + quality + "&speed=" + speed + "&touch_sensitivity=" + touch_sensitivity + "&weight=" + weight
                + "&durability=" + durability + "&overall_rating=" + overall_rating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showProductList(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Search.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        0,
                        -1,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    protected void showProductList(String response) {

        String type, name, quality, speed, touch_sensitivity, weight, durability, overall_rating;

        try {

            JSONObject jsonObj = new JSONObject(response);

            NewsArray = jsonObj.getJSONArray(JSON_ARRAY);

            for (int i = 0; i < NewsArray.length(); i++) {

                JSONObject jsonObject = NewsArray.getJSONObject(i);

                type = jsonObject.getString("type");

                name = jsonObject.getString("name");

                quality = jsonObject.getString("quality");

                speed = jsonObject.getString("speed");

                touch_sensitivity = jsonObject.getString("touch_sensitivity");

                weight = jsonObject.getString("weight");

                durability = jsonObject.getString("durability");

                overall_rating = jsonObject.getString("overall_rating");

                Products products = new Products(type, name, quality, speed, touch_sensitivity, weight, durability, overall_rating);

                productsList.add(products);

                productsRecyclerView.setMinimumHeight(productsRecyclerView.getHeight() + 340);

                productsAdapter.notifyDataSetChanged();

            }

            if (NewsArray.length() == 0)
                SEARCH_RESULTS.setVisibility(View.GONE);

            else
                SEARCH_RESULTS.setVisibility(View.VISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        pDialog.dismiss();

    }


    private class NewsAdapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater mLayoutInflater;

        private NewsAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = mLayoutInflater.inflate(R.layout.activity_search_item, viewGroup, false);

            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            viewHolder.setData(productsList.get(position).mType, productsList.get(position).mName,
                    productsList.get(position).mQuality, productsList.get(position).mSpeed, productsList.get(position).mTouchSensitivity,
                    productsList.get(position).mWeight, productsList.get(position).mDurability, productsList.get(position).mOverallRating);

        }

        @Override
        public int getItemCount() {
            return productsList.size();
        }

    }


    private class Products {

        String mType, mName, mQuality, mSpeed, mTouchSensitivity, mWeight, mDurability, mOverallRating;

        private Products(String type, String name, String quality, String speed,
                         String touch_sensitivity, String weight, String durability, String overall_rating){

            mType = type;
            mName = name;
            mQuality = quality;
            mSpeed = speed;
            mTouchSensitivity = touch_sensitivity;
            mWeight = weight;
            mDurability = durability;
            mOverallRating = overall_rating;

        }

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTypeTextView, mNameTextView, mQualityTextView, mSpeedTextView,
                            mTouchSensitivityTextView, mWeightTextView, mDurabilityTextView, mOverallRatingTextView;

        private ViewHolder(View itemView) {
            super(itemView);

            mTypeTextView = (TextView) itemView.findViewById(R.id.s_i_type);
            mNameTextView = (TextView) itemView.findViewById(R.id.s_i_name);
            mQualityTextView = (TextView) itemView.findViewById(R.id.s_i_quality);
            mSpeedTextView = (TextView) itemView.findViewById(R.id.s_i_speed);
            mTouchSensitivityTextView = (TextView) itemView.findViewById(R.id.s_i_touch_sensitivity);
            mWeightTextView = (TextView) itemView.findViewById(R.id.s_i_weight);
            mDurabilityTextView = (TextView) itemView.findViewById(R.id.s_i_durability);
            mOverallRatingTextView = (TextView) itemView.findViewById(R.id.s_i_overall_rating);

        }

        private void setData(String type, String name, String quality, String speed,
                             String touch_sensitivity, String weight, String durability, String overall_rating) {

            mTypeTextView.setText(type);
            mNameTextView.setText(name);
            mQualityTextView.setText(quality);
            mSpeedTextView.setText(speed);
            mTouchSensitivityTextView.setText(touch_sensitivity);
            mWeightTextView.setText(weight);
            mDurabilityTextView.setText(durability);
            mOverallRatingTextView.setText(overall_rating);

        }
    }

}