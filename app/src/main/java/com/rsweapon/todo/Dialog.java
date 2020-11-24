package com.rsweapon.todo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Dialog  extends AppCompatDialogFragment {
    EditText title,post;
    private ProgressDialog dialog;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.loding_dialog,null);
        title=view.findViewById(R.id.title);
        post=view.findViewById(R.id.post);
        builder.setView(view)
                .setTitle("ToDO")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference Myref=database.getReference("todo");
                        Map<Object,String>map=new HashMap<>();
                        map.put("title",title.getText().toString());
                        map.put("post",post.getText().toString());
                        String id= UUID.randomUUID().toString();

                        dialog = new ProgressDialog(getContext());
                        dialog.setTitle("ToDO Store  ");
                        dialog.setMessage("Please Wait ........");
                        dialog.setCanceledOnTouchOutside(true);
                        dialog.show();
                        Myref.child(id).setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(view.getContext(), "Data save", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                });


                    }
                });

        return builder.create();
    }
}
