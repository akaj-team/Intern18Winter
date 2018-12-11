package asiantech.internship.summer.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.Company;

public class ThreeFragment extends Fragment implements CompanyAdapter.onClickItem {
    private DBManager mDbManager;

    public ThreeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbManager = new DBManager(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        initCompany(view);
        return view;
    }

    private void initCompany(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewCompany);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDbManager.createDefaultCompany();
        List<Company> companies = mDbManager.getAllCompany();
        CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies, mRecyclerView,this);
        mRecyclerView.setAdapter(companyAdapter);
    }


    @Override
    public void onSelectItem(int idCompany) {
        Intent intent = new Intent(getActivity(), EmployeeActivity.class);
        intent.putExtra("idCompany", idCompany);
        startActivity(intent);
    }
}
