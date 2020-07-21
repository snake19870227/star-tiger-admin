
insert into stigeradmin.sys_user (user_flow, username, encode_password, short_name)
values (replace(uuid(), '-', ''), 'root', '{bcrypt}$2a$10$xgpV83U/KYsruVE4OoPp7OyKkKk6vc16zvR0IRohCh4EOu0sRFEr2', '超级管理员');
