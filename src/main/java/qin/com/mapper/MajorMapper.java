package qin.com.mapper;

import org.apache.ibatis.annotations.Param;
import qin.com.entity.Major;

import java.util.List;

public interface MajorMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Major record);
    int insertSelective(Major record);
    Major selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Major record);
    int updateByPrimaryKey(Major record);

    List<Major> selectAll();

    int deleteByList(@Param("deleteids") String[] deleteids);
}