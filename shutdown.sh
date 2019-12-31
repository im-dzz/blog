#!/bin/bash
echo "----------------"
ID=`ps -ef | grep blog | grep -v grep | awk '{print $2}'`
echo $ID
kill -9 $ID
echo "killed $ID"
echo "---------------"