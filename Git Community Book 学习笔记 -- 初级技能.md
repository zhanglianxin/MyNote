# Git Community Book 学习笔记 -- 初级技能

tags: git初级

---

## Git对象模型
### 对象
每个对象包括**类型、大小、内容**三个部分。
大小就是指内容的大小，内容取决于对象的类型，有四种类型的对象：*“blob”、“tree”、“commit”和“tag”*。

> * “blob”用来存储文件数据，通常是一个文件。 
> * “tree”有点像一个目录，它管理一些“tree”或是“blob”（就像文件和子目录）
> * 一个“commit”只指向一个“tree”，它用来标记项目某一个特定时间点的状态。它包括一些关于时间点的元数据，如时间戳、最近一次提交的作者、指向上次提交的指针等等。
> * 一个“tag”是来标记一个提交的方法

### 与SVN的区别

~~增量文件系统（Delta Storage systems）~~

Git把每次提交的文件的全部内容（snapshot）都会记录下来

### Blob对象
通常用来存储文件的内容。
一个“blob对象”就是一块二进制数据，它没有指向任何东西或有任何其它属性，甚至连文件名都没有。
因为blob对象全部都是数据，如两个文件在一个目录树（或是一个版本仓库）中有同样的数据内容，那么它们将会共享同一个blob对象。Blob对象和其所对应的文件所在路径、文件名是否被更改都完全没有关系。

    git show <sha1> # 查看一个blob对象里的内容

### Tree对象
一个tree对象有一串指向blob对象或是其它tree对象的指针，它一般用来表示内容之间的目录层次关系。
一个tree对象包括一串条目，每一个条目包括：mode、对象类型、SHA1值和名字。它用来表示一个目录树的内容。
一个tree对象可以指向：一个包含文件内容的blob对象，也可以是其它包含某个子目录内容的其它tree对象。（在submodule里，tree对象也可以指向commit对象）

    git ls-tree <sha1> // 查看tree对象

### Commit对象
指向一个tree对象，并且带有相关的描述信息。

    git show -s --pretty=raw <sha1> // 查看commit详细内容

一个提交由以下部分组成：
> * 一个tree对象：tree对象的SHA1签名，代表着目录在某一时间点的内容
> * 父对象：提交的SHA1签名代表着当前提交前一步的项目历史
> * 作者：做了此次修改的人的名字，还有修改日期
> * 提交者：实际创建提交的人的名字，同时也带有提交日期
> * 注释：用来描述此次提交

一般用`git commit`来创建一个提交，父对象一般是当前分支，同时把存储在当前索引的内容全部提交。

### Tag对象
一个标签对象包括一个对象名，对象类型，标签名，标签创建人的名字，还有一条可能包含有签名的消息。

    git cat-file tag < tagname >

## Git目录与工作目录
### Git目录
是为项目存储所有历史和元信息的目录 - 包括所有的对象，这些对象指向不同的分支。
每一个项目只能有一个“Git目录”，这个叫*“.git”*的目录在项目的根目录下。（默认设置）

    $>tree -L 1
    .
    |-- HEAD        # 这个git项目当前在哪个分支里
    |-- config      # 项目的配置信息，git config 命令会改动它
    |-- description # 项目的描述信息
    |-- hooks/      # 系统默认钩子脚本目录
    |-- index       # 索引文件
    |-- logs/       # 各个refs的历史信息
    |-- objects     # Git本地仓库的所有对象
    |-- refs/       # 标识项目里的每个分支指向了哪个提交

### 工作目录
存储着现在签出（checkout）来用来编辑的文件。当在项目的不同分支间切换时，工作目录里的文件经常会被替换和删除。所有历史信息都保存在“Git目录”中；工作目录只用来临时保存签出文件的地方，可以编辑工作目录的文件直到下次提交为止。

## Git索引
一个在工作目录和项目仓库间的暂存区（staging area）。有了它，可以把许多内容的修改一起提交。如果创建了一个提交，那么提交的是当前索引里的内容，而不是工作目录中的内容。

### 查看索引
使用`git status`命令是查看索引内容的最简单办法。
如果完全掌握了索引，就一般不会丢失任何信息，只要记得名字描述信息就能把它们找回来。


## 安装与初始化
### Git配置
使用Git的第一件事就是设置名字和email，这些就是在提交commit时的签名。

    git config --global user.name "zhanglianxin"
    git config --global user.email "zhanglianxin1993@163.com"

## 获得一个Git仓库
### Clone一个仓库
    git clone git@github.com:zhanglianxin/zhanglianxin.github.io.git 
    # 使用ssh支持的git协议速度快
    git clone https://github.com/zhanglianxin/zhanglianxin.github.io.git
### 初始化一个新的仓库
    git init

## 正常的工作流程
修改文件，将它们更新的内容添加到索引中。

    git add file1 file2 file3
现在为commit做好了准备，使用`git diff`命令再加上`--cached`参数，看看哪些文件将被提交。

    git diff --cached
（如果没有`--cached`参数，`git diff`会显示当前所有已做的但没有加入到索引里的修改）。也可以用`git status`命令来获得当前项目的一个状况。
如果要做进一步的修改，那就继续做。做完后就把心修改的文件加入到索引中。最后把他们提交：

    git commit
这会提示你输入本次修改的注释，完成后就会记录一个新的项目版本。
除了用`git add`命令，还可以用

    git commit -a
这会自动把所有内容被修改的文件（不包括新创建的文件）都添加到索引中，并且同时把它们提交。
【**commit注释的技巧**】
最好以一行短句子作为开头，来简要描述一下这次commit所作的修改（最好不要超过50个字符）；然后空一行再把详细的注释写清楚。这样就可以很方便地用工具把commit注释变成email通知，第一行作为标题，剩下的部分就作email的正文。

###Git跟踪的是内容不是文件
`git add`不但是用来添加不在版本控制中的新文件，也用于添加已在版本控制中但是刚修改过的文件；在这两种情况下，Git都会获得当前文件的快照并且把内容暂存（stage）到索引中，为下一次commit做好准备。

## 分支与合并基础
一个Git仓库可以维护很多开发分支。
现在创建一个新的叫“experimental”的分支：

    git branch experimental
运行下面这条命令：

    git branch
会得到当前仓库中存在的所有分支列表：

    experimental
    *master
星号（“*”）标识了当前工作在哪个分支下，输入：

    git checkout experimental
切换到“experimental”分支，先编辑里面的一个文件，再提交改动，最后切换回“master”分支。

    (edit file)
    git commit -a
    git checkout master
现在可以看一下原来在“experimental”分支下所做的修改还在不在；因为现在切换回了“master”分支，所以原来那些修改就不存在了。
现在可以在“master”分支下再做一些不同的修改：

    (edit file)
    git commit -a
这时，两个分支就有了各自不同的修改（diverged）；可以通过下面的命令来合并“experimental”和“master”两个分支：

    git merge experimental
如果这两个分支间的修改没有冲突（conflict），那么合并就完成了。如果有冲突，输入下面的命令就可以查看当前有哪些文件产生了冲突：

    git diff
当编辑了有冲突的文件，解决了冲突后就可以提交了：

    git commit -a
提交了合并的内容后就可以查看一下：

    gitk
执行了`gitk`后会有一个很漂亮的图形的显示项目的历史。
这时就可以删除“experimental”分支了：

    git branch -d experimental
`git branch -d`只能删除那些已经被当前分支合并的分支。如果要强制删除某个分支就用`git branch -D`；下面假设要删除一个叫“crazy-idea”的分支：

    git branch -D crazy-idea
分支是很轻量级且容易的，这样就很容易来尝试它。
### 如何合并
可以用下面的命令来合并两个分离的分支：`git merge`

    git merge branchname
这个命令把分支“branchname”合并到了当前分支里面。如有冲突（冲突--同一个文件在远程分支和本地分支里按不同的方式被修改了），那么命令的执行输出就会产生提示：

    自动合并失败；解决冲突再提交结果
在有问题的文件上会有冲突标记，手动解决完冲突后就可以把此文件添加到索引中去，用`git commit`命令来提交。
如果用`gitk`来查看commit的结果，会看到它有两个父分支：一个指向当前的分支，另外一个指向刚才合并进来的分支。
### 解决合并中的冲突
如果执行自动合并没有成功的话，git会在索引和工作树里设置一个特殊的状态，提示你如何解决合并中出现的冲突。
有冲突的文件会保存在索引中，除非解决了问题并且更新了索引，否则执行`git commit`都会失败。
如果执行`git status`会显示这些文件没有合并，这些有冲突的文件里面会添加冲突标识符。
需要做的就是编辑解决冲突，（接着把冲突标识符删掉），再执行下面的命令：

    git add filename
    git commit
注意：提交注释里已经有一些关于合并的信息了，通常是用这些默认信息，但是可以添加一些你想要的注释。

上面这些就是你要做一个简单合并所要知道的，但是git提供更多的信息来帮助解决冲突。
### 撤销一个合并
如果合并后想把当前的修改都放弃，用下面的命令回到合并之前的状态：

    git reset --hard HEAD
或者已经把合并后的代码提交，但还是想撤销：

    git reset --hard ORIG_HEAD
但是刚才这条命令在某些情况会很危险，如果把一个已经被另一个分支合并的分支给删了，那么以后在合并相关的分支时会出错。
### 快速向前合并
通常，一个合并会产生一个合并提交，把两个父分支里的每一个行内容都合并进来。
但是，如果当前的分支和另一个分支没有内容上的差异，就是说当前分支的每一个提交都已经存在另一个分支里了，git就会执行一个“快速向前（fast forward）”操作；git不创建任何新的提交爱哦，只是将当前分支指向合并进来的分支。

## 查看历史 - Git日志
`git log`命令可以显示所有的提交

    git log v2.5..          # commits since (no reachable from) v2.5
    git log test..master    # commits reachable from master but not test
    git log master..test    # commits reachable from test but not master
    git log master...test   # commits reachable from either test or master, 
                                but not both
    git log --since="2 weeks ago" # commits from the last 2 weeks
    git log Makefile        # commits that modify Makefile
    git log fs/             # commits that modify any file 
                                under fs/
    git log -S'foo()'       # commits that add or remove any file data matching the string 'foo()'
    git log --no-merge      # don't show merge commits
Git会根据`git log`命令的参数，按时间顺序显示相关的提交
也可以让`git log`显示补丁（patchs）：

    git log -p
### 日志统计
如果用`--stat`选项使用`git log`，它会显示在每个提交中哪些文件被修改了，这些文件分别添加或删除了多少行内容。

### 格式化日志
按要求来格式化日志输出。`--pretty`参数可以使用若干表现形式，如'oneline'：

    git log --pretty=oneline
或者可以使用'short'格式：

    git log --pretty=short
也可用'medium'，'full'，'fuller'，'email'或'raw'。也可以用`--pretty=format`参数来创建自动的“格式”：

    git log --pretty=format:'%h was %an, %ar, message: %s'
可以用`--graph`选项来可视化提交图：

    git log --pretty=format:'%h : %s' --graph
它会用ASCII字符来画出一个很漂亮的提交历史线。
### 日志排序
可以把日志记录按一些不同的顺序来显示。
如果要指定一个特定的顺序，可以为`git log`命令添加顺序参数（ordering option）。
按默认情况，提交会按逆时间（reverse chronological）顺序显示。
可以指定`--topo-order`参数，这就会让提交按拓扑顺序来显示（就是子提交在它们的父提交前显示）。如果用`git log`命令按拓扑顺序来显示git仓库的提交日志，会看到“开发线（development lines）”都会集合在一起。
也可以用`--data-order`参数，这样显示提交日志的顺序主要按提交日期来排序。这个参数和`--topo-order`有一点像，没有父分支会在它们的字分支前显示，但是其他的东西还是按提交时间来排序显示。会看到“开发线”没有集合一起，它们会像并行开发一样跳来跳去的。
最后，可以用`--reverse`参数来逆向显示所有日志。

## 比较提交 - Git Diff
可以用`git diff`来比较项目中任意两个版本的差异。

    git diff master..test
上面这条命令只显示两个分支间的差异，如果想找出‘master’‘test’的共有 父分支和‘test’分支之间的差异，则使用：

    git diff master...test
`git diff`可以找出项目上任意两点间的改动，或是用来查看别人提交进来的新分支。
### 哪些内容会被提交
通常用`git diff`来找当前工作目录和上次提交与本地索引间的差异。

    git diff
上面的命令会显示在当前的工作目录里，没有staged，且在下次提交时不会被提交的修改。
如果要看在下次提交时要提交的内容（staged，添加到索引中），可以运行：

    git diff --cached
上面的命令会显示当前的索引和上次提交间的差异；这些内容在不带“-a”参数运行 `git commit`命令时就会被提交。

    git diff HEAD
上面这条命令会显示工作目录与上次提交时之间的所有差别，这条命令所显示的内容都会在执行`git commit -a`命令时被提交。
### 更多的比较选项
如果要查看当前的工作目录与另外一个分支的差别，可以用下面的命令执行：

    git diff test
这会显示当前工作目录与另外一个叫“test”分支的差别。也可以加上路径限定符，来只比较某一个文件或目录。

    git diff HEAD -- ./lib
上面这条命令会显示当前目录下的lib目录与上次在当前分支提交之间的差别。

如果不是查看每个文件的详细差别，而是统计一下有哪些文件被改动，有多少行被改动，就可以使用`--stat`参数。

    git diff --stat

## 分布式的工作流程

### 公共Git仓库

### 将修改推到一个公共仓库

### 当推送代码失败时要怎么办
强制`git push`在上传修改时先更新，只要在分支名前面加一个加号。

    git push ssh://yourserver.com/~you/proj.git +master

## Git标签

### 轻量级标签
可以用`git tag`不带任何参数创建一个标签指定某个提交

    git tag stable-1 <sha1>
这样，可以用 `stable-1` 作为提交 `sha1` 的代称（refer）。
这样创建的是一个“轻量级标签”，这种分支通常是从来不移动的。
如果想为一个标签添加注释，或是为它添加一个签名，那么就需要创建一个“标签对象”。
### 标签对象
如果有“-a”、“-s”或是“-u”中间的一个命令参数被指定，那么就会创建一个标签对象，并且需要一个标签消息（tag message）。如果没有“-m”或是“-F”这些参数，那么就会启动一个编辑器来让用户输入标签消息。
当这样一条命令执行后，一个新的对象被添加到Git对象库中，并且标签引用就指向了一个标签对象，而不是指向一个提交。这样做的好处就是：可以为一个标签打处签名（sign），方便以后来查验这是不是一个正确的提交。

    git tag -a stable-1 <sha1>
标签对象可以指向任何对象，但是在通常情况下是一个提交。
### 签名的标签
~~~

~~~