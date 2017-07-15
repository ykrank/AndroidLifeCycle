# AndroidLifeCycle
===========
Easy way to handle android lifecycle

**AndroidLifeCycle** is an android tool for simply add listener to lifecycle of fragment or activity.You can also listen to wrapped context if base context is activity, or listen to view bound fragment or context. Support rxjava2 auto dispose.

Overview
--------
Often android application, you need response to some lifecycle event of fragment or activity(especially onStop or onDestory to release source). In general, we override fragment or activity event method. but sometime we need bind listener in other place, then i built this tools.

The idea is simple: add a empty fragment to activity fragmentManager or fragment childFragmentManager to dispatch lifecycle event, call your listener if lifecycle event arrive.

### Motivations
There is other lifecycle library like [android arch lifecycle][1]. but you should extend their base activity, base fragment, or implements their interface. In this library, you need not extend or implements anything, just use 
```
AndroidLifeCycle.with(fragment)
AndroidLifeCycle.with(activity)
AndroidLifeCycle.with(context)
AndroidLifeCycle.with(view)
```
then listen to lifecycle event anywhere.

Work with Rxjava
--------
Do not support Rxjava1.
You can add rxjava dispose support library, and then just
```
myThingObservable
    .to(AndroidRxDispose.withObservable(lifecycleContext))
    .subscribe(...)
```
myThingObservable can be Rxjava Observable, Single, Completable, Maybe, Flowable.
lifecycleContext can be Activity, Fragment, View

Download
--------

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

[1]: https://developer.android.com/topic/libraries/architecture/lifecycle.html