package example.four.com.rxjava2test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RxJava2";
    private ImageView imageView;
    private TextView textView;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_view);
        textView = findViewById(R.id.rx_text);

        // 开始测试
        StartTest();
    }

    public void loadPic(View view) {
        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        Observable.just(url)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<String>() {
                      @Override
                      public void accept(String s) throws Exception {
                          Glide.with(MainActivity.this)
                               .load(s)
                               .placeholder(R.drawable.icon_android)
                               .error(R.drawable.icon_error)
                               .into(imageView);
                      }
                  });

    }

    public void logStrings(View view) {
        String[] strings = new String[]{"Hello", "It's me", "你是一只聪明的猪"};
        Observable.fromArray(strings)
                  .subscribe(new Consumer<String>() {
                      @Override
                      public void accept(String s) throws Exception {
                          Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT)
                               .show();
                      }
                  });

    }

    public void testTimer(View view) {
        // 10 timer
        Log.e(TAG, "-->> timer start : " + System.currentTimeMillis());
        Observable.timer(10, TimeUnit.SECONDS)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                  .subscribe(new Consumer<Long>() {
                      @Override
                      public void accept(@NonNull Long aLong) throws Exception {
                          Log.e(TAG, "-->> timer :" + aLong + " at " + System.currentTimeMillis());
                      }
                  });

    }

    public void testInterval(View view) {
        // 11 interval
        Log.e(TAG, "-->> interval start : " + System.currentTimeMillis());
        mDisposable = Observable.interval(5, 10, TimeUnit.SECONDS)
                                .observeOn(Schedulers.newThread())
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.e(TAG, "-->> interval :" + aLong + " at " + System.currentTimeMillis());
                                    }
                                });

        // 由于我们这个是间隔执行，所以当我们的Activity 都销毁的时候，实际上这个操作还依然在进行
        // 所以，我们得花点小心思让我们在不需要它的时候干掉它。我们知道subscribe()方法返回的是Disposable对象，
        // 解决办法就是用一个成员变量 mDisposable 保存它，然后在Activity#onDeatory()方法中调用它的dispose（）方法
    }

    /*
    在Activity被销毁的时候把正在 间隔执行 的Observalbe停止掉
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void StartTest() {
        // 1
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    textView.append("Observable 发送 1" + "\n");

                    Log.e(TAG, "Observable 发送 1" + "\n");
                    e.onNext(1);

                    textView.append("Observable 发送 2" + "\n");
                    Log.e(TAG, "Observable 发送 2" + "\n");
                    e.onNext(2);

                    textView.append("Observable 发送 3" + "\n");
                    Log.e(TAG, "Observable 发送 3" + "\n");
                    e.onNext(3);
                    // 虽然在发送事件3之后调用了 onComplete()，导致Observer无法接收到后续事件，但是事件发送并不受影响。
                    e.onComplete();

                    textView.append("Observable 发送 4" + "\n");
                    Log.e(TAG, "Observable 发送 4" + "\n");
                    e.onNext(4);
                }
            }

        })
                  .subscribe(new Observer<Integer>() {
                      private int i = 1;
                      private Disposable mDisposable;

                      @Override
                      public void onSubscribe(Disposable d) {
                          textView.append("onSubscribe : " + d.isDisposed() + "\n");
                          Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
                          mDisposable = d;
                      }

                      @Override
                      public void onNext(Integer integer) {
                          Log.i(TAG, "已接收事件： " + i + " --> onNext()" + " isDisposed = " + mDisposable.isDisposed() +
                                  "\n");
                          textView.append("已接收事件：  " + i + "--> onNext()" + " isDisposed = " + mDisposable.isDisposed
                                  () + "\n");
                          if (i == 2) {
                              // 2 之后的上游事件Observer不再接收
                              mDisposable.dispose();
                              Log.i(TAG, "2 之后的上游事件Observer不再接收" + " isDisposed = " + mDisposable.isDisposed() + "\n");
                              textView.append("2 之后的上游事件Observer不再接收" + " isDisposed = " + mDisposable.isDisposed() +
                                      "\n");
                          }
                          i++;
                      }

                      @Override
                      public void onError(Throwable e) {
                          Log.i(TAG, "onError: " + e.getMessage());
                          textView.append("onError: " + e.getMessage() + "\n");
                      }

                      @Override
                      public void onComplete() {
                          Log.i(TAG, "onComplete: " + "不再接收事件");
                          textView.append("onComplete: " + "不再接收事件\n");
                      }
                  });

        // 2 map
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                }
            }
        })
                  .map(new Function<Integer, String>() {

                      @Override
                      public String apply(Integer integer) throws Exception {
                          return integer * 10 + "";
                      }
                  })
                  .subscribe(new Consumer<String>() {
                      @Override
                      public void accept(String s) throws Exception {
                          Log.w(TAG, "-->> accept: " + s);
                      }
                  });

        // 3 zip
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {

            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        })
                  .subscribe(new Consumer<String>() {
                      @Override
                      public void accept(String s) throws Exception {
                          Log.e(TAG, "-->> accept: " + s);
                      }
                  });

        // 4 cancat
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> accept concat " + integer);
                      }
                  });

        // 5 flatMap
//        Observable.create(new ObservableOnSubscribe<Integer>() {
        //            @Override
        //            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        //                if (!e.isDisposed()) {
        //                    e.onNext(1);
        //                    e.onNext(2);
        //                    e.onNext(3);
        //                }
        //            }
        //        })
        //                  .flatMap(new Function<Integer, ObservableSource<String>>() {
        //                      @Override
        //                      public ObservableSource<String> apply(Integer integer) throws Exception {
        //                          ArrayList<String> list = new ArrayList<>();
        //                          for (int i = 0; i < 3; i++) {
        //                              list.add("I'm value " + i);
        //                          }
        //                          return Observable.fromIterable(list)
        //                                           .delay((long) Math.random() * 10, TimeUnit.MICROSECONDS);
        //                      }
        //                  })
        //                  .observeOn(Schedulers.newThread())
        //                  .subscribeOn(AndroidSchedulers.mainThread())
        //                  .subscribe(new Consumer<String>() {
        //                      @Override
        //                      public void accept(String s) throws Exception {
        //                          Log.w(TAG, "-->> flatmap:accept: " + s);
        //                      }
        //                  });

        // 6,concatMap
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                if (!e.isDisposed()) {
//                    e.onNext(1);
//                    e.onNext(2);
//                    e.onNext(3);
//                }
//            }
//        })
//                  .concatMap(new Function<Integer, ObservableSource<String>>() {
//                      @Override
//                      public ObservableSource<String> apply(Integer integer) throws Exception {
//                          ArrayList<String> list = new ArrayList<>();
//                          for (int i = 0; i < 3; i++) {
//                              list.add("I'm value " + i);
//                          }
//                          return Observable.fromIterable(list);
//                      }
//                  })
//                  .observeOn(Schedulers.newThread())
//                  .subscribeOn(AndroidSchedulers.mainThread())
//                  .subscribe(new Consumer<String>() {
//                      @Override
//                      public void accept(String s) throws Exception {
//                          Log.w(TAG, "-->> concatmap:accept: " + s);
//                      }
//                  });

        // 7 distinct
        Observable.just(1, 2, 2, 1, 4, 3, 4, 5)
                  .distinct()
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> accept: distinct " + integer);
                      }
                  });

        // 8 filter
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                  .filter(new Predicate<Integer>() {
                      @Override
                      public boolean test(Integer integer) throws Exception {
                          return integer % 2 == 0;
                      }
                  })
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> accept: filter " + integer);
                      }
                  });

        // 9 buffer
        Observable.just(1, 2, 3, 4, 5)
                  .buffer(3, 2)
                  .subscribe(new Consumer<List<Integer>>() {
                      @Override
                      public void accept(List<Integer> integers) throws Exception {
                          Log.e(TAG, "-->> buffer length : " + integers.size());
                          Log.e(TAG, "-->> buffer values : ");
                          for (int i = 0; i < integers.size(); i++) {
                              Log.w(TAG, "-->> " + integers.get(i));
                          }
                      }
                  });

        // 12 doOnNext()
        ArrayList<Integer> list = new ArrayList<>();
        Observable.just(1, 2, 3, 4)
                  .doOnNext(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          list.add(integer);
                      }
                  })
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> accept: " + integer);
                      }
                  });
        Log.i(TAG, "-->> 保存的value: ");
        for (Integer i : list) {
            Log.i(TAG, "-->> " + i);
        }

        // 13,skip
        Observable.just(1, 2, 3, 4, 5)
                  .skip(2)
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> skip:accept: " + integer);
                      }
                  });

        // 14,take
        Observable.just(1, 2, 3, 4, 5)
                  .take(4)
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> take:accept: " + integer);
                      }
                  });

        // 15 Single
        Single.just(new Random().nextInt(100))
              .subscribe(new SingleObserver<Integer>() {
                  @Override
                  public void onSubscribe(Disposable d) {
                      Log.w(TAG, "-->> onSubscribe: ");
                  }

                  @Override
                  public void onSuccess(Integer integer) {
                      Log.w(TAG, "-->> Single，onSuccess: integer = " + integer);
                  }

                  @Override
                  public void onError(Throwable e) {
                      Log.w(TAG, "-->> Single，onError: " + e.getMessage());
                  }
              });

        // 16 debounce
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                if (!e.isDisposed()) {
//                    e.onNext(1); // skip
//                    Thread.sleep(499);
//
//                    e.onNext(2); // deliver
//                    Thread.sleep(501);
//
//                    e.onNext(3); // skip
//                    Thread.sleep(500);
//
//                    e.onNext(4); // skip
//                    Thread.sleep(501);
//
//                    e.onNext(5); // deliver
//                    e.onComplete();
//                }
//            }
//        })
//                  .debounce(500, TimeUnit.MILLISECONDS)
//                  .subscribeOn(Schedulers.io())
//                  .observeOn(AndroidSchedulers.mainThread())
//                  .subscribe(new Consumer<Integer>() {
//                      @Override
//                      public void accept(Integer integer) throws Exception {
//                          Log.w(TAG, "-->> debounce: accept: " + integer);
//                      }
//                  });

        // 17 defer
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.fromArray(new Integer[]{1, 2, 3});
            }
        });
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.w(TAG, "-->> onSubscribe: isDisposed = " + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.w(TAG, "-->> defer:onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "-->> defer:onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.w(TAG, "-->> defer:onComplete: ");
            }
        });

        // 18 last
        Observable.just(1, 2, 3, 4)
                  .last(2)
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> last:accept: " + integer);
                      }
                  });

        // 19 merge
        Observable.merge(Observable.just(1, 2, 3, 4, 5), Observable.just(6, 7, 8, 9, 10))
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> merge:accept: " + integer);
                      }
                  });

        // 20 reduce
        Observable.just(1, 2, 3, 4)
                  .reduce(new BiFunction<Integer, Integer, Integer>() {
                      @Override
                      public Integer apply(Integer integer, Integer integer2) throws Exception {
                          return integer + integer2;
                      }
                  })
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> reduce:accept: " + integer);
                      }
                  });

        // 21 scan
        Observable.just(1, 2, 3, 4)
                  .scan(new BiFunction<Integer, Integer, Integer>() {
                      @Override
                      public Integer apply(Integer integer, Integer integer2) throws Exception {
                          return integer + integer2;
                      }
                  })
                  .subscribe(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          Log.w(TAG, "-->> scan:accept: " + integer);
                      }
                  });

        // 22 window
        Observable.interval(1, TimeUnit.SECONDS)
                  .take(15)
                  .window(3, TimeUnit.SECONDS)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<Observable<Long>>() {
                      @Override
                      public void accept(Observable<Long> longObservable) throws Exception {
                          Log.e(TAG, "-->> Sub Divide begin..." + longObservable.toString());
                          longObservable.subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<Long>() {
                                            @Override
                                            public void accept(Long aLong) throws Exception {
                                                Log.w(TAG, "-->>Next: " + aLong );
                                            }
                                        });
                      }
                  });

        // simple
        Observable.interval(10,10, TimeUnit.MILLISECONDS)
                  .sample(1, TimeUnit.SECONDS)
                  .take(10)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<Long>() {
                      @Override
                      public void accept(Long aLong) throws Exception {
                          Log.w(TAG, "-->>sample: accept: " + aLong );
                      }
                  });

    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    Log.w(TAG, "-->> string emit: A ");
                    e.onNext("A");

                    Log.w(TAG, "-->> string emit: B ");
                    e.onNext("B");

                    Log.w(TAG, "-->> string emit: C ");
                    e.onNext("C");
                }
            }
        });
    }

    private Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    Log.w(TAG, "-->> integer emit: 1 ");
                    e.onNext(1);

                    Log.w(TAG, "-->> integer emit: 2 ");
                    e.onNext(2);

                    Log.w(TAG, "-->> integer emit: 3 ");
                    e.onNext(3);

                    Log.w(TAG, "-->> integer emit: 4 ");
                    e.onNext(4);

                    Log.w(TAG, "-->> integer emit: 5 ");
                    e.onNext(5);
                }
            }
        });
    }
}
