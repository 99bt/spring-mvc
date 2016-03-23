package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.YwUserPayDao;
import com.yaowang.lansha.entity.YwUserPay;

/**
 * 用户财物信息表 
 * @author 
 * 
 */
@SuppressWarnings("unchecked")
@Repository("ywUserPayDao")
public class YwUserPayDaoImpl extends BaseDaoImpl<YwUserPay> implements YwUserPayDao{
	@Override
	public YwUserPay setField(ResultSet rs) throws SQLException{
		YwUserPay entity = new YwUserPay();
		entity.setCardNo(rs.getString("card_no"));
		entity.setUserId(rs.getString("user_id"));
		entity.setName(rs.getString("name"));
		entity.setBank(rs.getString("bank"));
		return entity;
	}
	
	@Override
	public YwUserPay save(YwUserPay entity){
		String sql = "insert into yw_user_pay(card_no, user_id, name, bank) values(?,?,?,?)"; 
		if (StringUtils.isBlank(entity.getCardNo())){
			entity.setCardNo(createId());
		}
		Object[] args = new Object[]{
			entity.getCardNo(), entity.getUserId(), 
            entity.getName(), entity.getBank()
		};
		update(sql, args);
		return entity;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from yw_user_pay where card_no in";
		return executeUpdateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(YwUserPay entity){
		String sql = "update yw_user_pay set user_id = ?, name = ?, bank = ? where card_no = ?";
		Object[] args = new Object[]{
			entity.getUserId(), entity.getName(), 
            entity.getBank(), entity.getCardNo()
		};
		return update(sql, args);
	}

	@Override
	public YwUserPay getYwUserPayById(String cardNo){
		String sql = "select * from yw_user_pay where card_no = ?";
		return (YwUserPay) findForObject(sql, new Object[] { cardNo }, new MultiRow<YwUserPay>());
	}

	@Override
	public Map<String, YwUserPay> getYwUserPayMapByIds(String[] cardNos){
		String sql = "select * from yw_user_pay where card_no in";
		return queryForMap(sql, null, cardNos, new MapIdRow<String, YwUserPay>("cardNo", String.class));
	}

	@Override
	public List<YwUserPay> getYwUserPayList(YwUserPay entity){
		String sql = "select * from yw_user_pay where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql += " and user_id =  ?";
				args.add(entity.getUserId());
			}
		}
		return find(sql, args.toArray(), new MultiRow<YwUserPay>());
	}

	@Override
	public List<YwUserPay> getYwUserPayPage(YwUserPay entity, PageDto page){
		String sql = "select * from yw_user_pay where 1=1";
		List<Object> args = new ArrayList<Object>();
		if(entity != null){
			if(StringUtils.isNotBlank(entity.getUserId())){
				sql += " and user_id =  ?";
				args.add(entity.getUserId());
			}
		}
		return findForPage(sql, args.toArray(), new MultiRow<YwUserPay>(), page);
	}
	
}
