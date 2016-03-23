package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.lansha.dao.YwGameHotDao;
import com.yaowang.lansha.entity.YwGameHot;

/**
 * 热门推荐游戏
 *
 * @author
 */
@SuppressWarnings("unchecked")
@Repository("ywGameHotDao")
public class YwGameHotDaoImpl extends BaseDaoImpl<YwGameHot> implements YwGameHotDao {
    @Override
    public YwGameHot setField(ResultSet rs) throws SQLException {
        YwGameHot entity = new YwGameHot();
        entity.setId(rs.getString("id"));
        entity.setGameId(rs.getString("game_id"));
        entity.setOrderId(rs.getInt("order_id"));
        entity.setCreateTime(rs.getTimestamp("create_time"));
        return entity;
    }

    @Override
    public YwGameHot save(YwGameHot entity) {
        String sql = "insert into yw_game_hot(id, game_id, order_id, create_time) values(?,?,?,?)";
        if (StringUtils.isBlank(entity.getId())) {
            entity.setId(createId());
        }
        Object[] args = new Object[]{
                entity.getId(), entity.getGameId(),
                entity.getOrderId(), entity.getCreateTime()
        };
        update(sql, args);
        return entity;
    }

    @Override
    public Integer delete(String[] ids) {
        String sql = "delete from yw_game_hot where id in";
        return executeUpdateForInSQL(sql, null, ids);
    }

    @Override
    public Integer update(YwGameHot entity) {
        String sql = "update yw_game_hot set game_id = ?, order_id = ?, create_time = ? where id = ?";
        Object[] args = new Object[]{
                entity.getGameId(), entity.getOrderId(),
                entity.getCreateTime(), entity.getId()
        };
        return update(sql, args);
    }

    @Override
    public YwGameHot getYwGameHotById(String id) {
        String sql = "select * from yw_game_hot where id = ?";
        return (YwGameHot) findForObject(sql, new Object[]{id}, new MultiRow<YwGameHot>());
    }

    @Override
    public Map<String, YwGameHot> getYwGameHotMapByIds(String[] ids) {
        String sql = "select * from yw_game_hot where id in";
        return queryForMap(sql, null, ids, new MapIdRow<String, YwGameHot>("id", String.class));
    }

    @Override
    public List<YwGameHot> getYwGameHotList(YwGameHot entity) {
        String sql = "select * from yw_game_hot where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {

        }
        return find(sql, args.toArray(), new MultiRow<YwGameHot>());
    }

    @Override
    public List<YwGameHot> getYwGameHotPage(YwGameHot entity, PageDto page) {
        String sql = "select * from yw_game_hot where 1=1";
        List<Object> args = new ArrayList<Object>();
        if (entity != null) {

        }
        sql += " order by order_id, create_time desc";
        return findForPage(sql, args.toArray(), new MultiRow<YwGameHot>(), page);
    }

    @Override
    public Integer deleteByGameId(String... gameId) {
        String sql = "delete from yw_game_hot where game_id in";
        return executeUpdateForInSQL(sql, null, gameId);
    }

    @Override
    public YwGameHot getYwGameHotByGameId(String gameId) {
        String sql = "select * from yw_game_hot where game_id = ?";
        return (YwGameHot) findForObject(sql, new Object[]{gameId}, new MultiRow<YwGameHot>());
    }

    @Override
    public Map<String, YwGameHot> getYwGameHotMapByGameIds(String[] gameIds) {
        String sql = "select * from yw_game_hot where game_id in";
        return queryForMap(sql, null, gameIds, new MapIdRow<String, YwGameHot>("game_id", String.class));
    }

    @Override
    public Integer updateOrderId(String id, Integer orderId) {
        String sql = "update yw_game_hot set order_id = ? where id = ?";
        Object[] args = new Object[]{
                orderId, id
        };
        return update(sql, args);
    }

    @Override
    public List<String> getHotGameIdPage(int limit) {
        String sql = "select * from yw_game_hot order by order_id";
        List<YwGameHot> hots = null;
        List<String> result = new ArrayList<String>();
        if (limit <= 0) {
            hots = find(sql, null, new MultiRow<YwGameHot>());
        } else {
            PageDto page = new PageDto();
            page.setRowNum(limit);
            hots = findForPage(sql, null, new MultiRow<YwGameHot>(), page);
        }
        if (CollectionUtils.isNotEmpty(hots)) {
            for (int i = 0; i < hots.size(); i++) {
                result.add(hots.get(i).getGameId());
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getHotGame(int limit) {
        String sql = "select y.id as gameId,y.name as name,y.advert_small as advertSmall  from yw_game y,yw_game_hot h where y.id=h.game_id order by h.order_id";
        {
            PageDto page = new PageDto();
            page.setCount(false);
            page.setRowNum(limit);
            return findListMapForPage(sql, null, page);
        }
        //return queryForMap(sql, null, null, new MapIdRow<String, Integer>("gameId", String.class, "name", String.class));

    }

}
