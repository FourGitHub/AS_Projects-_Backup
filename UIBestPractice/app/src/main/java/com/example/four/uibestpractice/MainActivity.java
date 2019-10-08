package com.example.four.uibestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private TextView textView_right;
    private TextView textView_left;
    private Button button_send_right;
    private Button button_send_left;
    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_right = (TextView) findViewById(R.id.input_text_right);
        textView_left = (TextView) findViewById(R.id.input_text_left);
        button_send_right = (Button) findViewById(R.id.send_right);
        button_send_left = (Button) findViewById(R.id.send_left);
        recyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final MsgAdapter adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);

        button_send_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_right = textView_right.getText().toString();
                if(!msg_right.equals("")){
                    Msg msg = new Msg(msg_right,Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    recyclerView.scrollToPosition(msgList.size()-1);
                    textView_right.setText("");
                }
            }
        });

        button_send_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_left = textView_left.getText().toString();
                if(!msg_left.equals("")){
                    Msg msg = new Msg(msg_left,Msg.TYPE_RECEIVED);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    recyclerView.scrollToPosition(msgList.size()-1);
                    textView_left.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_in_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.harvest:
                Intent intent = new Intent(this, SummaryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
