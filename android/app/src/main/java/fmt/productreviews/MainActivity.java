package fmt.productreviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout PRODUCT_REVIEW, PRODUCT_SEARCH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PRODUCT_REVIEW = (LinearLayout) findViewById(R.id.product_review);
        PRODUCT_SEARCH = (LinearLayout) findViewById(R.id.product_search);

        PRODUCT_REVIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Review.class);
                startActivity(intent);
            }
        });

        PRODUCT_SEARCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });

    }

}