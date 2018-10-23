package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button create_database;
    private Button addData;
    private Button updateData;
    private Button DeleteData;
    private Button QueryData;








    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new MyDatabaseHelper(this,"BookStore.db",null,1);

        create_database = findViewById(R.id.create_database);
        create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.getWritableDatabase();
            }
        });

        //添加数据：
        addData = findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //开始组装第一条数据：
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",666);
                values.put("price",9.9);
                //添加一条数据
                db.insert("Book",null,values);
                values.clear();

                //开始组装第二条数据：
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",555);
                values.put("price",18.1);

                //添加一条数据
                db.insert("Book",null,values);
                Toast.makeText(getApplicationContext(),"数据添加成功",Toast.LENGTH_SHORT).show();

            }
        });

        //修改数据
        updateData = findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10);
                //Book 是表名,values 是数值，第三第四个参数是来指定具体更新的，其中第三个对应SQL语句中的Where部分，？是一个占位符。第四个参数就是用来提供占位符的内容的。
                db.update("Book",values,"name=?",new String[]{"The Da Vinci Code"});
                Toast.makeText(getApplicationContext(),"数据修改成功",Toast.LENGTH_SHORT).show();
                //另一种添加数据的方式：
//                db.execSQL("insert into Book(nea,author,pages,price) values(?,?,?,?)"
//                ,new String[]{"The Da Vinci Code","Dan Brown","454","10"});
            }
        });
        //删除数据
        DeleteData = findViewById(R.id.Delete_data);
        DeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                //Book  表名    第二个参数为SQL条件Where   第三个参数是占位符的匹配内容
                db.delete("Book","pages > ?",new String[]{"556"});
                Toast.makeText(getApplicationContext(),"数据删除成功",Toast.LENGTH_SHORT).show();
            }
        });

        //查询数据
        QueryData = findViewById(R.id.Query_data);
        QueryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                //查询Book表中的所有数据
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()) {
                    do{
                        //遍历Cursor对象，取出数据并打印
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("MainActivity","book id is"+ id);
                        Log.d("MainActivity","book name is"+ name);
                        Log.d("MainActivity","book author is"+ author);
                        Log.d("MainActivity","book pages is"+ pages);
                        Log.d("MainActivity","book prices is"+ price);


                    }while (cursor.moveToNext());
                      cursor.close();
                }
            }
        });


    }
}
