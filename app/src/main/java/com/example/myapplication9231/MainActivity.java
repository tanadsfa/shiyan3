// MainActivity.java
package com.example.myapplication9231;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private int[] images = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant};
    private String[] names = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // 创建列表数据
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", images[i]);
            item.put("text", names[i]);
            data.add(item);
        }

        // 创建SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.list_item,
                new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text}
        );

        listView.setAdapter(adapter);

        // 设置列表项点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = names[position];
                showCustomToast(selectedItem);
            }
        });
    }

    /**
     * 显示自定义的 Toast
     *
     * @param message 要显示的消息
     */
    private void showCustomToast(String message) {
        // 获取LayoutInflater
        LayoutInflater inflater = getLayoutInflater();

        // 加载自定义Toast布局
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));

        // 设置文本内容
        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        // 创建并显示 Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        // 居中显示toast.setGravity(Gravity.CENTER, 0, 0);
        // 设置 Toast 位置为屏幕底部，并添加一些偏移量
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100); // 100 是y轴偏移量，可以根据需要调整
        toast.show();
    }
}
