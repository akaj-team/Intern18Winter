package asiantech.internship.summer.file_storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import asiantech.internship.summer.R;
import asiantech.internship.summer.model.Company;

public class DatabaseFragment extends Fragment implements CompanyAdapter.OnclickNameCompany {
    private Database mDatabase;
    private List<Company> mListCompany;
    private RecyclerView mRecyclerViewCompany;
    private CompanyAdapter mCompanyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        mDatabase = new Database(Objects.requireNonNull(getActivity()).getApplicationContext());
        if (mDatabase.getAllCompany().size() == 0) {
            addListCompany();
        }
        initView(view);
        return view;
    }

    public void addListCompany() {
        mDatabase.insertCompany("Asiantech");
        mDatabase.insertCompany("Logigear");
        mDatabase.insertCompany("Fpt");
        mDatabase.insertCompany("MGM");
        mDatabase.insertCompany("Zenken");
        mDatabase.insertCompany("Enclave");
    }

    private void initView(View view) {
        mListCompany = mDatabase.getAllCompany();
        mRecyclerViewCompany = view.findViewById(R.id.recyclerViewCompany);
        mRecyclerViewCompany.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCompany.setLayoutManager(linearLayoutManager);
        mCompanyAdapter = new CompanyAdapter(mListCompany, Objects.requireNonNull(getActivity()).getApplicationContext(), this);
        mRecyclerViewCompany.setAdapter(mCompanyAdapter);
    }

    @Override
    public void viewEmployee(int position) {
        Company company = mListCompany.get(position);
        Intent intent = new Intent(getActivity(), EmployeeActivity.class);
        intent.putExtra("position", company.getId());
        String name = company.getName();
        intent.putExtra("name_company", name);
        startActivity(intent);
    }
}
