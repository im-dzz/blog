drop table blog;
create table blog(
serialno int auto_increment primary key,
title varchar(100) not null unique,
sub_title varchar(100),
content text,
create_date datetime,
update_date datetime
);

INSERT into blog values(0 ,'0101', '第一篇博客', '博客博客', 'something', sysdate(), sysdate()); 
INSERT into blog values(0 , 'Linux: 发布应用', '使用脚本自动发布', "<h2><a name=\"header-n179\" class=\"md-header-anchor md-print-anchor\" href=\"af://n179\"> </a><span>步骤</span></h2>
<h3><a name=\"header-n180\" class=\"md-header-anchor md-print-anchor\" href=\"af://n180\"> </a><span>准备过程</span></h3>
<p>	<span>到项目路径下找到build.bat并运行之，会在target目录生成一个jar包。</span></p>
<h3><a name=\"header-n182\" class=\"md-header-anchor md-print-anchor\" href=\"af://n182\"> </a><span>发布过程</span></h3>
<ol>
<li><p><span>登录服务器进入目录“/httx/run/.....”</span></p>
</li>
<li><p><span>运行shutdown.sh,关闭原有进程：sh shutdown.sh</span></p>
</li>
<li><p><span>删除旧的jar包： rm -f xxxxx.jar</span></p>
</li>
<li><p><span>将新生成的jar包拖到目标路径。</span></p>
</li>
<li><p><span>运行startup.sh,启动新的程序：sh startup.sh。shutdown.sh并不是立即终止，所有可能报端口被占用。</span></p>
<p><span>解析</span></p>
</li>

</ol>
<h3><a name=\"header-n195\" class=\"md-header-anchor md-print-anchor\" href=\"af://n195\"> </a><span>批处理文件解析</span></h3>
<ol>
<li><p><span>build.bat</span></p>
<pre><code class=\\'language-shell\\' lang=\\'shell\\'>mvn clean install -Dmaven.test.skip=true &gt;&gt;1.log
</code></pre>
<p>	<span>这只是一个maven命令，“clean” 清理删除已有的target目录；“install”清理、编译、测试、打包后再把项目发布到maven仓库（方便不同的项目间相互依赖）。“-Dmaven.test.skip=true”跳过测试阶段。</span></p>
</li>
<li><p><span>shutdown.sh</span></p>
<pre><code class=\\'language-shell\' lang=\'shell\'>#!/bin/bash
echo &quot;----------------&quot;
ID=`ps -ef | grep blog | grep -v grep | awk &#39;{print $2}&#39;`
echo $ID
kill -9 $ID
echo &quot;killed $ID&quot;
echo &quot;---------------&quot;
</code></pre>
<p><span>首先找到当前正在运行的进程，然后杀掉这个进程。</span></p>
<p><span>ID=</span><span>`</span><span>xxxxx</span><span>`</span><span> 表示先执行波浪号引用的命令，用此命令得到的结果赋值给ID。</span></p>
<p><span> </span><code>ps <span style=\'font-family:\"Open Sans\", \"Clear Sans\", \"Helvetica Neue\", Helvetica, Arial, sans-serif\'>-</span>ef | grep blog| grep <span style=\'font-family:\"Open Sans\", \"Clear Sans\", \"Helvetica Neue\", Helvetica, Arial, sans-serif\'>-</span>v grep | awk &#39;{print $2}&#39;</code></p>
<p><span>&quot;ps&quot;(process status)查看进程状态， &quot;-e&quot;表示显示所有程序，“-f”表示显示UID、PPID、C和STIME参数；</span></p>
<p><span>“|”是管道命令，表示一起执行。</span></p>
<p><span>“grep”(global regular expression print)表示使用正则表达式搜索文本。</span></p>
<p><span>“grep blog| grep -v xxxx” 表示搜索与payFront匹配的，但是又不包含”xxxx“的。“grep -v xxx” 表示排除&quot;xxx&quot;</span></p>
<p><span>awk &#39;{print $2}&#39; 表示排除文本中的第二项。</span></p>
<p><span>综合起来就是查找名称为“payFront”的第一个进程（第二个会被排除掉）。</span></p>
<p><span>“kill -9 $ID”会给进程发送一个不会阻塞的exit信号，让进程终止。</span></p>
</li>
<li><p><span>startup.sh</span></p>
<pre><code class=\'language-shell\' lang=\'shell\'>nohup java -jar -Djavax.net.ssl.trustStore=&quot;/home/spring/yxf/client.keystore&quot; blog-1.0-SNAPSHOT.jar --spring.profiles.active=test  &gt;/dev/null 2&gt;&amp;1 &amp;
echo &quot;start----------------&quot;
tail -f /httx/log/yxf/out.log
</code></pre>
<p><span>&quot;nohup&quot;(no hang up)不挂起地运行后面地命令，命令末尾地“&amp;”符号表示让程序在后台自动运行。</span></p>
<p><span>java -jar xxx运行jar包</span></p>
<p><span>“-Djavax.net.ssl.trustStore”参数是用来将证书导入java中的cacerts证书库。</span></p>
<p><span>&quot;blog-1.0-SNAPSHOT.jar&quot;为要运行的目标jar。</span></p>
<p><span>“--spring.profiles.active=test”指定为test环境。</span></p>
<p><span>“&gt;/dev/null 2&gt;&amp;1”将输出重定向到一个空的设备文件，即丢弃。</span><a href=\'https://www.cnblogs.com/520playboy/p/6275022.html\'><span>重定向</span></a></p>
<p><span>&quot;tail -f xxx&quot;显示文件末尾的内容，且会不断更新。 “tail -100”显示末尾100行。 “head”与“tail”类似。</span></p>
</li>
<li><p><span>删除文件</span></p>
<p><span>rm -f xxxxx.xxx   强制删除某文件</span></p>
<p><span>rm -rf  xxxx.xxx 强制、递归地删除某文件</span></p>
</li>

</ol>
<h2><a name=\"header-n228\" class=\"md-header-anchor md-print-anchor\" href=\"af://n228\"> </a><span>集成</span></h2>
<p><span>上面几步一个一个执行起来还是有些繁琐的，我们可以将其集成在一个脚本(deploy.sh)中：</span></p>
<pre><code class=\'language-shell\' lang=\'shell\'>echo &quot;----------------&quot;
ID=`ps -ef | grep blog | grep -v grep | awk &#39;{print $2}&#39;`
echo $ID
kill -9 $ID &amp;&amp; rm -f blog-1.0-SNAPSHOT.jar*
echo &quot;killed $ID&quot; 
echo &quot;---------------&quot;

# 这条命令执行后会弹出一个文件选择窗口，选择编译好的jar包即可
rz -y

nohup java -jar -Djavax.net.ssl.trustStore=&quot;/home/spring/yxf/client.keystore&quot; blog-1.0-SNAPSHOT.jar --spring.profiles.active=test  &gt;/dev/null 2&gt;&amp;1 &amp;
echo &quot;start----------------&quot;
tail -f /httx/log/yxf/out.log
</code></pre>
<p><span>以后再发布应用时，只需要在本地先编译好jar包，然后在服务器上sh deploy.sh即可.</span></p>
<p>&nbsp;</p>
", sysdate(), sysdate()); 
