# gitignore

> [Git - gitignore Documentation](https://git-scm.com/docs/gitignore)

## NAME

gitignore - 有意地指定不跟踪的文件为忽略状态

## SYNOPSIS

`$HOME/.config/git/ignore, $GIT_DIR/info/exclude, .gitignore`

## DESCRIPTION

`gitignore` 文件有意地指定不跟踪的文件，那些 Git 应该忽略的。已被 Git 跟踪的文件不受影响，详情参见下面的 [NOTES](## NOTES) 。

`gitignore` 文件的每一行都指定了一个模式。当要决定是否忽略一个路径时， Git 通常从多种源文件检查 `gitignore` 模式，使用下面的优先级顺序，从最高到最低（同一优先级中。最后匹配的模式决定了结果）。

* 从命令行读入的支持模式的命令

* 从和路径相同的目录下读入的 `.gitignore` 文件，或者在任一父目录下，更高层级（上至工作树的顶层）

  的文件模式会被更低层级（下至包含当前文件的路径）的文件覆盖。这些模式匹配关系到 `.gitignore` 文件的定位。一个项目通常在它的仓库中包括如 `.gitignore` 的文件，包含文件模式，产生于项目构建的一部分。

* 从 `$GIT_DIR/info/exclude` 读入的模式

* 从配置变量指定的文件读入的模式 `core.excludesFile`


哪个文件要设置模式取决于模式要怎样使用

* 应该被版本控制并且能通过克隆被分发到其它仓库的模式（例如，所有开发者都想忽略的文件）应该写入 `.gitignore` 文件
* 作用于一个特殊仓库但是不需要和其它仓库共享的模式（例如，存在于仓库中的辅助文件但是作用于一个用户的工作流）应该写入 `$GIT_DIR/info/exclude` 文件
* 一个用户想要 Git 在所有情况下都忽略的模式（例如，由用户选择的编辑器所产生的备份或临时文件）一般写入一个在用户的配置文件 `~/.gitconfig` 中被 `core.excludesFile` 指定的文件。它的默认值是 `$XDG_CONFIG_HOME/git/ignore` 。如果 未设置或者为空，会使用 `$HOME/.config/git/ignore` 代替

底层的 Git 管道工具，例如 `git ls-files` 和 `git read-tree` ，读取命令行选项指定的 `gitignore` 模式，或者从命令行选项指定的文件读取。更高级的 Git 工具，例如 `git status` 和 `git add` ，使用上面的源文件指定的模式。

## PATTERN FORMAT

* 一个空行不匹配文件，所以它可以用来作为分隔符提高可读性
* 以 # 开始的行作为注释使用。在第一个 # 之前放一个反斜线 `\` 可使模式以 # 开始
* ​


## NOTES

