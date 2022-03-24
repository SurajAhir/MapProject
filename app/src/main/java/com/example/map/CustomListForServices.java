package com.example.map;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.map.ServicesData.Images;
import com.example.map.ServicesData.Prices;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomListForServices extends BaseAdapter {
Context context;

    public CustomListForServices(Context context) {
        this.context = context;
    }

    @Override
        public int getCount() {
            return MainActivity.list_service.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1=LayoutInflater.from(context).inflate(R.layout.services_show,null);
            TextView name=view1.findViewById(R.id.name);
            TextView description=view1.findViewById(R.id.descripton);
            CircleImageView profileImage=view1.findViewById(R.id.profile_image);
           name.setText(MainActivity.list_service.get(i).getName());
           description.setText(MainActivity.list_service.get(i).getDescription());
           name.setSelected(true);
          description.setSelected(true);
            Log.d("Tag",i+"   "+MainActivity.list_service.get(i).getImages().length+"");
            Images[] images=MainActivity.list_service.get(i).getImages();
            if(images.length!=0&&images[0].getUrl()!=null) {
                    Picasso.get().load( images[0].getUrl()).placeholder(R.drawable.default_image).into(profileImage);

            }
            return view1;
        }

}
