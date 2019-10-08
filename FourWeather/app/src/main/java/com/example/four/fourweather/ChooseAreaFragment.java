package com.example.four.fourweather;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.four.fourweather.db.City;
import com.example.four.fourweather.db.County;
import com.example.four.fourweather.db.Province;
import com.example.four.fourweather.util.HttpUtil;
import com.example.four.fourweather.util.ToastUtil;
import com.example.four.fourweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/20 0020.
 */

public class ChooseAreaFragment extends Fragment {
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();       // ListView中显示的数据
    private List<Province> provinceList; // 省列表
    private List<County> countyList;     // 县列表
    private List<City> cityList;         // 市列表
    private Province selectedProvince;   // 省列表中选中的省份
    private City selectedCity;           // 市列表中选中的市
    private int currentLevel;            // 当前选中的级别

    private static final String TAG = "ChooseAreaFragment";

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 碎片的视图
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    /**
     * @param savedInstanceState 这个方法在确保与碎片相关联的活动一定已经创建完毕的时候调用，碎片里应该默认显示省份数据，然后由用户一级一级的往下筛选，
     *                           所以设置了监听器监听用户的筛选操作
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = countyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity) {
                        Log.i(TAG, "weatherId: " + weatherId);
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof WeatherActivity) {
                        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
                        weatherActivity.drawerLayout.closeDrawer(Gravity.START);
                        weatherActivity.swipeRefresh.setRefreshing(true);
                        weatherActivity.resquestWeather(weatherId);
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库中查找，如果在数据库中没有查询到，再向服务器发送请求查询
     */
    public void queryProvinces() {
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    /**
     * 查询全省所有的市，优先从数据库中查找，如果在数据库中没有查询到，再向服务器发送请求查询
     */
    public void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }

    }

    /**
     * 查询全市所有的县，优先从数据库中查找，如果在数据库中没有查询到，再向服务器发送请求查询
     */
    public void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            currentLevel = LEVEL_COUNTY;
            listView.setSelection(0);
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    /**
     * @param address 地址
     * @param type    类型
     *                根据传入的地址和类型从服务器上查询、解析、保存省市县数据
     *                解析和保存操作由Utility类中相应方法负责完成
     */
    public void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendHttpUrlConnectionResquest(address, new HttpCallbackListener() {
            @Override
            public void onResponse(String response) {
                boolean isHandledOk = false;
                if ("province".equals(type)) {
                    isHandledOk = Utility.handleProvinceResponse(response);
                } else if ("city".equals(type)) {
                    isHandledOk = Utility.handleCityResponse(response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    isHandledOk = Utility.handleCountyResponse(response, selectedCity.getId());
                }
                if (isHandledOk) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        ToastUtil.showToast(getContext(), "加载失败，请重试！", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
//                HttpUtil.sendOkHttpRequest(address, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                closeProgressDialog();
//                                ToastUtil.showToast(getContext(), "加载失败，请重试！", Toast.LENGTH_SHORT);
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String responseText = response.body().string();
//                        Log.i(TAG, "哈哈哈onResponse: " + responseText);
//                        boolean result = false;
//                        if ("province".equals(type)) {
//                            result = Utility.handleProvinceResponse(responseText);
//                        } else if ("city".equals(type)) {
//                            result = Utility.handleCityResponse(responseText, selectedProvince.getId());
//                        } else if ("county".equals(type)) {
//                            result = Utility.handleCountyResponse(responseText, selectedCity.getId());
//                        }
//                        if (result) {
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    closeProgressDialog();
//                                    if ("province".equals(type)) {
//                                        queryProvinces();
//                                    } else if ("city".equals(type)) {
//                                        queryCities();
//                                    } else if ("county".equals(type)) {
//                                        queryCounties();
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在奋力的加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }


    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
