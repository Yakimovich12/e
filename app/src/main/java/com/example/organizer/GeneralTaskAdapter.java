package com.example.organizer;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CheckBox;
import android.content.ContextWrapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GeneralTaskAdapter extends ArrayAdapter <GeneralTask>
{
    private LayoutInflater inflater;
    private int layout;
    private List<GeneralTask> gtl;
    public GeneralTaskAdapter(Context context,int resource,List<GeneralTask> gtl0)
    {
        super(context,resource,gtl0);
        this.gtl=gtl0;
        this.inflater=LayoutInflater.from(context);
        this.layout=resource;
    }
    public View getView(int position,View convertView,ViewGroup parent)
    {
        if(convertView==null)
        {
            convertView=inflater.inflate(this.layout,parent,false);
        }

        TextView nameT=(TextView)convertView.findViewById(R.id.name);
        TextView deadlineT=(TextView)convertView.findViewById(R.id.deadline);
        CheckBox flagT=(CheckBox)convertView.findViewById(R.id.flag);
        GeneralTask genT=gtl.get(position);
        nameT.setText(genT.getName());
        deadlineT.setText((genT.getDate().getYear()+"/"+genT.getDate().getMonth()+"/"+genT.getDate().getDay()));
        flagT.setChecked(genT.getFlag());
        flagT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                    {
                        genT.setFlag(isChecked);
                        writeTaskFile(getContext(),genT);////////////////////////
                    }

        }
        );

        return convertView;
    }
   public static void writeTaskFile(Context context,GeneralTask genT)  {//////////////////////////
        context.deleteFile(genT.getName()+".txt");
        FileOutputStream fou=null;
        try {
            fou=context.openFileOutput((genT.getName()+".txt"),MODE_PRIVATE);
            fou.write((genT.getName()+"///"+genT.getExplanation()+"///"+genT.getFlag()+"///"+
                    genT.getDate().getYear()+"::"+genT.getDate().getMonth()+"::"+genT.getDate().getDay()+"::").getBytes());
        } catch (IOException e) { }
        finally
        {
            try
            {
                if(fou!=null)
                    fou.close();
            }
            catch(IOException e){}
        }
    }

}
