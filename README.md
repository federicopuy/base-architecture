
![build](https://img.shields.io/circleci/build/github/federicopuy/base-architecture?token=f8d47cecc853e0364ebb9a75b9e557bba4b7d603)
![version](https://img.shields.io/bintray/v/federicopuy/BaseArchitecture/core)

# Base Architecture

This repository contains 4 Android modules, each of which serves a different purpose:

1) **core ->** contains base architecture components to create any Android App. Provides a set of abstract implementations for Activities and ViewModels, hence avoiding the boilerplate code needed to create and setup these.

2) **networking** ->  set of helper methods to perform api calls and manage the status of their responses.

3) **core-test** -> helper methods to make the testing of LiveData and Coroutines components easier and with reduced boilerplate. Also includes Mockito extensions to simplify testing.

4) **app** -> just a really simple test app, it's only purpose is testing the rest of the modules in this repository.

## Getting started

Just import the module that you require in your gradle file:

```groovy
implementation 'com.android.federicopuy.basearchitecture:core:x.y.z'
```
```groovy
implementation 'com.android.federicopuy.basearchitecture:networking:x.y.z'
```
```groovy
implementation 'com.android.federicopuy.basearchitecture:core-test:x.y.z'
```

(Please replace with the latest version number: ![version](https://img.shields.io/bintray/v/federicopuy/BaseArchitecture/core))
