package cn.oxo.iworks.databases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

public abstract class IMybatisPlusBaseUnits<Bean> extends ABBaseUnits{

	public abstract BaseMapper<Bean> getBaseMapper();
	

//    @Autowired
//    @Qualifier(IPlatformRedisService.name)
//    protected IPlatformRedisService platformRedisService;

    public LambdaQueryWrapper<Bean> create() {
        LambdaQueryWrapper<Bean> queryWrapper = Wrappers.lambdaQuery();

        return queryWrapper;

    }

    public Bean search(LambdaQueryWrapper<Bean> queryWrapper) {
        return getBaseMapper().selectOne(queryWrapper);
    }
    
    public List<Bean> selectList(LambdaQueryWrapper<Bean> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }
}
