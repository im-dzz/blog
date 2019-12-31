nohup java -jar blog-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
echo "start----------------"
tail -f /var/log/blog/blog.log