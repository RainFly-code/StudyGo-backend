package com.ncwu.studygo;

/**
 * 使用Curl命令行工具进行API测试指南
 * 
 * Curl是一个功能强大的命令行工具，可以用于发送HTTP请求测试API。
 * 下面是使用Curl测试StudyGo系统的示例命令：
 * 
 * 1. 用户登录
 * ```
 * curl -X POST http://localhost:8080/api/user/login \
 *      -H "Content-Type: application/json" \
 *      -d '{"username":"admin","password":"123456"}' \
 *      -c cookies.txt
 * ```
 * 说明：-c cookies.txt 将保存服务器返回的Cookie到文件中，用于后续请求
 * 
 * 2. 获取自习室列表
 * ```
 * curl -X GET "http://localhost:8080/api/studyroom/list?page=1&size=10" \
 *      -b cookies.txt
 * ```
 * 说明：-b cookies.txt 使用保存的Cookie进行请求
 * 
 * 3. 预约座位
 * ```
 * curl -X POST "http://localhost:8080/api/reservation/add?seatId=1&date=2023-12-01&days=2" \
 *      -b cookies.txt
 * ```
 * 
 * 4. 获取个人预约列表
 * ```
 * curl -X GET "http://localhost:8080/api/reservation/list?page=1&size=10" \
 *      -b cookies.txt
 * ```
 * 
 * 5. 取消预约
 * ```
 * curl -X PUT "http://localhost:8080/api/reservation/1/cancel" \
 *      -b cookies.txt
 * ```
 * 
 * 6. 发布公告（管理员）
 * ```
 * curl -X POST http://localhost:8080/api/admin/announcement/add \
 *      -H "Content-Type: application/json" \
 *      -d '{"title":"系统维护通知","content":"系统将于2023年12月1日进行维护","status":1}' \
 *      -b cookies.txt
 * ```
 * 
 * 7. 提交反馈
 * ```
 * curl -X POST http://localhost:8080/api/feedback/add \
 *      -H "Content-Type: application/json" \
 *      -d '{"type":1,"title":"测试反馈","content":"这是一条测试反馈内容"}' \
 *      -b cookies.txt
 * ```
 * 
 * 8. 获取个人反馈列表
 * ```
 * curl -X GET "http://localhost:8080/api/feedback/list?page=1&size=10" \
 *      -b cookies.txt
 * ```
 * 
 * 9. 管理员处理反馈
 * ```
 * curl -X PUT "http://localhost:8080/api/admin/feedback/1?status=2&reply=已处理" \
 *      -b cookies.txt
 * ```
 * 
 * 注意事项：
 * - 在Windows系统下使用curl命令时，可能需要修改命令格式或使用PowerShell
 * - 确保系统已经启动并在localhost:8080上运行
 * - 测试前先执行登录请求获取Cookie
 * - URL中的参数可能需要进行URL编码
 * - 在实际使用中替换示例中的ID（如seatId=1, /reservation/1/cancel等）为实际ID
 */
public class ApiTestWithCurl {
    // 该类只用于提供Curl测试指南，无实际代码
} 