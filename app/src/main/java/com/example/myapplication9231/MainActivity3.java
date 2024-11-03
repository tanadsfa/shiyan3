package com.example.myapplication9231;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ActionMode actionMode;
    private List<Integer> selectedItems = new ArrayList<>();  // 存储选中的项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        listView = findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // 启用多选模式

        // 定义示例数据
        String[] items = {"One", "Two", "Three", "Four", "Five"};

        // 创建 ArrayAdapter 并绑定到 ListView
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.text, items);
        listView.setAdapter(adapter);

        // 设置长按监听器，触发 ActionMode
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    return false;  // 如果已经在 ActionMode 中，则不再处理
                }
                toggleSelection(position);  // 切换选中状态
                actionMode = startActionMode(actionModeCallback);  // 启动 ActionMode
                return true;
            }
        });

        // 设置点击监听器，支持单击选择
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    toggleSelection(position);  // 切换选中状态
                    actionMode.setTitle(selectedItems.size() + " selected");  // 更新标题
                }
            }
        });
    }

    // 切换选中状态
    private void toggleSelection(int position) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(Integer.valueOf(position));  // 如果已选中则取消选中
            listView.setItemChecked(position, false);  // 取消选中
        } else {
            selectedItems.add(position);  // 如果未选中则添加到选中项列表
            listView.setItemChecked(position, true);  // 设置为选中状态
        }
    }

    // 定义 ActionMode 回调
    private final ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);  // 加载上下文菜单
            mode.setTitle(selectedItems.size() + " selected");  // 设置标题
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menu_delete) {
                // 删除选中的列表项
                for (int i = selectedItems.size() - 1; i >= 0; i--) {
                    int position = selectedItems.get(i);
                    String itemToDelete = adapter.getItem(position);
                    adapter.remove(itemToDelete);
                }
                Toast.makeText(MainActivity3.this, "删除了 " + selectedItems.size() + " 项", Toast.LENGTH_SHORT).show();
                mode.finish();  // 退出 ActionMode
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            selectedItems.clear();  // 清空选中项
            for (int i = 0; i < listView.getCount(); i++) {
                listView.setItemChecked(i, false);  // 清除所有项的选中状态
            }
        }
    };
}
