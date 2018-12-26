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
import asiantech.internship.summer.filestorage.model.Company;

public class SaveDatabaseFragment extends Fragment implements CompanyAdapter.OnClickCompany{
    DatabaseHelper databaseHelper;
    List<Company> mCompanies;
    public static final String ID_COMPANY = "idCompany";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_database, container, false);
        databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        if(databaseHelper.getCompaniesCount() == 0){
            addListCompany();
        }
        initCompany(view);
        return view;
    }
    private void initCompany(View view) {
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerViewCompany);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        List<Company> companies = databaseHelper.getAllCompany();
        mRecyclerView.setAdapter(new CompanyAdapter(companies, this));
    }
    private void addListCompany(){
        databaseHelper.addCompany(new Company("Asian Tech Company"));
        databaseHelper.addCompany(new Company("FPT Company"));
        databaseHelper.addCompany(new Company("Framgia Company"));
        databaseHelper.addCompany(new Company("Enclave Company"));
        databaseHelper.addCompany(new Company("Axon Active Company"));
        databaseHelper.addCompany(new Company("Gameloft Company"));
    }

    @Override
    public void onSelectItem(int idCompany) {
        Intent intent = new Intent(getActivity(), EmployeeActivity.class);
        intent.putExtra(ID_COMPANY, idCompany);
        startActivity(intent);
    }
}