package com.example.linm.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);

        final EditText TITLE = (EditText)findViewById(R.id.title);
        final EditText MAIN = (EditText)findViewById(R.id.main);
        final Button SAVE = (Button)findViewById(R.id.save);
        final Button LOAD = (Button)findViewById(R.id.load);
        final Button CLEAR = (Button)findViewById(R.id.clear);
        final Button DELETE = (Button)findViewById(R.id.delete);

        SAVE.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String title = TITLE.getText().toString();
                String main = MAIN.getText().toString();
                try(FileOutputStream fileOutputStream = openFileOutput(title,MODE_PRIVATE)){
                    fileOutputStream.write(main.getBytes()); //将数据写入文件
                    fileOutputStream.flush(); //将所有剩下的数据写入文件
                    fileOutputStream.close(); //关闭
                    Toast.makeText(FileEdit.this, "Save Successfully",Toast.LENGTH_SHORT).show();
                }
                catch (IOException ex){
                    Log.e("debug","Fail to save file");
                }
            }
        });

        LOAD.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String title = TITLE.getText().toString();
                try(FileInputStream fileInputStream = openFileInput(title)){
                    Log.e("debug","Success to load");
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    fileInputStream.close();
                    MAIN.setText(new String(contents));
                    Toast.makeText(FileEdit.this, "Load Successfully",Toast.LENGTH_SHORT).show();

                }
                catch (IOException ex){
                    MAIN.setText("");
                    Toast.makeText(FileEdit.this, "Failed to load",Toast.LENGTH_SHORT).show();
                    Log.e("debug","Failed to load");
                }
            }
        });

        CLEAR.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                MAIN.setText("");
            }
        });

        DELETE.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String title = TITLE.getText().toString();
                deleteFile(title);
                Toast.makeText(FileEdit.this, "Delete successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
