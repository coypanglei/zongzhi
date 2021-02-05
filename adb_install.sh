#!/bin/bash
echo $1
adb devices > devices.txt
echo "开始读取设备"

line_num=0
while read -r line
do
	if [ $line_num != 0 ] && [ -n "$line" ];
	then
		devices_info=`echo $line | cut -d " " -f 1`
		echo $devices_info
		adb  -s $devices_info install -d -r $1
	fi
	let line_num++
done < devices.txt