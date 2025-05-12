# StudyGo系统功能模块

## 1. 用户管理模块
- 登录/注册功能 ：用户可以登录和注册系统
- 用户信息管理 ：包括用户资料查看和修改
- 密码修改 ：用户可以修改自己的密码
## 2. 自习室管理模块
- 自习室列表展示 ：展示所有可用的自习室
- 自习室详情查看 ：查看自习室的详细信息，包括名称、位置、开放时间、座位数等
- 自习室搜索 ：根据关键词搜索自习室
- 自习室状态管理 ：自习室有不同状态（开放中、已关闭、维护中）
## 3. 座位管理模块
- 座位布局展示 ：以图形化方式展示自习室内的座位布局
- 座位状态显示 ：显示座位的不同状态（可用、已预约、维护中）
- 座位选择 ：用户可以选择空闲座位进行预约
## 4. 预约管理模块
- 座位预约 ：用户可以预约自习室的座位
- 我的预约 ：用户可以查看自己的预约记录
- 预约详情 ：查看预约的详细信息
## 5. 反馈管理模块
- 反馈提交 ：用户可以提交反馈意见
- 反馈列表 ：查看已提交的反馈
- 反馈详情 ：查看反馈的详细内容
## 6. 管理员功能模块
- 自习室管理 ：管理员可以添加、编辑、删除自习室，以及更改自习室状态
- 座位管理 ：管理员可以管理座位，包括添加、更新座位状态等
- 用户管理 ：管理员可以查看和管理用户信息
- 预约管理 ：管理员可以查看和管理所有预约记录
- 公告管理 ：管理员可以发布和管理系统公告
- 反馈处理 ：管理员可以查看和处理用户反馈
## 7. 系统架构
- MVC架构 ：系统采用Model-View-Controller架构
  - Model层：包含各种数据模型（StudyRoom、Seat、Reservation等）
  - View层：页面UI组件和布局
  - Service层：封装API调用，处理业务逻辑
- 工具类 ：包含HTTP请求、Toast提示、偏好设置等工具类
- 常量定义 ：系统中使用的常量和路由定义
系统整体采用鸿蒙HarmonyOS的ArkTS语言开发，UI组件使用ArkUI框架，具有良好的跨设备适配性和用户体验。

StudyGo自习室预约系统后端接口文档
1. 用户管理接口 (UserController)
1.1 用户注册
URL : /api/user/register
方法 : POST
请求体 : User对象
描述 : 用户注册
返回 : 注册结果
1.2 用户登录
URL : /api/user/login
方法 : POST
请求体 : User对象（包含username和password）
描述 : 用户登录
返回 : 登录结果和用户信息
1.3 获取用户信息
URL : /api/user/info
方法 : GET
描述 : 获取当前登录用户的信息
返回 : 用户信息
1.4 更新用户信息
URL : /api/user/update
方法 : PUT
请求体 : User对象
描述 : 更新当前登录用户的信息
返回 : 更新结果
1.5 修改密码
URL : /api/user/password
方法 : PUT
请求体 : {"oldPassword": "旧密码", "newPassword": "新密码"}
描述 : 修改当前登录用户的密码
返回 : 修改结果
1.6 管理员获取用户列表
URL : /api/admin/user/list
方法 : GET
参数 :
page: 页码（默认1）
size: 每页数量（默认10）
keyword: 关键词（可选）
描述 : 管理员获取用户列表
返回 : 用户列表分页数据
1.7 管理员获取用户详情
URL : /api/admin/user/{id}
方法 : GET
参数 : id: 用户ID
描述 : 管理员获取指定用户详情
返回 : 用户详情
1.8 管理员更新用户信息
URL : /api/admin/user/{id}
方法 : PUT
参数 : id: 用户ID
请求体 : User对象
描述 : 管理员更新指定用户信息
返回 : 更新结果
1.9 管理员删除用户
URL : /api/admin/user/{id}
方法 : DELETE
参数 : id: 用户ID
描述 : 管理员删除指定用户
返回 : 删除结果
1.10 管理员添加用户
URL : /api/admin/user/add
方法 : POST
请求体 : User对象
描述 : 管理员添加用户
返回 : 添加结果
2. 自习室管理接口 (StudyRoomController)
2.1 获取自习室列表
URL : /api/studyroom/list
方法 : GET
参数 :
page: 页码（默认1）
size: 每页数量（默认10）
status: 状态（可选）
描述 : 获取自习室列表
返回 : 自习室列表分页数据
2.2 获取自习室详情
URL : /api/studyroom/{id}
方法 : GET
参数 : id: 自习室ID
描述 : 获取自习室详情
返回 : 自习室详情
2.3 模糊查询自习室
URL : /api/studyroom/search
方法 : GET
参数 :
keyword: 关键词
page: 页码（默认1）
size: 每页数量（默认10）
描述 : 模糊查询自习室
返回 : 自习室列表分页数据
2.4 获取自习室座位列表
URL : /api/studyroom/{id}/seats
方法 : GET
参数 :
id: 自习室ID
date: 日期（可选）
status: 状态（可选）
描述 : 获取自习室座位列表
返回 : 座位列表
2.5 添加自习室
URL : /api/admin/studyroom/add
方法 : POST
请求体 : StudyRoom对象
描述 : 管理员添加自习室
返回 : 添加结果
2.6 更新自习室信息
URL : /api/admin/studyroom/{id}
方法 : PUT
参数 : id: 自习室ID
请求体 : StudyRoom对象
描述 : 管理员更新自习室信息
返回 : 更新结果
2.7 删除自习室
URL : /api/admin/studyroom/{id}
方法 : DELETE
参数 : id: 自习室ID
描述 : 管理员删除自习室
返回 : 删除结果
2.8 更新自习室状态
URL : /api/admin/studyroom/{id}/status
方法 : PUT
参数 :
id: 自习室ID
status: 状态
描述 : 管理员更新自习室状态
返回 : 更新结果
3. 座位管理接口 (SeatController)
3.1 管理员添加座位
URL : /api/admin/seat/add
方法 : POST
请求体 : Seat对象
描述 : 管理员添加座位
返回 : 添加结果
3.2 管理员获取所有座位信息
URL : /api/admin/seat/all
方法 : GET
描述 : 管理员获取所有座位信息
返回 : 所有座位列表
3.3 管理员更新座位信息
URL : /api/admin/seat/{id}
方法 : PUT
参数 : id: 座位ID
请求体 : Seat对象
描述 : 管理员更新座位信息
返回 : 更新结果
3.4 管理员更新座位状态
URL : /api/admin/seat/{id}/status
方法 : PUT
参数 :
id: 座位ID
status: 状态 0:可用 1:已预约 2:维护中
描述 : 管理员更新座位状态
返回 : 更新结果
3.5 管理员批量设置座位信息
URL : /api/admin/seat/batch
方法 : POST
参数 :
roomId: 自习室ID
startNumber: 起始编号
endNumber: 结束编号
status: 状态 0:可用 1:已预约 2:维护中
描述 : 管理员批量设置座位信息
返回 : 设置结果
3.6 管理员删除座位
URL : /api/admin/seat/delete/{id}
方法 : POST
参数 : id: 座位ID
描述 : 管理员删除座位
返回 : 删除结果
4. 预约管理接口 (ReservationController)
4.1 预约座位
URL : /api/reservation/add
方法 : POST
参数 :
seatId: 座位ID
date: 预约开始日期
days: 预约天数
描述 : 用户预约座位
返回 : 预约ID
4.2 获取个人预约列表
URL : /api/reservation/list
方法 : GET
参数 :
page: 页码（默认1）
size: 每页数量（默认10）
status: 状态（可选）
描述 : 获取当前登录用户的预约列表
返回 : 预约列表分页数据
4.3 获取预约详情
URL : /api/reservation/{id}
方法 : GET
参数 : id: 预约ID
描述 : 获取预约详情
返回 : 预约详情
4.4 取消预约
URL : /api/reservation/{id}/cancel
方法 : PUT
参数 : id: 预约ID
描述 : 用户取消预约
返回 : 取消结果
4.5 管理员获取预约列表
URL : /api/admin/reservation/list
方法 : GET
参数 :
page: 页码（默认1）
size: 每页数量（默认10）
userId: 用户ID（可选）
roomId: 自习室ID（可选）
status: 状态（可选）
startDate: 开始日期（可选）
endDate: 结束日期（可选）
描述 : 管理员获取预约列表
返回 : 预约列表分页数据
4.6 管理员处理预约
URL : /api/admin/reservation/{id}
方法 : PUT
参数 :
id: 预约ID
status: 状态 0:待审核 1:已通过 2:已拒绝 3:已取消
描述 : 管理员处理预约
返回 : 处理结果
4.7 导出预约记录
URL : /api/admin/reservation/export
方法 : GET
参数 :
startDate: 开始日期（可选）
endDate: 结束日期（可选）
roomId: 自习室ID（可选）
status: 状态（可选）
描述 : 管理员导出预约记录
返回 : 预约记录列表
5. 公告管理接口 (AnnouncementController)
5.1 获取公告列表
URL : /api/announcement/list
方法 : GET
参数 :
page: 页码（默认1）
size: 每页数量（默认10）
描述 : 获取公告列表
返回 : 公告列表分页数据
5.2 获取公告详情
URL : /api/announcement/{id}
方法 : GET
参数 : id: 公告ID
描述 : 获取公告详情
返回 : 公告详情
5.3 发布公告
URL : /api/admin/announcement/add
方法 : POST
请求体 : Announcement对象
描述 : 管理员发布公告
返回 : 发布结果
5.4 更新公告
URL : /api/admin/announcement/{id}
方法 : PUT
参数 : id: 公告ID
请求体 : Announcement对象
描述 : 管理员更新公告
返回 : 更新结果
5.5 删除公告
URL : /api/admin/announcement/{id}
方法 : DELETE
参数 : id: 公告ID
描述 : 管理员删除公告
返回 : 删除结果
5.6 更新公告状态
URL : /api/admin/announcement/{id}/status
方法 : PUT
参数 :
id: 公告ID
status: 状态 0:已下架 1:已发布
描述 : 管理员更新公告状态
返回 : 更新结果
6. 反馈管理接口 (FeedbackController)
由于FeedbackController的具体实现未在提供的代码中找到，但根据项目结构和命名规范，以下是可能的接口：
6.1 提交反馈
URL : /api/feedback/add
方法 : POST
请求体 : Feedback对象
描述 : 用户提交反馈
返回 : 提交结果
6.2 获取个人反馈列表
URL : /api/feedback/list
方法 : GET
参数 :
page: 页码（默认1）
size: 每页数量（默认10）
status: 状态（可选）
描述 : 获取当前登录用户的反馈列表
返回 : 反馈列表分页数据
6.3 获取反馈详情
URL : /api/feedback/{id}
方法 : GET
参数 : id: 反馈ID
描述 : 获取反馈详情
返回 : 反馈详情
6.4 管理员获取反馈列表
URL : /api/admin/feedback/list
方法 : GET
参数 :
page: 页码（默认1）
size: 每页数量（默认10）
type: 反馈类型（可选）
status: 状态（可选）
描述 : 管理员获取反馈列表
返回 : 反馈列表分页数据
6.5 管理员处理反馈
URL : /api/admin/feedback/{id}
方法 : PUT
参数 : id: 反馈ID
请求体 : {"status": 状态, "reply": "回复内容"}
描述 : 管理员处理反馈
返回 : 处理结果
