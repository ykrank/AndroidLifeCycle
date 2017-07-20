# AndroidLifeCycle
更简单添加生命周期事件监听

**AndroidLifeCycle** 是一个为Android Fragment, Activity方便添加监听的工具。你也可以为基础Context是Activity的Context, 或者已绑定Activity或者Fragment的View添加监听。

概述
--------
在Android应用的很多时候，你需要响应一些来自Fragment或者Activity的生命周期回调（特别是在onStop和onDestory中释放资源）。一般情况下，我们都是直接在Fragment或者Activity的实现中重写相关事件，但有时我们需要在对象外部绑定和解绑生命周期事件。为了应对这种情况，我开发了这个工具。

思路很简单：为activity的fragmentManager或者fragment的childFragmentManager添加一个空的fragment来分发生命周期事件，在对应事件来到时调用你注册的回调。

### 动机
之前也有一些生命周期框架，如[android arch lifecycle][android-arc-lifecycle]。但是它们都要求你继承它们的基础activity或者基础fragment，或者在你的基础类中实现他们的生命周期分发接口。但是在这个库中，你不需要基础activity或者fragment继承或者实现任何东西，只需要在使用时用以下方式绑定就好：

```
AndroidLifeCycle.with(fragment)
AndroidLifeCycle.with(activity)
AndroidLifeCycle.with(context)
AndroidLifeCycle.with(view)
```
然后就可以在任何地方添加回调了（当然要在对应组件生命周期之类绑定）。

使用方法
--------
你也可以参照demo中的使用方法。

监听 mActivity :
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
监听 mFragment :
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
将mFragment和mView绑定，然后监听：
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

Rxjava 的自动解绑
--------
参照 [AndroidAutoDispose][androidautodispose]

下载
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