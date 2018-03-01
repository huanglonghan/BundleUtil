# BundleUtil
简化Bundle数据传递
Intene intent = new BundleUtil().write(url).bind(new Intent(context, DownloadService.class))

BundleUtil bundleUtil = new BundleUtil();
if (!bundleUtil.praise(intent)) {
    return;
}
String url = bundleUtil.read(0);
