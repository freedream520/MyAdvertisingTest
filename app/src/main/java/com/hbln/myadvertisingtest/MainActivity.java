package com.hbln.myadvertisingtest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbln.myadvertisingtest.Config.ShareConstants;
import com.hbln.myadvertisingtest.Controller.NetController;
import com.hbln.myadvertisingtest.Controller.NetControllerDAO;
import com.hbln.myadvertisingtest.Controller.iCallbackResult;
import com.hbln.myadvertisingtest.FrameWork.BaseActivity;
import com.hbln.myadvertisingtest.FrameWork.BaseRecyclerAdapter;
import com.hbln.myadvertisingtest.FrameWork.UtilFactory;
import com.hbln.myadvertisingtest.Model.Travel;
import com.hbln.myadvertisingtest.Model.UserInfo;
import com.hbln.myadvertisingtest.Tool.Logs;
import com.hbln.myadvertisingtest.Tool.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private NetController controller;
    private BaseRecyclerAdapter<Travel> adapter;
    private List<Travel> travelList;
    private List<UserInfo> userList;
    private String usertime;
    private String xlid;
    //    private RecyclerView rv_user;
    private TextView tv_userinfo;
    //    private BaseRecyclerAdapter<UserInfo> rvUserInfoBaseRecyclerAdapter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        tv_userinfo = (TextView) findViewById(R.id.tv_userinfo);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        controller = new NetControllerDAO();
//        getTraveList();
        getDataUpdata();
    }

    private void getDataUpdata() {
        controller.getFirstDataUpdata(new iCallbackResult() {
            @Override
            public void success(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    int userUdpata = object.getInt("userupdata");
                    if (userUdpata == 1) {
                        getUserList();
                    }
                    int xlupdata = object.getInt("xlupdata");
                    if (xlupdata == 1) {
                        getTraveList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getTraveList() {
        controller.travelInfo(new iCallbackResult() {
            @Override
            public void success(String result) {
//                result = result.substring(1, result.length() - 1);
                try {
                    JSONObject object = new JSONObject(result);
                    Gson gson = new Gson();
                    travelList = gson.fromJson(object.getString("info")
                            , new TypeToken<List<Travel>>() {
                            }.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Logs.e(getClass(), "travelList.size()", travelList.size(), "travelList.toString()", travelList.toString());
                xlid = travelList.get(0).getId();
                UtilFactory.getSharedPreferencesUtil().put(ShareConstants.XLID, xlid);
                adapter = new BaseRecyclerAdapter<Travel>(getContext(), travelList, R.layout.item_recycler) {
                    @Override
                    public void convert(MyViewHolder viewHolder, Travel item, int position) {
                        viewHolder.setImageWithUrl(R.id.iv_item_rv_image, item.getImg());
                        viewHolder.setText(R.id.tv_item_rv_title, item.getTitle());
                        viewHolder.setText(R.id.tv_item_rv_date, String.format("出行时间：%s", item.getShijian()));
                        viewHolder.setText(R.id.tv_item_rv_price, String.format("优惠价格：%s元", item.getPrice()));
                    }
                };
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
                recyclerView.setAdapter(adapter);
                //开启线程刷新数据
                runThread.start();
            }
        });
    }


    public void getUserList() {
        controller.getUserList(new iCallbackResult() {
            @Override
            public void success(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Gson gson = new Gson();
                    userList = gson.fromJson(object.getString("info")
                            , new TypeToken<List<UserInfo>>() {
                            }.getType());
                    Logs.e(getClass(), "userList.size()", userList.size(), "userList.toString()", userList.toString());
                    usertime = userList.get(userList.size() - 1).getCreate_time();
                    UtilFactory.getSharedPreferencesUtil().put(ShareConstants.USER_TIME, usertime);
//                    rvUserInfoBaseRecyclerAdapter = new BaseRecyclerAdapter<UserInfo>(getContext(), userList, R.layout.item_main_list) {
//                        @Override
//                        public void convert(MyViewHolder viewHolder, UserInfo item, int position) {
//                            viewHolder.setText(R.id.tv_item_rv_user, item.getNickname() + "在" + TimeUtil.getInstance().getTime("yyyy年MM月dd日", item.getCreate_time() + "000") + "关注了天天游世界的微信公共号");
//                        }
//                    };
//                    rv_user.setLayoutManager(new LinearLayoutManager(getContext()));
//                    rv_user.setAdapter(rvUserInfoBaseRecyclerAdapter);
                    StringBuilder sUserinfo = new StringBuilder("");
                    index = 3;
                    for (int i = 0; i < index; i++) {
                        if (i > userList.size() - 1) {
                            continue;
                        }
                        UserInfo userInfo = userList.get(i);
                        sUserinfo.append(userInfo.getNickname()).append("在").append(TimeUtil.getInstance().getTime("yyyy年MM月dd日", userInfo.getCreate_time() + "000")).append("关注了天天游世界的微信公共号\n");
                    }
                    tv_userinfo.setText(sUserinfo.toString());
                    runThread.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Thread runThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(8 * 1000);
                    //线路信息刷新
                    final Travel travel = adapter.getData(1);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.removeData(0);
                            adapter.addDataLast(travel);
                        }
                    });

                    //用户更新
//                    if (index == rvUserInfoBaseRecyclerAdapter.getItemCount() - 1) {
//                        index = -1;
//                    }
//                    rv_user.smoothScrollToPosition(++index);

                    final StringBuilder sUserinfo = new StringBuilder("");
                    index++;
                    if (index <= userList.size()) {
                        for (int i = index - 3; i < index; i++) {
                            if (i > userList.size() - 1) {
                                continue;
                            }
                            UserInfo userInfo = userList.get(i);
                            sUserinfo.append(userInfo.getNickname()).append("在").append(TimeUtil.getInstance().getTime("yyyy年MM月dd日", userInfo.getCreate_time() + "000")).append("关注了天天游世界的微信公共号\n");
                        }
                        tv_userinfo.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_userinfo.setText(sUserinfo.toString());
                            }
                        });
                    } else {
                        index--;
                    }
                    getDataUpdata();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    );

}
