# LaravelBasic 

[TOC]

## 路由 和 MVC

### 路由

`app/Http/routes.php`

> 将用户的请求转发给相应的程序进行处理
>
> 建立 URL 和 程序之间的映射
>
> 请求类型：`get` `post` `put` `patch` `delete`

* 基本路由

  ```php
  Route::get('path', function() {
      return 'Hello Laravel';
  }); // get 请求方式

  Route::post('path', function() {
      return 'Hello Laravel';
  }); // post 请求方式
  ```

* 多请求路由

  ```php
  Route::match(['get', 'post'], 'path', function() {
      return 'match';
  }); // 指定请求方式

  Route::any('path', function() {
      return 'any';
  }); // 所有请求方式
  ```


* 路由参数

  ```php
  Route::get('usr/{id}', function($id) {
      return 'usrid: '.$id;
  }); // 指定参数

  Route::get('usr/{name?}', function($name = null) {
      return 'usrname: '.$name;
  }); // 可选路由参数，带默认值

  Route::get('usr/{name?}', function($name = 'Laravel') {
      return 'usrname: '.$name;
  }) -> where('name', '[A-Za-z]+'); // 使用正则表达式匹配

  Route::get('usr/{id}/{name?}', function($id, $name = 'Laravel') {
      return 'usrid: '.$id.' usrname: '.$name;
  }) -> where(['id' => '[0-9]+', 'name' => '[A-Za-z]+']); // 多参数使用正则表达式匹配
  ```

* 路由别名

  ```php
  Route::get('usr/zhanglianxin', ['as' => 'zhanglianxin', function() {
      return 'hi zhanglainxin';
  }]);
  ```

* 路由群组

  ```php
  Route::group(['prefix' => 'admin'], function() {
      Route::get('usr/zhanglianxin', function() {
          return 'hi zhanglainxin';
      });
  });
  ```

* 路由中输出视图

  ```php
  Route::get('wlc', function() {
      return view('welcome');
  });
  ```

### 控制器

* 新建控制器

  `app/Http/Controllers/Zhanglianxin/ZLXController.php`

* 路由和控制器关联

  ```php
  Route::get('path', 'XXXController@func');
  Route::get('path', ['uses' => 'XXXController@func']);
  Route::get('path', ['uses' => 'XXXController@func', 'as' => 'alias']);
  ```


* 参数绑定

  ```php
  Route::get('path/{param}', ['uses' => 'XXXController@func']);
  ```

  `XXXController`

  ```php
      public function func($param) {}
  ```

### 视图

* 新建视图

  `XXX-blade.php`

* 输出视图

  `XXXController`

  ```php
      public function func() {
          return view('viewname', ['var' => 'value']);
      }
  ```

### 模型

* 新建模型

  ```php
  namespace App;

  class ModelName extends Model {}
  ```

* 使用模型

  在控制器中调用模型的方法

## 数据库操作

### DB facade

* 新建数据表

  ```sql
  -- ----------------------------
  -- Table structure for employee
  -- ----------------------------
  DROP TABLE IF EXISTS `employee`;
  CREATE TABLE `employee` (
      `id` int(4) NOT NULL AUTO_INCREMENT,
      `name` varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
      `age` tinyint(3) unsigned NOT NULL DEFAULT '20' COMMENT '年龄',
      `sex` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '性别',
      `created_at` int(11) NOT NULL DEFAULT '0' COMMENT '新增时间',
      `updated_at` int(11) NOT NULL DEFAULT '0' COMMENT '修改时间',
      PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='员工表';
  ```

* 连接数据库

  `config/database.php`

  ```php
  return [
      'connections' => [
           'mysql' => [
               'driver'    => 'mysql',
               'host'      => env('DB_HOST', 'localhost'),
               'database'  => env('DB_DATABASE', 'forge'),
               'username'  => env('DB_USERNAME', 'forge'),
               'password'  => env('DB_PASSWORD', ''),
               'charset'   => 'utf8',
               'collation' => 'utf8_unicode_ci',
               'prefix'    => '',
               'strict'    => false,
               'engine'    => null,
          ],
      ],
  ]
  ```

  `.env`

  ```ini
  DB_HOST=127.0.0.1
  DB_DATABASE=laravel
  DB_USERNAME=root
  DB_PASSWORD=root
  ```

* 实现 CRUD

  ```php
  DB::insert('', []); // bool
  DB::select('', []); // array
  DB::update('', []); // int
  DB::delete('', []); // int
  ```


### 查询构造器

* 实现 CRUD

  ```php
  DB::table('tablename')->insert([]); // bool
  DB::table('tablename')->insert([[], []]); // bool
  DB::table('tablename')->insertGetId([]); // int

  DB::table('tablename')->get(); // array
  DB::table('tablename')->first(); // object(stdClass)
  DB::table('tablename')->orderBy()->get();
  DB::table('tablename')->where()->get();
  DB::table('tablename')->whereRaw('', [])->get();

  // 以最后一条记录 column2 为键 column1 为值的关联数组
  DB::table('tablename')->whereRaw('', [])->pluck('column1', 'column2'); // ["zlx" => 1003]
  DB::table('tablename')->whereRaw('', [])->lists('column1', 'column2'); // ["zlx" => 1003]
  // 查找指定字段内容
  DB::table('tablename')->select('column1', 'column2')->get();
  // 取数据表的部分数据
  DB::table('tablename')->chunk(2, function ($employees) {
  //    var_dump($employees); // array(2)
      foreach ($employees as $employee) {
          echo $employee->id . ' ' . $employee->name . '<br>';
      }
  });

  DB::table('tablename')->where()->update([]); // int
  DB::table('tablename')->where()->increment('', num); // int
  DB::table('tablename')->where()->increment('', num, []); // int
  DB::table('tablename')->where()->decrement('', num); // int

  DB::table('tablename')->where()->delete(); // int
  DB::table('tablename')->where('column', '>=', '')->delete(); // int

  DB::table('tablename')->truncate(); // [DANGEROUS]
  ```

### Eloquent ORM

* 实现 CRUD

  ```php
  $m = new Model();
  $m->save(); // bool
  Model::create([]); // MassAssignmentException $fillable, $guarded
  Model::firstOrCreate([]);
  Model::firstOrNew([]); // 不插入数据库

  Model::all(); // object(Illuminate\Database\Eloquent\Collection)
  Model::get(); // object(Illuminate\Database\Eloquent\Collection)

  Model::find(); // object
  Model::first(); // object
  Model::findOrFail();// NotFoundHttpException No query results for model
  // 取数据表的部分数据
  Model::chunk(2, function ($employees) {
  //    var_dump($employees); // array(2)
      foreach ($employees as $employee) {
          echo $employee->id . ' ' . $employee->name . '<br>';
      }
  });

  // 基本更新
  $m = Model::find();
  $m->prop = 'newProp';
  $m->save(); // bool
  // 批量更新
  Model::where()->update([]); // int

  // 主键查找删除
  $m = Model::find();
  $m->delete(); // bool
  // 主键删除
  Model::destroy(id); // int
  Model::destroy(id, id);
  Model::destroy([id, id]);
  // 条件查找删除
  Model::where()->delete(); // int
  ```

## Blade 模板引擎

> [Blade 模板](https://laravel-china.org/docs/5.1/blade)
>
> [Blade 模板中有关 section 的那些事](http://ofcss.com/2014/12/16/blade-keywords-yield-section-show-stop-override-append.html)

### 模板继承

* 定义页面布局

  `@section` 命令定义一个内容区块

  `@yield` 命令被用来显示指定区块的内容

  `reosurces/views/layouts/master.blade.php`

  ```php
  <html>
  <head>
      <title>应用程序名称 - @yield('title')</title>
  </head>
  <body>
  @section('sidebar')
      这是主要的侧边栏。
  @show

  <div class="container">
      @yield('content')
  </div>
  </body>
  </html>
  ```

* 继承页面布局

  `@extends` 指定子页面应该继承自哪一个布局

  `@section` 视图继承模板之后，可以使用该命令注入内容到模板的区块中

  `@parent` 当视图被渲染输出时，会以布局中的内容替换

  `resources/views/child.blade.php`

  ```php
  @extends('layouts.master')

  @section('title', '页面标题')

  @section('sidebar')
      @parent

      <p>这边会附加在主要的侧边栏。</p>
  @endsection

  @section('content')
      <p>这是我的主要内容。</p>
  @endsection
  ```

### 显示数据

* `{{ }}` 语法  自动调用PHP `htmlentites()` 函数来防御 XSS 攻击
* `@` 符号  告知 Blade 渲染引擎该表达式应该维持原样
* 三元运算符的简化  `{{ $name or 'Default'}}` `==` `{{ isset($name) ? $name : 'Default' }}`
* `{!! $name !!}`  显示未转义过的数据

### 控制结构

* 条件表达式 

  `@if` `@elseif` 

  `@else` `@endif` 

  `@unless` `@endunless`

* 循环结构  

  `@for` `@endfor` 

  `@foreach` `@endforeach` 

  `@forelse` `@empty` `@endforelse`

  `@while` `@endwhile`

* 引入子视图

  `@include` 引入已存在的视图，所有在父视图的可用变量在被引入的视图中都是可用的

  > **注意：**请避免在 Blade 视图中使用 `__DIR__` 及 `__FILE__` 常数，因为他们会引用视图被缓存的位置。

* 为集合渲染视图

  `@each('view.name', $jobs, 'job')`  

  第一个参数为每个元素要渲染的局部视图，

  第二个参数是要迭代的数组或集合，

  第三个参数为迭代时被分配至视图中的变量名称。

  也可以传递第四个参数至 `@each` 命令，此参数为当指定的数组为空时，将会被渲染的视图。

  `@each('view.name', $jobs, 'job', 'view.empty')`  

* 注释

  `{{-- --}}`

### 服务注入

### 扩充 Blade 

---

## Controller

### Request

> [HttpFoundation Component](https://github.com/symfony/symfony/tree/master/src/Symfony/Component/HttpFoundation)

#### 获取请求

```php
Route::put('user/{id}', 'UserController@update');

    public function update(Request $request, $id) {
        // ...
    }
```

* 基本请求信息

  获取请求的 URI 

  * `$request->path()` 返回请求的 URI
  * `$request->is('admin/*')` 验证请求 URI 与指定的规则是否相匹配
  * `$request->url()` 获取完整的请求路径，不带请求参数
  * `$request->fullUrl()` 获取完整的请求路径，带请求参数
  * `$request->fullUrlWithQuery(['bar' => 'baz'])` 获取完整的请求 URL，并在后面增加请求字串

  获取请求的方法

  * `$request->ajax()` 判断该请求是否是 AJAX 调用的结果
  * `$request->method()` 获取请求方法
  * `$request->isMethod('post')` 验证请求方法


#### 获取输入数据

* 获取特定输入值

  * `$request->input('name')`
  * `$request->name`
  * `$request->input('name', 'Sally')`
  * `$request->input('products.0.name')` `$request->input('products.*.name')` 数组形式的输入数据

* 获取 JSON 输入信息

  设置请求头 `Content-Type: application/json`，可以直接从 `input()` 方法中读取到内容，也可以读取数组。

  `$request->input('user.name')`

* 确认是否有输入值

  `$request->has('name')` 存在并且字符串不为空

* 获取所有输入数据

  ```php
  $input = $request->all(); // 以数组形式获取到所有输入数据
  foreach ($input as $key => $value) {
      echo $key . ': ' . $value;
  }
  ```

* 获取部分输入数据

  ```php
  // Get a subset containing the provided keys with values from the input data.
  $input = $request->only(['username', 'password']);
  $input = $request->only('username', 'password');
  // Get all of the input except for a specified array of items.
  $input = $request->except(['credit_card']);
  $input = $request->except('credit_card');
  ```

* 动态属性

  `$name = $request->name;` 

  处理动态属性的优先级是，从请求的数据中查找，没有的话再到路由参数中找。

### Response

#### 基本响应

* 响应对象
* 附加标头至响应
* 附加 Cookies 至响应
* Cookies 与加密

#### 响应类型

使用辅助函数 `response()` 可以生成其它类型的响应实例。

此函数不带任何参数时，返回  `Illuminate\Contracts\Routing\ResponseFactory` [contract](https://laravel-china.org/docs/5.2/contracts) 的实现。

* 视图响应

  控制响应状态码及标头，同时返回一个视图作为响应内容。

  `response()->view('hello', $data)->header('Content-Type', $type)` 

  如果不需要自定义 HTTP 状态码及标头，可以使用全局的 `view()` 函数。

  `view('hello', $data)`

* JSON 响应

  `json()` 方法会自动将标头的 `Content-Type` 设置为 `application/json`，并通过  PHP 的 `json_encode()` 函数将指定的数组转换为 JSON。

  `response()->json(['name' => 'Abigail', 'state' => 'CA'])`

  如果想创建一个 JSONP 响应，可以使用 `json()` 方法并加上 `setCallback()`。

  `response()->json(['name' => 'Abigail', 'state' => 'CA'])->setCallback($request->input('callback'))`

* [文件下载](https://laravel-china.org/docs/5.2/responses#文件下载)

* [文件响应](https://laravel-china.org/docs/5.2/responses#文件响应)

#### 重定向

重定向响应是类 `Illuminate\Http\RedirectResponse` 的实例，并且包含用户要重定向至另一个 URL 所需的标头。

通过全局的 `redirect()` 函数生成重定向响应的实例 

`redirect('home/dashboard')` 

还可以使用 `with()` 方法带数据重定向

`redirect('home/dashboard')->with('key', 'value')` 

重定向至前一个位置，可以使用全局 `back()` 函数。

`back()->withInput()` 或者 `redirect()->back()->withInput()`

* 重定向至命名路由

  `redirect()->route('login')` 

  如果需要带参数

  `redirect()->route('profile', ['id' => 1])`

  `redirect()->route('profile', [$user])`

* 重定向至控制器行为

  `redirect()->action('HomeController@index')` 

  ~~不需要指定完整的命名空间，因为 Laravel 的 `RouteServiceProvider` 会自动设置默认的控制器命名空间。~~

  如果需要带参数

  `redirect()->action('UserController@profile', ['id' => 1])`

* 重定向并加上 Session 闪存数据

  `redirect('dashboard')->with('status', 'Profile updated!')`


### Session

#### 访问 Session

`$request->session()->get('key')`

`$request->session()->get('key', 'default')`

`$request->session()->get('key', function() {return 'default';})`

`$request->session()->all()`

`session('key')` 

`session(['key' => 'value'])` 保存数据到 Session 中

`$request()->session()->has('users')` 判断 session 是否存在

`$request()->session()->put('key', 'value')` 保存数据到 Session 中

`$request()->session()->push('user.teams', 'developers')` 保存数据进 Session 数组值中

`$request()->session()->pull('key', 'default')` 从 Session 中取出并删除数据

`$request()->session()->forget('key')` 从 Session 中删除一条数据

`$request()->session()->flush()` 从 Session 中删除所有数据

`$request()->session()->regenerate()` 重新生成 Session ID

#### 闪存数据

`$request->session()->flash('status', 'Task was successful!')` 存入一条闪存数据

`$request->session()->reflash()` 保留闪存数据给更多请求

`$request->session()->keep(['username', 'email'])` 保留特定的闪存数据

### Middleware

