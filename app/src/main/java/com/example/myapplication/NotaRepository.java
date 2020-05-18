package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.db.NotaRoomDatabase;
import com.example.myapplication.db.dao.NotaDao;
import com.example.myapplication.db.entity.NotaEntity;

import java.util.List;

public class NotaRepository {
    private NotaDao notaDao;

    public NotaRepository(Application application) {
        NotaRoomDatabase db = NotaRoomDatabase.getDatabase(application);
        notaDao = db.notaDao();
    }

    public LiveData<List<NotaEntity>> getAll() {
        return notaDao.getAll();
    }

    public LiveData<List<NotaEntity>> getAllFavs() {
        return notaDao.getAllFavoritas();
    }

    public void insert(NotaEntity nota) {
        new InsertAsyncTask(notaDao).execute(nota);
    }

    public void update(NotaEntity nota) {
        new UpdateAsyncTask(notaDao).execute(nota);
    }

    public void deleteAll() {
        new DeleteAsyncTask(notaDao).execute();
    }

    public void deleteById(int id) {
        notaDao.deleteById(id);
    }

    private static class DeleteAsyncTask extends AsyncTask<NotaEntity,Void,Void> {
        private NotaDao notaDaoAsync;

        public DeleteAsyncTask(NotaDao notaDaoAsync) {
            this.notaDaoAsync = notaDaoAsync;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsync.deleteAll();
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<NotaEntity, Void, Void> {
        private NotaDao notaDaoAsync;

        public UpdateAsyncTask(NotaDao notaDaoAsync) {
            this.notaDaoAsync = notaDaoAsync;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsync.update(notaEntities[0]);
            return null;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<NotaEntity, Void, Void> {
        private NotaDao notaDaoAsync;

        public InsertAsyncTask(NotaDao notaDaoAsync) {
            this.notaDaoAsync = notaDaoAsync;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsync.insert(notaEntities[0]);
            return null;
        }
    }
}
