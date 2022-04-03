package com.kaist.gachanono.gachanonoserver.service;

import com.kaist.gachanono.gachanonoserver.mapper.TestMapper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaist.gachanono.gachanonoserver.vo.TestVo;

@Service
public class TestService {
    
    @Autowired
    //private TestMapper testMapper;

    public List<TestVo> selectTestList() {
        //return testMapper.selectTestList();
        return null;
    }
}
