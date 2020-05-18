package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.NotaRepository;
import com.example.myapplication.NotaRepositoryProvider;
import com.example.myapplication.R;
import com.example.myapplication.db.entity.NotaEntity;
import com.example.myapplication.viewmodel.NuevaNotaDialogViewModel;

//Clase para visualizar la nota y poder modificarla de forma independiente
public class ContenidoNota extends AppCompatActivity {

    private EditText tituloNotaET;
    private EditText contenidoNotaET;
    private Button aceptarBtn;
    private Button cancelarBtn;
    private String tituloNota;
    private String contenidoNota;
    private int idNota;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contenido_nota);

        Bundle bundle = getIntent().getExtras();
        tituloNota = bundle.getString("titulo");
        contenidoNota = bundle.getString("contenido");
        idNota = bundle.getInt("id");

        tituloNotaET = findViewById(R.id.tituloNotaET);
        contenidoNotaET = findViewById(R.id.contenidoNotaET);
        aceptarBtn = findViewById(R.id.aceptarBtn);
        cancelarBtn = findViewById(R.id.cancelarBtn);

        tituloNotaET.setText(tituloNota);
        contenidoNotaET.setText(contenidoNota);

        aceptarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Debe recuperar la instancia del repositorio, que se utiliza en el DashboardActivity!
                NotaRepository notaRepository = NotaRepositoryProvider.getRepository(getApplication());
                NotaEntity nota = notaRepository.getAll().getValue().stream().filter(notaEntity -> notaEntity.getId()==idNota).findAny().orElse(null);
                nota.setTitulo(tituloNotaET.getText().toString());
                nota.setContenido(contenidoNotaET.getText().toString());
                notaRepository.update(nota);
                finish();
            }
        });

        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
