package asiantech.internship.summer.file_storage;

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
import asiantech.internship.summer.model.Company;

public class DatabaseFragment extends Fragment implements CompanyAdapter.OnclickNameCompany {
    private Database mDatabase;
    private List<Company> mListCompany;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        mDatabase = new Database(Objects.requireNonNull(getActivity()).getApplicationContext());
        if (mDatabase.getAllCompany().size() == 0) {
            addListCompany();
        }
        initView(view);
        return view;
    }

    public void addListCompany() {
        mDatabase.insertCompany(getString(R.string.asiantech));
        mDatabase.insertCompany(getString(R.string.logigear));
        mDatabase.insertCompany(getString(R.string.fpt));
        mDatabase.insertCompany(getString(R.string.mgm));
        mDatabase.insertCompany(getString(R.string.zenken));
        mDatabase.insertCompany(getString(R.string.enclave));
    }

    private void initView(View view) {
        mListCompany = mDatabase.getAllCompany();
        RecyclerView recyclerViewCompany = view.findViewById(R.id.recyclerViewCompany);
        recyclerViewCompany.setHasFixedSize(true);
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewCompany.setLayoutManager(linearLayoutManager);*/
        recyclerViewCompany.setLayoutManager(new LinearLayoutManager(getActivity()));
        CompanyAdapter companyAdapter = new CompanyAdapter(mListCompany, this);
        recyclerViewCompany.setAdapter(companyAdapter);
    }

    @Override
    public void viewEmployee(int position) {
        Company company = mListCompany.get(position);
        Intent intent = new Intent(getActivity(), EmployeeActivity.class);
        intent.putExtra(getString(R.string.position), company.getId());
        intent.putExtra(getString(R.string.nameCompany), company.getName());
        startActivity(intent);
    }
}
