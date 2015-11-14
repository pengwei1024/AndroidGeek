/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.umeng.comm.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.Comment;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.imageloader.ImgDisplayOption;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners.FetchListener;
import com.umeng.comm.core.listeners.Listeners.LoginOnViewClickListener;
import com.umeng.comm.core.nets.responses.SimpleResponse;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.core.utils.ResFinder.ResType;
import com.umeng.comm.core.utils.TimeUtils;
import com.umeng.comm.ui.activities.UserInfoActivity;
import com.umeng.comm.ui.adapters.viewholders.FeedCommentViewHolder;
import com.umeng.comm.ui.utils.FeedViewRender;
import com.umeng.comm.ui.widgets.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Feed评论Adapter
 */
public class FeedCommentAdapter extends CommonAdapter<Comment, FeedCommentViewHolder> {
    private static Object mLock = new Object();
    private String mColon;
    private String mReplyText;
    private Context mContext;
    public FeedCommentAdapter(Context context) {
        super(context);
        mColon = ResFinder.getString("umeng_comm_colon");
        mReplyText = ResFinder.getString("umeng_comm_reply");
        this.mContext = context;
    }

    @Override
    protected FeedCommentViewHolder createViewHolder() {
        return new FeedCommentViewHolder();
    }

    private void renderCommentText(TextView textView, Comment comment) {
        // 设置评论昵称,普通形式为AA:评论内容,当回复回复某条评论时则是“AA回复BB”的形式
        String prefix = prepareCommentPrefix(comment);
        // 设置评论的内容
        textView.setText(prefix + comment.text);
        FeedViewRender.renderFriendText(mContext, textView, prepareRelativeUsers(comment));
    }

    private void setCommentCreator(NetworkImageView imageView, Comment comment) {
        ImgDisplayOption option = ImgDisplayOption.getOptionByGender(comment.creator.gender);
        imageView.setImageUrl(comment.creator.iconUrl, option);
        // 设置头像的点击事件,跳转到用户个人主页
        setClickFriendIconListener(imageView, comment.creator);
    }

    /**
     * 评论或者回复评论时涉及到的用户,如果是普通评论则只涉及评论的创建者,或者是回复评论,那么还涉及到被回复的对象
     *
     * @param comment
     * @return
     */
    private List<CommUser> prepareRelativeUsers(Comment comment) {
        List<CommUser> users = new ArrayList<CommUser>();
        users.add(comment.creator);
        if (isReplyCommemt(comment)) {
            users.add(comment.replyUser);
        }
        return users;
    }

    private boolean isReplyCommemt(Comment comment) {
        return comment.replyUser != null
                && !TextUtils.isEmpty(comment.replyUser.name);
    }

    private String prepareCommentPrefix(Comment comment) {
        String text = "";
        // 如果有回复用户且该用户不是自己
        if (isReplyCommemt(comment)) {
            text += mReplyText + comment.replyUser.name + mColon;
        }
        return text;
    }

    private void clickAnima(View targetView) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f);
        scaleAnimation.setDuration(100);
        targetView.startAnimation(scaleAnimation);
    }

    /**
     * 设置点击评论中好友icon的逻辑，跳转至相关好友的个人中心
     *
     * @param iconImageView 显示头像的ImageView
     * @param user 创建该评论的用户
     */
    private void setClickFriendIconListener(final ImageView iconImageView, final CommUser user) {
        iconImageView.setOnClickListener(new LoginOnViewClickListener() {
            @Override
            protected void doAfterLogin(View v) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.putExtra(Constants.TAG_USER, user);
                mContext.startActivity(intent);
            }
        });
    }

    private void setLikeIconOnClick(final TextView likeIconTv , final Comment comment){

        likeIconTv.setOnClickListener(new LoginOnViewClickListener() {

            @Override
            protected void doAfterLogin(final View v) {
                synchronized (mLock) {
                    CommunityFactory.getCommSDK(mContext).likeComment(comment, new FetchListener<SimpleResponse>() {
                        @Override
                        public void onStart() {

                            clickAnima(v);
                        }

                        @Override
                        public void onComplete(SimpleResponse response) {
                            if (response != null) {
                                if (ErrorCode.NO_ERROR == response.errCode) {
                                    if (comment.liked) {
                                        comment.liked = false;
                                        comment.likeCount--;
                                        likeIconTv.setCompoundDrawablesWithIntrinsicBounds(ResFinder.getResourceId(ResType.DRAWABLE, "umeng_comm_like_normal"), 0, 0, 0);

                                    } else {
                                        comment.liked = true;
                                        comment.likeCount++;
                                        likeIconTv.setCompoundDrawablesWithIntrinsicBounds(ResFinder.getResourceId(ResType.DRAWABLE, "umeng_comm_like_pressed"), 0, 0, 0);
                                    }
                                    likeIconTv.setText(comment.likeCount + "");
                                    comment.saveEntity();
                                }else if(ErrorCode.ERROR_COMMENT_LIKED==response.errCode){
                                    comment.liked=true;
                                    comment.likeCount++;
                                    comment.saveEntity();
                                    likeIconTv.setText(comment.likeCount + "");
                                    likeIconTv.setCompoundDrawablesWithIntrinsicBounds(ResFinder.getResourceId(ResType.DRAWABLE, "umeng_comm_like_pressed"), 0, 0, 0);
                                }else if(ErrorCode.ERROR_COMMENT_LIKE_CANCELED==response.errCode){
                                    comment.liked=false;
                                    comment.likeCount--;
                                    comment.saveEntity();
                                    likeIconTv.setText(comment.likeCount + "");
                                    likeIconTv.setCompoundDrawablesWithIntrinsicBounds(ResFinder.getResourceId(ResType.DRAWABLE, "umeng_comm_like_normal"), 0, 0, 0);
                                }
                            }
                        }
                    });
                }
            }});
    }

//    if(response.errCode==ErrorCode.NO_ERROR){
//        Log.d("like","success");
//        Toast.makeText(mContext,"成功",Toast.LENGTH_SHORT).show();
//    } else if(response.errCode==ErrorCode.ERROR_COMMENT_DELETE){
//        Toast.makeText(mContext, "点赞失败:"+ResFinder.getString("umeng_comm_comment_deleted"), Toast.LENGTH_SHORT).show();
//    }else if(ErrorCode.ERROR_COMMENT_LIKE_CANCELED==response.errCode){
//        Toast.makeText(mContext, "赞已被取消", Toast.LENGTH_SHORT).show();
//    }
//    else if(ErrorCode.ERROR_COMMENT_LIKED==response.errCode){
//        if(comment.liked){
//            Toast.makeText(mContext, "点赞失败:"+ResFinder.getString("umeng_comm_comment_liked"), Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(mContext, "取消点赞失败:"+ResFinder.getString("umeng_comm_comment_liked_cancel"), Toast.LENGTH_SHORT).show();
//        }
//
//    }



    @Override
    protected void setItemData(int position, FeedCommentViewHolder holder, View rootView) {
        final Comment comment = getItem(position);
        // 渲染评论文本
        renderCommentText(holder.contentTextView, comment);
        // 设置评论创建者的头像和头像图标的点击事件
        setCommentCreator(holder.userHeaderImageView, comment);
        setLikeIconOnClick(holder.likeCountTextView, comment);
        holder.userNameTextView.setText(comment.creator.name);
        holder.publishTimeTextView.setText(TimeUtils.format(comment.createTime));
        holder.likeCountTextView.setText(comment.likeCount+"");
        if(CommonUtils.isLogin(mContext)&& comment.liked){
            holder.likeCountTextView.setCompoundDrawablesWithIntrinsicBounds(
                    ResFinder.getResourceId(ResType.DRAWABLE, "umeng_comm_like_pressed"), 0, 0, 0);
        }else{
            holder.likeCountTextView.setCompoundDrawablesWithIntrinsicBounds(
                    ResFinder.getResourceId(ResType.DRAWABLE, "umeng_comm_like_normal"), 0, 0, 0);
        }
    }
}
