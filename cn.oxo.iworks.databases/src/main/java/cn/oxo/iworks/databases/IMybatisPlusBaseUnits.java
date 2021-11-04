package cn.oxo.iworks.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public abstract class IMybatisPlusBaseUnits<Bean> extends ABBaseUnits{

	public abstract BaseMapper<Bean> getBaseMapper();
	

    @Autowired
    @Qualifier(IPlatformRedisService.name)
    protected IPlatformRedisService platformRedisService;

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
