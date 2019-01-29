package com.app.stock.serviceImpl;

import com.app.stock.mapper.StoreTypeSelfMapper;
import com.app.stock.model.StoreType;
import com.app.stock.service.StoreTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/1/4
 */
@Service
public class StoreTypeServiceImpl implements StoreTypeService {

    @Autowired
    private StoreTypeSelfMapper storeTypeSelfMapper;

    @Override
    public Object getTypeInfo(Integer income,Long id) {
        List<StoreType> list = storeTypeSelfMapper.selectAll(id == null?income:null);
        List<Map<String, Object>> lists = getChild(id == null?0L:id,list);
        if(id == null) {
            return lists;
        }else{
            Map<String,Object> map = new HashMap<>();
            StoreType storeType = storeTypeSelfMapper.selectByPrimaryKey(id);
            map.put("name",storeType.getName());
            map.put("id",storeType.getId());
            map.put("income",storeType.getIncome());
            map.put("children",lists);
            return map;
        }
    }

    protected List<Map<String, Object>> getChild(Long id, List<StoreType> list) {
        List<Map<String,Object>> childList = new ArrayList<>();
        for(StoreType entity:list){
            if(entity.getParentId().equals(id)){
                Map<String, Object> map = new HashMap<>();
                map.put("name", entity.getName());
                map.put("id", entity.getId());
                map.put("income", entity.getIncome());
                // 递归获取子节点
                if(entity.getIsParent() == 0) {
                    map.put("children", getChild(entity.getId(), list));
                }else{
                    map.put("children",new ArrayList<>());
                }
                childList.add(map);
            }
        }
        return childList;
    }
}
