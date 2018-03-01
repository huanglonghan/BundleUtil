# BundleUtil
简化Bundle数据传递

###Demo
##Kotlin
```Kotlin
//构建附带数据的Intent
Intene intent = new BundleUtil().write(url).write(9).write(IntArray()).bind(new Intent(context, DownloadService.class))

BundleUtil bundleUtil = new BundleUtil();
//解析数据
if (!bundleUtil.praise(intent)) {
    return;
}
//读取附加数据
String url = bundleUtil.read();
int number1 = bundleUtil.read();
IntArray array1 = bundleUtil.read();
```
