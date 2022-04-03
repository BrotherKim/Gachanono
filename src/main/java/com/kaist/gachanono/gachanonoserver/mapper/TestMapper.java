package com.kaist.gachanono.gachanonoserver.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.kaist.gachanono.gachanonoserver.vo.TestVo;
import org.apache.ibatis.annotations.Mapper;

@Repository
@Mapper
public interface TestMapper {
    List<TestVo> selectTestList();
}
