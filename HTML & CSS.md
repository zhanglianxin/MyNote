# HTML & CSS

标签： CSS

---

[TOC]

---


## CSS 选择器

### 基础选择器

1. 元素选择器
2. id选择器
3. class选择器

### 组合选择器

```css
选择器1, 选择器2, 选择器n {
    属性名 : 属性值;
    ...
}
```

### 后代选择器
作用于所有后代元素
```css
父代名 后代名 {
    属性名 : 属性值;
    ...
}
```

###  子代选择器
只对子元素有效
```css
父代名 > 子代名 {
    属性名 : 属性值;
    ...
}
```

### 通配符选择器
```css
* {
    属性名 : 属性值;
    ...
}
```

### 伪类选择器
```css
选择器 : 伪类 { 
    属性名 : 属性值; 
    ...
}
```

#### 动态伪类
1. 锚点伪类
   `:link` `:visited` `:hover` `:active`
2. 用户行为伪类
   `:hover` `:active` `:focus`

#### UI元素状态伪类
`:enable` `:disable` `:checked`

#### CSS3的 :nth选择器
`:first-child` `:last-child`
`:nth-child()` `:nth-last-child`
```css
:nth-child(length); /*参数是具体数字*/
:nth-child(n); /*参数是n,n从0开始计算*/
:nth-child(n*length); /*n的倍数选择，n从0开始算*/
:nth-child(n+length); /*选择大于length后面的元素*/
:nth-child(-n+length); /*选择小于length前面的元素*/
:nth-child(n*length+1); /*表示隔几选一*/
```

`:nth-of-type()` `:nth-last-of-type()`
`:first-of-type` `:last-of-type`

`:only-child` 一个元素是它的父元素的唯一一个子元素
`:only-of-type` 一个元素他有很多个子元素，而其中只有一个子元素是唯一的

`:empty` 用来选择没有任何内容的元素

#### 否定选择器
`:not`

### 伪元素选择器
> CSS 伪元素用于向某些选择器设置特殊效果
```css
选择器 : 伪元素 { 
    属性名 : 属性值; 
    ...
}
```

`:first-line` 伪元素 用于向文本的首行设置特殊样式
`:first-letter` 伪元素 用于向文本的首字母设置特殊样式
**`:before` 伪元素 可以在元素的内容前面插入新内容**
**`:after` 伪元素 可以在元素的内容之后插入新内容**
*要插入的新内容放在content中*
`::selection` 用来改变浏览网页选中文的默认效果

```css
h1:before {
    content : url(https://www.google.com.hk/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png);
}
```

## 选择器优先级
CSS引入方式的优先级：
内联样式 > 内部样式表 > 外部样式表 > 浏览器默认样式

CSS选择器的优先级：

* id选择器 > class选择器 > 元素选择器 > 通配符选择器
* 选择器层级越多优先级越高

CSS属性优先级：

* 继承的CSS属性 < 指定的CSS属性
* 属性设置中标有`!important`规则的优先级最大

---

## 表格样式
### 表格常用属性样式
* 边距、尺寸、文本格式化、背景、边框

    * 文本格式化
        * 控制字体
            * 指定字体 font-family
            * 字体大小 font-size
            * 字体加粗 font-weight
        * 控制文本格式
            * 文本颜色 color
            * 文本排列 text-align
            * 文字修饰 text-decoration
            * 行高 line-height
            * 首行文本缩进 text-indent

* 垂直方向对齐（单元格内容的对齐方式）
    `vertical-align:top/middle/bottom;`

### 表格特有样式属性
* 边框合并
  `border-collapse:separate/collapse;`
* 边框边距
  `border-spacing:?/? ?;`

## 浮动定位
普通流定位、浮动定位、相对定位、绝对定位、固定定位

* float 属性 定义元素在哪个方向浮动
  `float:none/left/right;`
* clear 属性 定义哪边上不允许出现浮动元素
  `clear:none/left/right/both;`

## 显示
### 显示方式
一切皆为框；块级元素；内联元素/行内元素

* display 属性
  `display:none/block/inline;`

### 光标
* cursor 属性
  `cursor:default/pointer/crosshair/text/wait/help;`

## 列表样式
* 列表项标志 `list-style-type`
    * ul
        * none 无标记
        * disc 实心圆，默认
        * circle 空心圆
        * square 实心方块
    * ol
        * none 无标记
        * decimal 数字，默认
        * lower-roman 小罗马数字
        * upper-roman 大罗马数字
* 列表项图像 `list-style-image:url()`

## 定位机制
### 定位属性

| 属性          | 说明                             |
| ----------- | ------------------------------ |
| position    | static/relative/absolute/fixed |
| 偏移属性        | top/bottom/left/right:value    |
| z-index     | 设置元素的堆叠顺序                      |
| float/clear | 浮动定位属性                         |
> 使用`position`属性和偏移属性实现普通流定位、相对定位、绝对定位和固定定位
> 使用`float`属性实现浮动定位
> 其他属性为辅助属性

### 相对定位
* 元素仍保持其未定位前的形状
* 元素原本所占的空间仍保留
* 元素框会相对于它原来的位置偏移某个距离
  -设置垂直或水平位置，让元素相对于起点移动
* 设置元素为相对定位
  -`position:relative;`
  -`left/right:value;`
  -`top/bottom:value;`

### 绝对定位
* 将元素内容从普通流中完全移除，不占据空间
* 并使用偏移属性来固定该元素的位置
  -相对于最近的已定位祖先元素
  -如果元素没有已定位的祖先元素，那么它的位置相对于最初的包含块，比如body元素
* 设置元素为绝对定位
  -`position:absolute;`
  -`left:right:value;`
  -`top/bottom:value;`

### 堆叠顺序
* 一旦修改了元素的定位方式，则元素可能会发生堆叠
* 可以使用`z-index`属性来控制元素框出现的重叠顺序

### 固定定位
* 将元素的内容固定在页面的某个位置
  -元素从普通流中完全移除，不占用页面空间
  -当用户向下滚动页面时元素框并不随着移动
* 设置固定定位
  -`position:fixed;`
  -`left/top/right/bottom:value;`