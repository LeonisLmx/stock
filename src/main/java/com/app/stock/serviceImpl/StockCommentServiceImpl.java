package com.app.stock.serviceImpl;

import com.app.stock.common.commonEnum.ScoreEnum;
import com.app.stock.config.filtetKeys.SensitivewordFilter;
import com.app.stock.mapper.ScoreDetailSelfMapper;
import com.app.stock.mapper.StockCommentSelfMapper;
import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.model.ScoreDetail;
import com.app.stock.model.StockComment;
import com.app.stock.model.User;
import com.app.stock.service.ScoreDetailService;
import com.app.stock.service.StockCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lmx
 * Date 2018/12/28
 */
@Service
public class StockCommentServiceImpl implements StockCommentService {

    @Autowired
    private CommonserviceImpl commonservice;

    @Autowired
    private StockCommentSelfMapper stockCommentSelfMapper;

    @Autowired
    private ScoreDetailSelfMapper scoreDetailSelfMapper;

    @Autowired
    private ScoreDetailService scoreDetailService;

    // 发布吐槽接口
    @Override
    @Transactional
    public int publishComment(StockComment stockComment, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = commonservice.getCurrentInfo(request);
        if(user.getIsShield() == 1){
            return -1;
        }
        stockComment.setUserId(user.getId());
        stockComment.setCreateTime(new Date());
        if(StringUtils.isNotBlank(stockComment.getContent())){
            SensitivewordFilter filter = SensitivewordFilter.getInstance();
            stockComment.setContent(filter.replaceSensitiveWord(stockComment.getContent(), 1, "*"));
        }
        // 那说明今天已经添加过积分了
        if(scoreDetailSelfMapper.selectScoreDetailIsHaveByDateAndFrom(sdf.format(new Date()), ScoreEnum.getName(0),user.getId()) == 0){
            scoreDetailService.addScore(user.getId(),1,ScoreEnum.getName(0));
        }
        return stockCommentSelfMapper.insertSelective(stockComment);
    }

    // 获取吐槽内容

    /**
     * @param type 为0获取当前用户的吐槽，否则获取全部
     * @param stockCode 为空获取所有
     * @param stockMarket 为空获取所有
     * @return
     */
    public Map<String,Object> getComment(Integer type,String stockCode,String stockMarket,String date,
                                               Integer page,Integer page_size,HttpServletRequest request){
        Long userId = null;
        if(type != null && 0 == type){
            userId = commonservice.getCurrentInfo(request).getId();
        }
        PageHelper.startPage(page,page_size);
        List<Map<String,Object>> list = stockCommentSelfMapper.selectStockCommentByCondition(userId,stockCode,stockMarket,date);
        PageInfo pageInfo = new PageInfo(list);
        Map<String,Object> map = new HashMap<>();
        map.put("list",pageInfo.getList());
        /*map.put("page",page);
        map.put("per_page",page_size);
        map.put("count",pageInfo.getTotal());*/
        return map;
    }
}
