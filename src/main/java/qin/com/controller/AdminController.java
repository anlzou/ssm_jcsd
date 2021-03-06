package qin.com.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import qin.com.common.Logging;
import qin.com.common.ResponseCode;
import qin.com.common.ServerResponse;
import qin.com.entity.Admin;
import qin.com.service.AdminService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller //这是控制器的的注解，代码当前类为一个控制器
//@RequestMapping("/admin")  //这个注解表示控制器的映射的地址为服务器地址+admin
public class AdminController {

    @Resource(name = "adminServiceImpl")  //这个注解表示自动注入adminSeerviceImpl业务实现类
    @Autowired      //该注解表示后紧跟的变量进行封装，即自动产生getter和setter
    private AdminService adminService;

    @RequestMapping("/deleteallAdmin")
    @ResponseBody
    public ServerResponse deleteall(String id) {
        System.out.println(id);
        String[] deleteids = id.split(",");//把客户端传送过来的字符串（一般为“1，2，3，4”）转换成数组
//        System.out.println(deleteids);
        if (deleteids != null && deleteids.length > 0) {
            System.out.println("deleteall ok!======1");
            if (Logging.logging == 1 && adminService.deleteByList(deleteids) > 0) {//按照列表删除
//            for (String i : deleteids) {
//                adminService.deleteByPrimaryName(i);//按照名称删除
//            }
            System.out.println("deleteall ok!======2");
            return ServerResponse.createBySuccess(ResponseCode.SUCCESS.getCode(), "删除管理员成功");
            } else {
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "删除数据失败");
            }
        } else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "删除数据失败");
        }
    }


    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse deleteByPrimaryKey(Integer id) {
        if (Logging.logging == 1 && adminService.deleteByPrimaryKey(id) > 0) {
            return ServerResponse.createBySuccess("删除管理员成功", id);
        } else {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(), "删除数据失败");
        }
    }


    @RequestMapping("/insert")
    @ResponseBody
    public ServerResponse insert(Admin record) {//管理员的信息应该是由客户端传送过来
        Admin admin = new Admin();
        admin.setName(record.getName());
        admin.setPass(DigestUtils.md5DigestAsHex(record.getPass().getBytes()));//单向加密,无法解密
//        admin.setPass(record.getPass());
        admin.setPhone(record.getPhone());
        admin.setEmail(record.getEmail());
        admin.setQq(record.getQq());
        if (Logging.logging == 1 && adminService.insert(admin) > 0) {
            return ServerResponse.createBySuccess("添加数据成功", admin);
        } else {
            return ServerResponse.createByError();
        }
    }


    @RequestMapping("/insertSelectiveAdmin")
    @ResponseBody
    public ServerResponse insertSelective(Admin record) {
        Admin admin = new Admin();
        admin.setName(record.getName());
        admin.setPass(DigestUtils.md5DigestAsHex(record.getPass().getBytes()));//单向加密，无法解密
//        admin.setPass(record.getPass());
        admin.setPhone(record.getPhone());
        admin.setEmail(record.getEmail());
        admin.setQq(record.getQq());
        if (Logging.logging == 1 && adminService.insert(admin) > 0) {
            return ServerResponse.createBySuccess("添加管理数据成功", admin);
        } else {
            return ServerResponse.createByErrorMessage("添加管理员数据失败");
        }
    }


    @RequestMapping("/selectByPrimaryKey")
    @ResponseBody
    public ServerResponse selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Admin admin = adminService.selectByPrimaryKey(1);
        if (Logging.logging == 1 && admin != null) {
            return ServerResponse.createBySuccess(0, admin);
        } else {
            return ServerResponse.createByErrorMessage("找不到管理员");
        }
    }


    @RequestMapping("/updateByPrimaryKeySelectiveAdmin")
    @ResponseBody
    public ServerResponse updateByPrimaryKeySelective(Admin record) {
        Admin admin = new Admin();
        admin.setId(record.getId());//更新操作必须要有id主关键字段
        admin.setName(record.getName());
        //采用pring mvc的Digestutils.md5DigestAsHex()方法对密码进行加密
        admin.setPass(DigestUtils.md5DigestAsHex(record.getPass().getBytes()));
//        admin.setPass(record.getPass());
        admin.setPhone(record.getPhone());
        admin.setEmail(record.getEmail());
        admin.setQq(record.getQq());
        System.out.println("admin更新数据=="+admin.getId()+"=="+admin.getName());
        if (Logging.logging == 1 && adminService.updateByPrimaryKey(admin) > 0) {
            return ServerResponse.createBySuccess("更新管理数据成功", admin);
        } else {
            return ServerResponse.createByErrorMessage("更新管理数据失败");
        }
    }


    @RequestMapping("/updateByPrimaryKey")
    @ResponseBody
    public ServerResponse updateByPrimaryKey(Admin record) {
        Admin admin = new Admin();
        admin.setId(record.getId());//更新操作必须要有id主关键字段
        admin.setName(record.getName());
        //采用pring mvc的Digestutils.md5DigestAsHex()方法对密码进行加密
        admin.setPass(DigestUtils.md5DigestAsHex(record.getPass().getBytes()));
//        admin.setPass(record.getPass());
        admin.setPhone(record.getPhone());
        admin.setEmail(record.getEmail());
        admin.setQq(record.getQq());
        if (Logging.logging == 1 && adminService.updateByPrimaryKeySelective(admin) > 0) {
            return ServerResponse.createBySuccess("更新管理数据成功", admin);
        } else {
            return ServerResponse.createByErrorMessage("更新管理员数据失败");
        }
    }


    @RequestMapping("/listallAdmin")
    @ResponseBody
    public List<Admin>  listall(HttpServletRequest request, HttpServletResponse response) {
        if (Logging.logging == 1)
            return adminService.selectAll();
        return null;
    }


    @RequestMapping("/checkNameAndPass")
    @ResponseBody
    public ServerResponse checkNameAndPass(@Param("name") String name, @Param("pass") String pass) {
        if (Logging.logging == 1 && adminService.checkAdmin(name) > 0 && adminService.checkPass(DigestUtils.md5DigestAsHex(pass.getBytes())) > 0) {
            return ServerResponse.createBySuccess(name, pass);
        } else {
            return ServerResponse.createByErrorMessage("输入的管理员和密码错误");
        }
    }


    @RequestMapping("/checkEmailAndPass")
    @ResponseBody
    public ServerResponse checkEmailAndPass(@Param("email") String email, @Param("pass") String pass) {
        if (Logging.logging == 1 && adminService.checkEmail(email) > 0 && adminService.checkPass(DigestUtils.md5DigestAsHex(pass.getBytes())) > 0) {
            return ServerResponse.createBySuccess(email, pass);
        } else {
            return ServerResponse.createByErrorMessage("输入的邮箱和密码错误");
        }
    }


    @RequestMapping("/checkPhoneAndPass")
    @ResponseBody
    public ServerResponse checkPhoneAndPass(@Param("phone") String phone, @Param("pass") String pass) {
        if (Logging.logging == 1 && adminService.checkPhone(phone) > 0 && adminService.checkPass(DigestUtils.md5DigestAsHex(pass.getBytes())) > 0) {
            return ServerResponse.createBySuccess(phone, pass);
        } else {
            return ServerResponse.createByErrorMessage("输入的电话和密码错误");
        }
    }
}