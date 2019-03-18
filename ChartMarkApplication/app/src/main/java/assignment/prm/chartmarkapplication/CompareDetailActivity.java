package assignment.prm.chartmarkapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CompareDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_detail);
    }


    public void clickToViewLoveList(View view) {
        Intent intent = new Intent(this, LoveListActivity.class);
        startActivity(intent);
    }
}
