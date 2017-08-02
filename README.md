# glide-support
基于[glide](https://github.com/bumptech/glide)与
[android-gif-drawable](https://github.com/koral--/android-gif-drawable)对Glide处理Gif进行加强

## __示例apk__
![](screenshot/example-download.png)

## __快速开始__
####  Gradle
```
compile 'com.dyhdyh.support:glide-plus:1.0.1'
```

#### 示例
```
GlidePlus.with(this)
                .gifPlus()//开启gif增强
                .circle()//圆形
                //.crossFade()//淡入淡出(默认开启)
                //.transform(new ImageWrapperCircleTransformation())
                //.animate(new DrawableScaleBounceAnimator())
                .glide()
                .load(url)
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.error)
                .into(iv);
```

## __效果__
![](screenshot/screenshot.gif)

## __对比图__
![](screenshot/example-compared-small.png)

