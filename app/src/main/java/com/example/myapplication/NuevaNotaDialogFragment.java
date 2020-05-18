package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.myapplication.db.entity.NotaEntity;
import com.example.myapplication.viewmodel.NuevaNotaDialogViewModel;

public class NuevaNotaDialogFragment extends DialogFragment {

    public static NuevaNotaDialogFragment newInstance() {
        return new NuevaNotaDialogFragment();
    }

    View view;
    private EditText tituloET, contenidoET;
    private Switch favoritaSW;
    private RadioGroup colorRG;

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
                        mViewModel.insertarNota(new NotaEntity(titulo,contenido,favorita,color));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.nueva_nota_dialog_fragment,null);

        tituloET = view.findViewById(R.id.tituloET);
        contenidoET = view.findViewById(R.id.contenidoET);
        colorRG = view.findViewById(R.id.colorRG);
        favoritaSW = view.findViewById(R.id.favoritaSW);

        builder.setView(view);


        // Create the AlertDialog object and return it
        return builder.create();
    }
}
