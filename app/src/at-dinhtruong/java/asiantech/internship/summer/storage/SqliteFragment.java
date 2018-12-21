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

public class SqliteFragment extends Fragment implements CompanyAdapter.OnItemClickListener {
    private DBManager mDBManager;
    public static final String ID_COMPANY = "idCompany";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDBManager = new DBManager(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sqlite, container, false);
        initCompany(view);
        return view;
    }

    private void initCompany(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewCompany);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDBManager.createDefaultCompany();
        List<Company> companies = mDBManager.getAllCompany();
        CompanyAdapter companyAdapter = new CompanyAdapter(companies,this);
        mRecyclerView.setAdapter(companyAdapter);
    }


    @Override
    public void onItemClicked(int idCompany) {
        Intent intent = new Intent(getActivity(), EmployeeActivity.class);
        intent.putExtra(ID_COMPANY, idCompany);
        startActivity(intent);
    }
}
