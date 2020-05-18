package com.example.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.db.entity.NotaEntity;
import com.example.myapplication.ui.MyNotaRecyclerViewAdapter;
import com.example.myapplication.viewmodel.NuevaNotaDialogViewModel;

import java.util.stream.Collector;

public class EditarNotaDialogFragment extends DialogFragment {

    public static EditarNotaDialogFragment newInstance() {
        return new EditarNotaDialogFragment();
    }

    public EditarNotaDialogFragment() {}

    View view;
    private EditText tituloET, contenidoET;
    private Switch favoritaSW;
    private RadioGroup colorRG;
    private int idNota;
    private String titulo;
    private String contenido;
    private String color;
    private boolean favorita;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        titulo = bundle.getString("titulo");
        contenido = bundle.getString("contenido");
        color = bundle.getString("color");
        favorita = bundle.getBoolean("favorita");
        idNota = bundle.getInt("id");


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva Nota")
                .setMessage("Introduzca los datos de la nueva nota")
                .setPositiveButton("Guardar la nota", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String titulo = tituloET.getText().toString();
                        String contenido = contenidoET.getText().toString();
                        boolean favorita = favoritaSW.isChecked();
                        String color;
                        switch (colorRG.getCheckedRadioButtonId()) {
                            case R.id.azulRB:
                                color = "azul";
                                break;
                            case R.id.rojoRB:
                                color = "rojo";
                                break;
                            case R.id.verdeRB:
                                color = "verde";
                                break;
                            default:
                                color = "blanco";
                                break;
                        }
                        NuevaNotaDialogViewModel mViewModel = new ViewModelProvider(getActivity()).get(NuevaNotaDialogViewModel.class);
                        NotaEntity nota = mViewModel.getAllNotas().getValue().stream().filter(notaEntity -> notaEntity.getId()==idNota).findAny().orElse(null);
                        nota.setFavorita(favorita);
                        nota.setColor(color);
                        nota.setContenido(contenido);
                        nota.setTitulo(titulo);
                        mViewModel.updateNota(nota);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.editar_nota_dialog_fragment,null);

        tituloET = view.findViewById(R.id.tituloET);
        contenidoET = view.findViewById(R.id.contenidoET);
        colorRG = view.findViewById(R.id.colorRG);
        favoritaSW = view.findViewById(R.id.favoritaSW);

        tituloET.setText(titulo);
        contenidoET.setText(contenido);
        favoritaSW.setChecked(favorita);
        switch (color) {
            case "azul":
                colorRG.check(R.id.azulRB);
                break;
            case "rojo":
                colorRG.check(R.id.rojoRB);
                break;
            case "verde":
                colorRG.check(R.id.verdeRB);
                break;
        }

        builder.setView(view);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
