 >  它这个项目使用的是vue基于element-ui的脚手架，跟自己
    之前使用一样会出现，一旦之前登陆成功，如果再访问http://localhost:9528/login，
    方法就会一直进不了后台，因为此时已经登陆完成已经生成改用户的token，再请求
    login,无非就是再返回一个token,但是你并不是登陆成功，所以，脚手架那边就不会
    执行登陆成功后的代码（跳转页面），所以，这里直接访问http://localhost:9528/admin
    即可

-  二级面包屑点开没有默认的页面 Dashboard/博客管理/分类管理 再点击 博客管理 页面为空
-  上次登陆时间没有做

-  使用webMagic来爬取csdn，博客园，简书热门文章，在博客右侧显示、

-  将平时的执行日志放到elk中去

- 可以使用支付宝支付，买在文章中提到的书籍

- 图片的上传，使用自己搭建的 FastDFS（https://blog.csdn.net/qq_38263083/article/details/87921678）

- 为什么要搭建这样一个，为了完善自己的个性化博客，不再有广告如csdn等

- 设置博客被google和百度的搜索引擎爬取到

- 给博客增加动态看板娘 https://blog.csdn.net/Ryan_black/article/details/103517294


mybatisPlus 地址https://juejin.im/post/5b517d24e51d451907696dec
相对于mybatis来说，只做增加，不做修改
