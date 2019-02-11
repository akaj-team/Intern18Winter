package asiantech.internship.summer.filestorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Company;

public class DatabaseFragment extends Fragment implements CompanyAdapter.OnClickCompany{
    DatabaseHelper databaseCompany;
    public static final String ID_COMPANY = "idCompany";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        databaseCompany = new DatabaseHelper(getActivity());
        if(databaseCompany.getCompaniesCount() == 0){
            addListCompany();
        }
        initCompany(view);
        return view;
    }
    private void initCompany(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.rvCompany);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        List<Company> companies = databaseCompany.getAllCompany();
        mRecyclerView.setAdapter(new CompanyAdapter(companies, this));
    }
    private void addListCompany(){
        databaseCompany.addCompany(new Company("Asian Tech Company"));
        databaseCompany.addCompany(new Company("FPT Company"));
        databaseCompany.addCompany(new Company("Framgia Company"));
        databaseCompany.addCompany(new Company("Enclave Company"));
        databaseCompany.addCompany(new Company("Axon Active Company"));
        databaseCompany.addCompany(new Company("Gameloft Company"));
    }

    @Override
    public void onSelectItem(int idCompany) {
        Intent intent = new Intent(getActivity(), EmployeeActivity.class);
        intent.putExtra(ID_COMPANY, idCompany);
        startActivity(intent);
    }
}
