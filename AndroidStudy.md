# AndroidStudy

标签： Android

---

## 复选框属性

激活 `setActivated(boolean activated)`
选中 `setSelected(boolean selected)`
勾选 `setChecked(boolean checked)`

设置激活属性
>     /**
     * Changes the activated state of this view. A view can be activated or not.
     * Note that activation is not the same as selection.  Selection is
     * a transient property, representing the view (hierarchy) the user is
     * currently interacting with.  Activation is a longer-term state that the
     * user can move views in and out of.  For example, in a list view with
     * single or multiple selection enabled, the views in the current selection
     * set are activated.  (Um, yeah, we are deeply sorry about the terminology
     * here.)  The activated state is propagated down to children of the view it is set on.
     *
     * @param activated true if the view must be activated, false otherwise
     */

设置选中属性
>     /**
     * Changes the selection state of this view. A view can be selected or not.
     * Note that selection is not the same as focus. Views are typically
     * selected in the context of an AdapterView like ListView or GridView;
     * the selected view is the view that is highlighted.
     *
     * @param selected true if the view must be selected, false otherwise
     */

设置勾选状态
>     /**
     * Changes the checked state of this button.
     *
     * @param checked true to check the button, false to uncheck it
     */

## ListViewOptimization

* 复用历史缓存view对象 (converView)

```java
public View getView(int position, View convertView, ViewGroup parent) {
    TextView txtv;
    if (convertView == null) {
        txtv = new TextView(MainActivity.this);
        System.out.println("-- 创建新的view对象 --" + position);
    } else {
        txtv = (TextView) convertView;
        System.out.println("-- 复用历史view对象 --" + position);
    }
    txtv.setText("zhlx" + position);

    return txtv;
}
```

* 将高度设置为 `match_parent` 填充父窗体

```xml
<ListView
        android:id="@+id/lstv"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
</ListView>
```

## 将布局文件转换成view对象 (3种方式)

> Inflate a new view hierarchy from the specified xml resource.

```java
View view = View.inflate(context, resource, root);
```

```java
View view = LayoutInflater.from(context).inflate(resource, root);
```

```java
LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
View view = inflater.inflate(resource, root);
```

## 常见数据适配器的使用

* BaseAdapter

```java
ListView lstv = (ListView) findViewById(R.id.lstv);
// 为listview设置数据适配器
lstv.setAdapter(new MyBaseAdapter());

// 自定义数据适配器类继承BaseAdapter，实现里面的抽象方法
private class MyBaseAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            Context context = getApplicationContext();
            int resource = R.layout.item;
            ViewGroup root = null;
            // 将布局文件转换成view对象 -- 3种方式
//          view = View.inflate(context, resource, root);
//          view = LayoutInflater.from(context).inflate(resource, root);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, root);
        } else {
            view = convertView;
        }
        return view;
    }
}
```

* ArrayAdapter

```java
ListView lstv = (ListView) findViewById(R.id.lstv);

Context context = getApplicationContext();
//    int resource = R.layout.item;
String[] objects = {"张孝祥", "方立勋", "黎活明", "毕向东", "刘意", "韩顺平"};
//    
//    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, resource, objects);

int resource = R.layout.item2;
int textViewResourceId = R.id.txtv;

/**
 * @param context The current context.
 * @param resource The resource ID for a layout file containing 
 *                 a layout to use when instantiating views.
 *                 布局文件的资源ID，包含一个布局 当实例化views时使用
 * @param textViewResourceId The id of the TextView within 
 *                           the layout resource to be populated 
 *                           构成布局资源的TextView的id
 * @param objects The objects to represent in the ListView.
 */
ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, resource, textViewResourceId, objects);
lstv.setAdapter(adapter);
```

`item.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textAppearance="?android:attr/textAppearanceLarge"
    android:text="Large Text"
    android:textColor="#000000"
    android:id="@+id/txtv"
    xmlns:android="http://schemas.android.com/apk/res/android">
</TextView>
```

`item2.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@mipmap/ic_launcher"/>
    <TextView
        android:id="@+id/txtv"
        android:textColor="#000000"
        android:textSize="20sp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"/>
</LinearLayout>
```

* SimpleAdapter
```java
ListView lstv = (ListView) findViewById(R.id.lstv);

Context context = getApplicationContext();
List<Map<String, String>> data = new ArrayList<>();

Map<String, String> map = new HashMap<>();
map.put("name", "zhlx");
map.put("phone", "9517");
data.add(map);

Map<String, String> map2 = new HashMap<>();
map2.put("name", "zhlx");
map2.put("phone", "1991");
data.add(map2);

Map<String, String> map3 = new HashMap<>();
map3.put("name", "zhlx");
map3.put("phone", "4161");
data.add(map3);

Map<String, String> map4 = new HashMap<>();
map4.put("name", "zhlx");
map4.put("phone", "1090");
data.add(map4);

int resource = R.layout.item;
String[] from = new String[] { "name", "phone" };
int[] to = new int[] { R.id.tv_name, R.id.tv_phone };

/**
 * @param context The context where the View associated with this 
 *                SimpleAdapter is running
 * @param data A List of Maps. Each entry in the List corresponds 
 *             to one row in the list.
 *             The Maps contain the data for each row, and should 
 *             include all the entries specified in "from"
 * @param resource Resource identifier of a view layout that 
 *        defines the views for this list item. 
 *        The layout file should include at least those 
 *        named views defined in "to"
 * @param from A list of column names that will be added to 
 *        the Map associated with each item.
 * @param to The views that should display column in the "from" 
 *        parameter. These should all be TextViews. 
 *        The first N views in this list are given the values 
 *        of the first N columns in the from parameter.
 */
SimpleAdapter adapter = new SimpleAdapter(context, data, resource, from, to);

lstv.setAdapter(adapter);
```

`item.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <TextView
        android:id="@+id/tv_name"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:text="name"
        android:textColor="#ff0000"
        android:textSize="20sp"
        android:layout_height="wrap_content" />
    <TextView
        android:id="@+id/tv_phone"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:text="phone"
        android:textColor="#00ff00"
        android:textSize="20sp"
        android:layout_height="wrap_content" />
</LinearLayout>
```

* SimpleCursorAdapter

```java
lv = (ListView) findViewById(R.id.lv);

Context context = getApplicationContext();
int layout = R.layout.item;
c = dbHelper.select(db);
String[] from = new String[] {"title"};
int[] to = new int[] {R.id.tv};
int flags = CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
adapter = new SimpleCursorAdapter(context, layout, c, from, to, flags);

lv.setAdapter(adapter);
```

## Handler 消息循环机制

> [学姐的IT专栏 -- Android消息循环机制源码分析](http://mp.weixin.qq.com/s?__biz=MzA5MTE1NTE5NQ==&mid=405322810&idx=1&sn=65d605b60aeb6d97924dc1f932838196&scene=0&from=singlemessage&isappinstalled=0#wechat_redirect)

```java
private Handler handler = new Handler() {
    // 子类必须实现这个方法来接收消息
    @Override
    public void handleMessage(Message msg) {
    /**
     * User-defined message code so that the recipient can identify
     * what this message is about. Each Handler has its own name-space for message codes, 
     * so you do not need to worry about yours conflicting with other handlers.
     */
        switch (msg.what) {
            case REQUESTSUCCESS:
                String content = (String) msg.obj;
                tv_result.setText(content);
                break;
            case REQUESTNOTFOUND:
                Toast.makeText(getApplicationContext(), "请求资源不存在", Toast.LENGTH_SHORT).show();
                break;
            case REQUESTEXCEPTION:
                Toast.makeText(getApplicationContext(), "服务器忙 请稍后访问", Toast.LENGTH_SHORT).show();
                break;
        }
    }
};

new Thread() {
    @Override
    public void run() {
        String path = et_path.getText().toString().trim();
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); /* GET大写*/
            connection.setConnectTimeout(5000); /* NetworkOnMainThreadException*/
            int code = connection.getResponseCode();
            Log.i("code", code + ""); /* 获取服务器返回的数据*/
            if (code == 200) {
                InputStream in = connection.getInputStream();
                String content = StreamTools.readStream(in);
                if (content != null) {
                    /*
                     * ViewRootImpl$CalledFromWrongThreadException:
                     * Only the original thread that created a view hierarchy can touch its view
                     */
                    // tv_result.setText(content);
                    // 使用静态方法可以减少对象的创建
                    Message msg = Message.obtain();
                    msg.what = REQUESTSUCCESS;
                    msg.obj = content;
                    // 发送消息
                    handler.sendMessage(msg);
                }
            } else {
                Message msg = new Message();
                msg.what = REQUESTNOTFOUND;
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = REQUESTEXCEPTION;
            handler.sendMessage(msg);
        }
    }
}.start();
```

## 使用应用缓存减少网络请求

`/data/data/packagename/cache/xxx`

```java
private Handler handler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
        Bitmap bitmap = (Bitmap) msg.obj;
        iv.setImageBitmap(bitmap);
    }
};

new Thread() {
    @Override
    public void run() {
        File file = new File(getCacheDir(), Base64.encodeToString("cache".getBytes(), Base64.DEFAULT));
        if (file.exists() && file.length() > 0) {
            Log.i("MainActivity", "使用缓存");
            // 不请求网络资源，直接从缓存中获取
            Bitmap cache = BitmapFactory.decodeFile(file.getAbsolutePath());
            Message msg = Message.obtain();
            msg.obj = cache;
            handler.sendMessage(msg);
        } else {
            Log.i("MainActivity", "请求网络资源");
            String path = et_path.getText().toString().trim();
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream in = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    int length = -1;
                    byte[] buffer = new byte[1024];
                    while ((length = in.read(buffer)) != -1) {
                        // 写出到缓存
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                    in.close();
                    // 从缓存中获取
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                  iv.setImageBitmap(bitmap);

                    // 使用静态方法可以减少对象的创建
                    Message msg = Message.obtain();
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}.start();
```

## 在非主线程中请求更新UI

`runOnUiThread(Runnable action)`

```java
/**
 * Runs the specified action on the UI thread. 
 * If the current thread is the UI thread, then the action is executed immediately. 
 * If the current thread is not the UI thread, the action is posted to the event queue of the UI thread.
 *
 * @param action the action to run on the UI thread
 */
public final void runOnUiThread(Runnable action)
```

改进上面更新UI的操作

```java
    final Bitmap bitmap = BitmapFactory.
            decodeFile(file.getAbsolutePath());
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            iv.setImageBitmap(cache);
        }
    });
```

## 新闻客户端

**关键词：**
> ListView + HttpURLConnection + XmlPullParser + BaseAdapter + SmartImageView

1. 找到控件
2. 初始化数据
3. 设置数据适配器
4. 更新UI
5. 添加网络权限

`item.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="85dp">

    <!--新闻图片-->
    <com.loopj.android.image.SmartImageView
        android:id="@+id/iv_icon"
        android:src="@mipmap/ic_launcher"
        android:layout_margin="3dp"
        android:layout_width="80dp"
        android:layout_height="80dp" />

    <!--新闻标题-->
    <TextView
        android:id="@+id/tv_title"
        android:layout_marginTop="4dp"
        android:textSize="18sp"
        android:textColor="#000000"
        android:text="啊哈哈哈哈哈"
        android:singleLine="true"
        android:ellipsize="end"
        android:layout_toRightOf="@id/iv_icon"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <!--新闻描述-->
    <TextView
        android:id="@+id/tv_desc"
        android:layout_marginTop="6dp"
        android:textSize="14sp"
        android:textColor="#999999"
        android:text="哦呵呵呵呵呵"
        android:maxLines="2"
        android:ellipsize="end"
        android:layout_toRightOf="@+id/iv_icon"
        android:layout_below="@+id/tv_title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <!--新闻类型-->
    <TextView
        android:id="@+id/tv_type"
        android:textSize="14sp"
        android:textColor="#ff0000"
        android:text="跟帖"
        android:layout_alignParentRight="true"
        android:layout_alignBottom="@+id/iv_icon"
        android:layout_marginRight="3dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</RelativeLayout>
```

`news.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<channel>
	<item>
		<title>军报评徐才厚</title>
		<description>人死账不消 反腐步不停，支持，威武，顶，有希望了</description>
		<image>http://192.168.173.1:8080/img/a.jpg</image>
		<type>1</type>
		<comment>163</comment>
	</item>
	<item>
		<title>女司机翻车后直奔麻将室</title>
		<description>女司机翻车后直奔麻将室，称大难不死手气必红</description>
		<image>http://192.168.173.1:8080/img/b.jpg</image>
		<type>2</type>
		<comment></comment>
	</item>
	<item>
		<title>小伙当“男公关”以为陪美女</title>
		<description>来源：中国青年网，小伙当“男公关”以为陪美女，上工后被大妈吓怕</description>
		<image>http://192.168.173.1:8080/img/c.jpg</image>
		<type>3</type>
		<comment></comment>
	</item>
	<item>
		<title>男子看上女孩背影xxxx</title>
		<description>来源：新京报，看到正脸后很惊喜</description>
		<image>http://192.168.173.1:8080/img/d.jpg</image>
		<type>1</type>
		<comment>763</comment>
	</item>
</channel>
```

```java
    // 找到ListView控件
    lv = (ListView) findViewById(R.id.lv);
    // 调用自定义方法初始化数据
    initListData();
    
    private void initListData() {
        // 网络请求要开子线程
        new Thread() {
            @Override
            public void run() {
                try {
                    // 向网络请求xml数据
                    String path = "http://192.168.173.1:8080/news.xml";
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(5000);
                    int code = con.getResponseCode();
                    if (code == 200) {
                        InputStream in = con.getInputStream();
                        // 解析xml数据，封装到list集合
                        newsList = XmlParserUtils.parserXml(in);

                        // TODO: 更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 给listview设置数据适配器
                                lv.setAdapter(new MyAdapter());
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    // 自定义数据适配器
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // 设置要显示的条目为集合的大小
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                Context context = getApplicationContext();
                int resource = R.layout.item;
                ViewGroup root = null;
                view = View.inflate(context, resource, root);
            } else {
                view = convertView;
            }
            // 找到item里面的各个控件
            SmartImageView iv_icon = (SmartImageView) view.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);

            String imageUrl = newsList.get(position).getImage();
            // 直接将图片路径传入smartimageview
            iv_icon.setImageUrl(imageUrl);

            tv_title.setText(newsList.get(position).getTitle());
            tv_desc.setText(newsList.get(position).getDescription());
            String comment = newsList.get(position).getComment();
            int type = Integer.parseInt(newsList.get(position).getType());
            // 判断新闻类型，并添加评论数目
            switch (type) {
                case 1:
                    tv_type.setText(comment + " 国内");
                    break;
                case 2:
                    tv_type.setText("跟帖");
                    break;
                case 3:
                    tv_type.setText("国外");
                    break;
            }

            return view;
        }
    }
```

`XmlParserUtils`
```java
/**
 * 解析输入流数据，并将JavaBean对象封装成List集合
 */
public class XmlParserUtils {
    public static List<News> parserXml(InputStream in) {
        List<News> newsList = null;
        News news = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {
                type = parser.next();
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("channel".equals(parser.getName())) {
                            newsList = new ArrayList<>();
                        } else if ("item".equals(parser.getName())) {
                            news = new News();
                        } else if ("title".equals(parser.getName())) {
                            news.setTitle(parser.nextText());
                        } else if ("description".equals(parser.getName())) {
                            news.setDescription(parser.nextText());
                        } else if ("image".equals(parser.getName())) {
                            news.setImage(parser.nextText());
                        } else if ("type".equals(parser.getName())) {
                            news.setType(parser.nextText());
                        } else if ("comment".equals(parser.getName())) {
                            news.setComment(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName())) {
                            newsList.add(news);
                        }
                        break;
                }
                type = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
```

## 我的歌曲库

**关键词：**
> ListView + SQLiteOpenHelper + CursorAdapter + setOnItemClickListener + onCreateOptionsMenu + onOptionsItemSelected

主要功能：
> 创建数据库，根据选项菜单完成相应的增删改操作，使用`SimpleCursorAdapter`将数据查询出来实时地展示到ListView上。

1. 找到控件
2. 创建数据库，拿到该数据库
3. 设置数据适配器
4. 给lv中数据项设置点击监听
5. 创建选项菜单，实现菜单的功能
6. 编写数据的增删改查操作
7. 每次更改数据后更新UI

`DbHelper.java`
```java
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "songs.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table mysongs (_id integer primary key autoincrement , title text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor select(SQLiteDatabase db) {
        Cursor cursor = null;

        String table = "mysongs";
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = "_id desc";
        cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

        return cursor;
    }

    public long insert(SQLiteDatabase db, String title) {
        String table = "mysongs";
        String nullColumnHack = null;
        ContentValues values = new ContentValues();
        values.put("title", title);
        long rowId = db.insert(table, nullColumnHack, values);

        return rowId;
    }

    public int update(SQLiteDatabase db, int id, String title) {
        String table = "mysongs";
        ContentValues values = new ContentValues();
        values.put("title",title);
        String whereClause = "_id = ?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        int rows = db.update(table, values, whereClause, whereArgs);

        return rows;
    }

    public int delete(SQLiteDatabase db, int id) {
        String table = "mysongs";
        String whereClause = "_id = ?";
        String[] whereArgs = new String[] {String.valueOf(id)};;
        int rows = db.delete(table, whereClause, whereArgs);

        return rows;
    }
}
```

```java
    // 找到控件
    et = (EditText) findViewById(R.id.et);
    lv = (ListView) findViewById(R.id.lv);

    // 初始化数据库
    dbHelper = new DbHelper(getApplicationContext());
    // 拿到该数据库
    db = dbHelper.getWritableDatabase();

    Context context = getApplicationContext();
    int layout = R.layout.item;
    // 拿到数据库的游标
    c = dbHelper.select(db);
    String[] from = new String[] {"title"};
    int[] to = new int[] {R.id.tv};
    int flags = CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER;
    adapter = new SimpleCursorAdapter(context, layout, c, from, to, flags);

    // 设置数据适配器
    lv.setAdapter(adapter);

    // 设置数据项被点击的事件监听
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            c.moveToPosition(position);
            _id = c.getInt(0);
            String title = c.getString(1);
            et.setText(title);
        }
    });


// 创建选项菜单
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    int groupId = Menu.NONE;
    int itemId = Menu.FIRST;
    int order = 0;
    menu.add(groupId, itemId, order, "新增");
    menu.add(groupId, itemId + 1, order, "修改");
    menu.add(groupId, itemId + 2, order, "删除");

    return super.onCreateOptionsMenu(menu);
}

// 定义选项菜单的功能
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    String title = et.getText().toString().trim();
    switch (item.getItemId()) {
        // 判断增删改的类型
        case Menu.FIRST:
            long rowId = dbHelper.insert(db, title);
            if (rowId != -1) {
                Toast.makeText(getApplicationContext(), "新增成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "新增失败！", Toast.LENGTH_SHORT).show();
            }
            break;
        case Menu.FIRST + 1:
            int rows = dbHelper.update(db, _id, title);
            Toast.makeText(getApplicationContext(), "修改" + rows + "行", Toast.LENGTH_SHORT).show();
            break;
        case Menu.FIRST + 2:
            rows = dbHelper.delete(db, _id);
            Toast.makeText(getApplicationContext(), "删除" + rows + "行", Toast.LENGTH_SHORT).show();
            break;
    }

    // TODO: 2016/4/3 更新ListView
    et.setText("");
//  c.requery(); // @Deprecated
    c = dbHelper.select(db);
    adapter.changeCursor(c);
    lv.invalidate();

    return super.onOptionsItemSelected(item);
}
```

## 向服务器发送HTTP网络请求

### 使用 `HttpURLConnection` 构造网络请求

* GET
```java
new Thread() {
    @Override
    public void run() {
        String name = et_username.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();
        try {
            // 组拼请求路径
            String path = "http://169.254.80.1:8080/Login4Android/login?username=" + name +"&password=" + pwd;
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            int code = con.getResponseCode();
            if (code == 200) {
                InputStream in = con.getInputStream();
                // 解析网络返回的流数据
                String content = StreamTools.readStream(in);
                // 根据返回的内容更新UI
                showToast(content);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}.start();;
```

* POST
```java
new Thread() {
    @Override
    public void run() {
        String name = et_username.getText().toString().trim();
        String pwd = et_password.getText().toString().trim();
        // 构建请求实体内容
        String data = "username=" + name + "&password=" + pwd;
        String path = "http://169.254.80.1:8080/Login4Android/login";
        try {
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(5000);
            // 设置请求头的字段值
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 指定请求内容的长度
            con.setRequestProperty("Content-Length", String.valueOf(data.length()));
            // 设置URL连接是否允许输出
            con.setDoOutput(true);
            // 向服务器写数据
            con.getOutputStream().write(data.getBytes());
            int code = con.getResponseCode();
            if (code == 200) {
                InputStream in = con.getInputStream();
                // 解析网络返回的流数据
                String content = StreamTools.readStream(in);
                // 根据返回的内容更新UI
                showToast(content);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}.start();
```

### 使用开源项目 `AsyncHttpClient` 提交网络请求

* GET
```java
String name = et_username.getText().toString().trim();
String pwd = et_password.getText().toString().trim();
String url = "http://169.254.80.1:8080/Login4Android/login?username=" + name +"&password=" + pwd;

AsyncHttpClient client = new AsyncHttpClient();

ResponseHandlerInterface responseHandler = new AsyncHttpResponseHandler() {
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    }
};

client.get(url, responseHandler);
```


* POST
```java
String name = et_username.getText().toString().trim();
String pwd = et_password.getText().toString().trim();
String url = "http://169.254.80.1:8080/Login4Android/login";

AsyncHttpClient client = new AsyncHttpClient();

// 构造请求参数
RequestParams params = new RequestParams();
params.put("username", name);
params.put("password", pwd);

ResponseHandlerInterface responseHandler = new AsyncHttpResponseHandler() {
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    }
};

client.post(url, params, responseHandler);
```

## 多线程下载&断点续传

1. 建立网络连接，获取远程文件大小
2. 根据线程数量，计算每个线程下载的大小
3. 计算每个线程下载开始和结束的位置，并开启子线程下载

**子线程的操作**
1. 建立网络连接，设置请求头为分段下载
2. 创建随机访问文件对象，设置文件指针偏移量，获取输入流写到文件对象
3. 根据当前线程已下载的大小，保存下载进度以便下次继续下载
   3.1 下次开启下载时从文件中读取保存的下载进度，修改当前线程开始下载的位置为上次下载到的位置
4. 下载结束关闭随机访问文件对象，并删除保存进度的文件

```java
package com.itheima.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MultiThreadDownload {

	private static String path = "http://localhost:8080/QQ2013.exe";
	private static int threadCount = 3;
	
	private static String getFileName(String path) {
		
		return path.substring(path.lastIndexOf("/") + 1);
	}

	public static void main(String[] args) {
		try {
			URL url = new URL(path);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			int code = con.getResponseCode();
			if (code == 200) {
				// 获取文件大小
				int length = con.getContentLength();
//				System.out.println(length);
				// 如果本地已经存在完整的文件，就删除
				File file = new File(getFileName(path));
				if (file.length() == length) {
					file.delete();
//					new RandomAccessFile(file, "rw").setLength(length);
				}
				
				/*// 在本地创建相同大小的文件，提前申请空间
				RandomAccessFile raf = new RandomAccessFile("QQ2013.exe", "rw");
				raf.setLength(length);*/
				
				// 计算每个线程下载的大小
				int blockSize = length / threadCount;
				
				for (int i = 0; i < threadCount; i++) {
					// 计算每个线程下载的开始位置和结束位置
					int startIndex = i * blockSize;
					int endIndex = (i + 1) * blockSize - 1;
					// 计算最后一个线程下载的结束位置
					if (i == threadCount - 1) {
						endIndex = length - 1;
					}
					System.out.println("线程id" + i + "理论下载位置" + startIndex + "-" + endIndex);
					
					// 指定线程id和下载位置，开启多线程下载
					DownloadThread dt = new DownloadThread(i, startIndex, endIndex);
					dt.start();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class DownloadThread extends Thread {
		private int startIndex, endIndex, threadId;
		
		public DownloadThread(int threadId, int startIndex, int endIndex) {
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		@Override
		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setConnectTimeout(5000);
				
				// 读取保存的下载进度
				File file = new File(threadId + ".txt");
				if (file.exists() && file.length() > 0) {
					FileInputStream fis = new FileInputStream(file);
					BufferedReader bufr = new BufferedReader(new InputStreamReader(fis));
					int lastPosition = Integer.parseInt(bufr.readLine());
					// 设置开始下载的位置 = 上次下载的位置
					startIndex = lastPosition;
					System.out.println("线程id" + threadId + "实际下载位置" + lastPosition + "-" + endIndex);
					fis.close();
				}
				
				// 设置请求头Range
				con.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
				int code = con.getResponseCode();
				if (code == 206) {
					RandomAccessFile raf = new RandomAccessFile(getFileName(path), "rw");
					// 设置文件指针的偏移量
					raf.seek(startIndex);
					
					InputStream in = con.getInputStream();
					int len = -1;
					// 合理设置缓冲区大小
					byte[] buffer = new byte[1024 * 1024];
					int total = 0; // 当前线程已下载的大小
					while ((len = in.read(buffer)) != -1) {
						raf.write(buffer, 0, len);
						total += len;
						// 当前线程下载到的位置
						int currentThreadPosition = startIndex + total;
						
						// 保存下载进度
						RandomAccessFile raff = new RandomAccessFile(threadId + ".txt", "rwd");
						raff.write(String.valueOf(currentThreadPosition).getBytes());
						raff.close();
					}
					// 当前线程下载结束，关闭文件流
					raf.close();
					System.out.println("--- 线程id" + threadId + "下载完毕 ---");
					
					// 删除保存进度的文件
					boolean delete = new File(threadId + ".txt").delete();
					System.out.println(delete ? "删除成功" : "删除失败");
					
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

```

## Android中的多线程下载&断点续传
*使用开源项目 `xUtils`*
```java
// 下载按钮的onClick事件
public void download(View v) {
    // 获取下载路径
    String path = et_path.getText().toString().trim();

    HttpUtils http = new HttpUtils();
    // 文件的保存路径
    String target = Environment.getExternalStorageDirectory().getPath() + "/" + path.substring(path.lastIndexOf("/") + 1);
//        Log.i("target", target);
    boolean autoResume = true; // 是否支持断点续传
    // 开始下载
    http.download(path, target, autoResume, new RequestCallBack<File>() {
        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            // 设置进度条的最大范围
            progressBar.setMax((int) total);
            // 设置当前下载进度
            progressBar.setProgress((int) current);
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
        }
    });
}
```

## 开启Intent
> 绑定应用程序组件，并在应用程序之间进行通信。一般用于启动Activity、启动服务、发送广播等，承担了Android应用程序三大核心组件相互间的通信功能。

### 显示意图
```java
Intent intent = new Intent();
// 指定具体的应用包名和完整类名
intent.setClassName(packageName, className);
startActivity(intent);

startActivity(new Intent(packageContext, cls));
```

### 隐式意图
```java
Intent intent = new Intent();
intent.setAction(action);
intent.addCategory(category);
// setData和setType不能同时使用
// intent.setData(Uri.parse(scheme+data));
// intent.setType(mimeType);
intent.setDataAndType(Uri.parse(scheme+data), type);
startActivity(intent);
```

## Activity中的数据传递
1. putExtra()
   intent.putExtra(key, value);
   /***********************/
   Xxx data = intent.getXxxExtra(key);
2. putExtras()
   Bundle bundle = new Bundle();
   bundle.putInt(key, value);
   bundle.putString(key, value);

 intent.putExtras(bundle);
 /***********************/
 Bundle bundle = intent.getExtras();

 int value = bundle.getInt(key);
 String value = bundle.getString(key);

## 短信发送器

`activity.xml`
```
<EditText
    android:id="@+id/et_number"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="请输入手机号码" />

<Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="+"
    android:onClick="add"
    android:layout_alignBottom="@+id/et_number"
    android:layout_alignParentRight="true"/>

<EditText
    android:id="@+id/et_content"
    android:hint="请输入短信内容"
    android:gravity="top"
    android:layout_below="@+id/et_number"
    android:layout_width="match_parent"
    android:layout_height="350dp" />

<Button
    android:id="@+id/bt_insert"
    android:onClick="insert"
    android:text="插入短信模板"
    android:layout_below="@+id/et_content"
    android:layout_alignParentLeft="true"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />

<Button
    android:onClick="send"
    android:text="发送"
    android:layout_alignBottom="@+id/bt_insert"
    android:layout_alignParentRight="true"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
`MainActivity.java`
```java
    // 插入联系人
    public void add(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        int requestCode = 0;
        startActivityForResult(intent, requestCode);
    }
    // 插入短信模板
    public void insert(View view) {
        Intent intent = new Intent(this, TemplateActivity.class);
        int requestCode = 1;
        startActivityForResult(intent, requestCode);
    }

```
`activity_contact.xml`
```xml
<ListView
    android:id="@+id/lv"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
`item_contact.xml`
```xml
<TextView
    android:id="@+id/tv_name"
    android:text="姓名"
    android:textSize="20sp"
    android:textColor="#000000"
    android:layout_width="0dp"
    android:layout_weight="1"
    android:layout_height="wrap_content" />

<TextView
    android:id="@+id/tv_phone"
    android:text="电话号码"
    android:textSize="20sp"
    android:textColor="#000000"
    android:layout_width="0dp"
    android:layout_weight="1"
    android:layout_height="wrap_content" />
```
`ContactActivity.java`
```java
    private List<Person> list;
    private int resultCode = -1;
    private Intent data = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ListView lv = (ListView) findViewById(R.id.lv);

        list = new ArrayList<>();
        list.add(new Person("中国电信客服", "10000"));
        list.add(new Person("中国移动客服", "10086"));
        list.add(new Person("电信短信营业厅", "10001"));

        lv.setAdapter(new MyBaseAdapter());

        // 设置条目被点击的事件监听
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = list.get(position).getPhone();

                resultCode = 0;
                // 将数据存入intent
                data.putExtra("phone", phone);
                // 将结果返回给调用者
                setResult(resultCode, data);
                // 关闭当前界面
                finish();
            }
        });

    }

    // 当返回键按下执行的业务逻辑
    @Override
    public void onBackPressed() {
        data.putExtra("phone", "");
        setResult(resultCode);

        super.onBackPressed();
    }

    private class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.item_contact, null);
            } else {
                view = convertView;
            }

            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            tv_name.setText(list.get(position).getName());
            tv_phone.setText(list.get(position).getPhone());

            return view;
        }
    }
```
`activity_template.xml`
```xml
<ListView
    android:id="@+id/lv"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```
`item_template.xml`
```xml
<TextView
    android:id="@+id/tv"
    android:textColor="#000000"
    android:textSize="20sp"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```
`TemplateActivity.java`
```java
    private String[] objects = {"现在无法接听。有什么事吗？", "我马上会打给你。", "我稍后会再打给你。", "现在无法接听。能稍后再打给我吗？"};
    private int resultCode = -1;
    private Intent data = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        ListView lv = (ListView) findViewById(R.id.lv);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.item_template, R.id.tv, objects);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = objects[position];

                resultCode = 1;
                data.putExtra("content", content);

                setResult(resultCode, data);

                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        data.putExtra("content", "");
        setResult(resultCode, data);

        super.onBackPressed();
    }
```

`MainActivity.java`
```java
    // 获取开启界面返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            et_number.setText(data.getStringExtra("phone"));
        } else if (resultCode == 1) {
            et_content.setText(data.getStringExtra("content"));
        }

    }
    
    // 发送短信
    public void send(View view) {
        SmsManager manager = SmsManager.getDefault();
        String destinationAddress = et_number.getText().toString().trim();
        String content = et_content.getText().toString().trim();

        if (destinationAddress.length() == 0) {
            Toast.makeText(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else if (content.length() == 0) {
            Toast.makeText(getApplicationContext(), "请输入短信内容", Toast.LENGTH_SHORT).show();
            return;
        }

        String scAddress = null;
        // 分割可能过长的短信内容
        ArrayList<String> texts = manager.divideMessage(content);
        PendingIntent sentIntent = null;
        PendingIntent deliveryIntent = null;

        for (String text : texts) {
            manager.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent);
        }
    }
```


`AndroidManifest.xml`
```xml
<application>
    <!--......-->
    <activity android:name=".ContactActivity" />
    <activity android:name=".TemplateActivity" />
</application>

<uses-permission android:name="android.permission.SEND_SMS" />
```

## Activity四种启动模式
```xml
<activity android:launchMode="standard" />
<activity android:launchMode="singleTop" />
<activity android:launchMode="singleTask" />
<activity android:launchMode="singleInstance" />
```

* standard
  应用程序的默认启动模式

* singleTop
  **单一顶部模式**
  如果任务栈的栈顶存在这个要开启的activity，不会重新创建activity，而是复用已经存在activity。保证栈顶如果存在，不会重复创建。
  *应用场景：浏览器的书签*

* singleTask
  **单一任务栈**
  在当前任务栈里面只能有一个实例存在
  当开启activity的时候，就去检查在任务栈里面是否已经存在，如果有实例存在就复用这个已经存在的activity，并且把这个activity上面所有的别的activity都清空，复用这个已经存在的activity。保证整个任务栈里面只有一个实例存在。
  *应用场景：浏览器的activity*
  如果一个activity的创建需要占用大量的系统资源，一般配置这个activity为singleTask启动模式。

* singleInstance
  会运行在自己的任务栈里面，并且这个任务栈里面只有一个实例存在。如果要保证一个activity在整个手机操作系统里面只有一个实例存在，使用singleInstance。
  *应用场景：来电页面*


## Activity生命周期

`onCreate()`
当Activity第一次创建时调用。
静态初始化的地方：创建视图，绑定集合数据等等。这个方法也提供了一个Bundle包含先前冻结的状态，如果有的话。
`onReStart()`
activity停止过之后，start之前调用。
`onStart()`
当activity对用户可见时调用。
`onResume()`
当activity开始和用户交互时调用。在这时，居于activity栈顶，用户可以输入。
`onPause()`
当系统要开始恢复一个先前的activity时调用。典型的用法是提交未保存的改变到持久数据，停止动画，和其他可能会消耗CPU的操作等等。这个方法的实现必须非常快，因为下一个activity不会恢复直到这个方法返回。
`onStop()`
当对用户不再可见时调用，因为另一个activity已经恢复并且覆盖当前。可能发生在一个新的activity正在被开启，一个已存在的来到当前的前面，或者当前正在被销毁。
`onDestroy()`
在activity销毁之前最终调用。发生在 activity将要完成或者系统为了节省空间临时销毁当前activity的实例。可以使用`isFinishing()`区分这两种情况。

**横竖屏切换一次完整的生命周期**
onPause: 
onStop: 
onDestroy: 
onCreate: 
onStart: 
onResume: 

```xml
<!--指定竖屏-->
<activity android:screenOrientation="portrait"/>
<!--指定横屏-->
<activity android:screenOrientation="landscape"/>
```

## 广播接收者
> 广播是一种运用在应用程序之间传递消息的机制，广播接收者是用来过滤、接收并响应广播的一类组件，通过广播接收者可以监听系统中的广播消息，在不同组件之间进行通信。


## 短信防火墙

> [Beginning with Android 4.4, you should stop listening for the SMS_RECEIVED_ACTION broadcast, which you can do at runtime by checking the platform version then disabling your broadcast receiver for SMS_RECEIVED_ACTION with PackageManager.setComponentEnabledSetting(). However, you can continue listening for that broadcast if your app needs only to read special SMS messages, such as to perform phone number verification. Note that—beginning with Android 4.4—any attempt by your app to abort the SMS_RECEIVED_ACTION broadcast will be ignored so all apps interested have the chance to receive it.](http://android-developers.blogspot.jp/2013/10/getting-your-sms-apps-ready-for-kitkat.html)

## 帧动画

> java.lang.OutOfMemoryError: Failed to allocate a 41990412 byte allocation with 16777216 free bytes and 16MB until OOM
