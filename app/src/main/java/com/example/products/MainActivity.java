package com.example.products;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName, editDescription, editPrice, editTextId;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;
    Button btnviewUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editName = (EditText)findViewById(R.id.editText_name);
        editDescription = (EditText)findViewById(R.id.editText_description);
        editPrice = (EditText)findViewById(R.id.editText_price);
        editTextId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDB.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDB.updateDate(editTextId.getText().toString(),
                                editName.getText().toString(),
                                editDescription.getText().toString(),
                                editPrice.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDB.insertData(editName.getText().toString(),
                                editDescription.getText().toString(),
                                editPrice.getText().toString());
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDB.getAllData();
                        if(res.getCount() == 0) {
//                            Show message
                            showMessage("Error", "No data found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0)+"\n");
                            buffer.append("Name :" + res.getString(1)+"\n");
                            buffer.append("Description :" + res.getString(2)+"\n");
                            buffer.append("Price :" + res.getString(3)+"\n\n");
                        }
//                        Show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title, String Messsage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Messsage);
        builder.show();
    }
}