package com.hanifa.application.view.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hanifa.application.adapter.PengeluaranAdapter;
import com.hanifa.application.databinding.ActivityMainBinding;
import com.hanifa.application.model.entity.Pengeluaran;
import com.hanifa.application.utils.FunctionHelper;
import com.hanifa.application.view.add.AddDataActivity;

import java.util.ArrayList;
import java.util.List;

import static com.hanifa.application.databinding.ActivityMainBinding.inflate;

/**
 * Created by Hanifatus sholiha on 22/juni/2020.
 */

public class MainActivity extends AppCompatActivity
        implements PengeluaranAdapter.PengeluaranAdapterCallback {

    private ActivityMainBinding binding;
    private PengeluaranAdapter pengeluaranAdapter;
    private MainViewModel mainViewModel;

    private List<Pengeluaran> mPengeluarans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initAdapter();
        observeData();

        initAction();
    }

    private void initAction() {
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDataActivity.startActivity(MainActivity.this, false,
                        null);
            }
        });

        binding.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.deleteAllData();
                binding.tvTotal.setText("0");
            }
        });
    }

    private void initAdapter() {
        pengeluaranAdapter = new PengeluaranAdapter(this, mPengeluarans, this);
        binding.rvPengeluarans.setLayoutManager(new LinearLayoutManager(this));
        binding.rvPengeluarans.setItemAnimator(new DefaultItemAnimator());
        binding.rvPengeluarans.setAdapter(pengeluaranAdapter);
    }

    private void observeData() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getPengeluarans().observe(this,
                new Observer<List<Pengeluaran>>() {
                    @Override
                    public void onChanged(List<Pengeluaran> pengeluarans) {
                        if (pengeluarans.isEmpty()) {
                            binding.btnHapus.setVisibility(View.GONE);
                        } else {
                            binding.btnHapus.setVisibility(View.VISIBLE);
                        }

                        pengeluaranAdapter.addData(pengeluarans);
                    }
                });

        mainViewModel.getTotalPrice().observe(this,
                new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer == null) {
                            int totalPrice = 0;
                            String initPrice = FunctionHelper.rupiahFormat(totalPrice);
                            binding.tvTotal.setText(initPrice);
                        } else {
                            int totalPrice = integer;
                            String initPrice = FunctionHelper.rupiahFormat(totalPrice);
                            binding.tvTotal.setText(initPrice);
                        }
                    }
                });
    }

    @Override
    public void onEdit(Pengeluaran pengeluaran) {
        AddDataActivity.startActivity(this, true, pengeluaran);
    }

    @Override
    public void onDelete(Pengeluaran pengeluaran) {
        int uid = pengeluaran.uid;
        mainViewModel.deleteSingleData(uid);
    }
}
