UPSERT INTO HOSPITAL.DOCTOR("id","status") VALUES(1,1);
UPSERT INTO HOSPITAL.DOCTOR("id","status") VALUES(2,1);
UPSERT INTO HOSPITAL.DOCTOR("id","status") VALUES(5,1);

    //为什么使用逻辑删除 因为真删除会带来许多问题
   如果咱们真的把医生记录删除以后
   将来咱们想要查询医生开具的处方
   我们在做表连接的时候没有办法查询出来医生的数据了
   表里已经没有医生的记录
   医生表和其他表做表连接 肯定查不出来相关的业务数据的
   做逻辑删除不是真实的把医生数据删除掉
   仅仅只是修改医生的状态
   记录在数据库里还是存在的
   将来在做表连接的时候 还是可以查询出来和医生相关的业务数据
