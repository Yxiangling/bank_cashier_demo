# bank_cashier_demo
问题描述：银行会有很多来办业务的顾客，他们会排队等待服务；对于银行方面他们派出出纳员来服务顾客，如果排队的顾客数量过多，银行就会增加 
出纳员的数量，如果顾客的数目过少，则减少出纳员的数目；总之要保持一个平衡。
仿真思路:封装Customer类来表示顾客，每个顾客对象都会有一个需要服务的时间；使用有限容量的阻塞队列CustomerLine来模拟顾客的排队队列；
封装CustomerGenerator类来产生顾客，然后将产生的顾客加入到CustomerLine中去；
封装Teller类来表示银行的出纳员，Teller会从CustomerLine中取出； 
Customer来进行服务。封装TellerManage来管理所有的Teller及根据顾客/出纳员的比例来调整服务顾客的Teller数量。
在这里我们通过阻塞队列CustomerLine实现了Teller线程和CustomerGenerator线程之间的通信。
