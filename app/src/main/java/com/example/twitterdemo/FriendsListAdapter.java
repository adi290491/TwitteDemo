package com.example.twitterdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by aditya.sawant on 08-11-2016.
 */
public class FriendsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> lsUserList;
    private LayoutInflater inflater;

    public FriendsListAdapter(Context context, List<User> users) {
        mContext = context;
        lsUserList = users;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return lsUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return lsUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friends_list_item, parent, false);
            holder = new ViewHolder();
            holder.userName = (TextView) convertView.findViewById(R.id.tv_user_name);
            holder.screenName = (TextView) convertView.findViewById(R.id.tv_screen_name);
            holder.userProfilePic = (ImageView) convertView.findViewById(R.id.iv_profile_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = lsUserList.get(position);
        holder.userName.setText(user.name);
        holder.screenName.setText(user.screenName);
        String profileImageUrl = user.profileImageUrl;
        Glide.with(mContext).load(profileImageUrl).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.userProfilePic);

        return convertView;
    }


    class ViewHolder {

        private TextView userName, screenName;
        private ImageView userProfilePic;

    }


}

