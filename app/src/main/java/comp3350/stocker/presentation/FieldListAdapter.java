package comp3350.stocker.presentation;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import comp3350.stocker.R;



public class FieldListAdapter extends RecyclerView.Adapter<FieldListAdapter.ViewHolder>{

    static List<String[]> listData;
    boolean orderView = false;
    boolean dataToDisplay;

    // RecyclerView recyclerView;
    public FieldListAdapter(List<String[]> listData) {
        this.listData = listData;
        if(listData.get(0)[1] == ""){
            dataToDisplay = false;
        }
        else{
            dataToDisplay = true;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.field_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String[] fieldData = listData.get(position);

        holder.textView.setText(fieldData[0]);

        if(position == 0 && dataToDisplay ){
            holder.editText.setVisibility(View.INVISIBLE);
            holder.idText.setVisibility(View.VISIBLE);

            holder.idText.setText(fieldData[1]);
        }
        else{
            holder.idText.setVisibility(View.INVISIBLE);
            holder.editText.setVisibility(View.VISIBLE);

            holder.editText.setText(fieldData[1]);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2019-03-06 Ava determine if any function is desirable onClick(I don't think so, but this function must be overridden anyways)
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText editText;
        public TextView idText;
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.editText = (EditText) itemView.findViewById(R.id.editText);
            this.idText = (TextView) itemView.findViewById(R.id.idText);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                //each time the text is changed, the value in the listData is updated to current String
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    listData.get(getAdapterPosition())[1] = editText.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
