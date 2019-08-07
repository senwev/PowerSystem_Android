package com.eecso.powersystem;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CirCuit cirCuitBoard;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cirCuitBoard = (CirCuit) findViewById(R.id.CircuitBoard);
        Button button = (Button) findViewById(R.id.button);
        Button button1 = (Button) findViewById(R.id.calcu);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int num= cirCuitBoard.compoList.size();
               if(num>0)
               {
                   float x=0;
                   for(int i=0;i<num;i++)
                   {
                       if(cirCuitBoard.compoList.get(i) instanceof EEGener)
                       {
                           x+=0.15;
                       }
                       else if(cirCuitBoard.compoList.get(i) instanceof EETrans)
                       {
                           x+=0.3;
                       }
                       else if(cirCuitBoard.compoList.get(i) instanceof EELoad)
                       {
                           x+=0.8;
                       }
                   }
                   Toast.makeText(MainActivity.this,"电流标幺值为："+String.valueOf(1f/x),Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Toast.makeText(MainActivity.this,"还没有元件！",Toast.LENGTH_SHORT).show();
               }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinner.getSelectedItemPosition())
                {
                    case 0:
                        cirCuitBoard.addComponent(CirCuit.TYPE_GENER);
                        break;
                    case 1:
                        cirCuitBoard.addComponent(CirCuit.TYPE_TRANS);
                        break;
                    case 2:
                        cirCuitBoard.addComponent(CirCuit.TYPE_LOAD);
                        break;
                    case 3:
                        cirCuitBoard.removeAllComponent();
                        break;
                }
            }
        });



        String[] ctype = new String[]{"发电机", "变压器", "电力负载","清除全部！"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        spinner = super.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu); //通过getMenuInflater()方法得到MenuInflater对象，再调用它的inflate()方法就可以给当前活动创建菜单了，第一个参数：用于指定我们通过哪一个资源文件来创建菜单；第二个参数：用于指定我们的菜单项将添加到哪一个Menu对象当中。
        return true; // true：允许创建的菜单显示出来，false：创建的菜单将无法显示。
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.id_add_item:
                Toast.makeText(this, "已导出到/EECSO/output.png", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_remove_item:
                Toast.makeText(this, "创建失败，拓扑识别错误！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.visit:
                Uri uri = Uri.parse("http://www.eecso.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }
}
