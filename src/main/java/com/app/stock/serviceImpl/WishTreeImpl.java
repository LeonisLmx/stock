package com.app.stock.serviceImpl;

import com.app.stock.mapper.WishTreeSelfMapper;
import com.app.stock.model.User;
import com.app.stock.model.WishTree;
import com.app.stock.model.request.WishListRequest;
import com.app.stock.service.WishTreeInterface;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2019/2/24
 */
@Service
public class WishTreeImpl implements WishTreeInterface {

    @Autowired
    private WishTreeSelfMapper wishTreeSelfMapper;

    @Autowired
    private CommonserviceImpl commonservice;

    @Override
    public List<Map<String,Object>> getAllInfo(HttpServletRequest request, WishListRequest wishListRequest) {
        PageHelper.startPage(wishListRequest.getPage(),wishListRequest.getPage_size());
        User user = commonservice.getCurrentInfo(request);
        Long userId = user.getId();
        if(wishListRequest.getIsSelf() == null || wishListRequest.getIsSelf() == 0){
            // 如果未传或者传0，那么就查询所有
            userId = null;
        }
        List<Map<String,Object>> list = wishTreeSelfMapper.selectAllInfoByCondition(wishListRequest,userId);
        return list;
    }

    @Override
    public Map<String,Object> getAloneWish(Long Id) {
        return wishTreeSelfMapper.selectPrimarykeyById(Id);
    }

    @Override
    public int operateWish(WishTree wishTree,HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        if(wishTree.getId() != null){
            WishTree oldWish = wishTreeSelfMapper.selectByPrimaryKey(wishTree.getId());
            if(oldWish == null || oldWish.getIsDelete() == 1){
                return -1;
            }
            oldWish.setWish(wishTree.getWish());
            wishTreeSelfMapper.updateByPrimaryKeySelective(oldWish);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String month = sdf.format(new Date());
            if(wishTreeSelfMapper.selectIsWishByMonth(user.getId(),month) > 0){
                return -2;
            }
            wishTree.setUserId(user.getId());
            wishTree.setWishMonth(month);
            wishTree.setCreateTime(new Date());
            wishTreeSelfMapper.insertSelective(wishTree);
        }
        return 1;
    }

    @Override
    public int deleteWish(Long id) {
        WishTree oldWish = wishTreeSelfMapper.selectByPrimaryKey(id);
        if(oldWish == null || oldWish.getIsDelete() == 1){
            return -1;
        }
        oldWish.setIsDelete(1);
        wishTreeSelfMapper.updateByPrimaryKeySelective(oldWish);
        return 1;
    }
}
