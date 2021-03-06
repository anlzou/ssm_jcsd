package qin.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import qin.com.common.Logging;
import qin.com.common.ResponseCode;
import qin.com.common.ServerResponse;
import qin.com.entity.Course;
import qin.com.service.CourseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CourseController {
    @Resource(name = "courseServiceImpl")
    @Autowired
    private CourseService courseService;

    /**
     * 查询所有数据
     * ok
     */
    @RequestMapping("/listallCourse")
    @ResponseBody
    public List<Course>  listall(HttpServletRequest request, HttpServletResponse response) {
        if (Logging.logging == 1)
            return courseService.selectAll();
        return null;
    }

    /**
     * 删除选中的数据
     * ok
     */
    @RequestMapping("/deleteallCourse")
    @ResponseBody
    public ServerResponse deleteall(String id) {
        System.out.println(id);
        String[] deleteids = id.split(",");//把客户端传送过来的字符串（一般为“1，2，3，4”）转换成数组
//        System.out.println(deleteids);
        if (deleteids != null && deleteids.length > 0) {
            System.out.println("deleteall ok!======1");
            if (Logging.logging == 1 && courseService.deleteByList(deleteids) > 0) {//按照列表删除
                System.out.println("deleteall ok!======2");
                return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getCode(), "删除班级信息成功");
            } else {
                System.out.println("删除不成功");
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "删除数据失败");
            }
        } else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "删除数据失败");
        }
    }

    /**
     * 更新
     * ok
     */
    @RequestMapping("/updateByPrimaryKeySelectiveCourse")
    @ResponseBody
    public ServerResponse updateByPrimaryKeySelective(Course record) {
        Course course = new Course();
        course.setId(record.getId());
        course.setCode(record.getCode());
        course.setName(record.getName());
        course.setPeriod(record.getPeriod());
        course.setNature(record.getNature());
        System.out.println("Course更新数据==" + course.getId() + "==" + course.getName());
        if (Logging.logging == 1 && courseService.updateByPrimaryKey(course) > 0) {
            System.out.println("Course表更新成功");
            return ServerResponse.createBySuccess("更新管理数据成功", record);
        } else {
            return ServerResponse.createByErrorMessage("更新管理数据失败");
        }
    }

    /**
     * 插入数据
     * ok
     */
    @RequestMapping("/insertSelectiveCourse")
    @ResponseBody
    public ServerResponse insertSelective(Course record) {
        Course course = new Course();
        course.setId(record.getId());
        course.setCode(record.getCode());
        course.setName(record.getName());
        course.setPeriod(record.getPeriod());
        course.setNature(record.getNature());
        if (Logging.logging == 1 && courseService.insert(course) > 0) {
            return ServerResponse.createBySuccess("添加管理数据成功", course);
        } else {
            return ServerResponse.createByErrorMessage("添加管理员数据失败");
        }
    }
}