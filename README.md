# XwLogLib

## Android File Log Library

### Function

```XwLog.initXwLog(this, logDirName = "TestProject", logFileName = "Good.txt", useFileLog = true)```

1. 로그 디렉토리, 로그 파일 이름을 지정할 수 있습니다.
2. useFileLog ? "파일로그 사용" : "android.util.Log 사용"
3. Request Permission은 내부에서 요청합니다.

### Support Function

```XwLog.d(TAG, strMessage)```

```XwLog.e(TAG, strMessage)```

```XwLog.v(TAG, strMessage)```

```XwLog.w((TAG, strMessage)```


### Settings

build.gradle(project)
```
allprojects {
    repositories {
        ....
        maven { url 'https://jitpack.io' }
    }
}
```

build.gradle(Module: app)
```
implementation 'com.github.hinos-repo:XwLogLib:1.0
```
