# JavaScript

tags： javascript

---

[TOC]

---

## JavaScript 对象

* 内置对象

* 自定义对象

* 浏览器对象

* HTML DOM 对象

* ...

### 常用内置对象

* 简单数据对象

  * String Number Boolean

* 组合对象

  * Array Math Date

* 高级对象

  * Function RegExp

### String 对象的常用方法

* 大小写转换

  * x.toLowerCase()

  * x.toUpperCase()

* 获取指定字符

  * x.charAt(index)

  * x.charCodeAt(index) // 返回指定位置字符的 Unicode 编码

* 查询指定字符串

  * x.indexOf(findStr, [index])

  * x.lastIndexOf(findStr, [index])

* 获取子字符串

  * x.substring(start, [end]) // 不包含尾

  * x.substr(start, [end])    // 包含尾

* 替换子字符串

  * x.replace(findstr, tostr) // 返回替换后的字符串

* 拆分子字符串

  * x.split(bystr, [howmany]) // 分割用的字符串，可以指定返回的**数组**的最大长度

### String 对象与正则表达式

* x.replace(regexp, tostr) // 返回替换后的结果

* x.match(regexp) // 返回匹配字符串的**数组**

* x.search(regexp) // 返回匹配字符串的首字符位置索引

### Array 对象的常用方法

* 数组转换为字符串

  * x.join([bystr])

  * x.toString()

* 连接数组

  * x.concat(value, ...)

    * value 作为数组元素连接到数组的末尾

    * 返回连接后的数组

    * concat() 方法并不改变 x 自身的值

* 获取子数组

  * x.slice(start, [end]) // 不包括尾

* 反向数组

  * x.reverse()

* 数组排序

  * x.sort([sortfunc]) // 用来确定元素顺序的函数的名称

### Number 对象

* 数值转换为字符串

  * toString()

  * toFixed(num) // 保留小数点后一定位数

### Math 对象的常用方法

* 三角函数

* 反三角函数

* 计算函数

  * Math.sqrt(x)

  * Math.log(x)

  * Math.exp(x)

* 数值比较函数

  * Math.abs(x)

  * Math.max(x, y, ...)

  * Math.random()

  * Math.round(x)

### Data 对象的常用方法

```javascript
getDate() getDay() getFullYear()
setDate() setDay() setFullYear()

toString() 
toLocaleTimeString() 
toLocaleDateString()
```

### RegExp 对象

* 创建正则表达式对象

  * `var rgExp = /pattern /flags;`

  * `var rgExp = new RegExp("pattern", ["flags"]);`

* flags 标识有以下几个：

  * g 设定当前匹配为全局模式

  * i 忽略匹配中的大小写检测

  * m 多行搜索模式

### RegExp 对象的常用方法

```javascript
reg.compile(pattern, [flags]) // 编译正则表达式
reg.exec(str) // 检索字符串中指定的值，返回找到的值，并确定其位置
reg.test(str) // 检索字符串中指定的值返回 true 或 false
```

### 函数与 Function 对象

`arguments` 是一种特殊的对象，在函数代码中，表示函数的参数数组。

```javascripte
arguments.length // 函数的参数个数
arguments[i] // 第i个参数
```

使用 Function 对象创建函数

```javascript
var functionName = new Function(arg1, ..., argN, functionBody);
```

匿名函数

```javascript
var func = function(arg1, ..., argN) {
    func_body;
}
```

### 常用的全局函数

* parseInt()/parseFloat()

* isNaN()

* eval()

* decodeURI()/encodeURI()

`eval()` 函数用于计算某个字符串，以得到结果；或者用于执行其中的 Javascript 代码。

  * 只接受原始字符串作为参数

  * 如果参数中没有合法的表达式和语句，则抛出异常

  ```javascript
  var str = "2 + 3";
  alert(str); // 2 + 3
  alert(eval(str)); // 5
  // ...
  var str1 = "alert('hello')";
  eval(str1); // hello
  ```

`encodeURI()`：把字符串作为 URI 进行编码

`decodeURI()`：对 `encodeURI()` 函数编码过的 URI 进行解码


## Window 对象

> 表示浏览器窗口
>
> 所有 JavaScript 全局对象、函数以及变量均自动成为 window 对象的成员

* 常用属性：

  document：窗口中显示的 HTML 文档对象

  history：浏览过窗口的历史记录对象

  location：窗口文件地址对象

  name：窗口名称

  opener：打开当前窗口的 window 对象

* 常用方法

  alert() confirm() prompt() 对话框

  close() open() 关闭、打开窗口

  focus() blur() 窗口获得焦点或者失去焦点

  moveBy() moveTo() 移动窗口

  resizeBy() resizeTo() 调整窗口大小

  scrollBy() scrollTo() 滚动窗口中网页的内容

### 周期性定时器

`setInterval(exp, time)` 周期性触发代码 exp

* exp：执行语句

* time：时间周期，单位为毫秒

* 返回已经启动的定时器对象

`clearInterval(tID)` 停止启动的定时器

* tID：启动的定时器对象

### 一次性定时器

`setTimeout(exp, time)` 一次性触发代码 exp

* exp：执行语句

* time：间隔时间，单位为毫秒

* 返回已经启动的定时器

`clearTimeout(tID)` 停止启动的定时器

* tID：启动的定时器对象

## document 对象

> * 每个载入浏览器的HTML文档都会成为document对象
>
> * 通过使用document对象，可以从脚本中对HTML页面中的所有元素进行访问

### DOM 
> * Document Object Model 文档对象模型
>
>   * 当网页被加载时，浏览器会创建页面的文档对象模型
>
> * 通过 DOM，可以访问所有的 HTML 元素，连同它们所包含的文本和属性
>
>   * 可以对其中的内容进行修改和删除，同时也可以创建新的元素
>
> * HTML 文档中的所有节点组成了一个文档树（或节点树）
>
>   * document 对象是一棵文档树的根

#### DOM 节点树

> * HTML 文档中的所有节点组成了一个文档树（或节点树）
>
> * HTML 文档中的每个元素、属性、文本等都代表着树中的一个节点
>
>   * 整个文档是一个文档节点
>
>   * 每个 HTML 标签是一个元素节点
>
>   * 包含在 HTML 元素中的文本是文本节点
>
>   * 每一个 HTML 属性是一个属性节点
>
>   * 注释属于注释节点
>
>   * 一切皆节点
>
> ![DOM 节点树](http://www.w3school.com.cn/i/ct_htmltree.gif)

#### DOM 操作

> * 通过可编程的对象模型，JS获得了足够的能力来创建动态的 HTML
>
>   * 查找节点
>
>   * 读取节点信息
>
>   * 修改节点信息
>
>   * 创建新节点
>
>   * 删除节点

### DOM 操作 - 读取、修改

#### 节点信息

> * nodeName：节点名称
>
>   * 元素节点和属性节点：标签或属性名称
>
>   * 文本节点：永远是 `#text`
>
>   * 文档节点：永远是 `#document`
>
> * nodeType：节点类型
>
>   * 返回数值
>
>   * 元素节点：返回 1
>
>   * 属性节点：返回 2
>
>   * 文本节点：返回 3
>
>   * 注释节点：返回 8
>
>   * 文档节点：返回 9

#### 元素节点的内容

> * innerText
>
>   * 设置或获取位于对象起始和结束标签内的文本
>
> * innerHTML
>
>   * 设置或获取位于对象起始和结束标签内的 HTML

#### 节点属性

> * getAttribute() 方法：根据属性名称获取属性的值
>
> * setAttribute()、removeAttribute()
>
> * 将 HTML 标记、属性和 CSS 样式都对象化

#### 元素节点的样式

> * style 属性
>
>   * `node.style.color`
>
>   * `node.style.fontSize`
>
> * className 属性
>
>   * `node.className`

### DOM 操作 - 查询

#### 查询节点

* 根据元素 ID 查找节点

  `document.getElementById()`

  * 通过指定的 ID 来返回元素节点，忽略文档的结构

  * 查找整个 HTML 文档中的任何 HTML 元素

  * 如果 ID 值错误，则返回 `null`

* 根据层次查找节点

  + `parentNode` `firstChild` `lastChild`

    - 遵循文档的上下层次结构，查找单个节点

  + `childNodes`

    - 遵循文档的上下层次结构，查找多个节点

  + `previousSibling`

     - 前一个同级节点

  + `nextSibling`

     - 后一个同级节点

* 根据标签名查找节点

  `getElementsByTagName()` 

  + 根据指定的标签名返回所有的元素

    - 忽略文档的结构

    - 查找整个 HTML 文档中的所有元素

    - 如果标签名称错误，则返回长度为 0 的节点列表

  + 返回一个节点列表（数组）

    - 使用节点列表的 `length` 属性获取个数

    - `[index]`：定位具体的元素

* 根据 name 属性查找节点

  `getElementsByName()` 

  * 根据标签的 `name` 属性的值进行查询

### DOM 操作 - 增加

* 创建新节点

  `document.createElement(elementName)`

  - elementName：要创建的元素标签名称

  - 返回新创建的节点

* 添加新节点

  `parentNode.appendChild(newNode)`

  - 追加：新节点作为父节点的最后一个字节点存在

  `parentNode.insertBefore(newNode, refNode)`

  - refNode：参考节点，新节点位于此节点之前

### DOM 操作 - 删除

* 删除节点

  `node.removeChild(childNode)`

  - 删除某个子节点

  - childNode 必须是 node 的子节点

  `childNode.parentNode.removeChild(childNode)`

## HTML DOM 对象

> * 定义了用户 HTML 的一系列标准的对象，以及访问和处理HTML文档的标准方法
>
> * HTML标签对象化
>
>   - 将网页中的每个元素都看作一个对象
>
> ![常用 HTML DOM 对象](http://www.codexiu.cn/static/blog/imagesw8/2016/04/01/full/80b443355bfb190ed474b6d8a2546c6dd0d82d5c.jpg)

### 标准 DOM 与 HTML DOM

* 标准 DOM 提供了统一的操作接口

  - creteElement

  - appendChild

  - setAttribute

  - removeAttribute

  - nodeName

  - ...

* HTML DOM 提供了封装好的各种对象

  - Image

  - Select

  - Option

  - ...

* 操作节点，如创建、删除、查找等

  - 使用标准 DOM 操作

* 操作属性，如读取或者修改属性的值

  - 使用 HTML DOM 操作

### Select 与 Option 对象

* Select 对象

> 代表 HTML 表单中的一个下拉列表

  `<select>` 标签即表示一个 Select 对象

常用属性

  - options、selectedIndex、size

常用方法

  - add(option)、remove(index)

事件

  - onchange

* Option 对象

> 代表 HTML 表单中下拉列表中的一个选项

  `<option>` 标签表示一个 Option 对象

创建对象

  - `var o = new Option(text, value);`

常用属性

  - index、text、value、selected

### Table 对象

* Table 对象

> 代表一个 HTML 表格

  `<table>` 标签表示一个 Table 对象

常用属性和方法

  - rows、cells

  - inserRow(index); 返回 TableRow 对象

  - deleteRow(index);

* TableRow 对象

> 代表一个 HTML 表格行

  `<tr>` 标签表示一个 TableRow 对象

常用属性和方法

  - cells、innerHTML、rowIndex

  - insertCell(index); 返回 TableCell 对象

  - deleteCell(index);

* TableCell 对象

> 代表一个 HTML 表格单元格

  `<td>` 标签表示一个 TableCell 对象

常用属性和方法

  - cellIndex、innerHTML、colSpan、rowSpan

## DHTML 其他对象

> ![DHTML 对象模型回顾](http://www.codexiu.cn/static/blog/imagesw8/2016/03/31/full/b4bfa746df71b0e4cf59ff64a78d8ccdc99ce163.jpg)

### screen 对象

> 包含有关客户端显示屏幕的信息
>
> 常用于获取屏幕的分辨率和色彩

常用属性

  - width/height、availWidth/availHtight

### history 对象

> 包含用户（在浏览器窗口中）访问过的 URL

常用属性和方法

  - length：浏览器历史列表中的 URL 数量

  - back()、forward()、go(num)

### location 对象

> 包含有关当前 URL 的信息
>
> 常用于获取和改变当前正在浏览的网址

属性和方法

  -href 属性：当前窗口正在浏览的网页地址

  -replace(url)：转向到 url 网页地址

  -reload()：重新载入当前网址，同按下刷新按钮

### navigator 对象

> 包含有关浏览器的信息
>
> 常用于获取客户端浏览器和操作系统信息

## 事件

event 对象：事件触发后将会产生一个 event 对象

### 事件句柄

通过一个事件句柄，可以在某个事件发生时对某个元素进行某种操作。

![事件类型](http://s2.51cto.com/wyfs02/M01/7E/64/wKiom1b91t-gMYVbAABss6Ewwxw932.png)

### 事件定义

* 在 html 属性定义中直接处理事件

```html
<input type="button" value="按钮" onclick="method();" />
```
* js 代码中动态定义

```javascript
// btnObj 为一个按钮对象
btnObj.onclick = method;

// 或
btnObj.onclick = function() {
    alert("hello");
}
```

* 取消事件

```html
onXXX = "return false;";
```

### 事件的处理机制

* 事件的冒泡处理机制

  ![事件的冒泡处理机制](http://s3.51cto.com/wyfs02/M02/7E/67/wKiom1b-B3Syt3QzAAB9gWFNXFo170.png)

> [JavaScript 之 DOM-8 Event 对象(事件概述、事件处理、event 对象)](http://jasonteach.blog.51cto.com/5192112/1759084)

* 可取消事件的冒泡

```javascript
event.cancelBubble = true;
```

### even 对象

> 任何事件触发后都会产生一个 even 对象，记录事件发生时的鼠标位置、键盘按键状态和触发对象等信息

#### 获取 event 对象

需要考虑浏览器的兼容性

#### 使用 event 对象

对于 event 对象，经常需要获得事件源

```javascript
// 考虑浏览器的兼容性
function func(e) {
    var obj = e.srcElement || e.target;
    alert(obj.nodeName);
}
```
