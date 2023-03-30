#!/bin/bash

# 数据库用户名和密码
DB_USER="root"
DB_PASSWORD="000000"
# 数据库名
DB_NAME="mange"
# 定义过期时间（单位：天）
EXPIRE_DAYS=1

# 获取当前时间和过期时间
NOW=$(date +%s)
EXPIRE_TIME=$((NOW - EXPIRE_DAYS*24*3600))

# 删除过期数据
mysql -u ${DB_USER} -p${DB_PASSWORD} ${DB_NAME} -e "DELETE FROM msg WHERE time < ${EXPIRE_TIME};"

echo "完毕！"

# 使用crontab命令设置定时任务，每天0点执行
# crontab -e
# 0 0 * * * /bin/bash /root/del.sh