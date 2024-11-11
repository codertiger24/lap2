package com.example.lap2try;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lap2try.Adapter.MonhocAdapter;
import com.example.lap2try.DAO.MonhocDAO;
import com.example.lap2try.Model.Monhoc;

import java.util.ArrayList;

//import fpoly.hieuttph56046.lab2hieuttph56046.Adapter.MonhocAdapter;
//import fpoly.hieuttph56046.lab2hieuttph56046.DAO.MonhocDAO;
//import fpoly.hieuttph56046.lab2hieuttph56046.Model.Monhoc;

public class MainActivity extends AppCompatActivity {

    private EditText edtTitle,edtContent,edtDate,edtType;
    private Button btnAdd;
    private RecyclerView rcListmonhoc;

    private MonhocDAO monhocDAO;
    private ArrayList<Monhoc> arraymonhoc;
    private MonhocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTitle = findViewById(R.id.edttitle);
        edtContent = findViewById(R.id.edtcontent);
        edtDate = findViewById(R.id.edtdate);
        edtType = findViewById(R.id.edttype);
        btnAdd = findViewById(R.id.btnadd);
        rcListmonhoc = findViewById(R.id.rclistmonhoc);

        monhocDAO = new MonhocDAO(MainActivity.this);
        arraymonhoc = monhocDAO.getMonhoc();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcListmonhoc.setLayoutManager(linearLayoutManager);
        adapter = new MonhocAdapter(MainActivity.this,arraymonhoc);
        rcListmonhoc.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String title = edtTitle.getText().toString();
                    String content = edtContent.getText().toString();
                    String date = edtDate.getText().toString();
                    String type = edtType.getText().toString();
                    Monhoc monhoc = new Monhoc(title,content,date,type,0);
                    boolean isSuccess = monhocDAO.addMonhoc(monhoc);
                    if (isSuccess){
                        Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else {
                        Toast.makeText(MainActivity.this, "Thất bại, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }
        });
    }

    private void loadData(){
        arraymonhoc.removeAll(arraymonhoc);
        for (Monhoc monhoc : monhocDAO.getMonhoc()){
            arraymonhoc.add(monhoc);
        }
    }
    public void delete(int index){
        monhocDAO.deleteMonhoc(arraymonhoc.get(index));
        arraymonhoc.remove(index);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
    }

    public void update(int index){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_monhoc,null);
        alert.setTitle("Sửa");
        alert.setView(view);

        EditText udtitle = view.findViewById(R.id.edtudtitle);
        EditText udcontent = view.findViewById(R.id.edtudcontent);
        EditText uddate = view.findViewById(R.id.edtuddate);
        EditText udtype = view.findViewById(R.id.edtudtype);
        Button btnoke = view.findViewById(R.id.btnoke);
        Button btncancel = view.findViewById(R.id.btncancel);

        udtitle.setText(arraymonhoc.get(index).getTitle());
        udcontent.setText(arraymonhoc.get(index).getContent());
        uddate.setText(arraymonhoc.get(index).getDate());
        udtype.setText(arraymonhoc.get(index).getType());

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String newtitle = udtitle.getText().toString();
                    String newcontent = udcontent.getText().toString();
                    String newdate = uddate.getText().toString();
                    String newtype = udtype.getText().toString();

                    arraymonhoc.get(index).setTitle(newtitle);
                    arraymonhoc.get(index).setContent(newcontent);
                    arraymonhoc.get(index).setDate(newdate);
                    arraymonhoc.get(index).setType(newtype);

                    monhocDAO.updateMonhoc(arraymonhoc.get(index));
                    adapter.notifyDataSetChanged();
                    alertDialog.dismiss();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Nhập thất bại, mời nhập lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}