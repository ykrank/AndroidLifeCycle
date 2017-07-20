# AndroidLifeCycle

[中文说明][chinese-readme]

Easy way to handle android lifecycle

**AndroidLifeCycle** is an android tool for simply add listener to lifecycle of fragment or activity.You can also listen to wrapped context if base context is activity, or listen to view bound fragment or context. 

Overview
--------
Often in android application, you need response to some lifecycle event of fragment or activity(especially onStop or onDestory to release source). In general, we override fragment or activity event method. but sometime we need bind listener in other place, then i built this tools.

The idea is simple: add a empty fragment to activity fragmentManager or fragment childFragmentManager to dispatch lifecycle event, call your listener if lifecycle event arrive.

### Motivations
There is other lifecycle library like [android arch lifecycle][android-arc-lifecycle]. but you should extend their base activity, base fragment, or implements their interface. In this library, you need not extend or implements anything, just use 
```
AndroidLifeCycle.with(fragment)
AndroidLifeCycle.with(activity)
AndroidLifeCycle.with(context)
AndroidLifeCycle.with(view)
```
then listen to lifecycle event anywhere(of course when activity or fragment is active).

Usage
--------
You can see usage in demo.

Listen mActivity :
```
AndroidLifeCycle.with(mActivity)
                .listen(ActivityEvent.START, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.START");
                    }
                })
                .listen(ActivityEvent.RESUME, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.RESUME");
                    }
                })
                .listen(ActivityEvent.PAUSE, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.PAUSE");
                    }
                })
                .listen(ActivityEvent.STOP, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.STOP");
                    }
                })
                .listen(ActivityEvent.DESTROY, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.DESTROY");
                    }
                });
```
Listen mFragment :
```
AndroidLifeCycle.with(mFragmetn5)
                        .listen(FragmentEvent.START, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.START");
                            }
                        })
                        .listen(FragmentEvent.RESUME, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.RESUME");
                            }
                        })
                        .listen(FragmentEvent.PAUSE, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.PAUSE");
                            }
                        })
                        .listen(FragmentEvent.STOP, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.STOP");
                            }
                        })
                        .listen(FragmentEvent.DESTROY_VIEW, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.DESTROY_VIEW");
                            }
                        })
                        .listen(FragmentEvent.DESTROY, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.DESTROY");
                            }
                        });
```
Bind mFragment to mView, then listen it :
```
AndroidLifeCycle.bindFragment(mView, mFragment);
// then
AndroidLifeCycle.with(mView)
        .listen(ViewEvent.START, new LifeCycleListener() {
            @Override
            public void accept() {
                l("ViewEvent.START");
            }
        })
        .listen(ViewEvent.RESUME, new LifeCycleListener() {
            @Override
            public void accept() {
                l("ViewEvent.RESUME");
            }
        })
        .listen(ViewEvent.PAUSE, new LifeCycleListener() {
            @Override
            public void accept() {
                l("ViewEvent.PAUSE");
            }
        })
        .listen(ViewEvent.STOP, new LifeCycleListener() {
            @Override
            public void accept() {
                l("ViewEvent.STOP");
            }
        })
        .listen(ViewEvent.DESTROY, new LifeCycleListener() {
            @Override
            public void accept() {
                l("ViewEvent.DESTROY");
            }
        });
```

Rxjava auto dispose
--------
See [AndroidAutoDispose][androidautodispose]

Download
--------
[![Maven Central](https://img.shields.io/maven-central/v/com.github.ykrank/androidlifecycle.svg)](https://mvnrepository.com/artifact/com.github.ykrank/androidlifecycle)

```gradle
compile 'com.github.ykrank:androidlifecycle:x.y.z'
```

License
-------
    Copyright (C) 2017 Yk Rank

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[android-arc-lifecycle]: https://developer.android.com/topic/libraries/architecture/lifecycle.html
[androidautodispose]: https://github.com/ykrank/AndroidAutoDispose
[chinese-readme]: https://github.com/ykrank/AndroidLifeCycle/blob/master/README-ZH.md