package com.example.four.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;// 百度提供的自定控件，用于显示地图
    private BaiduMap mBaiduMap;// 地图的总控制器
    private BitmapDescriptor mMarker; //显示定位点
    private LocationClient mLocationClient; //定位类
    private boolean isFirstLoc = true;//是否是第一次定位
    private static final String TAG = "MainActivity";
    private static int i = 1;
    private List<String> permissionList = new ArrayList<>();

    private OrientationSensor mOrientationSensor;
    private float driection = 0;// 传感器方向


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());// 初始化操作一定在setContentView()方法前调用。
        setContentView(R.layout.activity_main);
        if (checkAllPermission()) { // 检查权限是否都获取了
            initLoc(); //初始化定位相关的设置
            mLocationClient.start();  //开始定位
        } else {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
//        checkPermissionByRxPermissions();
    }


    /**
     * 定位设置
     */

    private void initLoc() {
        mMapView = (MapView) findViewById(R.id.baidu_mapView);//地图显示控件 同时需要处理它的生命周期
        mBaiduMap = mMapView.getMap();//获取BadiuMap实例 地图控制器类
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(mBDLocationListener);//注册定位回调
        mOrientationSensor = new OrientationSensor(this);
        mOrientationSensor.start();
        mOrientationSensor.setOrientationListener(new OrientationSensor.OrientationSensorListener() {
            @Override// 注册传感器回调
            public void getOrientation(float x) {
                driection = x;
            }
        });
        mBaiduMap.setMyLocationEnabled(true);//开启定位图层， 只有先允许定位图层后设置数据才会生效
        LocationClientOption option = new LocationClientOption();//定位相关参数设置
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        //共有三种坐标可选
        //1. gcj02：国测局坐标；
        //2. bd09：百度墨卡托坐标；
        //3. bd09ll：百度经纬度坐标；

        option.setScanSpan(2000);
        //可选，设置发起定位请求的间隔时间

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照2S/1次频率输出GPS结果: option.setScanSpan(2000);

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        //加载设置
        mLocationClient.setLocOption(option);

    }

    //定位成功后回调该方法
    private BDLocationListener mBDLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //bdLocation封装了定位相关的各种信息
            //mapview 销毁后不在处理新接收的位置
            if (bdLocation == null || mBaiduMap == null) {
                return;
            }

            Log.d(TAG, "SUCCESS: ");
            //            Toast.makeText(MainActivity.this, "onReceiveLocation执行次数：" + i++, Toast.LENGTH_SHORT).show();
            getLocationInfo(bdLocation);

            //配置定位图层显示方式
            //有两个不同的构造方法重载 分别为三个参数和五个参数的
            //这里主要讲一下常用的三个参数的构造方法
            //三个参数：
            // LocationMode(定位模式：罗盘，跟随)
            // enableDirection（是否允许显示方向信息）
            // customMarker（自定义图标）
            mMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_start1);//设置用于显示当前位置的图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true, mMarker);
            mBaiduMap.setMyLocationConfiguration(configuration);

            //第一次定位需要更新下地图显示状态
            if (isFirstLoc) {
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder()
                        .target(ll)//地图缩放中心点
                        .zoom(18f);//缩放倍数 百度地图支持缩放21级 部分特殊图层为20级
                MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(builder.build());
                mBaiduMap.setMapStatus(update); //改变地图状态
                isFirstLoc = false;
            }


            //构造定位数据
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())//定位精度，Radius 半径
                    .latitude(bdLocation.getLatitude())//纬度
                    .longitude(bdLocation.getLongitude())//经度
                    .direction(driection)//方向 可利用手机方向传感器获取 此处为方便写死
                    .build();
            //设置定位数据
            mBaiduMap.setMyLocationData(data);

        }
    };

    public boolean checkAllPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.BODY_SENSORS);
        }
        if (!permissionList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    public void checkPermissionByRxPermissions() {
        /**
         * 以下为多个权限一次性授权
         */
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.BODY_SENSORS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                        if (aBoolean) {
                            // 仅当全部授权才为true
                            initLoc(); //初始化定位相关的设置
                            mLocationClient.start();  //开始定位
                        } else {
                            // 含有未授权的权限
                            Toast.makeText(MainActivity.this, "您拒绝了应用授权，无法使用权限！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

        /**
         * 以下为一个个权限依次授权。
         */
        //        RxPermissions rxPermissions = new RxPermissions(this);
        //        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.BODY_SENSORS)
        //                .subscribe(new Consumer<Permission>() {
        //                    int i = 1;
        //                    @Override
        //                    public void accept(Permission permission) throws Exception {
        //                        // 经过测试，每申请一个权限，会调用一次该方法。
        //                        if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
        //                            if (permission.granted) {
        //                                Toast.makeText(MainActivity.this, "FINE_LOCATION", Toast.LENGTH_SHORT).show();
        //                            } else {
        //                                Toast.makeText(MainActivity.this, "error 0", Toast.LENGTH_SHORT).show();
        //                                finish();
        //                            }
        //                        } else if (permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
        //                            if (permission.granted) {
        //                                Toast.makeText(MainActivity.this, "READ_PHOTO", Toast.LENGTH_SHORT).show();
        //                            } else {
        //                                Toast.makeText(MainActivity.this, "error 1", Toast.LENGTH_SHORT).show();
        //                                finish();
        //                            }
        //                        } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        //                            if (permission.granted) {
        //                                Toast.makeText(MainActivity.this, "WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
        //                            } else {
        //                                Toast.makeText(MainActivity.this, "error 2", Toast.LENGTH_SHORT).show();
        //                                finish();
        //                            }
        //                        } else if (permission.name.equals(Manifest.permission.BODY_SENSORS)) {
        //                            if (permission.granted) {
        //                                Toast.makeText(MainActivity.this, "BODY_SENSORS", Toast.LENGTH_SHORT).show();
        //                            } else {
        //                                Toast.makeText(MainActivity.this, "error 3", Toast.LENGTH_SHORT).show();
        //                                finish();
        //                            }
        //                        }
        //                        // 在确定所有权限被授权后开始写逻辑。或者获得一个权限就可以进行一小步逻辑操作。
        //                    }
        //                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

        //反注册OrientationSensor
        if (mOrientationSensor != null)
            mOrientationSensor.stop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    //初始化定位相关
                    initLoc();
                    //开始定位
                    mLocationClient.start();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void getLocationInfo(BDLocation location) {
        //Receive Location
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息,结果类似于“在北京天安门附近”
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Log.i(TAG, sb.toString());
    }
}



