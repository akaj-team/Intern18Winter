package asiantech.internship.summer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.model.Item;

public class DrawerLayoutActivity extends AppCompatActivity {
    private int mAvatar;
    private String mEmail;
    private int mDropdown;
    private int mBtnItem;
    private String mTvItem;
    private List<Item> mItem;
    private RecyclerView mRecyclerView;
    View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        initView();
    }

    public void initView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        setData();
        ItemAdapter itemAdapter = new ItemAdapter(mItem, this.getApplicationContext());
        mRecyclerView.setAdapter(itemAdapter);
    }

    public void setData(){
        mItem = new ArrayList<>();
        mItem.add(new Item(R.drawable.img_avatar_default, getString(R.string.Email), R.drawable.ic_arrow_drop_down_black_18dp));
        mItem.add(new Item(R.drawable.ic_move_to_inbox_black_18dp, getString(R.string.Inbox)));
        mItem.add(new Item(R.drawable.ic_send_black_18dp, getString(R.string.Outbox)));
        mItem.add(new Item(R.drawable.ic_delete_black_18dp, getString(R.string.Trash)));
        mItem.add(new Item(R.drawable.ic_warning_black_18dp, getString(R.string.Spam)));
    }
}
