grant replication slave on *.* to 'slaver'@'%' identified by '123456';
flush privileges;
show master status;