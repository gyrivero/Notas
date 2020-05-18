package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.NotaRepository;
import com.example.myapplication.NotaRepositoryProvider;
import com.example.myapplication.db.entity.NotaEntity;

import java.util.List;

public class NuevaNotaDialogViewModel extends AndroidViewModel {
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> notasFavoritas;
    private NotaRepository notaRepository;

    public NuevaNotaDialogViewModel(Application application) {
        super(application);
        notaRepository = NotaRepositoryProvider.getRepository(application);
        allNotas = notaRepository.getAll();
        notasFavoritas = notaRepository.getAllFavs();
    }

    //Metodo para recibir la lista de notas
    public LiveData<List<NotaEntity>> getAllNotas() {return allNotas;}

    //Metodo para recibir la lista de notas favoritas
    public LiveData<List<NotaEntity>> getNotasFavoritas() {return notasFavoritas;}

    //Metodo para crear una nueva nota
    public void insertarNota(NotaEntity notaEntity) {
        notaRepository.insert(notaEntity);
    }

    //Metodo para actualizar nota
    public void updateNota(NotaEntity notaEntity) {
        notaRepository.update(notaEntity);
    }

    //Metodo para borrar todas las notas
    public void borrarTodasNotas() {
        notaRepository.deleteAll();
    }
}
