package com.yaowang.lansha.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yaowang.lansha.entity.YwGameHot;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yaowang.common.dao.BaseDaoImpl;
import com.yaowang.common.dao.PageDto;
import com.yaowang.common.dao.SQLUtils;
import com.yaowang.lansha.dao.YwGameDao;
import com.yaowang.lansha.entity.YwGame;

/**
 * 游戏表
 *
 * @author
 */
@SuppressWarnings("unchecked")
@Repository("ywGameDao")
public class YwGameDaoImpl extends BaseDaoImpl<YwGame> implements YwGameDao {
    @Override
    public YwGame setField(ResultSet rs) throws SQLException {
        YwGame entity = new YwGame();
        entity.setId(rs.getString("id"));
        entity.setName(rs.getString("name"));
        entity.setTypeId(rs.getString("type_id"));
        entity.setBriefIntro(rs.getString("brief_intro"));
        entity.setIcon(rs.getString("icon"));
        entity.setScreen(rs.getString("screen"));
        entity.setStatus(rs.getInt("status"));
        entity.setOrderId(rs.getInt("order_id"));
        entity.setUpTime(rs.getTimestamp("up_time"));
        entity.setCreateTime(rs.getTimestamp("create_time"));
        entity.setDeveloper(rs.getString("developer"));
        entity.setAdvert(rs.getString("advert"));
        entity.setAndroidUrl(rs.getString("android_url"));
        entity.setIosUrl(rs.getString("ios_url"));
        entity.setQrcode(rs.getString("qrcode"));
        entity.setBackground(rs.getString("background"));
        entity.setAdvertSmall(rs.getString("advert_small"));
        entity.setSeo(rs.getString("seo"));
        entity.setResource(rs.getString("resource"));
        entity.setMobileBanner(rs.getString("mobile_banner"));
        return entity;
    }

    @Override
    public YwGame save(YwGame entity) {
        String sql = "insert into yw_game(id, name, developer, type_id, brief_intro, icon, screen, status, order_id, up_time, create_time, advert, qrcode, android_url, ios_url, background, advert_small,seo,resource,mobile_banner) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (entity.getCreateTime() == null) {
            entity.setCreateTime(new Date());
        }
        if (entity.getUpTime() == null) {
            entity.setUpTime(new Date());
        }
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(createId());
        }
        Object[] args = new Object[]{
                entity.getId(), entity.getName(), entity.getDeveloper(),
                entity.getTypeId(), entity.getBriefIntro(),
                entity.getIcon(), entity.getScreen(),
                entity.getStatus(), entity.getOrderId(),
                entity.getUpTime(), entity.getCreateTime(),
                entity.getAdvert(), entity.getQrcode(),
                entity.getAndroidUrl(), entity.getIosUrl(),
                entity.getBackground(), entity.getAdvertSmall(),
                entity.getSeo(), entity.getResource(), entity.getMobileBanner()
        };

        update(sql, args);
        return entity;
    }

    @Override
    public Integer delete(String[] ids) {
        String sql = "update yw_game set status = 0 where id in";
        return executeUpdateForInSQL(sql, null, ids);
    }

    @Override
    public YwGame getYwGameByName(String name) {
        String sql = "select * from yw_game where name = ?";
        return (YwGame) findForObject(sql, new Object[]{name}, new MultiRow<YwGame>());
    }

    @Override
    public YwGame getYwGameById(String id) {
        String sql = "select * from yw_game where id = ?";
        return (YwGame) findForObject(sql, new Object[]{id}, new MultiRow<YwGame>());
    }

    @Override
    public Map<String, YwGame> getYwGameMapByIds(String[] ids) {
        String sql = "select * from yw_game where id in";
        return queryForMap(sql, null, ids, new MapIdRow<String, YwGame>("id", String.class));
    }

    @Override
    public List<YwGame> getYwGamePage(YwGame game, String[] ids, PageDto page, Date startTime, Date endTime, Integer[] status) {
        StringBuffer sql = new StringBuffer("select * from yw_game where 1=1");
        List<Object> args = new ArrayList<Object>();
        if (game != null) {
            if (StringUtils.isNotBlank(game.getId())) {
                sql.append(" and id = ?");
                args.add(game.getId());
            }
            if (StringUtils.isNotBlank(game.getName())) {
                sql.append(" and name like ?");
                args.add("%" + game.getName() + "%");
            }
            if (StringUtils.isNotBlank(game.getTypeId())) {
                sql.append(" and type_id = ?");
                args.add(game.getTypeId());
            }
            if (game.getStatus() != null) {
                sql.append(" and status = ?");
                args.add(game.getStatus());
            }
            if (game.getUpTime() != null) {
                sql.append(" and up_time <= ?");
                args.add(game.getUpTime());
            }
        }
        if (startTime != null) {
            sql.append(" and create_time >= ?");
            args.add(startTime);
        }
        if (endTime != null) {
            sql.append(" and create_time <= ?");
            args.add(endTime);
        }

        if (ids != null) {
            sql.append(" and id in").append(SQLUtils.toSQLInString(ids));
        }
        if (status != null) {
            sql.append(" and status in").append(SQLUtils.toSQLInString(status));
        }

        sql.append(" order by order_id, create_time desc");
        if (page == null) {
            return find(sql.toString(), args.toArray(), new MultiRow<YwGame>());
        } else {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<YwGame>(), page);
        }
    }

    @Override
    public List<YwGame> getYwGameIndexPage(YwGame game, String[] ids, PageDto page, Date startTime, Date endTime, Integer[] status, boolean b) {
        StringBuffer sql = new StringBuffer("select g.* from yw_game g left join yw_user_room r on g.id = r.game_id where 1=1 ");
        if (b) {
            sql.append(" and exists (select id from yw_user_room where game_id=g.id and online = '1' ) ");
        }
        List<Object> args = new ArrayList<Object>();
        if (game != null) {
            if (game.getId() != null) {
                sql.append(" and g.id = ?");
                args.add(game.getId());
            }
            if (StringUtils.isNotBlank(game.getName())) {
                sql.append(" and g.name like ?");
                args.add("%" + game.getName() + "%");
            }
            if (game.getTypeId() != null) {
                sql.append(" and g.type_id = ?");
                args.add(game.getTypeId());
            }
            if (game.getStatus() != null) {
                sql.append(" and g.status = ?");
                args.add(game.getStatus());
            }
            if (game.getUpTime() != null) {
                sql.append(" and g.up_time <= ?");
                args.add(game.getUpTime());
            }
        }
        if (startTime != null) {
            sql.append(" and g.create_time >= ?");
            args.add(startTime);
        }
        if (endTime != null) {
            sql.append(" and g.create_time <= ?");
            args.add(endTime);
        }

        if (ids != null) {
            sql.append(" and g.id in").append(SQLUtils.toSQLInString(ids));
        }
        if (status != null) {
            sql.append(" and g.status in").append(SQLUtils.toSQLInString(status));
        }

        sql.append(" group by g.id order by g.order_id, sum(case when r.online='1' then 1 else 0 end) desc, g.create_time desc");
        if (page == null) {
            return find(sql.toString(), args.toArray(), new MultiRow<YwGame>());
        } else {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<YwGame>(), page);
        }
    }


    @Override
    public List<YwGame> getYwGamePages(YwGame game, String[] ids, PageDto page, Date startTime, Date endTime, Integer[] status) {
        StringBuffer sql = new StringBuffer("select * from yw_game where 1=1 and exists (select id from yw_user_room where game_id=yw_game.id and online=1 )");
        List<Object> args = new ArrayList<Object>();
        if (game != null) {
            if (StringUtils.isNotBlank(game.getId())) {
                sql.append(" and id = ?");
                args.add(game.getId());
            }
            if (StringUtils.isNotBlank(game.getName())) {
                sql.append(" and name like ?");
                args.add("%" + game.getName() + "%");
            }
            if (StringUtils.isNotBlank(game.getTypeId())) {
                sql.append(" and type_id = ?");
                args.add(game.getTypeId());
            }
            if (game.getStatus() != null) {
                sql.append(" and status = ?");
                args.add(game.getStatus());
            }
            if (game.getUpTime() != null) {
                sql.append(" and up_time <= ?");
                args.add(game.getUpTime());
            }
        }
        if (startTime != null) {
            sql.append(" and create_time >= ?");
            args.add(startTime);
        }
        if (endTime != null) {
            sql.append(" and create_time <= ?");
            args.add(endTime);
        }

        if (ids != null) {
            sql.append(" and id in").append(SQLUtils.toSQLInString(ids));
        }
        if (status != null) {
            sql.append(" and status in").append(SQLUtils.toSQLInString(status));
        }

        sql.append(" order by order_id, create_time desc");
        if (page == null) {
            return find(sql.toString(), args.toArray(), new MultiRow<YwGame>());
        } else {
            return findForPage(sql.toString(), args.toArray(), new MultiRow<YwGame>(), page);
        }
    }

    @Override
    public Integer updateStatus(String[] ids, Integer status) {
        String sql = "update yw_game set status = ? where id in";
        return executeUpdateForInSQL(sql, new Integer[]{status}, ids);
    }

    @Override
    public Integer update(YwGame entity) {
        StringBuffer sql = new StringBuffer("update yw_game set name = ?, developer = ?, type_id = ?, brief_intro = ?, status = ?, order_id = ?, up_time = ?, android_url = ?, ios_url = ? ,seo=?,mobile_banner=?,resource=?");
        List<Object> args = new ArrayList<Object>();
        args.add(entity.getName());
        args.add(entity.getDeveloper());
        args.add(entity.getTypeId());
        args.add(entity.getBriefIntro());
        args.add(entity.getStatus());
        args.add(entity.getOrderId());
        args.add(entity.getUpTime());
        args.add(entity.getAndroidUrl());
        args.add(entity.getIosUrl());
        args.add(entity.getSeo());
        args.add(entity.getMobileBanner());
        args.add(entity.getResource());
        if (entity.getStatus() != null) {
            sql.append(", status = ?");
            args.add(entity.getStatus());
        }
        if (StringUtils.isNotBlank(entity.getIcon())) {
            sql.append(", icon = ?");
            args.add(entity.getIcon());
        }
        if (StringUtils.isNotBlank(entity.getScreen())) {
            sql.append(", screen = ?");
            args.add(entity.getScreen());
        }
        if (StringUtils.isNotBlank(entity.getAdvert())) {
            sql.append(", advert = ?");
            args.add(entity.getAdvert());
        }
        if (StringUtils.isNotBlank(entity.getQrcode())) {
            sql.append(", qrcode = ?");
            args.add(entity.getQrcode());
        }
        if (StringUtils.isNotBlank(entity.getBackground())) {
            sql.append(", background = ?");
            args.add(entity.getBackground());
        }
        if (StringUtils.isNotBlank(entity.getAdvertSmall())) {
            sql.append(", advert_small = ?");
            args.add(entity.getAdvertSmall());
        }

        sql.append(" where id = ?");
        args.add(entity.getId());

        return update(sql.toString(), args.toArray());
    }

    @Override
    public Integer getYwGameCount() {
        return queryForInt("select count(distinct g.id)  from yw_game g ,yw_user_room r  where g.status = 1 and r.online = 1 and g.id = r.game_id");
    }

    @Override
    public List<Map<String, Object>> getYwGameListMap(YwGame ywGame) {
        StringBuilder sql = new StringBuilder("select id as gameId, name as gameName from yw_game where 1 = 1 ");
        List<Object> args = new ArrayList<Object>();
        if (ywGame != null) {
            if (ywGame.getStatus() != null) {
                sql.append(" and status = ?");
                args.add(ywGame.getStatus());
            }
        }
        sql.append(" order by order_id, create_time desc");
        return queryForList(sql.toString(), args.toArray());
    }

    @Override
    public List<YwGame> getYwGameList(YwGame ywGame, int limit) {
        StringBuilder sql = new StringBuilder("select * from yw_game where 1 = 1 ");
        List<Object> args = new ArrayList<Object>();
        if (ywGame != null) {
            if (ywGame.getStatus() != null) {
                sql.append(" and status = ?");
                args.add(ywGame.getStatus());
            }
            if (ywGame.getTypeId() != null) {
                sql.append(" and type_id = ?");
                args.add(ywGame.getTypeId());
            }
        }
        sql.append(" order by order_id");
        if (limit <= 0) {
            return find(sql.toString(), args.toArray(), new MultiRow<YwGame>());
        } else {
            PageDto page = new PageDto();
            page.setRowNum(limit);
            return findForPage(sql.toString(), args.toArray(), new MultiRow<YwGame>(), page);
        }


    }
}
