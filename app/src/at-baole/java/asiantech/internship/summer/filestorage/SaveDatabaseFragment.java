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
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.filestorage.model.Company;

public class SaveDatabaseFragment extends Fragment implements CompanyAdapter.OnClickCompany {
    public static final String ID_COMPANY = "CompanyId";
    DataAccess mDataAccess;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_database, container, false);
        mDataAccess = new DataAccess(Objects.requireNonNull(getActivity()).getApplicationContext());
        if (mDataAccess.getCompaniesCount() == 0) {
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
        List<Company> companies = mDataAccess.getAllCompany();
        mRecyclerView.setAdapter(new CompanyAdapter(companies, this, getContext()));
    }

    private void addListCompany() {
        mDataAccess.addCompany(new Company(getString(R.string.companyAsianTech)));
        mDataAccess.addCompany(new Company(getString(R.string.companySharkChau)));
        mDataAccess.addCompany(new Company(getString(R.string.companyBaoLe)));
        mDataAccess.addCompany(new Company(getString(R.string.companyTruongDinh)));
        mDataAccess.addCompany(new Company(getString(R.string.companyTranPhu)));
        mDataAccess.addCompany(new Company(getString(R.string.companyTranHung)));
    }

    @Override
    public void onSelectCompany(int companyId) {
        Intent intent = new Intent(getActivity(), EmployeeActivity.class);
        intent.putExtra(ID_COMPANY, companyId);
        startActivity(intent);
    }
}