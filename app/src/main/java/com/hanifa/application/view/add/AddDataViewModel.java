package com.hanifa.application.view.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hanifa.application.model.entity.Pengeluaran;
import com.hanifa.application.utils.database.DatabaseClient;
import com.hanifa.application.utils.database.daos.PengeluaranDao;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hanifatus sholiha on 22/juni/2020.
 */

public class AddDataViewModel extends AndroidViewModel {

    private PengeluaranDao pengeluaranDao;

    public AddDataViewModel(@NonNull Application application) {
        super(application);

        pengeluaranDao = DatabaseClient.getInstance(application).getAppDatabase().pengeluaranDao();
    }

    public void addPengeluaran(final String note, final int price) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Pengeluaran pengeluaran = new Pengeluaran();
                pengeluaran.keterangan = note;
                pengeluaran.harga = price;
                pengeluaranDao.insertData(pengeluaran);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void updatePengeluaran(final int uid, final String note, final int price){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                pengeluaranDao.updateData(note, price, uid);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
