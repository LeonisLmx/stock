package com.app.stock.serviceImpl;

import com.app.stock.common.commonEnum.ScoreEnum;
import com.app.stock.mapper.PetSelfMapper;
import com.app.stock.mapper.PetStockDetailSelfMapper;
import com.app.stock.mapper.ScoreDetailSelfMapper;
import com.app.stock.mapper.UserSelfMapper;
import com.app.stock.model.Pet;
import com.app.stock.model.PetStockDetail;
import com.app.stock.model.ScoreDetail;
import com.app.stock.model.User;
import com.app.stock.model.request.FeedPetRequest;
import com.app.stock.service.PetService;
import com.app.stock.service.ScoreDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lmx
 * Date 2018/12/29
 */
@Service
public class PetServiceImpl  implements PetService {

    private static final Integer DEFAULT_LEVEL = 11;
    private static final Integer RESET_PET = 100;

    @Autowired
    private CommonserviceImpl commonservice;

    @Autowired
    private PetSelfMapper petSelfMapper;

    @Autowired
    private PetStockDetailSelfMapper petStockDetailSelfMapper;

    @Autowired
    private ScoreDetailService scoreDetailService;

    @Override
    public boolean createPet(String name, HttpServletRequest request) {
        Long userId = commonservice.getCurrentInfo(request).getId();
        Pet pet = petSelfMapper.selectPrimarykeyByUserId(userId);
        if(pet == null){
            pet = new Pet();
            pet.setUserId(userId);
            pet.setName(name);
            pet.setLevel(DEFAULT_LEVEL);
            pet.setCreateTime(new Date());
            petSelfMapper.insertSelective(pet);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String feedPet(FeedPetRequest map, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user = commonservice.getCurrentInfo(request);
        Long userId = user.getId();
        Pet pet = petSelfMapper.selectPrimarykeyByUserId(userId);
        PetStockDetail petStockDetail = new PetStockDetail();
        petStockDetail.setPetId(pet.getId());
        if(petStockDetailSelfMapper.selectCountByDateAndPetId(pet.getId(),sdf.format(new Date())) > 0){
            return "当日已喂养过宠物";
        }
        petStockDetail.setbPrice(BigDecimal.valueOf(Double.valueOf(map.getPrice())));
        petStockDetail.setStockId(map.getStockId());
        petStockDetail.setStockName(map.getStockName());
        petStockDetail.setCreateTime(new Date());
        petStockDetail.setbTime(petStockDetail.getCreateTime());
        petStockDetailSelfMapper.insertSelective(petStockDetail);
        int score = calcScore(pet.getLevel());
        if(score > 0){
            scoreDetailService.addScore(userId,score,ScoreEnum.getName(1));
            pet.setChangeScore(score);
            petSelfMapper.updateByPrimaryKeySelective(pet);
        }
        return "喂养成功";
    }

    @Override
    public Map<String,Object> petInfo(HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        Pet pet = petSelfMapper.selectPrimarykeyByUserId(user.getId());
        List<PetStockDetail> list = petStockDetailSelfMapper.selectListByPetId(pet.getId());
        Map<String,Object> result = new HashMap<>();
        result.put("name",pet.getName());
        result.put("level",pet.getLevel());
        result.put("change_score",pet.getChangeScore() == null?0:pet.getChangeScore());
        result.put("percent",pet.getPercent() == null ? 0:pet.getPercent());
        List<Map<String,Object>> stocks = new LinkedList<>();
        for(PetStockDetail entity:list){
            Map<String,Object> map = new HashMap<>();
            map.put("stock_name",entity.getStockName());
            map.put("stock_id",entity.getStockId());
            map.put("price",entity.getbPrice());
            stocks.add(map);
        }
        result.put("list",stocks);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String resetPet(HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        Pet pet = petSelfMapper.selectPrimarykeyByUserId(user.getId());
        if(pet == null){
            return "当前没有宠物，无法重置";
        }
        if(user.getScore() < RESET_PET){
            return "积分不足，无法重置";
        }
        pet.setLevel(DEFAULT_LEVEL);
        petSelfMapper.updateByPrimaryKeySelective(pet);
        petStockDetailSelfMapper.deleteAllStockByPetId(pet.getId());
        scoreDetailService.addScore(user.getId(),-RESET_PET,ScoreEnum.getName(2));
        return "重置成功";
    }

    @Override
    public String saleStock(FeedPetRequest map, HttpServletRequest request) {
        User user = commonservice.getCurrentInfo(request);
        PetStockDetail petStockDetail = petStockDetailSelfMapper.selectIsHaveStock(map.getStockId(),user.getId());
        if(petStockDetail == null){
            return "未持有该股票";
        }
        petStockDetail.setsPrice(new BigDecimal(map.getPrice()));
        petStockDetail.setsTime(new Date());
        // 计算宠物等级
        Pet pet = petSelfMapper.selectPrimarykeyByUserId(user.getId());
        BigDecimal percent = (new BigDecimal(map.getPrice()).subtract(petStockDetail.getbPrice())).multiply(new BigDecimal(100)).divide(petStockDetail.getbPrice(),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal level = (new BigDecimal(100)).multiply(new BigDecimal(map.getPrice()).subtract(petStockDetail.getbPrice())).divide(petStockDetail.getbPrice(),4).divide(new BigDecimal(3),0,BigDecimal.ROUND_HALF_UP);
        pet.setLevel(pet.getLevel() + Integer.valueOf(level + ""));
        pet.setPercent(percent + "");
        petSelfMapper.updateByPrimaryKeySelective(pet);
        return "卖出成功";
    }

    protected int calcScore(int level){
        if(level >= 15 && level < 25){
            return 1;
        }else if(level >= 25 && level < 35){
            return 2;
        }else if(level >= 35){
            return 3;
        }
        return 0;
    }
}
