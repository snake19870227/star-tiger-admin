change master to master_host ='mysql13306', master_user ='slaver',
    master_password ='123456', master_port =3306, master_log_file ='mysql-bin.000001',
    master_log_pos =591, master_connect_retry =30;
start slave;