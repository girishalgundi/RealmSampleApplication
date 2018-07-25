package com.kiran.jvapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    RecyclerView rv_pending_notfi_list;
    private EditText edt_roll_no;
    private ListAdapter listAdapter;
    private List<UserDataModel> notification_lists;
    private LinearLayoutManager mLayoutManager;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        rv_pending_notfi_list = findViewById(R.id.rv_pending_notfi_list);
        edt_roll_no = findViewById(R.id.edt_roll_no);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        rv_pending_notfi_list.setLayoutManager(mLayoutManager);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRealm.beginTransaction();
                UserDataModel userDataModel = mRealm.createObject(UserDataModel.class);
                userDataModel.setRoll_no(i++);
                userDataModel.setStudent_name("Girish Alagundi");
                mRealm.commitTransaction();
                //


                RealmResults<UserDataModel> results = mRealm.where(UserDataModel.class).findAll();
                for (int i = 0; i < results.size(); i++)
                    notification_lists = results;

                if (results.size() > 0) {
                    listAdapter = new ListAdapter(MainActivity.this, notification_lists);
                    rv_pending_notfi_list.setAdapter(listAdapter);
                }


            }
        });


        findViewById(R.id.button_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<UserDataModel> results = mRealm.where(UserDataModel.class).findAll();

                if (results.size() == 0)
                    return;

                mRealm.beginTransaction();
                results.deleteAllFromRealm();
                mRealm.commitTransaction();

                i = 1;
                for (int i = 0; i < results.size(); i++)
                    notification_lists = results;

                rv_pending_notfi_list.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();

            }
        });


        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmResults<UserDataModel> results = mRealm.where(UserDataModel.class).equalTo("roll_no",
                        Integer.parseInt(edt_roll_no.getText().toString())).findAll();
                mRealm.beginTransaction();

                for (int i = 0; i < results.size(); i++)
                    results.get(i).setStudent_name("this row updated");

                mRealm.commitTransaction();

                notification_lists = results;
                rv_pending_notfi_list.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();

            }
        });
        findViewById(R.id.button_sort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmResults<UserDataModel> results = mRealm.where(UserDataModel.class).findAllSorted("roll_no", Sort.DESCENDING);

                for (int i = 0; i < results.size(); i++) {
                    notification_lists = results;
                    Log.d("1sortResult", Integer.toString(notification_lists.get(i).getRoll_no()));
                }
                rv_pending_notfi_list.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();

            }
        });


        findViewById(R.id.button_sort).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                RealmResults<UserDataModel> results = mRealm.where(UserDataModel.class).findAllSorted("roll_no", Sort.ASCENDING);
                for (int i = 0; i < results.size(); i++) {
                    notification_lists = results;

                    Log.d("2sortResult", Integer.toString(notification_lists.get(i).getRoll_no()));

                }




                rv_pending_notfi_list.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }


}
