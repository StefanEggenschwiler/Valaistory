package hevs.ch.valaistory;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import hevs.ch.valaistory.controller.PictureFetcher;


public class TestParseXMLActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_parse_xml);


        PictureFetcher pf = new PictureFetcher();
        try {
            Thread.sleep(20000);
        } catch(InterruptedException e) {

        }
        List<String> items = pf.getIdentifiers();
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_expandable_list_item_1,items);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);
    }
}
