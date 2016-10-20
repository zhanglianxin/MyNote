# Laravel Update Guide & PHP Collection

[TOC]

## [Upgrading To 5.2.0 From 5.1](https://laravel.com/docs/5.2/upgrade#upgrade-5.2.0)

### Collections

#### Eloquent Base Collections

The Eloquent collection instance now returns a base Collection (`Illuminate\Support\Collection`) for the following methods: `pluck`, `keys`, `zip`, `collapse`, `flatten`, `flip`.

调用 Eloquent 集合实例的 `pluck`, `keys`, `zip`, `collapse`, `flatten`, `flip` 方法现在会返回集合基类。

#### Key Preservation

The `slice`, `chunk`, and `reverse` methods now preserve keys on the collection. If you do not want these methods to preserve keys, use the `values` method on the `Collection` instance.

`slice`、`chunk` 和 `reverse` 方法现在会保留集合的键名，如果你不想这些方法保留键名，使用集合实例的 `values` 方法即可。

### Deprecations

The following features are deprecated in 5.2 and will be removed in the 5.3 release in June 2016:

- `Illuminate\Contracts\Bus\SelfHandling` contract. Can be removed from jobs.
  `Illuminate\Contracts\Bus\SelfHandling` 契约
- The `lists` method on the Collection, query builder and Eloquent query builder objects has been renamed to `pluck`. The method signature remains the same.
  集合的 `lists` 方法被重命名为 `pluck` 方法。
- Implicit controller routes using `Route::controller` have been deprecated. Please use explicit route registration in your routes file. This will likely be extracted into a package.
  隐式控制器路由 `Route::controller` 被废弃。在路由文件中请使用明确的路由注册。
- The `get`, `post`, and other route helper functions have been removed. You may use the `Route` facade instead.
- The `database` session driver from 5.1 has been renamed to `legacy-database` and will be removed. Consult notes on the "database session driver" above for more information.
  Laravel 5.1 的 `database` Session驱动被重命名为 `legacy-database`。
- The `Str::randomBytes` function has been deprecated in favor of the `random_bytes` native PHP function.
  `Str::randomBytes` 方法被废弃，直接使用 PHP 的 `random_bytes` 即可。
- The `Str::equals` function has been deprecated in favor of the `hash_equals` native PHP function.
  `Str::equals` 方法被废弃，直接使用 PHP 的 `hash_equals` 方法即可。
- `Illuminate\View\Expression` has been deprecated in favor of `Illuminate\Support\HtmlString`.
  `Illuminate\View\Expression` 被废弃，使用 `Illuminate\Support\HtmlString` 即可。
- The `WincacheStore` cache driver has been removed.



## [Upgrading To 5.1.11](https://laravel.com/docs/5.2/upgrade#upgrade-5.1.11)

## [Upgrading To 5.1.0](https://laravel.com/docs/5.2/upgrade#upgrade-5.1.0)

### Eloquent

#### The `create` Method

Eloquent's `create` method can now be called without any parameters. If you are overriding the `create`method in your own models, set the default value of the `$attributes` parameter to an array:

```php
public static function create(array $attributes = [])
{
    // Your custom implementation
}
```

#### The `find` Method

If you are overriding the `find` method in your own models and calling `parent::find()` within your custom method, you should now change it to call the `find` method on the Eloquent query builder:

```php
public static function find($id, $columns = ['*'])
{
    $model = static::query()->find($id, $columns);

    // ...

    return $model;
}
```

#### The `lists` Method

The `lists` method now returns a `Collection` instance instead of a plain array for Eloquent queries. If you would like to convert the `Collection` into a plain array, use the `all` method:

```php
User::lists('id')->all();
```

Be aware that the Query Builder `lists` method still returns an array.

#### Date Formatting

Previously, the storage format for Eloquent date fields could be modified by overriding the `getDateFormat`method on your model. This is still possible; however, for convenience you may simply specify a `$dateFormat`property on the model instead of overriding the method.

The date format is also now applied when serializing a model to an `array` or JSON. This may change the format of your JSON serialized date fields when migrating from Laravel 5.0 to 5.1. To set a specific date format for serialized models, you may override the `serializeDate(DateTime $date)` method on your model. This method allows you to have granular control over the formatting of serialized Eloquent date fields without changing their storage format.

### The Collection Class

#### The `sort` Method

The `sort` method now returns a fresh collection instance instead of modifying the existing collection:

```php
$collection = $collection->sort($callback);
```

#### The `sortBy` Method

The `sortBy` method now returns a fresh collection instance instead of modifying the existing collection:

```php
$collection = $collection->sortBy('name');
```

#### The `groupBy` Method

The `groupBy` method now returns `Collection` instances for each item in the parent `Collection`. If you would like to convert all of the items back to plain arrays, you may `map` over them:

```php
$collection->groupBy('type')->map(function($item)
{
    return $item->all();
});
```

#### The `lists` Method

The `lists` method now returns a `Collection` instance instead of a plain array. If you would like to convert the `Collection` into a plain array, use the `all` method:

```php
$collection->lists('id')->all();
```

### Deprecations

The following Laravel features have been deprecated and will be removed entirely with the release of Laravel 5.2 in December 2015:

- Route filters have been deprecated in preference of [middleware](https://laravel.com/docs/5.2/middleware).
  The `array_fetch` helper has been deprecated in favor of the `array_pluck` method.[中间件](http://laravelacademy.org/post/57.html)中的路由过滤器

- The `Illuminate\Contracts\Routing\Middleware` contract has been deprecated. No contract is required on your middleware. In addition, the `TerminableMiddleware` contract has also been deprecated. Instead of implementing the interface, simply define a `terminate` method on your middleware.
  `Illuminate\Contracts\Routing\Middleware`，中间件中不再需要任何contract，`Illuminate\Contracts\Routing\TerminableMiddleware`被废弃，在中间件中定义一个terminate方法替代实现该接口。

- The `Illuminate\Contracts\Queue\ShouldBeQueued` contract has been deprecated in favor of `Illuminate\Contracts\Queue\ShouldQueue`.
  `Illuminate\Contracts\Queue\ShouldBeQueued` 被废弃，使用 `Illuminate\Contracts\Queue\ShouldQueue`

- Iron.io "push queues" have been deprecated in favor of typical Iron.io queues and [queue listeners](https://laravel.com/docs/5.2/queues#running-the-queue-listener).
  Iron.io “推入队列” 被废弃， 使用Iron.io 队列和队列监听器.

- The `Illuminate\Foundation\Bus\DispatchesCommands` trait has been deprecated and renamed to `Illuminate\Foundation\Bus\DispatchesJobs`.
  `Illuminate\Foundation\Bus\DispatchesCommands` trait 被废弃并被重命名为`Illuminate\Foundation\Bus\DispatchesJobs`.

- `Illuminate\Container\BindingResolutionException` has been moved to `Illuminate\Contracts\Container\BindingResolutionException`.
  `Illuminate\Container\BindingResolutionException` 被移动到`Illuminate\Contracts\Container\BindingResolutionException`.

- The service container's `bindShared` method has been deprecated in favor of the `singleton` method.
  服务容器的 `bindShared` 方法被废弃，使用`singleton` 方法。

- The Eloquent and query builder `pluck` method has been deprecated and renamed to `value`.
  Eloquent和query builder的 `pluck` 方法被废弃并重命名为`value`.

- The collection `fetch` method has been deprecated in favor of the `pluck` method.
  Collection的 `fetch` 方法被废弃，使用 `pluck` 方法.

- The `array_fetch` helper has been deprecated in favor of the `array_pluck` method.
  `array_fetch` 帮助函数被废弃， 使用`array_pluck`


> ## [Laravel SQL查询中first, pluck与lists方法的使用](http://douyasi.com/laravel/first_pluck_lists.html)

# [PHP Collection](http://jmsyst.com/libs/php-collection#php-collection)

# [Collection Classes in PHP](https://www.sitepoint.com/collection-classes-in-php/)

# [Eloquent: Collections](https://laravel.com/docs/5.2/eloquent-collections)
