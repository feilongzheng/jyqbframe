package com.dfppm.giveu.view.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dfppm.giveu.R;
import com.dfppm.giveu.base.ParentAdapter;

import java.util.List;

/**
 * Created by zhengfeilong on 16/5/16.
 */
public class CustomListDialog extends CustomDialog {
    AdapterView.OnItemClickListener onItemClickListener;
    ItemAdapter itemAdapter;

    public CustomListDialog(Context context, AdapterView.OnItemClickListener onItemClickListener) {
        super(context, R.layout.custom_list_dialog, R.style.login_error_dialog_Style, Gravity.CENTER, false);

        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected WindowManager.LayoutParams getAttrs(WindowManager.LayoutParams windowParams, View contentView) {
        ListView lv = (ListView) contentView.findViewById(R.id.lv);

        itemAdapter = new ItemAdapter(mContext);
        lv.setAdapter(itemAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();

                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
        return super.getAttrs(windowParams, contentView);
    }

    public void setData(List<CharSequence> stringList){
        itemAdapter.setItemList(stringList);
    }
    public List<CharSequence> getData(){
        return itemAdapter.getItemList();
    }

    class ItemAdapter extends ParentAdapter<CharSequence> {

        public ItemAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = View.inflate(mContext, R.layout.custom_list_dialog_item, null);
            }
            TextView tv = getViewFromViewHolder(convertView, R.id.tv);

            CharSequence item = getItem(position);
            tv.setText(item);
            return convertView;
        }
    }


}
