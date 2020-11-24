package com.rsweapon.todo;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class ModelAdpter extends FirebaseRecyclerAdapter<Model,ModelAdpter.viewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ModelAdpter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Model model) {
holder.post.setText("''"+model.getPost()+"''");
holder.title.setText(model.getTitle());
holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View view) {
        DialogPlus dialogPlus=DialogPlus.newDialog(view.getContext())
                .setGravity(Gravity.CENTER)
                .setMargin(50,0,50,0)
                .setContentHolder(new ViewHolder(R.layout.update))
                .setExpanded(false)
                .create();

        View holderView=(LinearLayout)dialogPlus.getHolderView();

        EditText title=holderView.findViewById(R.id.title);

        EditText post=holderView.findViewById(R.id.post);



        title.setText(model.getTitle());
        post.setText(model.getPost());


        Button Update=holderView.findViewById(R.id.update);
        Button delete=holderView.findViewById(R.id.delete);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map=new HashMap<>();
                map.put("title",title.getText().toString());
                map.put("post",post.getText().toString());



                FirebaseDatabase.getInstance().getReference()
                        .child("todo")
                        .child(getRef(position).getKey())

                        .updateChildren(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialogPlus.dismiss();
                            }
                        });


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference()
                        .child("todo").child(getRef(position).getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(view.getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });
            }
        });

        dialogPlus.show();
        return true;
    }
});

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return  new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView post,title;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            post=itemView.findViewById(R.id.post);
            title=itemView.findViewById(R.id.title);
        }
    }
}


